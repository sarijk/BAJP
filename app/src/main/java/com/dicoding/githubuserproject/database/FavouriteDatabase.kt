package com.dicoding.githubuserproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.githubuserproject.data.local.FavouriteUser

@Database(entities = [FavouriteUser::class], version = 1)
abstract class FavouriteDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: FavouriteDatabase? = null

        fun getDatabase(context: Context): FavouriteDatabase? {
            if (INSTANCE == null) {
                synchronized(FavouriteDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    FavouriteDatabase::class.java,
                    "favourite_database").build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun favouriteDao(): FavouriteDao
}