package com.tangyx.game.util;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

/**
 * 背景音乐
 */
public class GameMusic implements OnCompletionListener{
	private static GameMusic INSTANCE;
	private Context mContext;
	private MediaPlayer mMediaPlayer;
	private boolean isResume;
	private boolean isPlay;

	public static GameMusic getInstance(Context context){
		if(INSTANCE==null){
			INSTANCE = new GameMusic(context);
		}
		return INSTANCE;
	}

	private GameMusic(Context ac) {
		this.mContext = ac;
		mMediaPlayer = new MediaPlayer();
	}

	public void prepareMediaPlayer(String musicName){
		mMediaPlayer.setOnCompletionListener(this);
		AssetFileDescriptor descriptor;
		try {
			descriptor = mContext.getAssets().openFd("dj/"+musicName);
			if(descriptor!=null){
				mMediaPlayer.reset();
				mMediaPlayer.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(),descriptor.getDeclaredLength());
				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				descriptor.close();
				mMediaPlayer.prepare();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void prepareMediaPlayer(AssetFileDescriptor descriptor){
		try {
			if(descriptor!=null){
				mMediaPlayer.release();
				mMediaPlayer.reset();
				mMediaPlayer.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(),descriptor.getDeclaredLength());
				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				descriptor.close();
				mMediaPlayer.prepare();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void play(boolean loop){
		if(mMediaPlayer !=null){
			mMediaPlayer.setLooping(loop);
			mMediaPlayer.start();
			isPlay =true;
		}
	}
	public void stop(){
		if(mMediaPlayer !=null&& isPlay){
			mMediaPlayer.stop();
		}
	}
	public void pause(){
		if(mMediaPlayer !=null&& isPlay){
			mMediaPlayer.pause();
		}
	}
	public void start(){
		if(mMediaPlayer !=null){
			mMediaPlayer.start();
			isPlay =true;
		}
	}
	public void restart(){
		mMediaPlayer.seekTo(0);
	}
	public void onPause(){
		if(mMediaPlayer !=null){
			if(mMediaPlayer.isPlaying()){
				mMediaPlayer.pause();
				isResume = true;
			}
		}
	}
	public void onResume(){
		if(mMediaPlayer !=null){
			if(isResume){
				mMediaPlayer.start();
				isResume = false;
			}
		}
	}
	@Override
	public void onCompletion(MediaPlayer mp) {//�ͷ���Դ
		mp.release();
	}
}
