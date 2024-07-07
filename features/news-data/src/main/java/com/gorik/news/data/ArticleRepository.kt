package com.gorik.news.data

import com.google.protobuf.Internal.ListAdapter
import com.gorik.news.data.models.Article
import com.gorik.news.database.NewsDatabase
import com.gorik.news.database.models.ArticleDBO
import com.gorik.newsapi.NewsApi
import com.gorik.newsapi.models.ArticleDTO
import com.gorik.newsapi.models.ResponseDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class ArticleRepository(
    val api: NewsApi,
    val db: NewsDatabase
) {


    fun getAll(

        mergeStrategy: MergeStrategy<RequestResult<List<Article>>> = RequestResponseMergeStrategy()

    ): Flow<RequestResult<List<Article>>> {
        val cachedArticles = getAllFromDB()
        val remoteArtilces = getAllFromServer()
        return cachedArticles.combine(cachedArticles, mergeStrategy::merge)
            .flatMapLatest { result ->
                if (result is RequestResult.Success) {
                    db.articlesDao.observerAll().map { articleDboList ->
                        articleDboList.map { articleDbo -> articleDbo.toArticle() }
                    }.map { RequestResult.Success(it) }
                } else {
                    flowOf(result)
                }

            }

    }


    //    ::: GET api articles flow, if it's result is SUCCESS ->   cache it in DB
// ::: FIRST we have to get qpi-request, then merge it with "inprogress " result and map from articlesDTO to clean article
    private fun getAllFromServer(): Flow<RequestResult<List<Article>>> {
        val apiRequest = flow { emit(api.everything()) }
            .onEach { result -> if (result.isSuccess) makeArticlesInCache(result.getOrThrow().articles) }
            .map { resultArticlesDTO -> resultArticlesDTO.toRequestResult() }

        val dummyResult = flowOf<RequestResult<ResponseDTO<ArticleDTO>>>(RequestResult.InProgress())
        return merge(
            dummyResult,
            apiRequest
        ).map { responseResult: RequestResult<ResponseDTO<ArticleDTO>> ->
            responseResult.map { responseDTO ->
                responseDTO.articles.map { it.toArticle() }
            }

        }
    }

    private suspend fun makeArticlesInCache(articles: List<ArticleDTO>) {

        val articlesToBeCached = articles.map { it.toArticleDBO() }
        db.articlesDao.insert(articlesToBeCached)

    }

    private fun getAllFromDB(): Flow<RequestResult<List<Article>>> {

        val articlesFromDB = db.articlesDao::getAll.asFlow()
            .map { articles -> RequestResult.Success(articles) }
            .catch { error -> RequestResult.Error<List<ArticleDBO>>(error = error) }


        val dummyResult = flowOf<RequestResult<List<ArticleDBO>>>(RequestResult.InProgress())

        return merge(
            dummyResult,
            articlesFromDB
        ).map { reqresult: RequestResult<List<ArticleDBO>> ->
            reqresult.map { response ->
                response.map { it.toArticle() }
            }
        }


    }


}


fun main() {
    runBlocking {

        val job = launch { }
        val state = "a b c d".split(" ").asFlow()


            .onEach { stringa ->
                delay(1000)
                println("...emitting...")

            }
            .stateIn(CoroutineScope(job))
            .onCompletion { println("---END---") }
        val sub1 = launch {

            state.collect() {
                delay(2000)
                println("Gotta from state -> $it")
            }

        }
        val sub2 = launch {

            state.collect() {
                println("Gotta from state(2) -> $it")
            }

        }
        delay(1000)
//        job.cancel()

    }
}



