package com.greattone.greattone.MusicPlay;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicPlay {
	static MusicPlay musicPlay=new MusicPlay();
	MediaPlayer myMediaPlayer;
	String urlPath;
	Context context;
	boolean isPrepare;
	public static void build(Context context,String urlPath) {
		musicPlay.context=context;
		musicPlay.urlPath=urlPath;
		musicPlay.	init();
	}
	public static void start() {
		if (musicPlay.myMediaPlayer!=null&&musicPlay.isPrepare) {
			musicPlay.myMediaPlayer.start();
		}
	}
	public static void stop() {
		if (musicPlay.myMediaPlayer!=null&&musicPlay.myMediaPlayer.isPlaying()) {
			musicPlay.myMediaPlayer.stop();
		}
	}
	public static void pause() {
		if (musicPlay.myMediaPlayer!=null&&musicPlay.myMediaPlayer.isPlaying()) {
			musicPlay.myMediaPlayer.stop();
		}
	}
	public static void finish() {
		if (musicPlay.myMediaPlayer!=null) {
			musicPlay.	   myMediaPlayer.stop();
			musicPlay. myMediaPlayer.release();
		}
	}
	private void init() {
		  myMediaPlayer=new MediaPlayer();
			try {
				myMediaPlayer.reset();
				myMediaPlayer.setDataSource(urlPath);
				myMediaPlayer.setLooping(true);
//				myMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				myMediaPlayer.prepareAsync();//异步的准备
				myMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
		            @Override
		            public void onPrepared(MediaPlayer mp) {
		            	isPrepare=true;
//		                int max = mp.getDuration();
//		                song_progress_normal.setMax(max);
		            }
		        });
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
}
