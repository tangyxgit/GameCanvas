package com.tangyx.game.util;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

/**
 * Create tangyx 2016/12/20
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

	/**
	 * 准备好播放的音乐资源
	 * @param musicName
     */
	public void prepareMediaPlayer(String musicName){
		mMediaPlayer.setOnCompletionListener(this);
		AssetFileDescriptor descriptor;
		try {
			//音乐文件在assets文件夹下
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

	/**
	 * 播放音乐
	 * @param loop
     */
	public void play(boolean loop){
		if(mMediaPlayer !=null){
			mMediaPlayer.setLooping(loop);
			mMediaPlayer.start();
			isPlay =true;
		}
	}

	/**
	 * 停止音乐
	 */
	public void stop(){
		if(mMediaPlayer !=null&& isPlay){
			mMediaPlayer.stop();
		}
	}

	/**
	 * 暂停音乐
	 */
	public void pause(){
		if(mMediaPlayer !=null&& isPlay){
			mMediaPlayer.pause();
		}
	}

	/**
	 * 暂停后继续播放
	 */
	public void start(){
		if(mMediaPlayer !=null){
			mMediaPlayer.start();
			isPlay =true;
		}
	}
	public void restart(){
		mMediaPlayer.seekTo(0);
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
	public void onCompletion(MediaPlayer mp) {
		/**
		 * 释放资源
		 */
		mp.release();
	}
}
