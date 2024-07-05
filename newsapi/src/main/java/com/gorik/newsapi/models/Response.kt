package com.gorik.newsapi.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *{
 * "status": "ok",
 * "totalResults": 6969,
 * -"articles": [..]
*/

@Serializable
data class Response<E> (
    @SerialName("status") val status:String,
    @SerialName("totalResults") val totalResults:Int,
    @SerialName("articles") val articles:List<E>,
)