package com.tangyx.game;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.tangyx.game.util.GameScaleAnimation;
import com.tangyx.game.util.GameSoundPool;

public class SceneActivity extends BaseHomeActivity implements OnClickListener{
	private ImageView mSelect1, mSelect2, mSelect3, mSelect4;
	private GameScaleAnimation mScaleAnimation;
	private boolean isAnimation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scene);
		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
    	int screenWidth = displayMetrics.widthPixels;
    	int screenHeight = displayMetrics.heightPixels;
		TranslateAnimation animationRight = new TranslateAnimation(screenWidth, 0, 0, 0);
		animationRight.setDuration(1500);
		TranslateAnimation animationBottom = new TranslateAnimation(0, 0, screenHeight, 0);
		animationBottom.setDuration(1500);
		TranslateAnimation animationLeft = new TranslateAnimation(-screenWidth, 0, 0, 0);
		animationLeft.setDuration(1800);
		TranslateAnimation animationTop = new TranslateAnimation(0, 0, -screenHeight, 0);
		animationTop.setDuration(2000);
		mSelect1 = (ImageView) findViewById(R.id.selectcos1);
		mSelect1.setOnClickListener(this);
		mSelect1.startAnimation(animationRight);
		mSelect2 = (ImageView) findViewById(R.id.selectcos2);
		mSelect2.setOnClickListener(this);
		mSelect2.startAnimation(animationLeft);
		mSelect3 = (ImageView) findViewById(R.id.selectcos3);
		mSelect3.setOnClickListener(this);
		mSelect3.setAnimation(animationBottom);
		mSelect4 = (ImageView) findViewById(R.id.selectcos4);
		mSelect4.setOnClickListener(this);
		mSelect4.setAnimation(animationTop);
		mScaleAnimation = new GameScaleAnimation(100,true);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.selectcos1:
			clickAnimation(mSelect1);
			break;
		case R.id.selectcos2:
			clickAnimation(mSelect2);
			break;
		case R.id.selectcos3:
			clickAnimation(mSelect3);
			break;
		case R.id.selectcos4:
			clickAnimation(mSelect4);
			break;
		}
	}
	private Intent intent;
	private void clickAnimation(View v){
		if(!isAnimation){
			GameSoundPool.getInstance(this).play(GameSoundPool.GAMECLICK,100);
			isAnimation = true;
			mScaleAnimation.startAnima(v);
			intent = new Intent(this,GameMapActivityView.class);
			intent.putExtra(getString(R.string.select), v.getId());
			intent.putExtra(getString(R.string.type), this.getIntent().getIntExtra(getString(R.string.type), R.drawable.player0));
			handler.sendEmptyMessage(0);
		}
	}
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				if(!mScaleAnimation.isBol()){
					isAnimation =false;
					mScaleAnimation.setBol(true);
					startActivity(intent);
				}else{
					handler.sendEmptyMessage(0);
				}
				break;
			}
		};
	};
}
