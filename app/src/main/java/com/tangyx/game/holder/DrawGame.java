package com.tangyx.game.holder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by tangyx on 2016/12/22.
 *
 */

public abstract class DrawGame {

    private Context mContext;
    protected Paint mPaint;

    public DrawGame(Context context) {
        this.mContext = context;
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        initialize();
    }

    /**
     * 初始化内容
     */
    abstract void initialize();

    /**
     * 绘制内容
     * @param canvas
     */
    abstract void onDraw(Canvas canvas);

    /**
     * 数据更新
     */
    abstract void updateGame();

    public Context getContext() {
        return mContext;
    }
}
