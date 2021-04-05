package com.example.fundandroidsubs.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.example.fundandroidsubs"
    const val SCHEME = "content"


    class UserColumns : BaseColumns{
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val COLUMN_NAME_USERNAME = "username"
            const val COLUMN_NAME_NAME = "name"
            const val COLUMN_NAME_AVATAR_URL = "avatar_url"
            const val COLUMN_NAME_COMPANY = "company"
            const val COLUMN_NAME_LOCATION = "location"
            const val COLUMN_NAME_REPOSITORIES = "repositories"
            const val COLUMN_NAME_FOLLOWERS = "followers"
            const val COLUMN_NAME_FOLLOWING = "following"


            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}