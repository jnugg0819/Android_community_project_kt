package com.mamba.kt_community.ItemClickListener

import android.view.View
import com.mamba.kt_community.Adapter.board.BoardAdapter

interface OnBoardItemClickListener {

    abstract fun replyBtnClick(viewHolder: BoardAdapter.ViewHolder, view: View, position: Int)

    abstract fun likeBtnClick(viewHolder: BoardAdapter.ViewHolder, view: View, position: Int)

}