package com.dicoding.consumerapp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val id: Int,
    @SerializedName("avatar_url")
    val avatarURL: String?,
    val login: String?,
): Parcelable