package com.gorik.news_common

import android.util.Log

interface Logger {

    fun d (tag:String,mes:String)
    fun e (tag:String,mes:String)
}

fun AndroidLogcatLogger():Logger{
    return object : Logger{
        override fun d(tag: String, mes: String) {
            Log.d(tag, mes)
        }

        override fun e(tag: String, mes: String) {
            Log.e(tag, mes)

        }

    }
}