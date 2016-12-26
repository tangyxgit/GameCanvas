package com.tangyx.game.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.tangyx.game.util.SLog;
import com.tangyx.game.util.ScreenUtils;

/**
 * Created by tangyx on 2016/12/24.
 *
 */

public class DrawPlayerBullet extends DrawGame {
    /**
     * 子弹样式
     */
    private Bitmap mBullet;
    private float mBulletX;
    private float mBulletY;
    /**
     * 子弹模式
     */
    public final static int PLAYER_BULLET_A=1;//垂直中间发出
    public final static int PLAYER_BULLET_B=2;//左边发出
    public final static int PLAYER_BULLET_C=3;//右边发出
    public final static int PLAYER_BULLET_D=4;//双重子弹
    private int mBulletType;
    /**
     * 子弹运行速度
     */
    private float mBulletSpeed;
    /**
     * 子弹是否有效，当子弹飞出屏幕不可见的时候，这时候子弹已经失效。
     */
    private boolean isDead;


    public DrawPlayerBullet(Context context, Object... objects) {
        super(context,objects);
    }

    @Override
    void initialize(Object... objects) {//定义变量不要初始化，否则赋值失败。
        super.initialize(objects);
        mBulletX = (float) objects[0];
        mBulletY = (float) objects[1];
        mBullet = (Bitmap) objects[2];
        mBulletType = (int) objects[3];
    }

    @Override
    void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBullet,mBulletX,mBulletY,mPaint);
    }

    @Override
    void updateGame() {
        onSetSpeed();
        switch (mBulletType){
            case PLAYER_BULLET_D://垂直向上飞行
            case PLAYER_BULLET_A:
                mBulletY -=mBulletSpeed;
                break;
            case PLAYER_BULLET_B://左边倾斜向上飞行
                mBulletY -=mBulletSpeed;
                mBulletX -=mBulletSpeed/4;
                break;
            case PLAYER_BULLET_C://右边倾斜向上飞行
                mBulletY -=mBulletSpeed;
                mBulletX +=mBulletSpeed/4;
                break;
        }
        if(mBulletY<0){
            isDead = true;
        }
    }

    /**
     * 根据屏幕高度计算子弹飞行的速度
     */
    private void onSetSpeed(){
        float screenHeight = ScreenUtils.getScreenHeight(getContext());
        switch (mBulletType){
            case PLAYER_BULLET_B:
            case PLAYER_BULLET_C:
            case PLAYER_BULLET_D:
            case PLAYER_BULLET_A:
                mBulletSpeed = screenHeight / 48f;
                break;
        }
    }

    public boolean isDead() {
        return isDead;
    }

}
