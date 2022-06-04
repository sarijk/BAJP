package com.dicoding.githubuserproject.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserproject.data.local.User
import com.dicoding.githubuserproject.data.network.Api
import com.dicoding.githubuserproject.data.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    val followersList = MutableLiveData<ArrayList<User>>()

    fun setFollowersList(username: String) {
        val retrofitClient = RetrofitClient.apiInstance().create(Api::class.java)
        val responseApi = retrofitClient.getFollowersUsers(username)
        responseApi.enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        followersList.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })

    }

    fun getFollowersList(): LiveData<ArrayList<User>> {
        return followersList
    }
}