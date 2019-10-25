package com.reza.base.entity

data class NewsItem(
    val id: Int,
    val title: String,
    val by: String,
    val time: Long,
    val score: Int,
    val kids: List<Int>?
) {
    companion object {
        const val TABLE_NEWS_FAVORITE: String = "TABLE_NEWS_FAVORITE"
        const val NEWS_ID: String = "NEWS_ID"
        const val NEWS_TITLE: String = "NEWS_TITLE"
        const val NEWS_AUTHOR: String = "NEWS_AUTHOR"
        const val NEWS_DATE: String = "NEWS_DATE"
        const val NEWS_SCORE: String = "NEWS_SCORE"
        const val NEWS_COMMENT: String = "NEWS_COMMENT"
    }
}