package com.mamba.kt_community.Fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mamba.kt_community.Adapter.search.SearchAdapter

import com.mamba.kt_community.R
import com.mamba.kt_community.data.data.viewmodel.HomeViewModel
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    //viewModel
    private var viewModel:HomeViewModel?=null
    //view
    private var recyclerView:RecyclerView?=null
    private lateinit var searchFmEdt:EditText
    //adapter
    private var searchAdapter:SearchAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView=inflater.inflate(R.layout.fragment_search, container, false)

        //searchView 초기화
        searchFmEdt=rootView.findViewById(R.id.search_fm_edt)

        //ViewModel 가져오기
        viewModel=activity!!.application!!.let {
            ViewModelProvider(
                viewModelStore, ViewModelProvider.AndroidViewModelFactory(it)).get(HomeViewModel::class.java)}


        //어댑터 및 리사이클러뷰 초기화
        searchAdapter= SearchAdapter(activity!!)
        recyclerView=rootView.findViewById(R.id.search_fm_rcy)
        val layoutManager=GridLayoutManager(activity,1)
        recyclerView!!.layoutManager=layoutManager


        //search버튼 눌렀을시 해당 텍스트 넘겨줌과 동시에 검색어저장
        /*searchFmBtn.setOnClickListener {
            viewModel!!.selectSearchAll(searchFmEdt.text.toString(),recyclerView!!,searchAdapter!!)
        }*/

        searchFmEdt.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {



            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(searchFmEdt.text.toString().isEmpty()){
                    searchAdapter!!.clearData()
                    recyclerView!!.adapter=searchAdapter
                }else{
                    viewModel!!.selectSearchAll(searchFmEdt.text.toString(),recyclerView!!,searchAdapter!!)
                }

            }

        })
        return rootView
    }


}
