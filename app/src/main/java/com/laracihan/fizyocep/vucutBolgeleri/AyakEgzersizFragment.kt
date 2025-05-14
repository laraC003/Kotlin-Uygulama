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

class AyakEgzersizFragment : Fragment() {

    private lateinit var videoView: VideoView
    private lateinit var exitButton: ImageButton
    private lateinit var mediaController: MediaController
    private lateinit var scrollView: ScrollView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ayak_egzersiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoView = view.findViewById(R.id.videoView)
        exitButton = view.findViewById(R.id.btnExitFullscreen)
        scrollView = view.findViewById(R.id.scrollView)

        mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        // Butonlar ve CheckBox'lar arasında ilişkiyi kurma
        val videoMappings = listOf(
            Triple(R.id.btnVideo1, R.raw.ayak_egzersizi, Pair(R.id.checkbox1, R.id.statusText1)),
            Triple(R.id.btnVideo2, R.raw.ayak_egzersizi2, Pair(R.id.checkbox2, R.id.statusText2)),
            Triple(R.id.btnVideo3, R.raw.ayak_egzersizi3, Pair(R.id.checkbox3, R.id.statusText3)),
            Triple(R.id.btnVideo4, R.raw.ayak_egzersizi4, Pair(R.id.checkbox4, R.id.statusText4)),
            Triple(R.id.btnVideo5, R.raw.ayak_egzersizi5, Pair(R.id.checkbox5, R.id.statusText5)),
            Triple(R.id.btnVideo6, R.raw.ayak_egzersizi6, Pair(R.id.checkbox6, R.id.statusText6)),

        )

        for ((buttonId, videoRes, checkboxPair) in videoMappings) {
            val button = view.findViewById<Button>(buttonId)
            val checkbox = view.findViewById<CheckBox>(checkboxPair.first)
            val statusText = view.findViewById<TextView>(checkboxPair.second)

            // Butona tıklanıldığında videoyu oynat ve checkbox'ı işaretle
            button.setOnClickListener {
                enterFullscreen()
                playVideo(videoRes)

                checkbox.isChecked = true
                statusText.text = "Yapıldı"
            }

            // Checkbox durumu değiştirildiğinde TextView metnini güncelle
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                statusText.text = if (isChecked) "Yapıldı" else "Yapılmadı"
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
