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

class BelEgzersizFragment : Fragment() {

    private lateinit var videoView: VideoView
    private lateinit var exitButton: ImageButton
    private lateinit var mediaController: MediaController
    private lateinit var scrollView: ScrollView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bel_egzersiz, container, false)
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
            R.id.btnBelVideo1 to R.raw.bel_egzersizi,
            R.id.btnBelVideo2 to R.raw.bel_egzersizi2,
            R.id.btnBelVideo3 to R.raw.bel_egzersizi3,
            R.id.btnBelVideo4 to R.raw.bel_egzersizi4,
            R.id.btnBelVideo5 to R.raw.bel_egzersizi5,
            R.id.btnBelVideo6 to R.raw.bel_egzersizi6,
            R.id.btnBelVideo7 to R.raw.bel_egzersizi7,
            R.id.btnBelVideo8 to R.raw.bel_egzersizi8,
            R.id.btnBelVideo9 to R.raw.bel_egzersizi9,
            R.id.btnBelVideo10 to R.raw.bel_egzersizi10,
            R.id.btnBelVideo11 to R.raw.bel_egzersizi11,
            R.id.btnBelVideo12 to R.raw.bel_egzersizi12,
            R.id.btnBelVideo13 to R.raw.bel_egzersizi13,
            R.id.btnBelVideo14 to R.raw.bel_egzersizi14,
            R.id.btnBelVideo15 to R.raw.bel_egzersizi15,
            R.id.btnBelVideo16 to R.raw.bel_egzersizi16,
            R.id.btnBelVideo17 to R.raw.bel_egzersizi17,
            R.id.btnBelVideo18 to R.raw.bel_egzersizi18
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
