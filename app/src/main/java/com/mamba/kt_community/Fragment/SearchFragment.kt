package com.mamba.kt_community.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mamba.kt_community.Adapter.search.SearchAdapter

import com.mamba.kt_community.R
import com.mamba.kt_community.data.data.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    //viewModel
    private var viewModel:HomeViewModel?=null
    //view
    private var recyclerView:RecyclerView?=null
    private lateinit var searchFmEdt:EditText
    private lateinit var searchFmBtn:Button
    //adapter
    private var searchAdapter:SearchAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView=inflater.inflate(R.layout.fragment_search, container, false)
        searchFmEdt=rootView.findViewById(R.id.search_fm_search_edt)
        searchFmBtn=rootView.findViewById(R.id.search_fm_search_btn)

        //ViewModel 가져오기
        viewModel=activity!!.application!!.let {
            ViewModelProvider(
                viewModelStore, ViewModelProvider.AndroidViewModelFactory(it)).get(HomeViewModel::class.java)}


        //어댑터 및 리사이클러뷰 초기화
        searchAdapter= SearchAdapter(activity!!)
        recyclerView=rootView.findViewById(R.id.search_fm_rcy)
        val layoutManager=GridLayoutManager(activity,1)
        recyclerView!!.layoutManager=layoutManager

        //search버튼 눌렀을시 해당 텍스트 넘겨줌
        searchFmBtn.setOnClickListener {
            viewModel!!.selectSearchAll(searchFmEdt.text.toString(),recyclerView!!,searchAdapter!!)
        }

        return rootView
    }


}
