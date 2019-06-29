package me.aluceps.practicetexttospeech

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import me.aluceps.practicetexttospeech.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    private var textToSpeech: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupButton()
        setupTextToSpeech()
    }

    override fun onStart() {
        super.onStart()
        textToSpeech = TextToSpeech(this, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech?.shutdown()
    }

    override fun onInit(status: Int) {
        when (status) {
            TextToSpeech.SUCCESS -> {
                Log.d("TextToSpeech", "onInit: SUCCESS")
            }
            TextToSpeech.STOPPED -> {
                Log.d("TextToSpeech", "onInit: STOPPED")
            }
            else -> {
                Log.d("TextToSpeech", "onInit: Error=$status")
            }
        }
    }

    private fun setupButton() {
        binding.readText.setOnClickListener {
            binding.text.text.toString().toSpeech()
        }
    }

    private fun String.toSpeech() {
        if (isEmpty()) return
        textToSpeech?.let { tts ->
            if (tts.isSpeaking) return@let
            tts.speak(this, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    private fun setupTextToSpeech() {
        val result = textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                Log.d("TextToSpeech", "status: $utteranceId")
            }

            override fun onDone(utteranceId: String?) {
                Log.d("TextToSpeech", "status: $utteranceId")
            }

            override fun onError(utteranceId: String?) {
                Log.d("TextToSpeech", "status: $utteranceId")
            }
        })
        Log.d("TextToSpeech", "status: $result")
    }
}
