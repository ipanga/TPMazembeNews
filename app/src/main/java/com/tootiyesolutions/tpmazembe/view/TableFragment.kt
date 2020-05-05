package com.tootiyesolutions.tpmazembe.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.tootiyesolutions.tpmazembe.R
import com.tootiyesolutions.tpmazembe.viewmodel.TableViewModel

class TableFragment : Fragment() {

    companion object {
        fun newInstance() = TableFragment()
    }

    private lateinit var viewModel: TableViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_table, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TableViewModel::class.java)
    }

}
