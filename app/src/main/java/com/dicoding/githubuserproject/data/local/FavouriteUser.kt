package com.dicoding.githubuserproject.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favourite_user")
data class FavouriteUser(
    @SerializedName("avatar_url")
    val avatarURL: String?,
    val login: String?,
    @PrimaryKey
    val id: Int?,
)