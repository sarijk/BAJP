package com.dicoding.githubuserproject.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserproject.adapter.FavouriteAdapter
import com.dicoding.githubuserproject.data.local.FavouriteUser
import com.dicoding.githubuserproject.databinding.ActivityFavouriteBinding
import com.dicoding.githubuserproject.model.FavouriteViewModel

class FavouriteActivity : AppCompatActivity() {
    private lateinit var adapter: FavouriteAdapter
    private lateinit var faveViewModel: FavouriteViewModel
    private lateinit var binding: ActivityFavouriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBack.setOnClickListener {
            val back = Intent(this, MainActivity::class.java)
            startActivity(back)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FavouriteAdapter()
        binding.recyclerView.adapter = adapter

        faveViewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)

        faveViewModel.getFavourite()?.observe(this, {
            if (it != null) {
                val list = mapList(it)
                adapter.updatedData(list)
            } else {
                Toast.makeText(this, "Error in getting data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun mapList(fave: List<FavouriteUser>): ArrayList<FavouriteUser> {
        val listFave = ArrayList<FavouriteUser>()
        for(favouriteUser in fave) {
            val faveMapped = FavouriteUser(
                favouriteUser.avatarURL,
                favouriteUser.login,
                favouriteUser.id
            )
            listFave.add(faveMapped)
        }
        return listFave
    }
}