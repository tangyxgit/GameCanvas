package com.tangyx.game.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
/**
 * 按钮点击反转的动画效果。
 */
public class GameScaleAnimation implements AnimationListener{
	public ScaleAnimation scale0;
	public ScaleAnimation scale1;
	private View iv;
	private boolean bol;
	private int duration;
	public GameScaleAnimation(int duration) {
		scale0 = new ScaleAnimation(1, 0, 1, 1, 1, 0.5f, 1, 0);
		scale1 = new ScaleAnimation(0, 1, 1, 1, 1, 0.5f,100, 0);
		scale0.setDuration(duration);
		scale1.setDuration(duration);
		scale0.setAnimationListener(this);
		scale1.setAnimationListener(this);
		this.duration = duration;
	}

	/**
	 *
	 * @param duration 动画执行的时常
	 * @param isBol 是否执行结束
     */
	public GameScaleAnimation(int duration,boolean isBol) {
		scale0 = new ScaleAnimation(1, 0, 1, 1, 1, 0.5f, 1, 0);
		scale1 = new ScaleAnimation(0, 1, 1, 1, 1, 0.5f,100, 0);
		scale0.setDuration(duration);
		scale1.setDuration(duration);
		scale0.setAnimationListener(this);
		scale1.setAnimationListener(this);
		this.duration = duration;
		this.bol = isBol;
	}
	public void startAnimation(View civ){
		stopAnimation();
		iv = civ;
		iv.startAnimation(scale0);
	}
	public void stopAnimation(){
		if(iv!=null){
			iv.clearAnimation();
		}
	}

	/**
	 * 动画执行完成
	 * @param animation
     */
	@Override
	public void onAnimationEnd(Animation animation) {
		if(!bol){
			if(animation.hashCode()==scale0.hashCode()){
				iv.startAnimation(scale1);
			}else{
				iv.startAnimation(scale0);
			}
		}else{
			if(animation.hashCode()==scale0.hashCode()){
				iv.startAnimation(scale1);
			}else{
				scale0.setDuration(duration);
				scale1.setDuration(duration);
				iv.startAnimation(scale0);
				iv.clearAnimation();
				bol = false;
			}
		}
	}
	@Override
	public void onAnimationRepeat(Animation animation) {
		
	}
	@Override
	public void onAnimationStart(Animation animation) {
		
	}
	public boolean isBol() {
		return bol;
	}
	public void setBol(boolean bol) {
		this.bol = bol;
	}
}
