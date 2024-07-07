package com.gorik.news.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorik.news.data.RequestResult
import com.gorik.news.main.models.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class NewsMainViewModel(
    val getAllArticleUseCase: GetAllArticleUseCase
) : ViewModel() {

    val state:StateFlow<State> = getAllArticleUseCase()
        .map { it.toState()}
        .stateIn(viewModelScope, SharingStarted.Lazily,State.None)

}



private fun RequestResult<List<com.gorik.news.data.models.Article>>.toState(): State {
    return when (this) {
        is RequestResult.Success -> {
            State.Success(articles = data)
        }

        is RequestResult.InProgress -> {
            State.Loading(articles = data)
        }

        is RequestResult.Error -> {
            State.Error()
        }
    }
}

sealed class State {


    data object None : State()
    class Loading(val articles: List<Article>? = null) : State()
    class Error(val articles: List<Article>? = null) : State()
    class Success(val articles: List<Article>) : State()
}


