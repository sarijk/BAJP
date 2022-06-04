package com.dicoding.githubuserproject.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.githubuserproject.data.local.User
import com.dicoding.githubuserproject.data.local.UserResponse
import com.dicoding.githubuserproject.data.network.Api
import com.dicoding.githubuserproject.data.network.RetrofitClient
import com.dicoding.githubuserproject.database.FavouriteDao
import com.dicoding.githubuserproject.database.FavouriteDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var faveDao: FavouriteDao? = null
    private var faveDb: FavouriteDatabase? = FavouriteDatabase.getDatabase(application)

    init {
        faveDao = faveDb?.favouriteDao()
    }
    var users: MutableLiveData<ArrayList<User>> = MutableLiveData()

    fun setObserver(input: String) {
        val retrofitClient = RetrofitClient.apiInstance().create(Api::class.java)
        val responseApi = retrofitClient.getSearchUsers(input)
        responseApi.enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    users.postValue(response.body()?.items)
                } else {
                    users.postValue(null)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                users.postValue(null)
            }

        })

    }

    fun getObserver(): LiveData<ArrayList<User>> {
        return users
    }
}
