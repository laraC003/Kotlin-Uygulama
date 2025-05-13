package com.laracihan.fizyocep



import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class BoyunEgzersizFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_boyun_egzersiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoView = view.findViewById<VideoView>(R.id.videoView)

        // raw klasöründeki video URI'si
        val videoUri = Uri.parse("android.resource://${requireContext().packageName}/${R.raw.boyun_egzersizi}")
        videoView.setVideoURI(videoUri)

        // Medya kontrol çubuğu
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        // Videoyu oynat
        videoView.start()
    }
}
