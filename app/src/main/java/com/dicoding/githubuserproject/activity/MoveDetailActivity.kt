package com.dicoding.githubuserproject.activity

import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubuserproject.R
import com.dicoding.githubuserproject.adapter.SectionsPagerAdapter
import com.dicoding.githubuserproject.databinding.ActivityMoveDetailBinding
import com.dicoding.githubuserproject.model.MoveDetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MoveDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoveDetailBinding
    private lateinit var moveDetailModel: MoveDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoveDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBack.setOnClickListener {
            val back = Intent(this, MainActivity::class.java)
            startActivity(back)
        }

        val avatarURL = intent.getStringExtra(EXTRA_AVATAR)
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        moveDetailModel = ViewModelProvider(this).get(MoveDetailViewModel::class.java)
        moveDetailModel.setDetail(username.toString())
        moveDetailModel.getDetail().observe(this, {
            if (it != null) {
                binding.apply {
                usernameToolbar.text = it.login
                    Glide.with(this@MoveDetailActivity)
                    .load(it.avatarURL)
                    .apply(RequestOptions())
                    .into(avatarDetail)
                nameDetail.text = it.name
                locationDetail.text = it.location
                repositoryDetail.text = "${it.publicRepos}"
                companyDetail.text = it.company
                followersDetail.text = "${it.followers}"
                followingDetail.text = "${it.following}"
                }
            }
        })

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = moveDetailModel.checkFavourite(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count>0) {
                        binding.toggleButton.isChecked = true
                        isChecked = true
                    } else {
                        binding.toggleButton.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.toggleButton.setOnClickListener{
            isChecked = !isChecked
            if (isChecked) {
                moveDetailModel.addToFavourite(avatarURL, username, id)
            } else {
                moveDetailModel.removeFavourite(id)
            }

            binding.toggleButton.isChecked = isChecked
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
        tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        supportActionBar?.title = username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    companion object {
        const val EXTRA_AVATAR = "extra_avatar"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

}