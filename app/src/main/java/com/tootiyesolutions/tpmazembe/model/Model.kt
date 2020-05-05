package com.tootiyesolutions.tpmazembe.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Article(
    @ColumnInfo(name = "article_id")
    @SerializedName("id")
    val articleId: String?,

    @ColumnInfo(name = "article_title")
    @SerializedName("titre")
    val articleTitle: String?,

    @ColumnInfo(name = "article_url")
    @SerializedName("link")
    val articleUrl: String?,

    @ColumnInfo(name = "article_date")
    @SerializedName("date_article")
    val articleDate: String?,

    @ColumnInfo(name = "article_content")
    @SerializedName("contenu")
    val articleContent: String?,

    @ColumnInfo(name = "image_url")
    @SerializedName("jetpack_featured_media_url")
    val imageUrl: String?
){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}