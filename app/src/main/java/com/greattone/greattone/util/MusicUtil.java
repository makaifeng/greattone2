package com.greattone.greattone.util;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class MusicUtil {

	private static ListenOverBack back;
	private static String currenPath;
	private static String fileNames;
	private static boolean isOver = true;
	private static MediaRecorder mediaRecorder;
	private static MediaPlayer player;

	public static final String SDK = Environment.getExternalStorageDirectory()
			.getAbsolutePath();
	public static final String PICTUREPATH = SDK + "/haoqinsheng/image/";
	public static final String QQFACE = "qqface.txt";
	public static final String VOICEPATH = SDK + "/haoqingsheng/vocie/";

	private static String getFileName(boolean paramBoolean) {
		if (paramBoolean)
			fileNames = "";
		if (fileNames.isEmpty())
			fileNames = System.currentTimeMillis() + ".amr";
		return fileNames;
	}

	public static String getVoiceName() {
		return VOICEPATH + fileNames;
	}

	public static boolean isOverPlay() {
		return isOver;
	}

	public static void StartVoice() {
		StartVoice(fileNames, null);
	}

	public static void StartVoice(String path,
			ListenOverBack listenOverBack) {
		back = listenOverBack;
		if (path.equals(currenPath)) {
			isOver = true;
			currenPath = "";
			back.cancle();
			player.stop();
			return;
		}
		if (player != null)
			player = null;
		currenPath = path;
		player = new MediaPlayer();
		player.reset();
		try {
			if (path.startsWith("http:")) {
				player.setDataSource(path);
			} else {
				player.setDataSource(VOICEPATH + path);
			}
			player.setLooping(false);
			player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
				public void onPrepared(MediaPlayer mediaPlayer) {
					MusicUtil.isOver = false;
					MusicUtil.player.start();
				}
			});
			player.prepare();
			player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				public void onCompletion(MediaPlayer mediaPlayer) {
					MusicUtil.isOver = true;
					if (MusicUtil.back != null)
						MusicUtil.back.back();
				}
			});
		} catch (IllegalArgumentException exception) {
			isOver = true;
			exception.printStackTrace();
		} catch (SecurityException exception) {
			isOver = true;
			exception.printStackTrace();
		} catch (IllegalStateException exception) {
			isOver = true;
			exception.printStackTrace();
		} catch (IOException exception) {
			isOver = true;
			exception.printStackTrace();
		}
	}

	public static void startRecord() {
		startRecord(false);
	}

	public static void startRecord(String string) {
		if (mediaRecorder != null)
			mediaRecorder = null;
		File localFile = new File(VOICEPATH);
		if (!localFile.exists()) {
			localFile.mkdir();
			localFile.mkdirs();
		}
		try {
			mediaRecorder = new MediaRecorder();
			mediaRecorder.setAudioSource(1);
			mediaRecorder.setOutputFormat(3);
			mediaRecorder.setAudioEncoder(1);
			mediaRecorder.setOutputFile(VOICEPATH + string);
			mediaRecorder.prepare();
			mediaRecorder.start();
			return;
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	public static void startRecord(boolean Boolean) {
		startRecord(getFileName(Boolean));
	}

	public static void stopRecord() {
		if (mediaRecorder == null)
			return;
		try {
			mediaRecorder.stop();
			mediaRecorder.reset();
			mediaRecorder.release();
			mediaRecorder = null;
		} catch (Exception exception) {
				Log.e("info", "结束录音异常了");
		}
	}

	public static abstract interface ListenOverBack {
		public abstract void back();

		public abstract void cancle();
	}

}
