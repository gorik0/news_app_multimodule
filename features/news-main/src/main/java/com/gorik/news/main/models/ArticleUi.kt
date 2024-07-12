package com.gorik.news.main.models

internal data class ArticleUi(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val url: String,
) {

}
