package com.tangyx.game.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import com.tangyx.game.R;
import com.tangyx.game.util.BitmapUtils;
import com.tangyx.game.util.ScreenUtils;
import com.tangyx.game.util.SizeUtils;

/**
 * Created by tangyx on 2016/12/22.
 *
 */

public class DrawPlayer extends DrawGame {
    /**
     * 主角
     */
    private Bitmap mPlayer;
    private float mPlayerX;
    private float mPlayerY;
    /**
     * 主角的控制位置
     */
    private Bitmap mCollect;
    private float mCollectX;
    private float mCollectY;
    private int mCollectCount;
    private Paint mCollectPaint;
    /**
     * 尾气喷气
     */
    private Bitmap mPlayerBlow;
    private float mSpeedAngle=0.05f;
    private float mBlowAngle = 1;
    private Matrix mBlowMatrix;

    public DrawPlayer(Context context,int player) {
        super(context,player);
    }

    @Override
    void initialize(Object... objects) {
        mPlayer = BitmapUtils.ReadBitMap(getContext(), (Integer) objects[0]);
        int wh = SizeUtils.dp2px(getContext(),20);
        mPlayer = BitmapUtils.getBitmap(mPlayer,wh,wh);
        mPlayerBlow = BitmapUtils.ReadBitMap(getContext(), R.drawable.playblow0);
        mPlayerBlow = BitmapUtils.getBitmap(mPlayerBlow,wh,wh);
        mCollect = BitmapUtils.ReadBitMap(getContext(),R.drawable.collect);
        wh = SizeUtils.dp2px(getContext(),30);
        mCollect = BitmapUtils.getBitmap(mCollect,wh,wh);
        int screenW = ScreenUtils.getScreenWidth(getContext());
        int screenH = ScreenUtils.getScreenHeight(getContext());
        //初始化战机的位置
        mPlayerX = screenW/2-mPlayer.getWidth()/2;
        mPlayerY = screenH-mPlayer.getHeight()-screenH/10;
        mCollectX = screenW/2-mCollect.getWidth()/2;
        mCollectY = mPlayerY+mPlayer.getHeight()+mCollect.getHeight()/2;
        mBlowMatrix = new Matrix();
        mCollectPaint = new Paint();
        mCollectPaint.setAntiAlias(true);
    }

    @Override
    void onDraw(Canvas canvas) {
        mPaint.setAlpha(255);
        canvas.drawBitmap(mPlayer,mPlayerX,mPlayerY,mPaint);
        canvas.drawBitmap(getBlowAnimation(), (mPlayerX-mPlayerBlow.getWidth()/4),mPlayerY+mPlayerBlow.getHeight()/1.5f,mPaint);
    }

    /**
     * 尾气喷气动画
     */
    private Bitmap getBlowAnimation(){
        mBlowAngle += mSpeedAngle;
        float sx =1.5f;
        float sy = mBlowAngle;
        mBlowMatrix.reset();
        mBlowMatrix.postScale(sx,sy);
        if(mBlowAngle>=1.2||mBlowAngle<1){
            mSpeedAngle=-mSpeedAngle;
        }
        return Bitmap.createBitmap(mPlayerBlow,0,0,mPlayerBlow.getWidth(),mPlayerBlow.getHeight(),mBlowMatrix,true);
    }

    /**
     * 绘制操作的位置按钮
     */
    public void onDrawCollect(Canvas canvas,String text){
        if(mCollectCount==(Integer.MAX_VALUE-1)){
            mCollectCount=0;
        }
        mCollectPaint.setTextSize(20f);
        mCollectCount++;
        if(mCollectCount%2==0){
            mCollectPaint.setAlpha(0);
        }else{
            mCollectPaint.setAlpha(255);
        }
        mCollectX = mPlayerX-(mCollect.getWidth()-mPlayer.getWidth())/2;
        mCollectY = mPlayerY+mPlayer.getHeight()+mCollect.getHeight()/2;
        canvas.drawBitmap(mCollect,mCollectX,mCollectY,mCollectPaint);
        String fire = getContext().getString(R.string.fire);
        Rect rect = getTextRect(fire,mCollectPaint);
        float tx = mCollectX+(mCollect.getWidth()-rect.width())/2;
        float ty = mCollectY+mCollect.getHeight()/2+rect.height()/2;
        //点击这个地方游戏继续
        canvas.drawText(fire, tx, ty, mCollectPaint);
        int w = ScreenUtils.getScreenWidth(getContext());
        int h = ScreenUtils.getScreenHeight(getContext());
        mPaint.setTextSize(SizeUtils.sp2px(getContext(),20));
        //游戏暂停提示语
        rect = getTextRect(text,mPaint);
        canvas.drawText(text,(w-rect.width())/2 ,h/3, mPaint);
    }

    @Override
    void updateGame() {

    }

    public void setPlayerX(float mPlayerX) {
        this.mPlayerX = mPlayerX-mPlayer.getWidth()/1.5f;
    }

    public void setPlayerY(float mPlayerY) {
        this.mPlayerY = mPlayerY-mPlayer.getHeight() * 2.5f;
    }

    public float getCollectX() {
        return mCollectX;
    }

    public float getCollectY() {
        return mCollectY;
    }


    public Bitmap getCollect() {
        return mCollect;
    }
}
