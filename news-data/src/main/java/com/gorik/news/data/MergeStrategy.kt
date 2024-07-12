package com.gorik.news.data

import com.gorik.news.data.RequestResult as r
import com.gorik.news.data.RequestResult

interface MergeStrategy<T> {

    fun merge(left: T, right: T): T

}

/**
 * Possible state is :
 *  - success
 *  - error
 *  - loading;
 *
 * Possible data sources:
 *  - db
 *  - api
 *
 *  Below is the strategy of theirs  possible combinations merging, i.e. which one data source use.
 *
 *  */

class RequestResponseMergeStrategy<T : Any> : MergeStrategy<RequestResult<T>> {
    override fun merge(cache: RequestResult<T>, server: RequestResult<T>): RequestResult<T> {

        println("RIOGORIO ::: "+ cache.toString()+server.toString())


        return when {
            cache is r.Success && server is r.Success -> merge(cache, server)
            cache is r.Success && server is r.Error -> merge(cache, server)
            cache is r.Success && server is r.InProgress -> merge(cache, server)

            cache is r.InProgress && server is r.Success -> merge(cache, server)
            cache is r.InProgress && server is r.Error -> merge(cache, server)
            cache is r.InProgress && server is r.InProgress -> merge(cache, server)


            cache is r.Error && server is r.Success -> merge(cache, server)
            cache is r.Error && server is r.InProgress -> merge(cache, server)

            else -> error("Unimplemented branch" + server.toString() + cache.toString())
        }
    }


    private fun merge(cache: r.Success<T>, server: r.InProgress<T>): RequestResult<T> {

        return r.InProgress(data = cache.data)
    }

    private fun merge(cache: r.InProgress<T>, server: r.InProgress<T>): RequestResult<T> {
        return when {
            server.data != null -> r.InProgress(server.data)
            else -> r.InProgress(cache.data)
        }
    }

    private fun merge(cache: r.Success<T>, server: r.Success<T>): RequestResult<T> {
        return r.Success(data = server.data)
    }


    private fun merge(cache: r.Success<T>, server: r.Error<T>): RequestResult<T> {
        return r.Error(data = cache.data, error = server.error)
    }


    private fun merge(cache: r.InProgress<T>, server: r.Success<T>): RequestResult<T> {
        return r.InProgress(data = server.data)
    }


    private fun merge(cache: r.InProgress<T>, server: r.Error<T>): RequestResult<T> {
        return r.Error(data = server.data ?: cache.data, error = server.error)
    }


    private fun merge(cache: r.Error<T>, server: r.Success<T>): RequestResult<T> {
        return server
    }

    private fun merge(cache: r.Error<T>, server: r.InProgress<T>): RequestResult<T> {
        return server

    }


}