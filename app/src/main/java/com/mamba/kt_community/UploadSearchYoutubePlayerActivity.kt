package com.mamba.kt_community

import android.content.Intent.getIntent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class UploadSearchYoutubePlayerActivity : YouTubeBaseActivity(),
    YouTubePlayer.OnInitializedListener {


    private var ytpv: YouTubePlayerView? = null
    private var ytp: YouTubePlayer? = null
    private val serverKey = "Ar8uZATzfcDd6mSvJktcT1Ex1hgQn6w" //콘솔에서 받아온 서버키를 넣어줍니다

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_search_youtube_player)

        ytpv = findViewById<YouTubePlayerView>(R.id.youtubeplayer)
        ytpv!!.initialize(serverKey, this)
    }

    override fun onInitializationFailure(
        arg0: YouTubePlayer.Provider,
        arg1: YouTubeInitializationResult
    ) {
        Toast.makeText(this, "Initialization Fail", Toast.LENGTH_LONG).show()
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider,
        player: YouTubePlayer, wasrestored: Boolean
    ) {
        ytp = player

        val gt = getIntent()
        ytp!!.loadVideo(gt.getStringExtra("videoId"))
    }
}
