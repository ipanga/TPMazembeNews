package com.tootiyesolutions.tpmazembe.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.tootiyesolutions.tpmazembe.R
import com.tootiyesolutions.tpmazembe.viewmodel.FixturesViewModel

class FixturesFragment : Fragment() {

    companion object {
        fun newInstance() = FixturesFragment()
    }

    private lateinit var viewModel: FixturesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fixtures, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FixturesViewModel::class.java)
    }

}
