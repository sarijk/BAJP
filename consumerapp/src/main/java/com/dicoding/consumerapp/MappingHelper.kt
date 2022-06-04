package com.dicoding.consumerapp

import android.database.Cursor

object MappingHelper {
    fun mapCursor(cursor: Cursor?): ArrayList<User> {
        val list = ArrayList<User>()
        while (cursor?.moveToNext() == true) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.ID))
            val avatar = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.AVATAR))
            val username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.USERNAME))

            list.add(
                User(
                    id,
                    avatar,
                    username
                )
            )
        }
        return list
    }
}