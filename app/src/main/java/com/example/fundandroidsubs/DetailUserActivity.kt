package com.example.fundandroidsubs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.example.fundandroidsubs.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailUserBinding

    private lateinit var shareContent: String

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

        val sectionPagerAdapter = SectionPagerAdapter(this)
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLE[position])
        }.attach()

        supportActionBar?.elevation = 0f

//        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
//        supportActionBar?.title = user.name
//        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
//        binding.username.text = user.username
//        Glide.with(this)
//            .load(user.avatar)
//            .into(binding.avaUser)


    }

    override fun onClick(v: View) {

    }
}