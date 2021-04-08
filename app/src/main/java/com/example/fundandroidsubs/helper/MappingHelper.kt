package com.example.fundandroidsubs.helper

import android.database.Cursor
import com.example.fundandroidsubs.db.DatabaseContract
import com.example.fundandroidsubs.entity.User

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

    fun mapCursorToObject(notesCursor: Cursor?): User {
        var user = User()
        notesCursor?.apply {
            moveToFirst()
            val username = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.COLUMN_NAME_USERNAME))
            val avatarUrl = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.COLUMN_NAME_AVATAR_URL))
            user = User(username, avatar = avatarUrl)
        }
        return user
    }
}