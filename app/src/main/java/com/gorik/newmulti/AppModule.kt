package com.gorik.newmulti

import android.content.Context
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.core.content.contentValuesOf
import com.gorik.news.database.NewNewsDatabase
import com.gorik.news.database.NewsDatabase
import com.gorik.newsapi.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton

    fun provideNewsApi(): NewsApi {
        return NewsApi(
            baseUrl =BuildConfig.NEWS_API_BASE_URL ,
            apiKey =BuildConfig.NEWS_API_KEY,
        )
    }


    @Provides
    @Singleton

    fun provideNewsDB(@ApplicationContext ctx:Context):NewsDatabase{
return NewNewsDatabase(ctx)
    }
}