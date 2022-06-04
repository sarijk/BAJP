package com.dicoding.githubuserproject.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubuserproject.data.local.FavouriteUser
import com.dicoding.githubuserproject.databinding.ItemFavouriteBinding
import com.dicoding.githubuserproject.activity.MoveDetailActivity

class FavouriteAdapter: RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {
    private var listFavourite = ArrayList<FavouriteUser>()

    fun updatedData(items: ArrayList<FavouriteUser>) {
        this.listFavourite = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val binding = ItemFavouriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bind(listFavourite[position])
    }

    override fun getItemCount(): Int {
        return listFavourite.size
    }

    inner class FavouriteViewHolder(private val binding: ItemFavouriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favouriteUser: FavouriteUser) {
            Glide.with(itemView.context)
                .load(favouriteUser.avatarURL)
                .apply(RequestOptions().override(550, 550))
                .into(binding.itemAvatar)

            binding.itemUsername.text = favouriteUser.login

            itemView.setOnClickListener {
                val context = itemView.context
                val detailActivity = Intent(context, MoveDetailActivity::class.java)
                detailActivity.putExtra(MoveDetailActivity.EXTRA_AVATAR, favouriteUser.avatarURL)
                detailActivity.putExtra(MoveDetailActivity.EXTRA_USERNAME, favouriteUser.login)
                detailActivity.putExtra(MoveDetailActivity.EXTRA_ID, favouriteUser.id)
                context.startActivity(detailActivity)
            }
        }
    }
}