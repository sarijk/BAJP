package com.dicoding.consumerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.consumerapp.databinding.ItemCardBinding

class ListAdapter: RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private var listUser = ArrayList<User>()

    fun updatedData(items: ArrayList<User>) {
        this.listUser = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])

    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    inner class ListViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatarURL)
                    .apply(RequestOptions().override(550, 550))
                    .into(itemAvatar)

                itemUsername.text = user.login
            }
        }
    }
}