package com.laracihan.fizyocep.vucutBolgeleri

import com.laracihan.fizyocep.R
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.media.MediaPlayer
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth

class OmuzEgzersizFragment : Fragment() {

    private lateinit var videoView: VideoView
    private lateinit var exitButton: ImageButton
    private lateinit var scrollView: ScrollView
    private lateinit var mediaController: MediaController

    private var currentCheckbox: CheckBox? = null
    private var currentStatusText: TextView? = null
    private val handler = Handler(Looper.getMainLooper())
    private var stopRunnable: Runnable? = null
    private var userId: String? = null
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_omuz_egzersiz, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firebaseUser = FirebaseAuth.getInstance().currentUser
        userId = firebaseUser?.uid

        if (userId == null) {
            Toast.makeText(requireContext(), "Kullanıcı bilgisi alınamadı.", Toast.LENGTH_SHORT).show()
            return
        }

        sharedPrefs = requireContext().getSharedPreferences("checkbox_prefs", Context.MODE_PRIVATE)

        videoView = view.findViewById(R.id.videoView)
        exitButton = view.findViewById(R.id.btnExitFullscreen)
        scrollView = view.findViewById(R.id.scrollView)

        mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        val videoMappings = listOf(
            Triple(R.id.btnVideo1, R.raw.omuz_egzersizi, Triple(R.id.checkbox1, R.id.statusText1, 1)),
            Triple(R.id.btnVideo2, R.raw.omuz_egzersizi2, Triple(R.id.checkbox2, R.id.statusText2, 2)),
            Triple(R.id.btnVideo3, R.raw.omuz_egzersizi3, Triple(R.id.checkbox3, R.id.statusText3, 3)),
            Triple(R.id.btnVideo4, R.raw.omuz_egzersizi4, Triple(R.id.checkbox4, R.id.statusText4, 4)),
            Triple(R.id.btnVideo5, R.raw.omuz_egzersizi5, Triple(R.id.checkbox5, R.id.statusText5, 5)),
            Triple(R.id.btnVideo6, R.raw.omuz_egzersizi6, Triple(R.id.checkbox6, R.id.statusText6, 6))
        )

        for ((buttonId, videoRes, views) in videoMappings) {
            val button = view.findViewById<Button>(buttonId)
            val checkbox = view.findViewById<CheckBox>(views.first)
            val statusText = view.findViewById<TextView>(views.second)
            val videoIndex = views.third
            val key = "${userId}_video_done_$videoIndex"

            // Önceki durumu SharedPreferences’tan oku
            val isDone = sharedPrefs.getBoolean(key, false)
            checkbox.isChecked = isDone
            statusText.text = if (isDone) "Yapıldı" else "Yapılmadı"
            checkbox.isEnabled = isDone

            // Checkbox’ı manuel tıklamaya kapat
            checkbox.setOnClickListener {
                if (!checkbox.isEnabled) checkbox.isChecked = false
            }

            button.setOnClickListener {
                currentCheckbox = checkbox
                currentStatusText = statusText
                checkbox.isChecked = false
                checkbox.isEnabled = false
                statusText.text = "İzleniyor..."

                playVideoLoopedFor30Seconds(videoRes, videoIndex)
                enterFullscreen()
            }
        }

        exitButton.setOnClickListener {
            exitFullscreen()
        }
    }

    private fun playVideoLoopedFor30Seconds(resId: Int, videoIndex: Int) {
        val uri = Uri.parse("android.resource://${requireContext().packageName}/$resId")
        videoView.setVideoURI(uri)

        videoView.setOnPreparedListener { mp ->
            mp.isLooping = true
            videoView.start()
        }

        cancelStopRunnable()

        stopRunnable = Runnable {
            if (videoView.isPlaying) {
                videoView.pause()

                currentCheckbox?.apply {
                    isChecked = true
                    isEnabled = true
                }

                currentStatusText?.text = "Yapıldı"

                val key = "${userId}_video_done_$videoIndex"
                sharedPrefs.edit().putBoolean(key, true).apply()

                exitFullscreen()
            }
        }

        handler.postDelayed(stopRunnable!!, 30_000) // 30 saniye sonra durdur
    }

    private fun cancelStopRunnable() {
        stopRunnable?.let { handler.removeCallbacks(it) }
        stopRunnable = null
    }

    private fun enterFullscreen() {
        videoView.visibility = View.VISIBLE
        exitButton.visibility = View.VISIBLE
        scrollView.visibility = View.GONE
    }

    private fun exitFullscreen() {
        cancelStopRunnable()
        videoView.stopPlayback()
        videoView.visibility = View.GONE
        exitButton.visibility = View.GONE
        scrollView.visibility = View.VISIBLE
    }
}
