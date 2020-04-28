package com.mamba.kt_community.ItemClickListener

import android.view.View
import com.mamba.kt_community.Adapter.upload.UploadSearchAdapter

interface OnUploadSearchItem {
    abstract fun itemClick(viewHolder: UploadSearchAdapter.ViewHolder, view: View, position: Int)

    abstract fun itemLongClick(viewHolder: UploadSearchAdapter.ViewHolder, view: View, position: Int):Unit

}