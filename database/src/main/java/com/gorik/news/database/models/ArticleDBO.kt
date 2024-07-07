package com.gorik.news.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
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

@Entity(tableName = "articles")
data class ArticleDBO (
    @PrimaryKey(autoGenerate = true) val id:Long=0,
     @Embedded(prefix = "source.") val source:SourceDBO,
    @ColumnInfo("name") val name:String,
    @ColumnInfo("author") val author:String,
    @ColumnInfo("title") val title:String,
    @ColumnInfo("description") val description:String,
    @ColumnInfo("url") val url:String,
    @ColumnInfo("urlToImage") val urlToImage:String,
    @ColumnInfo("publishedAt") val publishedAt: Date,
    @ColumnInfo("content") val content:String,


    ){
}
data class SourceDBO (
    @ColumnInfo("id") val id :String,
    @ColumnInfo("name") val name :String,
)
