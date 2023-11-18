package com.example.sudoku.model

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import com.example.sudoku.R
import com.example.sudoku.databinding.ActivityMainBinding

class MusicPlayer(val soundPool: SoundPool) {
    lateinit var binding: ActivityMainBinding
    var sound1234 = 0.8f
    var clockSound = 1f
    var soundFlag = true

    fun playBackgroundMusic() {
        music.isLooping = true; //무한재생
        music.start();
    }

    fun musicOnOff() {
        if (music.isPlaying) {
            music.pause()
            binding.musicButton.setBackgroundResource(R.drawable.baseline_music_off_24)
        } else {
            music.start()
            binding.musicButton.setBackgroundResource(R.drawable.baseline_music_note_24)
        }
    }

    fun effetOnOff() {
        if (soundFlag) {
            sound1234 = 0f
            clockSound = 0f
            binding.soundButton.setBackgroundResource(R.drawable.baseline_volume_off_24)
            soundFlag = false
        } else {
            sound1234 = 0.8f
            clockSound = 1f
            binding.soundButton.setBackgroundResource(R.drawable.baseline_volume_up_24)
            soundFlag = true
        }
    }

    fun playEffectSound(effectSound: Int) {
        soundPool!!.play(effectSound, sound1234, sound1234, 1, 0, 1f)
    }

    fun playEffectClockSound(effectSound: Int) {
        soundPool!!.play(effectSound, clockSound, clockSound, 1, 0, 1f)
    }
}