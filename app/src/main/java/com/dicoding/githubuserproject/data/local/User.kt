package com.dicoding.githubuserproject.data.local

import com.google.gson.annotations.SerializedName

data class User (
    val id: Int,
    @SerializedName("avatar_url")
    val avatarURL: String?,
    val login: String?,
)