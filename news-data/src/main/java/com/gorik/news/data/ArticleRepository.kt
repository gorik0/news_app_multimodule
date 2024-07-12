package com.gorik.news.data

import com.google.protobuf.Internal.ListAdapter
import com.gorik.news.data.models.Article
import com.gorik.news.database.NewsDatabase
import com.gorik.news.database.models.ArticleDBO
import com.gorik.news_common.Logger
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
import okhttp3.internal.cacheGet
import retrofit2.http.Query
import javax.inject.Inject


class ArticleRepository @Inject constructor(
    val api: NewsApi,
    val db: NewsDatabase,
    val logger: Logger
) {


    fun getAll(
query: String,
        mergeStrategy: MergeStrategy<RequestResult<List<Article>>> = RequestResponseMergeStrategy()

    ): Flow<RequestResult<List<Article>>> {

        val cachedArticles = getAllFromDB()
        val remoteArtilces = getAllFromServer(query)
        return cachedArticles.combine(remoteArtilces, mergeStrategy::merge)
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
    private fun getAllFromServer(query:String): Flow<RequestResult<List<Article>>> {
        val apiRequest = flow { emit(api.everything(query)) }
            .onEach { result -> if (result.isSuccess) makeArticlesInCache(result.getOrThrow().articles) }
            .onEach {  result-> if (result.isFailure){
                logger.e(LOG_TAG,"Error geteting dat from server .Cause  =${result.exceptionOrNull()}")
            } }
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
println("RIOGORIO :::: "+Thread.currentThread().name)
        val articlesFromDB = db.articlesDao::getAll.asFlow()
            .map<List<ArticleDBO>,RequestResult<List<ArticleDBO>>>{RequestResult.Success(it)  }
            .catch {

                logger.e(LOG_TAG,"Errror getting from db. Cause ${it}")
                emit(RequestResult.Error(error = it))
            }


        val dummyResult = flowOf<RequestResult<List<ArticleDBO>>>(RequestResult.InProgress())

        return merge(
            dummyResult,
            articlesFromDB
        ).map { reqresult ->
            reqresult.map { response ->
                response.map { it.toArticle() }
            }
        }


    }

    private companion object{
        const val LOG_TAG = "Article Repo"
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



