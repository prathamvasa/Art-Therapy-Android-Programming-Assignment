package com.example.i851409.arttherapy;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;


public class MusicPlay extends IntentService {
    private boolean flag = false;

    //Implementing the constructor for this class
    public MusicPlay() {
        super("Intent Service Hii");
    }

    //Invoke the method to handle the intent service
    protected void onHandleIntent(Intent intent) {

        //Instantiating the MediaPlayer class
        MediaPlayer media_player = MediaPlayer.create(MusicPlay.this, R.raw.eraser);
        media_player.start();
        while(media_player.isPlaying())
        {
            System.out.println();
        }

        //Reset and refresh the media player music clip
        media_player.reset();
        media_player.release();

    }

}
