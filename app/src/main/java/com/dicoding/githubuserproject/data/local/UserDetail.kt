package com.dicoding.githubuserproject.data.local

import com.google.gson.annotations.SerializedName

data class UserDetail(
    @SerializedName("avatar_url")
    val avatarURL: String?,
    val name: String,
    val login: String,
    val location: String,
    val company: String,
    val followers: Int,
    val following: Int,
    @SerializedName("public_repos")
    val publicRepos: Int?,
)
