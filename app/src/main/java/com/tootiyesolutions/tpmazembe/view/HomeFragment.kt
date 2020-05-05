package com.tootiyesolutions.tpmazembe.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tootiyesolutions.tpmazembe.R
import com.tootiyesolutions.tpmazembe.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    // Check if first load
    private var firstLoad: Boolean = true

    private lateinit var viewModel: HomeViewModel
    private val articlesListAdapter = ArticlesListAdapter(arrayListOf())

    // Variables manage scrolling
    private var isLoading: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.refresh(firstLoad.equals(true))

        // Populate the RecyclerView
        articlesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articlesListAdapter
            setHasFixedSize(true)
        }

        Log.d("Valynk", "Itemcount onViewCreated = "+ articlesList.layoutManager!!.getItemCount())

        // LoadMore listener
        addScrollerListener()

        homeRefreshLayout.setOnRefreshListener {
            articlesList.visibility = View.GONE
            homeListError.visibility = View.GONE
            homeLoadingView.visibility = View.VISIBLE
            viewModel.refreshBypassCache(firstLoad.equals(true))
            // Stop display of refresh spinner
            homeRefreshLayout.isRefreshing = false
        }

        observeViewModel()
    }

    private fun addScrollerListener()
    {
        // this listener is freely provided for by android, no external library
        articlesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            // for this tutorial, this is the ONLY method that we need, ignore the rest
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    // Recycle view scrolling downwards...
                    // this if statement detects when user reaches the end of recyclerView, this is only time we should load more
                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        // remember "!" is the same as "== false"
                        // here we are now allowed to load more, but we need to be careful
                        // we must check if itShouldLoadMore variable is true [unlocked]
                        if (!isLoading){
                            Toast.makeText(context, "RUNNING LOAD MORE", Toast.LENGTH_SHORT).show()
                            viewModel.refresh(firstLoad.equals(false))
                        }
                    }
                }
            }
        })
    }

    fun observeViewModel(){
        viewModel.articles.observe(viewLifecycleOwner, Observer {articles ->
            // If list of articles is not empty, we make the RecyclerView visible
            articles?.let{
                articlesList.visibility = View.VISIBLE
                articlesListAdapter.updateArticleList(articles)
            }
        })

        viewModel.articlesLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                homeListError.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                homeLoadingView.visibility = if(it) View.VISIBLE else View.GONE
                if(it){
                    homeListError.visibility = View.GONE
                    // articlesList.visibility = if(firstLoad) View.GONE else View.VISIBLE
                }
            }
        })
    }

}