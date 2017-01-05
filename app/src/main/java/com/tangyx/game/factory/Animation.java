package com.tangyx.game.factory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by tangyx on 2017/1/5.
 */

public class Animation {
    /** 上一帧播放时间 **/
    private long mLastPlayTime = 0;
    /** 播放当前帧的ID **/
    private int mPlayID = 0;
    /** 动画frame数量 **/
    private int mFrameCount = 0;
    /** 用于储存动画资源图片 **/
    private Bitmap[] mframeBitmap = null;
    /** 是否循环播放 **/
    private boolean mIsLoop = false;
    /** 播放结束 **/
    public boolean mIsend = false;
    /** 动画播放间隙时间 **/
    private static final int ANIM_TIME = 100;

    public Animation(Context context, int [] frameBitmapID, boolean isloop) {
        mFrameCount = frameBitmapID.length;
        mframeBitmap = new Bitmap[mFrameCount];
        mIsLoop = isloop;
    }

    public Animation(Bitmap [] frameBitmap, boolean isloop) {
        mFrameCount = frameBitmap.length;
        mframeBitmap = frameBitmap;
        mIsLoop = isloop;
    }

    //重置动画
    public void reset() {
        mLastPlayTime = 0;
        mPlayID =0;
        mIsend= false;
    }

    /**
     * 绘制动画中的其中一帧
     * @param paint
     * @param x
     * @param y
     * @param frameID
     */
    public void DrawFrame(Canvas canvas, Paint paint, int x, int y, int frameID) {
        canvas.drawBitmap(mframeBitmap[frameID], x, y, paint);
    }


    /**
     * 绘制动画
     * @param paint
     * @param x
     * @param y
     */
    public void DrawAnimation(Canvas canvas, Paint paint, float x, float y) {
        //如果没有播放结束则继续播放
        if (!mIsend) {
            canvas.drawBitmap(mframeBitmap[mPlayID], x, y, paint);
            long time = System.currentTimeMillis();
            if (time - mLastPlayTime > ANIM_TIME) {
                mPlayID++;
                mLastPlayTime = time;
                if (mPlayID >= mFrameCount) {
                    //标志动画播放结束
                    mIsend = true;
                    if (mIsLoop) {//设置循环播放
                        mIsend = false;
                        mPlayID = 0;
                    }
                }
            }
        }
    }
}
