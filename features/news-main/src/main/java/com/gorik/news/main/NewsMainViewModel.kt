package com.gorik.news.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorik.news.data.RequestResult
import com.gorik.news.main.models.ArticleUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider


@HiltViewModel
internal class NewsMainViewModel @Inject constructor(
    getAllArticleUseCase: Provider<GetAllArticleUseCase>
) : ViewModel() {

    val state:StateFlow<State> = getAllArticleUseCase.get().invoke(query = "android")
        .map { it.toState()}
        .stateIn(viewModelScope, SharingStarted.Lazily,State.None)

}



private fun RequestResult<List<ArticleUi>>.toState(): State {
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


internal sealed class State(val articles:List<ArticleUi>?) {


    data object None : State(articles = null)
    class Loading( articles: List<ArticleUi>? = null) : State(articles)
    class Error( articles: List<ArticleUi>? = null) : State(articles)
    class Success( articles: List<ArticleUi>) : State(articles)
}





