package com.gorik.news.main

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.provider.FontsContractCompat.Columns
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.gorik.news.main.models.ArticleUi


@Composable
fun NewsMain() {
    NewsMain(vm = viewModel())

}


@Composable
internal fun NewsMain(vm: NewsMainViewModel) {

    val state by vm.state.collectAsState()

    val currentState = state

    if (state != State.None) {
        MainNewsContent(currentState)
    }
}

@Composable
private fun MainNewsContent(currentState: State) {

    Column {
        if (currentState is State.Error) {
            ErrorMessage(currentState)
        }
        if (currentState is State.Loading) {
            ProgressIndicator(currentState)
        }
        if (currentState.articles != null) {
            Articles(articles = currentState.articles)
        }
    }

}

@Composable
private fun ProgressIndicator(currentState: State.Loading) {


    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        CircularProgressIndicator()
    }

}

@Composable
private fun ErrorMessage(currentState: State.Error) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.error),

        ) {
        Text(text = "Error while update")
    }
}

@Composable
private fun Articles(articles: List<ArticleUi>) {
    LazyColumn {
        items(articles) { article ->
            key(article.id) {
                Article(article)
            }
        }
    }
}

@Composable
private fun Article(article: ArticleUi) {

    Row(Modifier.padding(bottom = 4.dp)) {
        article.imageUrl?.let { imageUrl ->
            var isImageVisible by remember { mutableStateOf(true) }
            if (isImageVisible) {
                AsyncImage(
                    model = imageUrl,
                    onState = { state ->
                        if (state is AsyncImagePainter.State.Error) {
                            isImageVisible = false
                        }
                    },
                    contentDescription = stringResource(R.string.image_descipption),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(150.dp)
                )
            }
        }
        Spacer(modifier = Modifier.size(4.dp))
        Column(Modifier.padding(8.dp)) {
            Text(
                text = article.title ?: "NO_TTITLE",
                style = MaterialTheme.typography.headlineLarge,
                maxLines = 1
            )
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = article.description ?: "NO DESCRIPTION",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3
            )
        }
    }
}
