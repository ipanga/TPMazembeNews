package com.tootiyesolutions.tpmazembe.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.tootiyesolutions.tpmazembe.model.Article
import com.tootiyesolutions.tpmazembe.model.ArticleDatabase
import com.tootiyesolutions.tpmazembe.network.ArticlesApiService
import com.tootiyesolutions.tpmazembe.util.NotificationsHelper
import com.tootiyesolutions.tpmazembe.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): BaseViewModel(application) {
    // From database
    private var loadFromDatabase: Boolean = false

    // Page to be loaded
    private var pageToLoad: Int = 1

    // Variable which will store in SharedPref time when the DB has been updated
    private var prefHelper = SharedPreferencesHelper(getApplication())

    // Refresh time in nanosecond (5 minutes)
    private var refreshTime = 1 * 60 * 1000 * 1000 * 1000L

    private val articlesService =
        ArticlesApiService()
    // Observer of ArticlesApiService.getArticles
    private val disposable = CompositeDisposable()

    val articles = MutableLiveData<List<Article>>()
    val articlesLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(firstLoad: Boolean) {
        val updateTime = prefHelper.getUpdateTime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime && firstLoad){
            fetchFromDatabase()
        } else{
            if(loadFromDatabase) fetchFromRemote(true) else fetchFromRemote(firstLoad)
        }
    }

    fun refreshBypassCache(firstLoad: Boolean) {
        fetchFromRemote(firstLoad)
    }

    private fun fetchFromDatabase() {
        loading.value = true
        // Fetch data from DB through Coroutine
        launch {
            val articles = ArticleDatabase(getApplication()).articleDao().getAllArticles()
            articlesRetrieved(articles)
            Toast.makeText(getApplication(), "Articles retrieved from database", Toast.LENGTH_SHORT).show()
            loadFromDatabase = true
        }
    }

    // Downloading JSON data from web server
    private fun fetchFromRemote(firstLoad: Boolean) {
        loading.value = true

        // If first load, collect data from page 1
        if (firstLoad){
            pageToLoad = 1
        }

        disposable.add(
            articlesService.getArticles(pageToLoad)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Article>>(){
                    override fun onSuccess(articleList: List<Article>) {
                        storeArticlesLocally(articleList, firstLoad)
                        // Increment pages
                        pageToLoad++
                        loadFromDatabase = false
                        Toast.makeText(getApplication(), "Articles retrieved from endpoint", Toast.LENGTH_SHORT).show()
                        NotificationsHelper(getApplication()).createNotification()
                    }

                    override fun onError(e: Throwable) {
                        articlesLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun articlesRetrieved(articleList: List<Article>) {
        articles.value = articleList
        articlesLoadError.value = false
        loading.value = false
    }

    private fun storeArticlesLocally(list: List<Article>, firstLoad: Boolean){
        // Store data into DB through Coroutine
        launch {
            val dao = ArticleDatabase(getApplication()).articleDao()

            // If it's the first time to load articles, delete then all previous data
            if (firstLoad){
                dao.deleteAllArticles()
            }

            // Convert our list to Array as DB function requirement, then insert into DB
            dao.insertAll(*list.toTypedArray())

            // Collect from DB the list of Articles to be populated into views
            val articles = ArticleDatabase(getApplication()).articleDao().getAllArticles()

            articlesRetrieved(articles)
        }

         if(firstLoad){
             // Saving into preferences time which the DB has been updated
             prefHelper.saveUpdateTime(System.nanoTime())
         }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}