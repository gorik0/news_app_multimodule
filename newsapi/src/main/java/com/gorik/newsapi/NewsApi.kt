package com.gorik.newsapi

import androidx.annotation.IntRange
import com.gorik.newsapi.models.ArticleDTO
import com.gorik.newsapi.models.Language
import com.gorik.newsapi.models.ResponseDTO
import com.gorik.newsapi.models.SortBy
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date


/**
 * [API Docs](https://newsapi.org/docs/get-started)*/

interface NewsApi {

    /**
     * Api Details [here](https://newsapi.org/docs/endpoints/everything#sources)
     * */

    @GET("/everything")
    suspend fun everything(
        @Query("q") q: String? = null,
        @Query("from") from: Date? = null,
        @Query("to") to: Date? = null,
        @Query("sortBy") sortBy: SortBy? = null,
        @Query("language") language: Array<Language>? = null,
        @Query("pageSize") @androidx.annotation.IntRange(from = 0, to = 100) pageSize: Int = 100,
        @Query("page") @IntRange(from = 1) page: Int = 1,


        ): Result<ResponseDTO<ArticleDTO>>
}


fun NewsApi(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
    json: Json = Json,
    apiKey: String,
): NewsApi {


    val retrofit = retrofit(baseUrl, okHttpClient, json,apiKey)
    return retrofit.create()

}

private fun retrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient?,
    json: Json,
    apiKey: String,
): Retrofit {
    val okHttpClientModified = (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
        .addInterceptor(CustomApiKeyInterceptor(apiKey))
        .build()

    val jsonConverterFactory = json.asConverterFactory("application/json".toMediaType())
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .addConverterFactory(jsonConverterFactory)
        .client(okHttpClientModified)
        .run { if (okHttpClient != null) client(okHttpClient) else this }
        .build()
    return retrofit
}










