package com.tangyx.game.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.tangyx.game.factory.Animation;
import com.tangyx.game.util.ScreenUtils;

/**
 * Created by tangyx on 2017/1/5.
 *
 */

public class DrawBoom extends DrawGame {

    final static int TYPE_A=1;
    final static int TYPE_B=2;
    private float mBoomX, mBoomY;
    private float mSpeedX, mSpeedY;
    private float mAddX = 0.03f;
    private float mAddY =0.03f;
    /**
     * 爆炸效果慢镜头时长，不然可能看不见效果就消失了。
     */
    private int mBoomDuration =30;//慢劲头特效
    /**
     * 爆炸动画
     */
    private Animation mAnimation;
    /**
     * 爆炸类型
     */
    public int mBoomType;
    /**
     * 爆炸图
     */
    private Bitmap bitmap;
    private Matrix mMatrix;
    private float mMaxScale = -1;
    private boolean isEnd;
    private int mCurrentFrameIndex = 0;

    public DrawBoom(Context context, Bitmap[] deadBitmap, float x, float y, int duration, int type) {
        super(context);
        mAnimation = new Animation(deadBitmap, false);
        this.mBoomX = x;
        this.mBoomY = y;
        this.mBoomDuration = duration;
        this.mBoomType = type;
    }

    public DrawBoom(Context context, Bitmap deadBitmap, float x, float y, int duration, boolean isWidth, int type) {
        super(context);
        this.bitmap = deadBitmap;
        this.mBoomX = x;
        this.mBoomY = y;
        mMatrix = new Matrix();
        this.mBoomDuration = duration;
        if(isWidth){//最大膨胀到全屏宽度
            mMaxScale = ((float) ScreenUtils.getScreenWidth(getContext()))/deadBitmap.getWidth();
        }
        this.mBoomType = type;
    }

    @Override
    void onDraw(Canvas canvas) {
        if(mBoomType ==TYPE_A){
            mAnimation.DrawAnimation(canvas, mPaint, mBoomX, mBoomY);
        }else if(mBoomType ==TYPE_B){
            float bmw,bx,by;
            Bitmap bm = getBitmap();
            bmw= mSpeedX *100;
            if(mBoomX >=ScreenUtils.getScreenWidth(getContext())/2){
                bmw = bm.getWidth()/2;
            }
            bx = mBoomX -bmw;//偏移计算
            by = mBoomY -bm.getHeight()/2;//偏移计算
            canvas.drawBitmap(bm,bx,by,mPaint);
        }
    }

    /**
     * 图片由小变大
     * @return
     *
     */
    private Bitmap getBitmap(){
        mSpeedX += mAddX;
        mSpeedY += mAddY;
        if(mMaxScale !=-1&& mSpeedX >= mMaxScale){
            isEnd =true;
        }
        mMatrix.reset();
        mMatrix.postScale(mSpeedX, mSpeedY);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mMatrix, true);
    }

    @Override
    void updateGame() {
        if(mCurrentFrameIndex < mBoomDuration){
            mCurrentFrameIndex++;
            if(mAnimation !=null&& mAnimation.mIsend){
                mAnimation.reset();
            }
        }else{
            mCurrentFrameIndex =0;
            isEnd = true;
        }
    }

    public boolean isEnd() {
        return isEnd;
    }
}
