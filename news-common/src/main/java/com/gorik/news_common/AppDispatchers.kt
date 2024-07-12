package com.gorik.news_common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

open class AppDispatchers (

    val io:CoroutineDispatcher = Dispatchers.IO,
    val main:MainCoroutineDispatcher = Dispatchers.Main,
    val default:CoroutineDispatcher = Dispatchers.Default,
    val uncofined:CoroutineDispatcher = Dispatchers.Unconfined,
){

    companion object :AppDispatchers()


}