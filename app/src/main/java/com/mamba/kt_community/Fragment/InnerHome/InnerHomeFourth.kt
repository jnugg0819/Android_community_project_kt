package com.mamba.kt_community.Fragment.InnerHome

import android.content.Context
import android.content.Intent
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
import com.mamba.kt_community.ItemClickListener.OnBoardItemClickListener
import com.mamba.kt_community.MasterReplyActivity

import com.mamba.kt_community.R
import com.mamba.kt_community.data.data.viewmodel.HomeViewModel


class InnerHomeFourth : Fragment() {

    private var viewModel: HomeViewModel?=null
    private var recyclerView: RecyclerView? = null
    var adapter: BoardAdapter?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView=inflater.inflate(R.layout.fragment_innter_home_fourth, container, false)

        viewModel=activity!!.application!!.let {
            ViewModelProvider(
                viewModelStore, ViewModelProvider.AndroidViewModelFactory(it)).get(HomeViewModel::class.java)}

        adapter=BoardAdapter(activity!!)
        recyclerView=rootView.findViewById(R.id.home_fourth)
        val layoutManager = GridLayoutManager(activity, 1)
        recyclerView!!.layoutManager=layoutManager

        //어댑터 댓글 및 좋아요 클릭이벤트
        clickAdapterButton()

        viewModel!!.getFourthTimeLine(recyclerView!!, adapter!!)

        return rootView
    }

    private fun clickAdapterButton() {
        adapter!!.setOnItemClickListener(object : OnBoardItemClickListener {
            override fun replyBtnClick(
                viewHolder: BoardAdapter.ViewHolder,
                view: View,
                position: Int
            ) {
                val item=adapter!!.getItem(position)
                val intent= Intent(activity, MasterReplyActivity::class.java)
                intent.putExtra("boardIdx",item.boardIdx)
                startActivity(intent)
            }

            override fun likeBtnClick(
                viewHolder: BoardAdapter.ViewHolder,
                view: View,
                position: Int
            ) {
            }

        })
    }


}
