package com.tangyx.game.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.tangyx.game.util.ScreenUtils;

/**
 * Created by tangyx on 2016/12/29.
 *
 */

public class DrawEnemyBullet extends DrawGame {

    public final static int BULLET_A=-2;
    public final static int BULLET_B=1;
    public final static int BULLET_C=2;
    public final static int BULLET_E=-5;
    public final static int BULLET_F=-6;
    /**
     * 子弹资源
     */
    private Bitmap mEnemyBullet;
    /**
     * 子弹常量
     */
    private float mBulletX, mBulletY;
    /**
     * 当前子弹类型
     */
    private int mBulletType;
    /**
     * 子弹自由落体的速度
     */
    private float mSpeed;
    /**
     *  子弹x，y分别运行的速度
     */
    private float mSpeedX, mSpeedY;
    /**
     * 子弹是否已经失效，比如飞出屏幕不可见。
     */
    private boolean isDead;
    /**
     * enemy子弹普通模式
     * @param bmpBullet
     * @param bmpEnemy
     * @param bulletType
     */
    public DrawEnemyBullet(Context context,Bitmap bmpBullet, DrawEnemy bmpEnemy, int bulletType) {
        super(context);
        this.mEnemyBullet = bmpBullet;
        this.mBulletX = bmpEnemy.getEnemyX()+(bmpEnemy.getWidth()/2);
        this.mBulletY =bmpEnemy.getEnemyY()+bmpEnemy.getHeight()/2;
        this.mBulletType = bulletType;
        onSetSpeed(bmpEnemy);
    }
    /**
     * 子弹自动追踪模式
     */
    public DrawEnemyBullet(Context context,Bitmap bmpBullet,DrawPlayer player,float ex,float ey,int bulletType) {
        super(context);
        this.mEnemyBullet = bmpBullet;
        this.mBulletX = ex;
        this.mBulletY =ey;
        this.mBulletType = bulletType;
        float cx = player.getPlayerX() - mBulletX;
        float cy = player.getPlayerY() - mBulletY;
        mSpeedY =((float) ScreenUtils.getScreenHeight(context))/200;
        if(cy<0){
            mSpeedY =-mSpeedY;
        }
        float runtime = cy/ mSpeedY;
        mSpeedX = cx / runtime;
        if(cx>0&& mSpeedX >=5){
            mSpeedX =1;

        }else if(cx<0&& mSpeedX <=-5){
            mSpeedX =-1;
        }
    }

    @Override
    void onDraw(Canvas canvas) {
        canvas.save();
        switch (mBulletType) {
            case BULLET_E:
                canvas.drawBitmap(mEnemyBullet, mBulletX, mBulletY,mPaint);
                break;
            default:
                canvas.drawBitmap(mEnemyBullet, mBulletX, mBulletY,mPaint);
                break;
        }
        canvas.restore();
    }

    @Override
    void updateGame() {
        float screenH = ScreenUtils.getScreenHeight(getContext());
        float screenW = ScreenUtils.getScreenWidth(getContext());
        switch (mBulletType) {
            case BULLET_A://自由落体轨迹
                mBulletY += mSpeed;
                if(mBulletY >screenH){
                    isDead=true;
                }
                break;
            case BULLET_B://自由落体
                mBulletY += mSpeed;
                if(mBulletY >screenH){
                    isDead=true;
                }
                break;
            case BULLET_C://跟踪
                mBulletY += mSpeedY;
                mBulletX += mSpeedX;
                if(mBulletY <0|| mBulletY >screenH|| mBulletX >screenW|| mBulletX <0){
                    isDead=true;
                }
                break;
        }
    }

    /**
     * 分配子弹速度
     */
    private void onSetSpeed(DrawEnemy bmpEnemy){
        float screenH = ScreenUtils.getScreenHeight(getContext());
        switch (mBulletType) {
            case BULLET_A:
                mSpeed =bmpEnemy.getEnemySpeed()+5;
                break;
            case BULLET_B:
                mSpeed =bmpEnemy.getEnemySpeed()+3;
                break;
            case BULLET_C:
                mSpeed =bmpEnemy.getEnemySpeed()+5;
                break;
            case BULLET_E:
                mSpeed =screenH/80;
                break;
            case BULLET_F:
                mSpeed =screenH/100;
                break;
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public float getBulletX() {
        return mBulletX;
    }

    public float getBulletY() {
        return mBulletY;
    }

    public int getWidth(){
        return mEnemyBullet.getWidth();
    }
    public int getHeight(){
        return mEnemyBullet.getHeight();
    }
}
