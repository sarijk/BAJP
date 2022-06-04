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

class FollowingViewModel : ViewModel() {
    val followingList = MutableLiveData<ArrayList<User>>()

    fun setFollowingList(username: String) {
        val retrofitClient = RetrofitClient.apiInstance().create(Api::class.java)
        val responseApi = retrofitClient.getFollowingUsers(username)
        responseApi.enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        followingList.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })

    }

    fun getFollowingList(): LiveData<ArrayList<User>> {
        return followingList
    }
}