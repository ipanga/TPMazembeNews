package com.tootiyesolutions.tpmazembe.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg articles: Article): List<Long>

    @Query("SELECT * FROM article")
    suspend fun getAllArticles(): List<Article>

    @Query("SELECT * FROM article WHERE uuid = :articleId")
    suspend fun getArticle(articleId: Int): Article

    @Query("DELETE FROM article")
    suspend fun deleteAllArticles()
}