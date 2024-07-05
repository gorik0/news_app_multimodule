package com.gorik.newsapi.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * "id": null,
 * "name": "Gizmodo.com"
 * */
@Serializable
data class Source (
    @SerialName("id") val id :String,
    @SerialName("name") val name :String,
)
