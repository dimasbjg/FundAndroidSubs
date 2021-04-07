package com.example.fundandroidsubs.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.fundandroidsubs.db.DatabaseContract.UserColumns.Companion.COLUMN_NAME_AVATAR_URL
//import com.example.fundandroidsubs.db.DatabaseContract.UserColumns.Companion.COLUMN_NAME_COMPANY
//import com.example.fundandroidsubs.db.DatabaseContract.UserColumns.Companion.COLUMN_NAME_FOLLOWERS
//import com.example.fundandroidsubs.db.DatabaseContract.UserColumns.Companion.COLUMN_NAME_FOLLOWING
//import com.example.fundandroidsubs.db.DatabaseContract.UserColumns.Companion.COLUMN_NAME_LOCATION
//import com.example.fundandroidsubs.db.DatabaseContract.UserColumns.Companion.COLUMN_NAME_NAME
//import com.example.fundandroidsubs.db.DatabaseContract.UserColumns.Companion.COLUMN_NAME_REPOSITORIES
import com.example.fundandroidsubs.db.DatabaseContract.UserColumns.Companion.COLUMN_NAME_USERNAME
import com.example.fundandroidsubs.db.DatabaseContract.UserColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "favoritesuserdb"

        private const val DATABASE_VERSION = 1

//        private const val SQL_CREATE_TABLE_FAVORITES = "CREATE TABLE $TABLE_NAME" +
//                "($COLUMN_NAME_USERNAME TEXT NOT NULL PRIMARY KEY," +
//                "$COLUMN_NAME_NAME TEXT NOT NULL," +
//                "$COLUMN_NAME_AVATAR_URL TEXT NOT NULL," +
//                "$COLUMN_NAME_COMPANY TEXT," +
//                "$COLUMN_NAME_LOCATION TEXT," +
//                "$COLUMN_NAME_REPOSITORIES INTEGER NOT NULL," +
//                "$COLUMN_NAME_FOLLOWERS INTEGER NOT NULL," +
//                "$COLUMN_NAME_FOLLOWING INTEGER NOT NULL)"

        private const val SQL_CREATE_TABLE_FAVORITES = "CREATE TABLE $TABLE_NAME" +
                "($COLUMN_NAME_USERNAME TEXT NOT NULL PRIMARY KEY," +
                "$COLUMN_NAME_AVATAR_URL TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}