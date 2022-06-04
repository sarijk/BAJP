package com.dicoding.githubuserproject.data.network

import com.dicoding.githubuserproject.BuildConfig
import com.dicoding.githubuserproject.data.local.User
import com.dicoding.githubuserproject.data.local.UserDetail
import com.dicoding.githubuserproject.data.local.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getSearchUsers(
        @Query("q") query: String,
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getDetailUsers(
        @Path("username") username: String,
    ): Call<UserDetail>

    @GET("users/{username}/following")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getFollowingUsers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/followers")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getFollowersUsers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

}