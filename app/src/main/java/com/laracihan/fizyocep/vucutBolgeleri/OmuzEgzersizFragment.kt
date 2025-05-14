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
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ScrollView
import android.widget.TextView

class OmuzEgzersizFragment : Fragment() {

    private lateinit var videoView: VideoView
    private lateinit var exitButton: ImageButton
    private lateinit var mediaController: MediaController
    private lateinit var scrollView: ScrollView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_omuz_egzersiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoView = view.findViewById(R.id.videoView)
        exitButton = view.findViewById(R.id.btnExitFullscreen)
        scrollView = view.findViewById(R.id.scrollView)

        mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        val buttons = listOf(
            R.id.btnVideo1 to R.raw.omuz_egzersizi,
            R.id.btnVideo2 to R.raw.omuz_egzersizi2,
            R.id.btnVideo3 to R.raw.omuz_egzersizi3,
            R.id.btnVideo4 to R.raw.omuz_egzersizi4,
            R.id.btnVideo5 to R.raw.omuz_egzersizi5,
            R.id.btnVideo6 to R.raw.omuz_egzersizi6
        )

        for ((buttonId, videoRes) in buttons) {
            view.findViewById<Button>(buttonId).setOnClickListener {
                enterFullscreen()
                playVideo(videoRes)
            }
        }
        videoView = view.findViewById(R.id.videoView)
        exitButton = view.findViewById(R.id.btnExitFullscreen)
        scrollView = view.findViewById(R.id.scrollView)

        mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        val videoMappings = listOf(
            Triple(R.id.btnVideo1, R.raw.omuz_egzersizi, Pair(R.id.checkbox1, R.id.statusText1)),
            Triple(R.id.btnVideo2, R.raw.omuz_egzersizi2, Pair(R.id.checkbox2, R.id.statusText2)),
            Triple(R.id.btnVideo3, R.raw.omuz_egzersizi3, Pair(R.id.checkbox3, R.id.statusText3)),
            Triple(R.id.btnVideo4, R.raw.omuz_egzersizi4, Pair(R.id.checkbox4, R.id.statusText4)),
            Triple(R.id.btnVideo5, R.raw.omuz_egzersizi5, Pair(R.id.checkbox5, R.id.statusText5)),
            Triple(R.id.btnVideo6, R.raw.omuz_egzersizi6, Pair(R.id.checkbox6, R.id.statusText6))
        )

        for ((buttonId, videoRes, checkboxPair) in videoMappings) {
            val button = view.findViewById<Button>(buttonId)
            val checkbox = view.findViewById<CheckBox>(checkboxPair.first)
            val statusText = view.findViewById<TextView>(checkboxPair.second)

            button.setOnClickListener {
                enterFullscreen()
                playVideo(videoRes)
            }

            checkbox.setOnCheckedChangeListener { _, isChecked ->
                statusText.text = if (isChecked) "Yapıldı" else "Yapılmadı"
            }
        }

        exitButton.setOnClickListener {
            exitFullscreen()
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
