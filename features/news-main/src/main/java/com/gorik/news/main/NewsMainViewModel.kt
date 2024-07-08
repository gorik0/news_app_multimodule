package com.gorik.news.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorik.news.data.RequestResult
import com.gorik.news.main.models.Article
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider


@HiltViewModel
internal class NewsMainViewModel @Inject constructor(
    val getAllArticleUseCase: Provider<GetAllArticleUseCase>
) : ViewModel() {

    val state:StateFlow<State> = getAllArticleUseCase.get().invoke()
        .map { it.toState()}
        .stateIn(viewModelScope, SharingStarted.Lazily,State.None)

}



private fun RequestResult<List<Article>>.toState(): State {
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





