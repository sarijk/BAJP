package com.dicoding.githubuserproject.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dicoding.githubuserproject.data.local.FavouriteUser

@Dao
interface FavouriteDao {
    @Insert
    suspend fun addFavourite(favouriteUser: FavouriteUser)

    @Query("SELECT * FROM favourite_user")
    fun getFavourite(): LiveData<List<FavouriteUser>>

    @Query("SELECT count(*) FROM favourite_user WHERE favourite_user.id = :id")
    suspend fun checkFavourite(id: Int): Int

    @Query("DELETE FROM favourite_user WHERE favourite_user.id = :id")
    suspend fun removeFavourite(id: Int): Int

    @Query("SELECT * FROM favourite_user")
    fun dataProvider(): Cursor
}