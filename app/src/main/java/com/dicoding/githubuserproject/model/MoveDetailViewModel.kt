package com.dicoding.githubuserproject.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.dicoding.githubuserproject.data.local.FavouriteUser
import com.dicoding.githubuserproject.data.local.UserDetail
import com.dicoding.githubuserproject.data.network.Api
import com.dicoding.githubuserproject.data.network.RetrofitClient
import com.dicoding.githubuserproject.database.FavouriteDao
import com.dicoding.githubuserproject.database.FavouriteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoveDetailViewModel(application: Application): AndroidViewModel(application) {
    var userDetail = MutableLiveData<UserDetail>()

    private var faveDao: FavouriteDao?
    private var faveDb: FavouriteDatabase? = FavouriteDatabase.getDatabase(application)

    init {
        faveDao = faveDb?.favouriteDao()
    }

    fun setDetail(username: String) {
        val retrofitClient = RetrofitClient.apiInstance().create(Api::class.java)
        val responseApi = retrofitClient.getDetailUsers(username)
        responseApi.enqueue(object : Callback<UserDetail> {
                override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                    if (response.isSuccessful) {
                        userDetail.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getDetail() : MutableLiveData<UserDetail> {
        return userDetail
    }

    fun addToFavourite(avatarURL: String?, username: String?, id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val fave = FavouriteUser(
                avatarURL,
                username,
                id
            )
            faveDao?.addFavourite(fave)
        }
    }

    suspend fun checkFavourite(id: Int) = faveDao?.checkFavourite(id)

    fun removeFavourite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            faveDao?.removeFavourite(id)
        }
    }
}