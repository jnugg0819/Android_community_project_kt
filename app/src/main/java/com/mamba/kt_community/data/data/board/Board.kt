package com.mamba.kt_community.data.data.board

data class Board(
    var boardIdx: String?,
    var title: String?,
    var contents: String?,
    var likeCnt: Int,
    var creatorId: String?,
    var createdDatetime: String?,
    var updaterId: String?,
    var updatedDatetime: String?,
    var youtubeAddress: String?,
    var musicName: String?,
    var singerName: String?,
    var relatedSong: String?,
    var musicCategory: String?,
    var fileList: List<BoardFile>?
) {

    var isLikeCheck = false
}