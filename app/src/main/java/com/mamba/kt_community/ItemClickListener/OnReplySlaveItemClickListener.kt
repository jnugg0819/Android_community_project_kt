package com.mamba.kt_community.ItemClickListener

import android.view.View
import com.mamba.kt_community.Adapter.reply.SlaveReplyAdapter

interface OnReplySlaveItemClickListener {
     fun onItemClick(viewHolder: SlaveReplyAdapter.ViewHolder, view: View, position: Int)

     fun thumbsUpClick(viewHolder: SlaveReplyAdapter.ViewHolder, view: View, position: Int)
}