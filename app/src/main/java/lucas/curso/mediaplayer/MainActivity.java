package lucas.curso.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private Button btPlay, btPause, btPara;
    private SeekBar seekBar;
    private MediaPlayer mp;

    private AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btPlay = findViewById(R.id.bt_play);
        btPause = findViewById(R.id.bt_pause);
        btPara = findViewById(R.id.bt_stop);

        mp = MediaPlayer.create(getApplicationContext(), R.raw.teste);
        iniciarSeekBar();
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp != null){
                    mp.start();
                }
            }
        });


        btPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp.isPlaying()){
                    mp.pause();
                }
            }
        });

        btPara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp.isPlaying()){
                    mp.stop();
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.teste);
                }
            }
        });
    }
    private void iniciarSeekBar(){
        seekBar = findViewById(R.id.seekVolume);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int voluMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int voluAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        seekBar.setMax(voluMax);
        seekBar.setProgress(voluAtual);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mp.isPlaying()){
            mp.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mp != null && mp.isPlaying()){
            mp.stop();
            mp.release();
            mp = null;
        }
    }
}