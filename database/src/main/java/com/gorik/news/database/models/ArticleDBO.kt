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
 * "description": "The rehabilitat
 * "url": "https://readwrite.com/mt-gox-begins-creditor-repayments-in-bitcoin-and-bitcoin-cash/",
 * "urlToImage": "https://readwrite.com/wp-content/uploads/2024/07/f7241310-8110-4543-8b45-18d74cf08d80.webp",
 * "publishedAt": "2024-07-05T11:01:57Z",
 * },
* */

@Entity(tableName = "articles")
data class ArticleDBO (
    @PrimaryKey(autoGenerate = true) val id:Long=0,
     @Embedded(prefix = "source") val source:SourceDBO,
    @ColumnInfo("author") val author:String?,
    @ColumnInfo("title") val title:String?,
    @ColumnInfo("description") val description:String?,
    @ColumnInfo("url") val url:String?,
    @ColumnInfo("urlToImage") val urlToImage:String?,
    @ColumnInfo("publishedAt") val publishedAt: Date,
    @ColumnInfo("content") val content:String?,

    ){
}
data class SourceDBO (
     val id :String,
     val name :String,
)
