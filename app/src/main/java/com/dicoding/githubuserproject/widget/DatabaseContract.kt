package com.dicoding.githubuserproject.widget

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.dicoding.githubuserproject"
    const val SCHEME = "content"

    class FavouriteColumns: BaseColumns {
        companion object {
            private const val TABLE_NAME = "favourite_user"
            const val ID = "id"
            const val AVATAR = "avatarURL"
            const val USERNAME = "login"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}