package com.tootiyesolutions.tpmazembe.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tootiyesolutions.tpmazembe.R
import com.tootiyesolutions.tpmazembe.databinding.ActivityDetailBinding
import com.tootiyesolutions.tpmazembe.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private var articleUuid = 0

    private lateinit var dataBinding: ActivityDetailBinding

    // val args: DetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        // The below code will run only if the argument is not null to collect the value of the parameter passed
        articleUuid = getIntent().getExtras()?.let { DetailActivityArgs.fromBundle(it).articleUuid }!!
        // Solution no2 -> articleUuid = args.articleUuid

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch(articleUuid)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.articleLiveData.observe(this, Observer { article ->
            article?.let {
                // Binding data to view
                dataBinding.article = article
            }
        })
    }
}
