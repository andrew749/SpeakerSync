package com.andrew749.speakersync;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by andrew on 1/24/15.
 */
public class Player extends Activity {
    private SeekBar seekBar;
    private ImageButton pauseButton, playButton;
    private TextView songTitle, currentTime, songDuration;
    private byte[] byteStream;

    enum PLAY_STATE {NOT_PLAYING, PLAYING, PAUSED, STOPPED}

    ;
    public PLAY_STATE state;
    static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songTitle = (TextView) findViewById(R.id.songName);
        currentTime = (TextView) findViewById(R.id.curTime);
        songDuration = (TextView) findViewById(R.id.fullTime);
        playButton = (ImageButton) findViewById(R.id.playButton);
        pauseButton = (ImageButton) findViewById(R.id.pauseButton);
        byteStream = getIntent().getByteArrayExtra("Data");
        playMp3(byteStream);
    }

    private void playMp3(byte[] mp3SoundByteArray) {
        try {
            // create temp file that will hold byte array
            File tempMp3 = File.createTempFile("kurchina", "mp3", getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(mp3SoundByteArray);
            fos.close();

            // Tried reusing instance of media player
            // but that resulted in system crashes...
            mediaPlayer = new MediaPlayer();

            // Tried passing path directly, but kept getting
            // "Prepare failed.: status=0x1"
            // so using file descriptor instead
            FileInputStream fis = new FileInputStream(tempMp3);
            mediaPlayer.setDataSource(fis.getFD());

            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException ex) {
            String s = ex.toString();
            ex.printStackTrace();
        }
    }
    public void resumeMP3(){
        if(state==PLAY_STATE.PAUSED){
            mediaPlayer.start();
        }
    }
    public  void stopMP3(){
        if(state==PLAY_STATE.PLAYING||state==PLAY_STATE.PAUSED){
            mediaPlayer.stop();
        }
    }
    public void pauseMP3() {
        if (state == PLAY_STATE.PLAYING) {
            mediaPlayer.pause();
        }
    }
}
