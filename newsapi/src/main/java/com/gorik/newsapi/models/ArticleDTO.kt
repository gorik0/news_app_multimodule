package com.gorik.newsapi.models

import com.gorik.newsapi.utils.DateSerializerCustom
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

/**
*
 * -"sourc`e": {
 * "id": null,
 * "name": "ReadWrite"
 * },
 * "author": "Radek Zielinski",
 * "title": "Mt Gox Begins Creditor Repayments in Bitcoin and Bitcoin Cash",
 * "description": "The rehabilitation trustee of Mt. Gox, the defunct cryptocurrency exchange, announced on Friday that it has initiated repayments to some… Continue reading Mt Gox Begins Creditor Repayments in Bitcoin and Bitcoin Cash\nThe post Mt Gox Begins Creditor Repayments…",
 * "url": "https://readwrite.com/mt-gox-begins-creditor-repayments-in-bitcoin-and-bitcoin-cash/",
 * "urlToImage": "https://readwrite.com/wp-content/uploads/2024/07/f7241310-8110-4543-8b45-18d74cf08d80.webp",
 * "publishedAt": "2024-07-05T11:01:57Z",
 * "content": "The rehabilitation trustee of Mt. Gox, the defunct cryptocurrency exchange, announced on Friday that it has initiated repayments to some creditors in Bitcoin (BTC) and Bitcoin Cash (BCH). These repay… [+2235 chars]"
 * },
* */

@Serializable
data class ArticleDTO (


    @SerialName("source") val source:SourceDTO,
    @SerialName("author") val author:String?,
    @SerialName("title") val title:String?,
    @SerialName("description") val description:String?,
    @SerialName("url") val url:String?,
    @SerialName("urlToImage") val urlToImage:String??,
    @SerialName("publishedAt") @Serializable(with = DateSerializerCustom::class) val publishedAt: Date,
    @SerialName("content") val content:String?,


    ){
}