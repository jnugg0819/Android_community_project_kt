package com.mamba.kt_community.Fragment.InnerHome

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mamba.kt_community.Adapter.board.BoardAdapter

import com.mamba.kt_community.R
import com.mamba.kt_community.data.data.viewmodel.HomeViewModel


class InnerHomeSixth : Fragment() {

    private var viewModel: HomeViewModel? = null
    private var recyclerView: RecyclerView? = null
    var adapter: BoardAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_innter_home_sixth, container, false)

        viewModel = activity!!.application!!.let {
            ViewModelProvider(
                viewModelStore, ViewModelProvider.AndroidViewModelFactory(it)
            ).get(HomeViewModel::class.java)
        }

        adapter = BoardAdapter(activity!!)
        recyclerView = rootView.findViewById(R.id.home_sixth)
        val layoutManager = GridLayoutManager(activity, 1)
        recyclerView!!.layoutManager = layoutManager

        viewModel!!.getSixthTimeLine(recyclerView!!, adapter!!)

        return rootView
    }


}
