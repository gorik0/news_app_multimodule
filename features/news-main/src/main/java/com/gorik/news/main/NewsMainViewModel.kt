package com.gorik.news.main

import androidx.lifecycle.ViewModel
import com.gorik.news.main.models.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal  class NewsMainViewModel: ViewModel(){

    val _articles = MutableStateFlow(State.None)
    val articles : StateFlow<State>
        get() = _articles.asStateFlow()

sealed class State{


    object None:State()
    class Loading:State()
    class Error:State()
    class Success(val articles:List<Article>):State()
}
}