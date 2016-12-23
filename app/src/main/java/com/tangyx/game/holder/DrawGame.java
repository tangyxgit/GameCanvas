package com.tangyx.game.holder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by tangyx on 2016/12/22.
 *
 */

public abstract class DrawGame {

    private Context mContext;
    protected Paint mPaint;

    public DrawGame(Context context,Object... objects) {
        this.mContext = context;
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        initialize(objects);
    }

    /**
     * 初始化内容
     */
    abstract void initialize(Object... objects);

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
    /**
     * 获取字体的高宽
     */
    Rect getTextRect(String text,Paint paint){
        Rect rect = new Rect();
        paint.getTextBounds(text,0,text.length(),rect);
        return rect;
    }
}
