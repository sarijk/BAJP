package com.dicoding.githubuserproject.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.githubuserproject.data.local.FavouriteUser
import com.dicoding.githubuserproject.database.FavouriteDao
import com.dicoding.githubuserproject.database.FavouriteDatabase

class FavouriteViewModel(application: Application): AndroidViewModel(application) {
    private var faveDao: FavouriteDao?
    private var faveDb: FavouriteDatabase? = FavouriteDatabase.getDatabase(application)

    init {
        faveDao = faveDb?.favouriteDao()
    }

    fun getFavourite(): LiveData<List<FavouriteUser>>? {
        return faveDao?.getFavourite()
    }
}