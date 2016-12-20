package com.tangyx.game.util;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * 点击操作的音效
 */
public class GameSoundPool {
	public static GameSoundPool INSTANCE;
	public final static int LAUNCHER=0;
	public final static int PLAYERBULLET=1;
	public final static int GAMECLICK=2;
	public final static int ENEMYCLEARA=3;
	public final static int ENEMYCLEARB=4;
	public final static int ENEMYCLEARC=5;
	public int mVolume =15;
	private SoundPool mSoundPool;
	private Context mContext;
	private int dex;
	private Map<Integer, Integer> mSoundMap;
	public static GameSoundPool getInstance(Context context){
		if(INSTANCE==null){
			INSTANCE = new GameSoundPool(context);
		}
		return INSTANCE;
	}
	private GameSoundPool(Context context) {
		mSoundPool = new SoundPool(10,AudioManager.STREAM_MUSIC, 100);
		mSoundMap = new HashMap<>();
		this.mContext = context;
	}
	/**
	 * add music
	 */
	public void addMusic(int id,int resId,int priority){
		mSoundMap.put(id, mSoundPool.load(mContext, resId, priority));
	}
	public void play(int soundId,int priority){
		mSoundPool.play(mSoundMap.get(soundId), mVolume, mVolume, priority, 0, 1f);
	}
	public void clear(){
		if(mSoundMap.size()>0){
			while(dex< mSoundMap.size()){
				if(mSoundMap.get(dex)!=null){
					mSoundPool.unload(mSoundMap.get(dex));
				}
				dex++;
			}
		}
	}
}
