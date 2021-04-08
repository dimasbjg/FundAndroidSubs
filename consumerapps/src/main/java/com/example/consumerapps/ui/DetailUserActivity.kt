package com.example.consumerapps.ui

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.consumerapps.R
import com.example.consumerapps.adapter.SectionPagerAdapter
import com.example.consumerapps.databinding.ActivityDetailUserBinding
import com.example.consumerapps.db.DatabaseContract
import com.example.consumerapps.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.example.consumerapps.entity.User
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetailUserBinding
    private var isFavorites: Boolean = false
    private lateinit var mainViewModel: MainViewModel
    private lateinit var uriWithUsername: Uri

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLE = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        var user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        user.username.let { mainViewModel.setUserDetail(it) }
        mainViewModel.getDetailUser().observe(this, {
            binding.apply {
                Glide.with(this@DetailUserActivity)
                    .load(it.avatar)
                    .into(binding.avaUser)
                supportActionBar?.title = it.name
                username.text = it.username
                name.text = it.name
                ("Location : " + it.location).also { binding.location.text = it }
                ("company : " + it.company).also { binding.company.text = it }
                ("followers : " + it.followers).also { binding.follower.text = it }
                ("following : " + it.following).also { binding.following.text = it }
            }
        })

        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = user.username
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLE[position])
        }.attach()

        supportActionBar?.elevation = 0f




        //check if favorite

        uriWithUsername = Uri.parse(CONTENT_URI.toString() + "/" + user.username)
        Log.d("Uri", uriWithUsername.toString())
        val cursor: Cursor? = contentResolver.query(uriWithUsername,null,null,null,null)

        if (cursor?.moveToNext()!!) {
            isFavorites = true
            setStatusFavorites(true)
        }



        //set/unset favorites
        binding.fab.setOnClickListener {


            isFavorites = !isFavorites
            //insert query here
            if (isFavorites) {
                //insert favorites
                val values = ContentValues()
                user.username.let { mainViewModel.setUserDetail(it) }
                mainViewModel.getDetailUser().observe(this, {
                    values.put(DatabaseContract.UserColumns.COLUMN_NAME_USERNAME, it.username)
                    values.put(DatabaseContract.UserColumns.COLUMN_NAME_AVATAR_URL, it.avatar)
                })
                contentResolver.insert(CONTENT_URI, values)
                uriWithUsername = Uri.parse(CONTENT_URI.toString() + "/" + user.username)
                Toast.makeText(this, "Add to Favorites", Toast.LENGTH_SHORT).show()
            } else {
                //delete favorites
                contentResolver.delete(uriWithUsername, null, null)
                Toast.makeText(this, "Remove from Favorites", Toast.LENGTH_SHORT).show()
            }

            setStatusFavorites(isFavorites)
        }


    }

    private fun setStatusFavorites(status: Boolean) {
        if (status) {
            //set to favorite icon
            binding.fab.setImageResource(R.drawable.ic_baseline_favorite_black)
        } else {
            //set to unfovarite icon
            binding.fab.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }


    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}