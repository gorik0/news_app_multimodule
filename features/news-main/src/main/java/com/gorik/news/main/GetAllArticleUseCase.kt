package com.gorik.news.main

import com.gorik.news.data.ArticleRepository
import com.gorik.news.data.RequestResult
import com.gorik.news.data.map
import com.gorik.news.main.models.ArticleUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.gorik.news.data.models.Article as DataArticle


internal class GetAllArticleUseCase @Inject constructor(
    val articlesRepository:ArticleRepository
) {

 operator fun invoke(query:String): Flow<RequestResult<List<ArticleUi>>> {
//         :: map articles data to UI article
     return articlesRepository.getAll(query)
         .map { reqResult-> reqResult.map { listOfArticle->listOfArticle.map { it.toUIarticle() } }}


 }
    private fun DataArticle.toUIarticle():ArticleUi{
        TODO()
    }
}

