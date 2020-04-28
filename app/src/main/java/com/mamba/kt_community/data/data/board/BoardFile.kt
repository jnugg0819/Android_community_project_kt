package com.mamba.kt_community.data.data.board

data class BoardFile(
    var idx: Int,
    var boardIdx: Int,
    var originalFileName: String?,
    var storedFilePath: String?,
    var fileSize: String?
)
