package com.tootiyesolutions.tpmazembe.network

import com.tootiyesolutions.tpmazembe.model.Article
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ArticlesApiService {
    private val BASE_URL = "https://tpmazembe.fr"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ArticlesApi::class.java)

    fun getArticles(page: Int): Single<List<Article>> {
        return api.getArticles(page)
    }
}