package com.tangyx.game;

import java.io.InputStream;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.tangyx.game.util.GameMusic;
import com.tangyx.game.util.GameSoundPool;

public class GameMapActivityView extends BaseHomeActivity implements OnGestureListener,OnClickListener,OnTouchListener{
	private ViewFlipper mViewFlipper;
	private GestureDetector detector;
	private TextView mStart;
	private int selectPosition =1;
	private String file = "";
	private int imagePadding;
	private LinearLayout.LayoutParams layoutParams;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_map);
		ViewGroup root = getAllView(this);
		FontManager(root);
		mViewFlipper = (ViewFlipper) findViewById(R.id.cosview);
		detector = new GestureDetector(this);
		mStart = (TextView) findViewById(R.id.lets);
		mStart.setOnClickListener(this);
		mStart.setOnTouchListener(this);
		int clickId = getIntent().getIntExtra(getString(R.string.select),-1);
		if(clickId==-1){
			finish();
		}
		switch (clickId) {
		case R.id.selectcos1:
			file = getString(R.string.select1);
			break;
		case R.id.selectcos2:
			file = getString(R.string.select2);
			break;
		case R.id.selectcos3:
			file = getString(R.string.select3);
			break;
		case R.id.selectcos4:
			file = getString(R.string.select4);
			break;
		}
		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
		imagePadding = displayMetrics.heightPixels/7;
		int c = displayMetrics.widthPixels/6;
		int lay = displayMetrics.widthPixels-c;
		layoutParams = new LinearLayout.LayoutParams(lay,lay);
		mViewFlipper.setPadding(c/2,c, 0, 0);
		handler.sendEmptyMessage(0);
		Toast.makeText(this, getString(R.string.lr), Toast.LENGTH_SHORT).show();
	}
	Handler handler = new Handler(){
		int index=1;
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				ImageView iv = new ImageView(GameMapActivityView.this);
				iv.setPadding(imagePadding, imagePadding, imagePadding, imagePadding);
				String name = file+index+ getString(R.string.jpg);
				iv.setBackgroundDrawable(new BitmapDrawable(ReadBitMap(GameMapActivityView.this, name)));
				mViewFlipper.addView(iv,layoutParams);
				index++;
				if(index<=4){
					handler.sendEmptyMessage(0);
				}
				break;
			}
		};
	};
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.detector.onTouchEvent(event);
	}
	@Override
	public boolean onDown(MotionEvent arg0) {
		return false;
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float arg2,
			float arg3) {
		if(e1.getX() - e2.getX() > 120){
			mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.left_in));
			mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.left_out));
			mViewFlipper.showNext();
			selectPosition++;
			if(selectPosition >4){
				selectPosition =1;
			}
		}else if(e2.getX() - e1.getX() > 120){
			mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.right_in));
			mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.right_out));
			mViewFlipper.showPrevious();
			selectPosition--;
			if(selectPosition <1){
				selectPosition = 4;
			}
		}
		return false;
	}
	@Override
	public void onLongPress(MotionEvent e) {
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			mStart.setTextColor(Color.RED);
		}else if(event.getAction() == MotionEvent.ACTION_UP){
			mStart.setTextColor(Color.WHITE);
		}
		return false;
	}
	public Bitmap ReadBitMap(Context context,String resname) {
		 try {
			 BitmapFactory.Options opt = new BitmapFactory.Options();
			 opt.inPreferredConfig = Bitmap.Config.RGB_565;
			 opt.inPurgeable = true;
			 opt.inInputShareable = true;
			 InputStream is = context.getAssets().open(resname);
			 return BitmapFactory.decodeStream(is, null, opt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
	 }
	@Override
	protected void onRestart() {
		super.onRestart();
	}
    @Override
	public void onClick(View v) {
    	GameMusic.getInstance(this).pause();
		GameSoundPool.getInstance(this).play(GameSoundPool.GAMECLICK,100);
		String startName= file + selectPosition +getString(R.string.jpg);
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra(getString(R.string.type), this.getIntent().getIntExtra(getString(R.string.type), R.drawable.player0));
		intent.putExtra(getString(R.string.cosback), startName);
		intent.putExtra(getString(R.string.cos),selectPosition);
		this.startActivity(intent);
	}
}
