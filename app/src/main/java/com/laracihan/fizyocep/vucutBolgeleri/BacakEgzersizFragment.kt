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
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth

class BacakEgzersizFragment : Fragment() {

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
    ): View? = inflater.inflate(R.layout.fragment_bacak_egzersiz, container, false)

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

        // videoMappings: buttonId, Pair(videoResId, durationMs), Triple(checkboxId, statusTextId, index)
        val videoMappings = listOf(
            Triple(R.id.btnVideo1, Pair(R.raw.bacak_egzersizi, 30_000), Triple(R.id.checkbox1, R.id.statusText1, 1)),
            Triple(R.id.btnVideo2, Pair(R.raw.bacak_egzersizi2, 30_000), Triple(R.id.checkbox2, R.id.statusText2, 2)),
            Triple(R.id.btnVideo3, Pair(R.raw.bacak_egzersizi3, 30_000), Triple(R.id.checkbox3, R.id.statusText3, 3)),
            Triple(R.id.btnVideo4, Pair(R.raw.bacak_egzersizi4, 30_000), Triple(R.id.checkbox4, R.id.statusText4, 4)),
            Triple(R.id.btnVideo5, Pair(R.raw.bacak_egzersizi5, 30_000), Triple(R.id.checkbox5, R.id.statusText5, 5)),
            Triple(R.id.btnVideo6, Pair(R.raw.bacak_egzersizi6, 30_000), Triple(R.id.checkbox6, R.id.statusText6, 6)),
            Triple(R.id.btnVideo7, Pair(R.raw.bacak_egzersizi7, 30_000), Triple(R.id.checkbox7, R.id.statusText7, 7)),
            Triple(R.id.btnVideo8, Pair(R.raw.bacak_egzersizi8, 20_000), Triple(R.id.checkbox8, R.id.statusText8, 8)),
            Triple(R.id.btnVideo9, Pair(R.raw.bacak_egzersizi9, 20_000), Triple(R.id.checkbox9, R.id.statusText9, 9))
        )

        for ((buttonId, videoData, viewData) in videoMappings) {
            val button = view.findViewById<Button>(buttonId)
            val (videoResId, durationMs) = videoData
            val (checkboxId, statusTextId, index) = viewData

            val checkbox = view.findViewById<CheckBox>(checkboxId)
            val statusText = view.findViewById<TextView>(statusTextId)

            val key = "${userId}_bacak_done_$index"
            val isDone = sharedPrefs.getBoolean(key, false)

            // Checkbox ve text durumunu SharedPreferences'den yükle
            checkbox.isChecked = isDone
            statusText.text = if (isDone) "Yapıldı" else "Yapılmadı"
            checkbox.isEnabled = isDone

            // Eğer checkbox disabled ise (yani yapılmadıysa) kullanıcı manuel olarak işaretleyemesin
            checkbox.setOnClickListener {
                if (!checkbox.isEnabled) checkbox.isChecked = false
            }

            button.setOnClickListener {
                currentCheckbox = checkbox
                currentStatusText = statusText

                checkbox.isChecked = false
                checkbox.isEnabled = false
                statusText.text = "İzleniyor..."

                playVideoWithDuration(videoResId, durationMs, key)
                enterFullscreen()
            }
        }

        exitButton.setOnClickListener {
            exitFullscreen()
        }
    }

    private fun playVideoWithDuration(resId: Int, durationMs: Int, key: String) {
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
                sharedPrefs.edit().putBoolean(key, true).apply()

                exitFullscreen()
            }
        }

        handler.postDelayed(stopRunnable!!, durationMs.toLong())
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
