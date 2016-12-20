package com.tangyx.game;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.tangyx.game.util.GameMusic;
import com.tangyx.game.util.GameScaleAnimation;
import com.tangyx.game.util.GameSoundPool;

public class MainActivity extends BaseHomeActivity  implements OnClickListener,OnTouchListener,AnimationListener{
	private final static int ClickColor=Color.BLUE;
	private TextView mStart, mAbout, mQuit;
	private ImageView mPlayer1, mPlayer2, mPlayer1Bullet, mPlayer2Bullet;
	private TranslateAnimation mTranslateAnimation;
	private GameScaleAnimation mGameAnimation;
	private boolean mPlayerBullet;
	private int mType =0;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		ViewGroup root = getAllView(this);
		FontManager(root);
		mStart = (TextView) findViewById(R.id.start);
		mAbout = (TextView) findViewById(R.id.me);
		mQuit = (TextView) findViewById(R.id.end);
		mPlayer1 = (ImageView) findViewById(R.id.playo);
		mPlayer2 = (ImageView) findViewById(R.id.playt);
		mStart.setOnClickListener(this);
		mStart.setOnTouchListener(this);
		mStart.setTextColor(ClickColor);
		mAbout.setOnClickListener(this);
		mAbout.setOnTouchListener(this);
		mAbout.setTextColor(ClickColor);
		mQuit.setOnClickListener(this);
		mQuit.setOnTouchListener(this);
		mQuit.setTextColor(ClickColor);
		mPlayer1.setOnClickListener(this);
		mPlayer2.setOnClickListener(this);
		mPlayer1Bullet = (ImageView) findViewById(R.id.pb0);
		mPlayer2Bullet = (ImageView) findViewById(R.id.pb1);
		mGameAnimation = new GameScaleAnimation(1000);
		GameMusic.getInstance(this).prepareMediaPlayer(getString(R.string.menumusic));
		GameMusic.getInstance(this).play(true);
		mType = R.id.playo;
		GameSoundPool.getInstance(this).addMusic(GameSoundPool.GAMECLICK, R.raw.click,0);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if(!mPlayerBullet){
			//获取在屏幕的绝对坐标，最左上角开始计算
			int[] local = new int[2];
			mPlayer1Bullet.getLocationOnScreen(local);
			mTranslateAnimation = new TranslateAnimation(0, 0,local[1],0);
			mTranslateAnimation.setDuration(100);
			mTranslateAnimation.setAnimationListener(this);
			mPlayer1Bullet.startAnimation(mTranslateAnimation);
			mPlayer2Bullet.setVisibility(View.GONE);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start:
			AnimaCilck(mStart,0);
			break;
		case R.id.end:
			AnimaCilck(mQuit,2);
			break;
		case R.id.playo:
			selectPlayer(mPlayer1);
			break;
		case R.id.playt:
			selectPlayer(mPlayer2);
			break;
		}
	}
	Handler hander = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				if(!mGameAnimation.isBol()){
					Intent intent = new Intent(MainActivity.this,SceneActivity.class);
					intent.putExtra(getString(R.string.type), mType);
					startActivity(intent);
				}else{
					hander.sendEmptyMessage(0);
				}
				break;
			case 1:
				if(!mGameAnimation.isBol()){
					mGameAnimation.stopAnima();
				}else{
					hander.sendEmptyMessage(1);
				}
				break;
			case 2:
				if(!mGameAnimation.isBol()){
					finish();
				}else{
					hander.sendEmptyMessage(2);
				}
				break;
			case 3:
				
				break;
			case 4:
				hander.sendEmptyMessage(4);
				break;
			}
		};
	};
	/**
	 */
	private void AnimaCilck(View iv,int type){
		iv.clearAnimation();
		mGameAnimation.setBol(true);
		mGameAnimation.scale0.setDuration(100);
		mGameAnimation.scale1.setDuration(100);
		mGameAnimation.startAnima(iv);
		hander.sendEmptyMessage(type);
	}
	@Override
	protected void onResume() {
		super.onResume();
		GameMusic.getInstance(this).onResume();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		GameMusic.getInstance(this).stop();
	}
	@Override
	protected void onRestart() {
		super.onRestart();
		playerAnimation(mPlayer1Bullet);
		mType = R.id.playo;
	}
	/**
	 */
	private void selectPlayer(ImageView play){
		mType = play.getId();
		if(play.hashCode()== mPlayer1.hashCode()){
			mPlayerBullet =false;
			playerAnimation(mPlayer1Bullet);
		}else{
			playerAnimation(mPlayer2Bullet);
		}
	}
	private void playerAnimation(ImageView pb){
		if(pb.hashCode() == mPlayer1Bullet.hashCode()){
			mPlayerBullet =false;
			mPlayer1Bullet.setVisibility(View.VISIBLE);
			mPlayer1Bullet.startAnimation(mTranslateAnimation);
			mPlayer2Bullet.setVisibility(View.GONE);
			mPlayer2Bullet.clearAnimation();
		}else{
			mPlayerBullet = true;
			mPlayer2Bullet.setVisibility(View.VISIBLE);
			mPlayer2Bullet.startAnimation(mTranslateAnimation);
			mPlayer1Bullet.setVisibility(View.GONE);
			mPlayer1Bullet.clearAnimation();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		System.out.println(event.getAction());
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			((TextView) v).setTextColor(Color.RED);
		}else if(event.getAction() == MotionEvent.ACTION_UP){
			mStart.setTextColor(ClickColor);
			mAbout.setTextColor(ClickColor);
			mQuit.setTextColor(ClickColor);
		}
		return false;
	}
	@Override
	public void onAnimationEnd(Animation arg0) {
		if(mPlayerBullet){
			mPlayer2Bullet.startAnimation(mTranslateAnimation);
		}else{
			mPlayer1Bullet.startAnimation(mTranslateAnimation);
		}
	}
	@Override
	public void onAnimationRepeat(Animation animation) {
		
	}
	@Override
	public void onAnimationStart(Animation animation) {
		
	}
}
