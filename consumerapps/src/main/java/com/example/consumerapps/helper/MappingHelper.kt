package com.example.consumerapps.helper

import android.database.Cursor
import com.example.consumerapps.db.DatabaseContract
import com.example.consumerapps.entity.User

object MappingHelper {

    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<User> {
        val userList = ArrayList<User>()

        userCursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.COLUMN_NAME_USERNAME))
                val avatarUrl = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.COLUMN_NAME_AVATAR_URL))
                userList.add(User(username,avatar = avatarUrl))
            }
        }
        return userList
    }
}