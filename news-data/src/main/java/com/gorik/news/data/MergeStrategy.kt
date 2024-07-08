package com.gorik.news.data

import com.gorik.news.data.RequestResult as r
import com.gorik.news.data.RequestResult

interface MergeStrategy<T> {

    fun merge(left:T,right:T): T

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

class RequestResponseMergeStrategy<T:Any>:MergeStrategy<RequestResult<T>>{
    override fun merge(cache: RequestResult<T>, server: RequestResult<T>): RequestResult<T> {

return when{
    cache is r.Success && server is r.Success -> merge( cache,server)
    cache is r.InProgress && server is r.Success -> merge( cache,server)

    cache is r.Success && server is r.Error -> merge( cache,server)

    cache is r.Success && server is r.InProgress -> merge( cache,server)
    cache is r.InProgress && server is r.InProgress -> merge( cache,server)


    else-> error("Unimplemented usecase!!!")

}
    }




    private fun merge(cache:r.Success<T>,server:r.Success<T>):RequestResult<T>{
        return r.Success(data = server.data)
    }


  private fun merge(cache:r.InProgress<T>,server:r.Success<T>):RequestResult<T>{
        return  r.InProgress(data = server.data)
    }


  private fun merge(cache:r.Success<T>,server:r.Error<T>):RequestResult<T>{
        return r.Error(data = cache.data, error = server.error)
    }






  private fun merge(cache:r.Success<T>,server:r.InProgress<T>):RequestResult<T>{
        return r.InProgress(data = cache.data)
    }




  private fun merge(cache:r.InProgress<T>,server:r.InProgress<T>):RequestResult<T>{
        val data = server.data ?: cache.data
            return r.InProgress(data = data)

    }









}