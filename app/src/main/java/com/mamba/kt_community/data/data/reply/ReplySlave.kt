package com.mamba.kt_community.data.data.reply

data class ReplySlave(
    var boardIdx: Int,
    var masterIdx: Int,
    var slaveIdx: Int,
    var creatorId: String?,
    var createdDatetime: String?,
    var updaterId: String?,
    var updatedDatetime: String?,
    var thumbsUp: Int,
    var thumbsDown: Int,
    var contents: String?
) {
    var isThumbsUpCheck = false
}