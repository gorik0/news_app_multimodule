package com.gorik.news.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.gorik.news.database.models.ArticleDBO
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticlesDao {


    @Query("SELECT * FROM articles")
    fun getAll(): List<ArticleDBO>


    @Delete
    suspend fun delete(articles :List<ArticleDBO>)


    @Insert
    suspend  fun insert(articles :List<ArticleDBO>)


    @Query("DELETE FROM articles")
    suspend  fun clear()

}