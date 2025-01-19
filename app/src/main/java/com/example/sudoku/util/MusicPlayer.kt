import android.content.Context
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
    private var backgroundMusic: MediaPlayer? = null

    fun playBackgroundMusic(context: Context, resId: Int) {
        if (backgroundMusic == null) {
            backgroundMusic = MediaPlayer.create(context, resId)
            backgroundMusic?.isLooping = true
            backgroundMusic?.start()
        } else if (!backgroundMusic!!.isPlaying) {
            backgroundMusic?.start()
        }
    }

    fun stopBackgroundMusic() {
        backgroundMusic?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
        backgroundMusic = null
    }

    fun musicOnOff(context: Context, resId: Int) {
        if (backgroundMusic?.isPlaying == true) {
            stopBackgroundMusic()
            binding.musicButton.setBackgroundResource(R.drawable.baseline_music_off_24)
        } else {
            playBackgroundMusic(context, resId)
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

        soundStreamId?.let { soundPool.setVolume(it, sound1234Volume, sound1234Volume) }
        clockSoundStreamId?.let { soundPool.setVolume(it, clockSoundVolume, clockSoundVolume) }

        soundFlag = !soundFlag
    }

    fun playEffectSound(effectSound: Int) {
        soundStreamId = soundPool.play(effectSound, sound1234Volume, sound1234Volume, 1, 0, 1f)
    }
    fun playEffectClockSound(effectSound: Int) {
        clockSoundStreamId = soundPool.play(effectSound, clockSoundVolume, clockSoundVolume, 1, 0, 1f)
    }

    fun release() {
        stopBackgroundMusic()
        soundPool.release()
    }
}

