package com.example.fundandroidsubs

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.fundandroidsubs.databinding.ActivityDetailUserBinding
import com.example.fundandroidsubs.db.DatabaseHelper
import com.example.fundandroidsubs.db.UserHelper
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    private var isFavorites: Boolean = false

    private lateinit var userHelper: UserHelper

    private lateinit var mainViewModel: MainViewModel


    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLE = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

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
        val cursor: Cursor = userHelper.queryByUsername(user.username)
        if (cursor.moveToNext()) {
            isFavorites = true
            setStatusFavorites(true)
        }

        //set/unset favorites
        binding.fab.setOnClickListener {
            isFavorites = !isFavorites

            //insert query here



            setStatusFavorites(isFavorites)
        }


    }

    private fun setStatusFavorites(status: Boolean) {
        if (status) {
            //set to favorite icon
        } else {
            //set to unfovarite icon
        }
    }

}