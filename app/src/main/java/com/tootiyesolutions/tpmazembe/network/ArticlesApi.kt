package com.tootiyesolutions.tpmazembe.network

import com.tootiyesolutions.tpmazembe.model.Article
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticlesApi {
    @GET("wp-json/wp/v2/posts")
    fun getArticles(@Query("page") page: Int): Single<List<Article>>
}

/*
interface ArticlesApi {
    @GET("wp-json/wp/v2/posts?page=1")
    fun getArticles(i: Int): Single<List<Article>>
}*/
