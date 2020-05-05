package com.tootiyesolutions.tpmazembe.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.tootiyesolutions.tpmazembe.R
import com.tootiyesolutions.tpmazembe.databinding.ItemArticleBinding
import com.tootiyesolutions.tpmazembe.model.Article
import kotlinx.android.synthetic.main.item_article.view.*

class ArticlesListAdapter(val articlesList: ArrayList<Article>): RecyclerView.Adapter<ArticlesListAdapter.ArticleViewHolder>(), ArticleClickListener {

    // Function to update Recyclerview items
    fun updateArticleList(newArticlesList: List<Article>){
        articlesList.clear()
        articlesList.addAll(newArticlesList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemArticleBinding>(inflater, R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount() = articlesList.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.view.article = articlesList[position]
        holder.view.listener = this
    }

    // Implementing clic action on an article
    override fun onArticleClicked(v: View) {
        val uuid = v.articleId.text.toString().toInt()
        val action = HomeFragmentDirections.actionDetailActivity()
        action.articleUuid = uuid
        Navigation.findNavController(v).navigate(action)
    }

    class ArticleViewHolder(var view: ItemArticleBinding): RecyclerView.ViewHolder(view.root)
}