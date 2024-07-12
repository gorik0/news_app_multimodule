package com.gorik.news.main

import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorik.news.data.RequestResult
import com.gorik.news.main.models.ArticleUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Provider


@HiltViewModel
internal class NewsMainViewModel @Inject constructor(
    getAllArticleUseCase: Provider<GetAllArticleUseCase>
) : ViewModel() {

    val state: StateFlow<State> = getAllArticleUseCase.get().invoke(query = "android")
        .map { it.toState() }
        .stateIn(viewModelScope, SharingStarted.Lazily, State.None)

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


internal sealed class State(val articles: List<ArticleUi>?) {


    data object None : State(articles = null)
    class Loading(articles: List<ArticleUi>? = null) : State(articles)
    class Error(articles: List<ArticleUi>? = null) : State(articles)
    class Success(articles: List<ArticleUi>) : State(articles)
}


fun main() {
    runBlocking {

        val joba = launch(Dispatchers.IO) {

            println("sd")

        }
        println(joba.toString())
        val flou = getFlow()
            .onStart { println("___before ___") }
            .stateIn(CoroutineScope(joba), SharingStarted.Lazily, State.None)

        launch {

            flou.collect() {
                println("GOTTA -->>> " + it)
                println(Thread.currentThread().name)
            }
        }

        delay(3000)
        joba.cancel()
        delay(10000)
    }


}


fun getFlow(): Flow<String> = "egor pety mihal lucius".split(" ").asFlow().transform { value ->
    delay(1000)
    emit(value)
    delay(100)
    emit(value)

}



