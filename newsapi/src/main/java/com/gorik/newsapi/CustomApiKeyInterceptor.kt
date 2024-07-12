package com.gorik.newsapi

import okhttp3.Interceptor
import okhttp3.Response

class CustomApiKeyInterceptor(val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .url(
                chain.request().url.newBuilder()
                    .addQueryParameter("apiKey", apiKey)
                    .build()
            )
            .build()
        println("RIOGORIO   :::::  "+chain.request().url)
        return chain.proceed(request)
    }


}
