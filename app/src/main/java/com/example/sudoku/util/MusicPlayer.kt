package com.example.sudoku.util

import android.media.MediaPlayer
import android.media.SoundPool
import com.example.sudoku.R
import com.example.sudoku.databinding.ActivityMainBinding

class MusicPlayer(private val soundPool: SoundPool, private val binding: ActivityMainBinding) {
    private var sound1234Volume = 0.8f
    private var clockSoundVolume = 1f
    private var soundFlag = true
    private var soundStreamId: Int? = null
    private var clockSoundStreamId: Int? = null

    fun playBackgroundMusic(music: MediaPlayer) {
        music.isLooping = true // 무한 재생
        music.start()
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
            sound1234Volume = 0f
            clockSoundVolume = 0f
            binding.soundButton.setBackgroundResource(R.drawable.baseline_volume_off_24)
        } else {
            sound1234Volume = 0.8f
            clockSoundVolume = 1f
            binding.soundButton.setBackgroundResource(R.drawable.baseline_volume_up_24)
        }
        // 현재 재생 중인 효과음의 볼륨도 업데이트
        soundStreamId?.let { soundPool.setVolume(it, sound1234Volume, sound1234Volume) }
        clockSoundStreamId?.let { soundPool.setVolume(it, clockSoundVolume, clockSoundVolume) }

        soundFlag = !soundFlag
    }

    fun playEffectSound(effectSound: Int) {
        // 효과음 재생하고 streamId 저장
        soundStreamId = soundPool.play(effectSound, sound1234Volume, sound1234Volume, 1, 0, 1f)
    }

    fun playEffectClockSound(effectSound: Int) {
        // 시계 효과음 재생하고 streamId 저장
        clockSoundStreamId = soundPool.play(effectSound, clockSoundVolume, clockSoundVolume, 1, 0, 1f)
    }
}
