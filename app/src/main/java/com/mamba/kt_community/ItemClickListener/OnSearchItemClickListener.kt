package com.mamba.kt_community.ItemClickListener

import android.view.View
import com.mamba.kt_community.Adapter.search.SearchAdapter

interface OnSearchItemClickListener {
    fun searchItemClick(viewHolder: SearchAdapter.ViewHolder, view: View, position: Int)

}