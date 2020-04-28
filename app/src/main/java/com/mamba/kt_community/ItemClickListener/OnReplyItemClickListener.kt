package com.mamba.kt_community.ItemClickListener

import android.view.View
import com.mamba.kt_community.Adapter.reply.MasterReplyAdapter

interface OnReplyItemClickListener {
    abstract fun onItemClick(viewHolder: MasterReplyAdapter.ViewHolder, view: View, position: Int)

    abstract fun thumbsUpClick(viewHolder: MasterReplyAdapter.ViewHolder, view: View, position: Int)
}