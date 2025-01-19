package com.example.sudoku.util

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import com.example.sudoku.R
import com.example.sudoku.util.App

class Sounds {
    private val context = App.context()
    private val audioAttributes: AudioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()
    val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(6)
        .setAudioAttributes(audioAttributes)
        .build()
    val sound1 = soundPool.load(context, R.raw.sound1, 1)
    val sound2 = soundPool.load(context, R.raw.sound2, 1)
    val sound3 = soundPool.load(context, R.raw.sound3, 1)
    val sound4 = soundPool.load(context, R.raw.sound4, 1)
    val clock = soundPool.load(context, R.raw.clock, 1)
}