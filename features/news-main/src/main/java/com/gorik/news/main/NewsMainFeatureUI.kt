package com.gorik.news.main

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.provider.FontsContractCompat.Columns
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gorik.news.main.models.ArticleUi


@Composable
fun NewsMain(){
    NewsMain(vm = viewModel())

}



@Composable
internal fun NewsMain (vm:NewsMainViewModel ){

    val state by vm.state.collectAsState()

    val currentState = state

    if (state!=State.None){
        MainNewsContent(currentState)
    }
}

@Composable
private fun MainNewsContent(currentState: State) {

    Column{
        if (currentState is State.Error){
ErrorMessage(currentState)
        }
        if (currentState is State.Loading){
ProgressIndicator(currentState)
        }
        if (currentState.articles !=null){
Articles(articles = currentState.articles)
        }
    }

}

@Composable
private fun ProgressIndicator(currentState: State.Loading) {
    
    
    Box(contentAlignment = Alignment.Center, modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)){
        CircularProgressIndicator()
    }

}

@Composable
private fun ErrorMessage(currentState: State.Error) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.error),

    ){
        Text(text = "Error while update")
    }
}

@Composable
private fun Articles(articles: List<ArticleUi>) {
    LazyColumn {
        items(articles){
            article->
            key(article.id){
                Article(article)
            }
        }
    }
}

@Composable
private fun Article(article: ArticleUi) {

    Column(Modifier.padding(8.dp)) {
        Text(text = article.title, style = MaterialTheme.typography.headlineLarge, maxLines = 1)
        Spacer(modifier = Modifier.size(20.dp))
        Text(text = article.description, style = MaterialTheme.typography.bodyMedium, maxLines = 3)
    }
}
