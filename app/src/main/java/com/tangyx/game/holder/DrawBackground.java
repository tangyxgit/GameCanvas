package com.tangyx.game.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.tangyx.game.util.BitmapUtils;
import com.tangyx.game.util.ScreenUtils;

/**
 * Created by tangyx on 2016/12/22.
 *
 */

public class DrawBackground extends DrawGame {

    private Bitmap mBackgroundTop;
    private Bitmap mBackgroundBottom;
    /**
     * 因为背景是全屏的，并且飞机是一直在往上运动，所以X坐标不需要运动，只需要改变y的坐标来完成运动效果
     */
    private float mTopY;
    private float mBottomY;
    /**
     * 背景移动速度
     */
    private float mSpeedBY;

    public DrawBackground(Context context,String background){
        super(context,background);
    }

    @Override
    void initialize(Object... objects) {
        super.initialize(objects);
        //加载背景图片
        mBackgroundBottom = BitmapUtils.ReadBitMap(getContext(), (String) objects[0]);
        //把背景图大小设置为手机屏幕大小
        mBackgroundBottom = BitmapUtils.getBitmap(getContext(), mBackgroundBottom,0);
        //把背景图在垂直翻转生成一张新的图片
        mBackgroundTop = BitmapUtils.getScaleMap(mBackgroundBottom);
        mBottomY = 0;
        /**
         * 第一张背景放在屏幕的顶部外面
         */
        mTopY = -ScreenUtils.getScreenHeight(getContext());
        mSpeedBY=10;
    }

    @Override
    void onDraw(Canvas canvas) {
        //背景是一张图片，所以我们应该通过canvas的drawBitmap来实现，
        // 景的图片其实一直是一张图片在由上至下运动，通过两张图片链接进行循环
        canvas.drawBitmap(mBackgroundBottom,0, mBottomY,mPaint);
        canvas.drawBitmap(mBackgroundTop,0, mTopY,mPaint);
    }

    @Override
    void updateGame() {
        mBottomY +=mSpeedBY;
        mTopY +=mSpeedBY;
        //当mBackgroundBottom已经移动出屏幕就自动设置到屏幕之外的最顶部。
        if(mBottomY >= ScreenUtils.getScreenHeight(getContext())){
            mBottomY = -ScreenUtils.getScreenHeight(getContext());
        }
        //mBackgroundTop已经移动出屏幕就自动设置到屏幕之外的最顶部。
        if(mTopY >= ScreenUtils.getScreenHeight(getContext())){
            mTopY = -ScreenUtils.getScreenHeight(getContext());
        }
    }

    public void setSpeedBY(float mSpeedBY) {
        this.mSpeedBY = mSpeedBY;
    }
}
