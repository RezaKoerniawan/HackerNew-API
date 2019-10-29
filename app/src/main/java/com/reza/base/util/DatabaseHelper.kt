package com.reza.base.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.reza.base.entity.NewsItem
import org.jetbrains.anko.db.*

class DatabaseHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Favorite.db", null, 1) {
    companion object {
        private var instance: DatabaseHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(ctx.applicationContext)
            }
            return instance as DatabaseHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(
            NewsItem.TABLE_NEWS_FAVORITE, true,
            NewsItem.NEWS_ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            NewsItem.NEWS_TITLE to TEXT,
            NewsItem.NEWS_AUTHOR to TEXT,
            NewsItem.NEWS_DATE to INTEGER,
            NewsItem.NEWS_SCORE to INTEGER,
            NewsItem.NEWS_TOTAL_COMMENT to INTEGER
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(NewsItem.TABLE_NEWS_FAVORITE, true)
    }
}

// Access property for Context
val Context.database: DatabaseHelper
    get() = DatabaseHelper.getInstance(applicationContext)