package com.gorik.news.main

import com.gorik.news.data.ArticleRepository
import com.gorik.news.data.models.Article
import kotlinx.coroutines.flow.Flow

class GetAllArticleUseCase(
    val articlesRepository:ArticleRepository
) {

 operator fun invoke(): Flow<List<Article>> {
     return articlesRepository.getAll()
 }
}