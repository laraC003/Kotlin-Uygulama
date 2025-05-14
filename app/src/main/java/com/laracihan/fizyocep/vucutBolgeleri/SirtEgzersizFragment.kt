package com.laracihan.fizyocep.vucutBolgeleri

import com.laracihan.fizyocep.R
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ScrollView

class SirtEgzersizFragment : Fragment() {

    private lateinit var videoView: VideoView
    private lateinit var exitButton: ImageButton
    private lateinit var mediaController: MediaController
    private lateinit var scrollView: ScrollView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sirt_egzersiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoView = view.findViewById(R.id.videoView)
        exitButton = view.findViewById(R.id.btnExitFullscreen)
        scrollView = view.findViewById(R.id.scrollView)

        mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        val buttonsWithVideos = listOf(
            R.id.btnVideo1 to R.raw.sirt_egzersizi,
            R.id.btnVideo2 to R.raw.sirt_egzersizi2,
            R.id.btnVideo3 to R.raw.sirt_egzersizi3,
            R.id.btnVideo4 to R.raw.sirt_egzersizi4,
            R.id.btnVideo5 to R.raw.sirt_egzersizi5,
            R.id.btnVideo6 to R.raw.sirt_egzersizi6,
            R.id.btnVideo7 to R.raw.sirt_egzersizi7,
            R.id.btnVideo8 to R.raw.sirt_egzersizi8,
            R.id.btnVideo9 to R.raw.sirt_egzersizi9,
            R.id.btnVideo10 to R.raw.sirt_egzersizi10,
            R.id.btnVideo11 to R.raw.sirt_egzersizi11,
            R.id.btnVideo12 to R.raw.sirt_egzersizi12,
            R.id.btnVideo13 to R.raw.sirt_egzersizi13
        )

        for ((buttonId, videoRes) in buttonsWithVideos) {
            view.findViewById<Button>(buttonId).setOnClickListener {
                enterFullscreen()
                playVideo(videoRes)
            }
        }

        exitButton.setOnClickListener {
            exitFullscreen()
        }
    }

    private fun playVideo(resId: Int) {
        val videoUri = Uri.parse("android.resource://${requireContext().packageName}/$resId")
        videoView.setVideoURI(videoUri)
        videoView.start()
    }

    private fun enterFullscreen() {
        videoView.visibility = View.VISIBLE
        exitButton.visibility = View.VISIBLE
        scrollView.visibility = View.GONE
    }

    private fun exitFullscreen() {
        videoView.stopPlayback()
        videoView.visibility = View.GONE
        exitButton.visibility = View.GONE
        scrollView.visibility = View.VISIBLE
    }
}
