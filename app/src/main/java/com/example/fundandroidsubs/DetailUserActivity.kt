package com.example.fundandroidsubs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.fundandroidsubs.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailUserBinding

    private lateinit var shareContent: String

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        supportActionBar?.title = user.name
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        binding.username.text = user.username
        Glide.with(this)
            .load(user.avatar)
            .into(binding.avaUser)
        binding.name.text = user.name
        binding.location.text = "Location : " + user.location
        binding.company.text = "Company : " + user.company
        binding.repository.text = "Repository : " + user.repository
        binding.following.text = "Following : " + user.following
        binding.follower.text = "Followers : " + user.followers

        binding.btShare.setOnClickListener(this)


        shareContent = "Username github : ${user.username} \nNama : ${user.name}\nRepository : ${user.repository}" +
                "\nCompany : ${user.company}"
    }

    override fun onClick(p0: View?) {
        val intent = Intent()

        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, shareContent)
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, "Bagikan ke : "))
    }
}