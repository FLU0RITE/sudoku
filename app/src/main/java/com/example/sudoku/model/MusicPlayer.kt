package com.example.sudoku.model

import android.media.MediaPlayer
import android.media.SoundPool
import com.example.sudoku.R
import com.example.sudoku.databinding.ActivityMainBinding

class MusicPlayer(val soundPool: SoundPool, val binding: ActivityMainBinding) {
    var sound1234 = 0.8f
    var clockSound = 1f
    var soundFlag = true

    fun playBackgroundMusic(music: MediaPlayer) {
        music.isLooping = true; //무한재생
        music.start();
    }

    fun musicOnOff(music: MediaPlayer) {
        if (music.isPlaying) {
            music.pause()
            binding.musicButton.setBackgroundResource(R.drawable.baseline_music_off_24)
        } else {
            music.start()
            binding.musicButton.setBackgroundResource(R.drawable.baseline_music_note_24)
        }
    }

    fun effectOnOff() {
        if (soundFlag) {
            sound1234 = 0f
            clockSound = 0f
            binding.soundButton.setBackgroundResource(R.drawable.baseline_volume_off_24)
            soundFlag = false
        } else {
            sound1234 = 0.8f
            clockSound = 2f
            binding.soundButton.setBackgroundResource(R.drawable.baseline_volume_up_24)
            soundFlag = true
        }
    }

    fun playEffectSound(effectSound: Int) {
        soundPool.play(effectSound, sound1234, sound1234, 1, 0, 1f)
    }

    fun playEffectClockSound(effectSound: Int) {
        soundPool.play(effectSound, clockSound, clockSound, 1, 0, 1f)
    }
}