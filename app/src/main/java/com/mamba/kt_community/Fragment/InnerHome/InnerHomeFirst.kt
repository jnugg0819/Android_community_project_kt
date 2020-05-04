package com.mamba.kt_community.Fragment.InnerHome

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mamba.kt_community.Adapter.board.BoardAdapter
import com.mamba.kt_community.BranchActivity
import com.mamba.kt_community.ItemClickListener.OnBoardItemClickListener
import com.mamba.kt_community.MasterReplyActivity

import com.mamba.kt_community.R
import com.mamba.kt_community.data.data.board.Board
import com.mamba.kt_community.data.data.viewmodel.HomeViewModel


class InnerHomeFirst : Fragment() {

    private var viewModel:HomeViewModel?=null
    private var recyclerView: RecyclerView? = null
    var adapter: BoardAdapter?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val rootView=inflater.inflate(R.layout.fragment_innter_home_first, container, false)

        viewModel=activity!!.application!!.let {
            ViewModelProvider(
                viewModelStore, ViewModelProvider.AndroidViewModelFactory(it)).get(HomeViewModel::class.java)}

        adapter=BoardAdapter(activity!!)
        recyclerView=rootView.findViewById(R.id.home_first)
        val layoutManager = GridLayoutManager(activity, 1)
        recyclerView!!.layoutManager=layoutManager

        //어댑터 댓글 및 좋아요 클릭이벤트
        clickAdapterButton()

        viewModel!!.getFirstTimeLine(recyclerView!!,adapter!!)

        return rootView
    }

    private fun clickAdapterButton() {
        adapter!!.setOnItemClickListener(object :OnBoardItemClickListener{
            //댓글
            override fun replyBtnClick(
                viewHolder: BoardAdapter.ViewHolder,
                view: View,
                position: Int
            ) {
                val item=adapter!!.getItem(position)
                val intent=Intent(activity,MasterReplyActivity::class.java)
                intent.putExtra("boardIdx",item.boardIdx)
                startActivity(intent)
            }

            //좋아요
            override fun likeBtnClick(
                viewHolder: BoardAdapter.ViewHolder,
                view: View,
                position: Int
            ) {

                if(BranchActivity.currentUserEmail!=null){

                    val on = (view as ToggleButton).isChecked
                    val item = adapter!!.getItem(position)

                    //누르게된다면 BoardIdx,UserId
                    val boardIdx = Integer.parseInt(item.boardIdx!!)

                    val likeTextView = viewHolder.itemView.findViewById<TextView>(R.id.cardview_like_txt)

                    if (on) {
                        var plus = Integer.parseInt(likeTextView.text.toString())
                        plus += 1
                        likeTextView.text = plus.toString()
                        view.setBackground(
                            ContextCompat.getDrawable(
                                view.getContext(),
                                R.drawable.ic_favorite_black_18dp
                            )
                        )
                        viewModel!!.updateLike(boardIdx,BranchActivity.currentUserEmail,true)//좋아요 +1
                        viewModel!!.insertLikeUserInfo(boardIdx,BranchActivity.currentUserEmail)

                    } else {
                        var minus = Integer.parseInt(likeTextView.text.toString())
                        minus -= 1
                        likeTextView.text = minus.toString()
                        view.setBackground(
                            ContextCompat.getDrawable(
                                view.getContext(),
                                R.drawable.ic_favorite_border_black_18dp
                            )
                        )
                        viewModel!!.updateLike(boardIdx,BranchActivity.currentUserEmail,false)//좋아요 +1
                        viewModel!!.deleteLikeUserInfo(boardIdx,BranchActivity.currentUserEmail)
                    }
                } else { Toast.makeText(activity, "로그인을 먼저 해주세요",Toast.LENGTH_LONG).show() }


            }

        })
    }


}
