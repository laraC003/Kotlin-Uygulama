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
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BacakEgzersizFragment : Fragment() {

    private lateinit var videoView: VideoView
    private lateinit var exitButton: ImageButton
    private lateinit var scrollView: ScrollView
    private lateinit var mediaController: MediaController

    private var currentCheckbox: CheckBox? = null
    private var currentStatusText: TextView? = null
    private var currentFirestoreKey: String? = null

    private val handler = Handler(Looper.getMainLooper())
    private var stopRunnable: Runnable? = null
    private var userId: String? = null
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var db: FirebaseFirestore

    private val args: BacakEgzersizFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_bacak_egzersiz, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = args.kullaniciId
        if (userId.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Kullanıcı ID alınamadı", Toast.LENGTH_SHORT).show()
            return
        }

        db = FirebaseFirestore.getInstance()
        sharedPrefs = requireContext().getSharedPreferences("checkbox_prefs", Context.MODE_PRIVATE)

        videoView = view.findViewById(R.id.videoView)
        exitButton = view.findViewById(R.id.btnExitFullscreen)
        scrollView = view.findViewById(R.id.scrollView)

        mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

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

            val sharedPrefsKey = "${userId}_bacak_done_$index"
            val firestoreKey = "bacak_egzersizi_$index"

            db.collection("users").document(userId!!).collection("tamamlananEgzersizler")
                .document(firestoreKey).get()
                .addOnSuccessListener {
                    val isDone = it.getBoolean("tamamlandi") ?: false
                    checkbox.isChecked = isDone
                    checkbox.isEnabled = isDone
                    statusText.text = if (isDone) "Yapıldı" else "Yapılmadı"
                }

            checkbox.setOnClickListener {
                if (!checkbox.isEnabled) checkbox.isChecked = false
            }

            button.setOnClickListener {
                currentCheckbox = checkbox
                currentStatusText = statusText
                currentFirestoreKey = firestoreKey

                checkbox.isChecked = false
                checkbox.isEnabled = false
                statusText.text = "İzleniyor..."

                playVideoWithDuration(videoResId, durationMs, sharedPrefsKey)
                enterFullscreen()
            }
        }

        exitButton.setOnClickListener {
            exitFullscreen()
        }
    }

    private fun playVideoWithDuration(resId: Int, durationMs: Int, sharedPrefsKey: String) {
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
                sharedPrefs.edit().putBoolean(sharedPrefsKey, true).apply()

                currentFirestoreKey?.let { key -> saveToFirestore(key) }

                exitFullscreen()
            }
        }

        handler.postDelayed(stopRunnable!!, durationMs.toLong())
    }

    private fun saveToFirestore(egzersizAdi: String) {
        val data = mapOf(
            "tamamlandi" to true,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("users")
            .document(userId!!)
            .collection("tamamlananEgzersizler")
            .document(egzersizAdi)
            .set(data)
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
