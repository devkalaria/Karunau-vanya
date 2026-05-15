package com.example.karunadavaanya

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.karunadavaanya.adapter.SoundAdapter
import com.example.karunadavaanya.model.Sound

class ForestSoundsActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forest_sounds)

        recyclerView = findViewById(R.id.soundsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val soundList = listOf(
            Sound("Bird", R.raw.bird),
            Sound("Elephant", R.raw.elephant),
            Sound("Forest", R.raw.forest),
            Sound("Tiger Roar", R.raw.tiger_roar),
            Sound("Cheetah Roar", R.raw.cheetah_roar)
        )

        recyclerView.adapter = SoundAdapter(soundList) { sound ->
            playSound(sound.resourceId)
        }
    }

    private fun playSound(resId: Int) {
        // Stop and release previous player
        mediaPlayer?.stop()
        mediaPlayer?.release()

        // Create and start new player
        mediaPlayer = MediaPlayer.create(this, resId)
        mediaPlayer?.start()

        // Release when finished
        mediaPlayer?.setOnCompletionListener {
            it.release()
            mediaPlayer = null
        }
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
