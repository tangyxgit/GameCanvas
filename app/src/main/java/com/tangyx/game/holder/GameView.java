package com.tangyx.game.holder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by tangyx on 2016/12/19.
 *
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback,Runnable{
    /**
     * surface的控制器
     */
    private SurfaceHolder mHolder;
    /**
     * 画布
     */
    private Canvas mCanvas;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 游戏背景
     */
    private String mBackGround;
    private DrawBackground mDrawBackground;

    /**
     * 是否暂停，或者退出了
     */
    private boolean isRun=true;

    public GameView(Context context,int type,String background,int cos) {
        super(context);
        this.mBackGround = background;
        mHolder = getHolder();
        mHolder.addCallback(this);
    }


    /**
     * 初始化资源
     */
    private void initGame(){
        //初始化画笔
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        //加载背景图片资源
        mDrawBackground = new DrawBackground(getContext(),this.mBackGround);
    }

    /**
     * 绘制内容以及更新内容
     */
    private void onGameDraw(){
        if(mCanvas==null)return;
        mCanvas.drawColor(Color.WHITE);
        mDrawBackground.onDraw(mCanvas);
        mDrawBackground.updateGame();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initGame();
        //启动线程，开始加载游戏内容
        Thread mGameThread = new Thread(this);
        mGameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRun=false;
    }


    /**
     * 游戏的绘制肯定不能阻塞UI，所以我们需要在线程中来完成绘制，通过锁定画布，解锁画笔来提交绘制的内容
     */
    @Override
    public void run() {
        while (isRun){
            //锁定画布
            mCanvas = mHolder.lockCanvas();
            synchronized (mHolder){
                onGameDraw();
                //执行完成后提交画布
                if(mCanvas!=null){
                    mHolder.unlockCanvasAndPost(mCanvas);
                }
            }

        }
    }
}
