package com.example.consumerapps.ui

import android.content.Intent
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapps.R
import com.example.consumerapps.adapter.ListUserAdapter
import com.example.consumerapps.databinding.ActivityFavoritesListBinding
import com.example.consumerapps.db.DatabaseContract
import com.example.consumerapps.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.example.consumerapps.entity.User
import com.example.consumerapps.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoritesListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesListBinding
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var adapter: ListUserAdapter

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "My Favorites User"


        actionBarToggle = ActionBarDrawerToggle(this, binding.drawerLayout, 0, 0)
        binding.drawerLayout.addDrawerListener(actionBarToggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        actionBarToggle.syncState()

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.favorites -> {
                    val intent = Intent(this, FavoritesListActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "My Favorites", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.setting -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        //set recyclerview
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()


        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadNotesAsync()
            }
        }

        contentResolver.registerContentObserver(
            DatabaseContract.UserColumns.CONTENT_URI,
            true,
            myObserver
        )

        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            savedInstanceState.getParcelableArrayList<User>(EXTRA_STATE)?.also { adapter.mData = it }
        }

        binding.rvFavorites.layoutManager = LinearLayoutManager(this)
        binding.rvFavorites.adapter = adapter

    }

    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressBar.visibility = View.VISIBLE
            val defferedUsers = async(Dispatchers.IO) {

                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val users = defferedUsers.await()
            binding.progressBar.visibility = View.INVISIBLE
            if (users.size > 0) {
                adapter.setData(users)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        binding.drawerLayout.openDrawer(binding.navView)
        return true
    }

    override fun onBackPressed() {
        when {
            this.binding.drawerLayout.isDrawerOpen(GravityCompat.START) -> {
                this.binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            else -> {
                finish()
            }
        }
    }

}