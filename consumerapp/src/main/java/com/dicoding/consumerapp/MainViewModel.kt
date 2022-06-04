package com.dicoding.consumerapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application): AndroidViewModel(application) {
    private var list = MutableLiveData<ArrayList<User>>()

    fun setFavourite(context: Context) {
        val cursor = context.contentResolver.query(
            DatabaseContract.FavouriteColumns.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val listConverted = MappingHelper.mapCursor(cursor)
        list.postValue(listConverted)
    }

    fun getFavourite(): LiveData<ArrayList<User>> {
        return list
    }
}