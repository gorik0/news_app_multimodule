package com.gorik.newsapi.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * "id": null,
 * "name": "Gizmodo.com"
 * */
@Serializable
data class SourceDTO (
    @SerialName("id") val id :String,
    @SerialName("name") val name :String,
)
