package com.gorik.news.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gorik.news.database.dao.ArticlesDao
import com.gorik.news.database.models.ArticleDBO
import com.gorik.news.database.utils.Converters



class NewsDatabase internal  constructor(val originalDb:NewsDatabaseRoom){
    val articlesDao:ArticlesDao
        get() = originalDb.articleDao()
}
@Database(entities = [ArticleDBO::class], version = 1)
@TypeConverters(Converters::class)
abstract class  NewsDatabaseRoom :RoomDatabase(){


    abstract fun articleDao():ArticlesDao

}


fun NewNewsDatabase(ctx: Context):NewsDatabase{

    val db = Room.databaseBuilder(ctx,NewsDatabaseRoom::class.java,"news").build()
    return NewsDatabase(db)



}