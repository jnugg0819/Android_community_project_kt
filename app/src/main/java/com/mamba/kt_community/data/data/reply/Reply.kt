package com.mamba.kt_community.data.data.reply

data class Reply(
    var boardIdx: Int,
    var masterIdx: Int,
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
