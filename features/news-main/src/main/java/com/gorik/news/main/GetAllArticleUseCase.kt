package com.gorik.news.main

import com.gorik.news.data.ArticleRepository
import com.gorik.news.data.RequestResult
import com.gorik.news.data.map
import com.gorik.news.main.models.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.gorik.news.data.models.Article as DataArticle


class GetAllArticleUseCase(
    val articlesRepository:ArticleRepository
) {

 operator fun invoke(): Flow<RequestResult<List<Article>>> {

//         :: map articles data to UI article
     return articlesRepository.getAll()
         .map { reqResult-> reqResult.map { listOfArticle->listOfArticle.map { it.toUIarticle() } }}


 }
    private fun DataArticle.toUIarticle():Article{
        TODO()
    }
}

