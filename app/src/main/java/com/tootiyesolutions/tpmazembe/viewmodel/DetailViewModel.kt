package com.tootiyesolutions.tpmazembe.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tootiyesolutions.tpmazembe.model.Article
import com.tootiyesolutions.tpmazembe.model.ArticleDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application): BaseViewModel(application) {
    val articleLiveData = MutableLiveData<Article>()

    fun fetch(uuid: Int){
        launch {
            val article = ArticleDatabase(getApplication()).articleDao().getArticle(uuid)
            articleLiveData.value = article
        }
    }
}