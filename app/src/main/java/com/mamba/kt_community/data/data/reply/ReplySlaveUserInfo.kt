package com.mamba.kt_community.data.data.reply

data class ReplySlaveUserInfo(
    var boardIdx: Int,
    var masterIdx: Int,
    var slaveIdx: Int,
    var pressedId: String?,
    var pressedDatetime: String?
)