package com.gorik.news.data

import com.gorik.news.data.models.Article
import com.gorik.news.data.models.Source
import com.gorik.news.database.models.ArticleDBO
import com.gorik.news.database.models.SourceDBO
import com.gorik.newsapi.models.ArticleDTO
import com.gorik.newsapi.models.SortBy
import com.gorik.newsapi.models.SourceDTO

internal fun ArticleDBO.toArticle(): Article {
    return Article(
        source = this.source.toSource(),
                author = this.author,
                title = this.title,
                description = this.description,
                url = this.url,
                urlToImage = this.urlToImage,
                publishedAt = this.publishedAt,
                content = this.content,
    )
}

internal fun ArticleDTO.toArticle(): Article {
    return Article(
        source = this.source.toSource(),
        author = this.author,
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content,
    )

}
internal fun ArticleDTO.toArticleDBO() :ArticleDBO{
    return ArticleDBO(
        source = this.source.toSourceDBO(),
        author = this.author,
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content,
    )

}

internal  fun SourceDBO.toSource():Source{
    return Source(id = this.id,name = this.name)
}
internal  fun SourceDTO.toSource():Source{
    return Source(id = this.id?:name,name = this.name)
}
internal  fun SourceDTO.toSourceDBO():SourceDBO{
    return SourceDBO(id = this.id?:name,name = this.name)
}
