package com.dicoding.githubuserproject.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dicoding.githubuserproject.database.FavouriteDao
import com.dicoding.githubuserproject.database.FavouriteDatabase

class FavouriteContentProvider : ContentProvider() {
    companion object {
        private const val AUTHORITY = "com.dicoding.githubuserproject"
        private const val TABLE_NAME = "favourite_user"
        const val FAVOURITE_ID = 1
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVOURITE_ID)
        }
    }

    private var userDao: FavouriteDao? = null

    override fun onCreate(): Boolean {
        userDao = context.let { ctx ->
            ctx?.let { FavouriteDatabase.getDatabase(it)?.favouriteDao() }
        }
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when(uriMatcher.match(uri)) {
            FAVOURITE_ID -> {
                cursor = userDao?.dataProvider()
                if (context != null) {
                    cursor?.setNotificationUri(context?.contentResolver, uri)
                }
            } else -> {
                cursor = null
            }
        }
        return cursor
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}