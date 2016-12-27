package com.tangyx.game.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.tangyx.game.util.ScreenUtils;

import java.util.Random;

/**
 * Created by tangyx on 2016/12/26.
 *
 */

public class DrawEnemy extends DrawGame {
    /**
     * 敌机类型
     */
    public final static int TYPE_A=1;
    public final static int TYPE_B=2;
    public final static int TYPE_C=3;
    public final static int TYPE_D=4;
    public final static int TYPE_E=5;
    public final static int TYPE_F=6;
    public final static int TYPE_G=7;
    public final static int TYPE_H=8;
    public final static int TYPE_I=9;
    public final static int TYPE_J=10;
    public final static int TYPE_K=11;
    public final static int TYPE_L=12;
    public final static int TYPE_M=13;
    public final static int TYPE_N=14;
    public final static int TYPE_O=15;
    public final static int TYPE_P=16;
    public final static int TYPE_Q=17;
    public final static int TYPE_R=18;
    public final static int TYPE_S=19;
    /**
     * 高级敌机
     */
    public final static int TYPE_T=20;
    /**
     * 高级敌机
     */
    public final static int TYPE_U=21;
    /**
     * 敌机自动跟踪主角
     */
    public final static int TYPE_V=22;
    /**
     * 敌机自动跟踪主角
     */
    public final static int TYPE_W=23;
    public final static int TYPE_X=24;
    public final static int TYPE_Y=25;
    public final static int TYPE_Z=26;
    /**
     * 敌机样式
     */
    private Bitmap mEnemy;
    private float mEnemyX;
    private float mEnemyY;
    /**
     * 敌机的生命值
     */
    private int mEnemyLife;
    /**
     * 当前敌机飞行模式
     */
    private int mEnemyType;
    /**
     * 敌机飞行速度
     */
    private float mEnemySpeed;
    private float mEnemySpeedX;
    private float mEnemySpeedY;
    /**
     * 敌机初始化位置距离左边上边的距离
     */
    private float mMarginLeft;
    private float mMarginTop;
    /**
     * 战机是否死亡
     */
    private boolean isDead;
    private Random mRandom;
    private float mAngle;
    /**
     * 敌机在某一位置停留
     * 位置从屏幕左上角计算
     */
    private boolean mEnemyStopLeft, mEnemyStopTop;
    /**
     * 让敌机在某一个位置停留一段时常
     */
    public int stopToTime;
    private float mTempEnemyY = 1f;
    private Matrix mMatrix;

    public DrawEnemy(Context context,Object... objects) {
        super(context,objects);
    }

    @Override
    void initialize(Object... objects) {
        super.initialize(objects);
        mRandom = new Random();
        this.mEnemy = (Bitmap) objects[0];
        this.mEnemyX = (float) objects[1];
        this.mEnemyY = (float) objects[2];
        this.mEnemyLife = (int) objects[3];
        this.mEnemyType = (int) objects[4];
        mMatrix = new Matrix();
        if(objects.length==6){//敌机去追杀主角的战机类型
            mEnemySpeedY = ScreenUtils.getScreenHeight(getContext())/100;
            mEnemySpeedX = ((float)objects[5]-this.mEnemyY)/100;
        }else{//普通类型，不可追踪主角
            onSetSpeed();
        }
    }

    @Override
    void onDraw(Canvas canvas) {
        Bitmap bm = getMatrixBitmap();
        canvas.drawBitmap(bm,mEnemyX,mEnemyY,mPaint);
    }

    /**
     * 敌机相对于主角方向旋转
     * 保证敌机头部朝向跟随主机位置移动
     */
    private Bitmap getMatrixBitmap(){
        if(mAngle==0){
            return mEnemy;
        }
        mMatrix.reset();
        //旋转角度
        mMatrix.setRotate(mAngle);
        return Bitmap.createBitmap(mEnemy,0, 0, mEnemy.getWidth(), mEnemy.getHeight(), mMatrix, true);
    }

    /**
     * 面向主角方向 重新计算移动坐标
     * isTrack 是否需要去追杀主角
     */
    public void getAngleRotate(float playerX,float playerY,boolean isTrack){
        float cx = playerX-mEnemyX;
        float cy = playerY-mEnemyY;
        float k = Math.abs(cy/cx);//计算偏移量 斜率
        mAngle = (float) (90-Math.toDegrees(Math.atan(k)));//把弧度转换成角度
        if(cx>0){
            mAngle = -mAngle;
        }
        if(isTrack){//重新计算坐标 追杀主角
            mEnemySpeedX = cx/50;
            mEnemySpeedY = cy/50;
            if(cy<0){
                mEnemySpeedY=-mEnemySpeedY;
            }
            if((cx<0&&mEnemySpeedX>0)||(cx>0&&mEnemySpeedX<0)){
                mEnemySpeedX=-mEnemySpeedX;
            }
        }
    }

    /**
     * 更新敌机运行轨迹
     * 并且判断敌机是否离开屏幕
     */
    @Override
    void updateGame(){
        float screenH = ScreenUtils.getScreenHeight(getContext());
        float screenW = ScreenUtils.getScreenWidth(getContext());
        switch (mEnemyType){
            case TYPE_A://左边垂直下降
                if(!isDead){
                    if(mEnemySpeed<=3){
                        mEnemySpeed+=1;
                    }
                    mEnemyY+=mEnemySpeed;
                    if(mEnemyY>screenH){
                        isDead=true;
                    }
                }
                break;
            case TYPE_B:
                if(!isDead){//向右倾斜下降
                    mEnemyX+=mEnemySpeed/2;
                    mEnemyY+=mEnemySpeed;
                }
                break;
            case TYPE_C:
                if(!isDead){//向左倾斜下降
                    mEnemyX-=mEnemySpeed/2;
                    mEnemyY+=mEnemySpeed;
                }
                break;
            case TYPE_D:
                if(!isDead){//右边垂直倾斜下降
                    if(mEnemySpeed<=3){
                        mEnemySpeed+=1;
                    }
                    mEnemyY+=mEnemySpeed;
                }
                break;
            case TYPE_E:
                if(!isDead){//普通模式 子弹自动追踪主角
                    if(mEnemyY>screenH){
                        isDead = true;
                        break;
                    }
                    mEnemyY+=mEnemySpeed;
                }
                break;
            case TYPE_F:
                if(!isDead){//向右横向移动循环碰撞
                    if(mAngle>=360){
                        mAngle=0;
                    }
                    mAngle+=1;
                    if(mEnemyX>=screenW-mEnemy.getWidth()){
                        mEnemyStopLeft = true;
                    }else if(mEnemyX<=0){
                        mEnemyStopLeft = false;
                    }
                    if(mEnemyStopLeft){
                        mEnemyX-=mEnemySpeed;
                    }else{
                        mEnemyX+=mEnemySpeed;
                    }
                    mEnemyY+=mEnemySpeed/2;
                }
                break;
            case TYPE_G:
                if(!isDead){//向左横向移动循环碰撞
                    if(mAngle<=-360){
                        mAngle=0;
                    }
                    mAngle-=1;
                    if(mEnemyX<=0){
                        mEnemyStopLeft = true;
                    }else if(mEnemyX>screenW-mEnemy.getWidth()){
                        mEnemyStopLeft = false;
                    }
                    if(mEnemyStopLeft){
                        mEnemyX+=mEnemySpeed;
                    }else{
                        mEnemyX-=mEnemySpeed;
                    }
                    mEnemyY+=mEnemySpeed/2;
                }
                break;
            case TYPE_H:
                if(!isDead){//想右切面运行
                    if(mEnemyY>=screenH/2||mEnemyX>screenW-mEnemy.getWidth()){
                        mEnemyStopLeft = true;
                    }
                    if(mEnemyStopLeft){
                        if(mEnemyStopTop){
                            mEnemyX +=0;
                            mEnemyY +=mEnemySpeed;
                        }else{
                            mEnemyX-=mEnemySpeed/2;
                            mEnemyY-=mEnemySpeed/2;
                        }
                        if(mEnemyY<=0){
                            mEnemyStopTop = true;
                        }
                    }else{
                        mEnemyX+=mEnemySpeed/2;
                        mEnemyY+=mEnemySpeed/2;
                    }
                }
                break;
            case TYPE_I:
                if(!isDead){//想左切面运行
                    if(mEnemyY>=screenH/2||mEnemyX-mEnemy.getWidth()<=0){
                        mEnemyStopLeft = true;
                    }
                    if(mEnemyStopLeft){
                        if(mEnemyStopTop){
                            mEnemyX+=0;
                            mEnemyY+=mEnemySpeed;
                        }else{
                            mEnemyX+=mEnemySpeed/2;
                            mEnemyY-=mEnemySpeed/2;
                        }
                        if(mEnemyY<=0){
                            mEnemyStopTop = true;
                        }
                    }else{
                        mEnemyX-=mEnemySpeed/2;
                        mEnemyY+=mEnemySpeed/2;
                    }
                }
                break;
            case TYPE_J:
                if(!isDead){//右边V形运动
                    if(mEnemyY>=screenH/2){
                        mEnemyStopLeft = true;
                    }
                    if(mEnemyStopLeft){
                        mEnemyX+=mEnemySpeedX;
                        mEnemyY+=mEnemySpeedY;
                    }else{
                        mAngle=0;
                        mEnemyY+=mEnemySpeed;
                        mEnemyX+=mEnemySpeed/2;
                    }
                }
                break;
            case TYPE_K:
                if(!isDead){//左边V形运动
                    if(mEnemyY>=screenH/2){
                        mEnemyStopLeft = true;
                    }
                    if(mEnemyStopLeft){
                        mEnemyX+=mEnemySpeedX;
                        mEnemyY+=mEnemySpeedY;
                    }else{
                        mAngle=0;
                        mEnemyY+=mEnemySpeed;
                        mEnemyX-=mEnemySpeed/2;
                    }
                }
                break;
            case TYPE_L:
                if(!isDead){//向上冲锋战斗机
                    if(mEnemyStopLeft){
                        mEnemyY-=mEnemySpeed/2;
                        stopToTime++;
                        if(mEnemyY<0||mEnemyY>screenH){
                            isDead = true;
                            break;
                        }
                        if(mEnemyY<=screenH/7){
                            if(stopToTime >=500){
                                mEnemySpeed=20;
                            }else{
                                mEnemyStopLeft =false;
                            }
                        }
                    }else{
                        mEnemyY+=mEnemySpeed/2;
                        if(mEnemyY>=screenH/3){
                            mEnemyStopLeft = true;
                        }
                    }
                }
                break;
            case TYPE_M:
                if(!isDead){//向下冲锋战斗机
                    if(mEnemyStopLeft){
                        stopToTime++;
                        mEnemyY-=mEnemySpeed/2;
                        if(mEnemyY<=screenH/7){
                            mEnemyStopLeft =false;
                        }
                    }else{
                        if(stopToTime >=500){
                            mEnemySpeed=20;
                        }
                        mEnemyY+=mEnemySpeed/2;
                        if(mEnemyY>=screenH/4){
                            if(stopToTime <300){
                                mEnemyStopLeft = true;
                            }else if(mEnemyY<0||mEnemyY>screenH){
                                isDead = true;
                                break;
                            }
                        }
                    }
                }
                break;
            case TYPE_N://左边由上至上到中间
                if(mEnemyStopLeft){
                    mEnemyY-=mEnemySpeed;
                    mEnemyX+=mEnemySpeed/2;
                    if(mEnemyY<=mEnemy.getHeight()&&mEnemyX>=screenW/2-mEnemy.getWidth()){
                        mEnemyStopTop = true;
                        mEnemyStopLeft = false;
                    }
                    if(mEnemyY<mEnemy.getHeight()){
                        mEnemyY = mEnemy.getHeight();
                    }
                    if(mEnemyX>screenW/2-mEnemy.getWidth()){
                        mEnemyX = screenW/2-mEnemy.getWidth();
                    }
                }else{
                    if(mEnemyStopTop){
                        mEnemyY+=mEnemySpeed;
                        if(mEnemyY>screenH){
                            isDead = true;
                            break;
                        }
                    }else{
                        mEnemyY+=mEnemySpeed*3;
                        if(mEnemyY>=(screenH/2)){
                            mEnemyStopLeft = true;
                        }
                    }
                }
                break;
            case TYPE_O://右边由上至上到中间
                if(mEnemyStopLeft){
                    mEnemyY-=mEnemySpeed;
                    mEnemyX-=mEnemySpeed/2;
                    if(mEnemyY<=mEnemy.getHeight()&&mEnemyY<=screenW/2+mEnemy.getWidth()){
                        mEnemyStopTop = true;
                        mEnemyStopLeft = false;
                    }
                    if(mEnemyY<mEnemy.getHeight()){
                        mEnemyY = mEnemy.getHeight();
                    }
                    if(mEnemyX<screenW/2+mEnemy.getWidth()){
                        mEnemyX = screenW/2+mEnemy.getWidth();
                    }
                }else{
                    if(mEnemyStopTop){
                        mEnemyY+=mEnemySpeed;
                    }else{
                        mEnemyY+=mEnemySpeed*3;
                        if(mEnemyY>=screenH/2){
                            mEnemyStopLeft = true;
                        }
                    }
                }
                break;
            case TYPE_P://左侧Z字形运动
                if(mEnemyX>=screenW-mEnemy.getWidth()){
                    mEnemyStopLeft = true;
                }else if(mEnemyX<=0){
                    mEnemyStopLeft = false;
                }
                if(mEnemyY<=0){
                    mTempEnemyY = -mTempEnemyY;
                }
                if(mEnemyStopLeft){
                    mEnemyY-= mTempEnemyY *2;
                    mEnemyX-=mEnemySpeed;
                }else{
                    mEnemyY-= mTempEnemyY *2;
                    mEnemyX+=mEnemySpeed;
                }
                break;
            case TYPE_Q://右侧z字形运动
                if(mEnemyX<=0){
                    mEnemyStopLeft = true;
                }else if(mEnemyX>=screenW-mEnemy.getWidth()){
                    mEnemyStopLeft = false;
                }
                if(mEnemyY<=0){
                    mTempEnemyY = -mTempEnemyY;
                }
                if(mEnemyStopLeft){
                    mEnemyY-= mTempEnemyY *2;
                    mEnemyX+=mEnemySpeed;
                }else{
                    mEnemyY-= mTempEnemyY *2;
                    mEnemyX-=mEnemySpeed;
                }
                break;
            case TYPE_R://左边中间螺旋出场
                if(mAngle>=360){
                    mAngle = 0;
                }
                mAngle+=20;
                if(mEnemyY>screenH/2+100){
                    mEnemySpeedY = - (mRandom.nextInt(3)+2);
                }else if(mEnemyY<screenH/2){
                    mEnemySpeedY =mRandom.nextInt(3)+5;
                }
                mEnemyX+=mEnemySpeedX;
                mEnemyY+=mEnemySpeedY;
                if(mEnemyX>screenW){
                    isDead=true;
                }
                break;
            case TYPE_S://右边中间螺旋出场
                if(mAngle<=-360){
                    mAngle = 0;
                }
                mAngle-=20;
                if(mEnemyY>screenH/2+100){
                    mEnemySpeedY = - (new Random().nextInt(3)+2);
                }else if(mEnemyY<screenH/2){
                    mEnemySpeedY = (new Random().nextInt(3)+5);
                }
                mEnemyX-=mEnemySpeedX;
                mEnemyY+=mEnemySpeedY;
                if(mEnemySpeedX<0){
                    isDead=true;
                }
                break;
            case TYPE_T://左边高级将领
                if(mEnemyStopLeft){
                    if(mEnemyX>=mMarginLeft||mEnemyX<=0){
                        mEnemySpeedX= -mEnemySpeedX;
                    }
                    mEnemyY+=mEnemySpeedX*2;
                    if(mEnemyY>=mMarginTop){
                        mEnemyY = mMarginTop;
                    }
                    if(mEnemyY<=mEnemy.getHeight()){
                        mEnemyY=mEnemy.getHeight();
                    }
                }else{
                    if(mEnemyX>=mMarginLeft){
                        mEnemySpeedX= -mEnemySpeedX;
                        mEnemyStopLeft =true;
                    }
                }
                mEnemyX+=mEnemySpeedX;
                break;
            case TYPE_U://右边高级将领
                if(mEnemyStopLeft){
                    if(mEnemyX<=mMarginLeft||mEnemyX>screenW-mEnemy.getWidth()){
                        mEnemySpeedX= -mEnemySpeedX;
                    }
                    mEnemyX+=mEnemySpeedX*2;
                    if(mEnemyY>=mMarginTop){
                        mEnemyY = mMarginTop;
                    }
                    if(mEnemyY<=mEnemy.getHeight()){
                        mEnemyY=mEnemy.getHeight();
                    }
                }else{
                    if(mEnemyX<=mMarginLeft){
                        mEnemySpeedX= -mEnemySpeedX;
                        mEnemyStopLeft =true;
                    }
                }
                mEnemyX-=mEnemySpeedX;
                break;
            case TYPE_V://左边自动追踪
                stopToTime++;
                mEnemyY+=mEnemySpeedY;
                mEnemyX+=mEnemySpeedX;
                if(!mEnemyStopLeft){
                    if(mEnemyY>=screenH/4){
                        mEnemyStopLeft = true;
                    }
                    if(stopToTime %25==0){
                        mEnemyX = -mEnemySpeedX;
                    }
                }else if(mEnemyY<0||mEnemyY>screenH||mEnemyX>screenW||mEnemyX<0){
                    isDead=true;
                }
                break;
            case TYPE_W://右边自动追踪
                stopToTime++;
                mEnemyY+=mEnemySpeedY;
                mEnemyX+=mEnemySpeedX;
                if(!mEnemyStopLeft){
                    if(mEnemyY>=screenH/4){
                        mEnemyStopLeft = true;
                    }
                    if(stopToTime %25==0){
                        mEnemySpeedX = -mEnemySpeedX;
                    }
                }else if(mEnemyY<0||mEnemyY>screenH||mEnemyX>screenW||mEnemyX<0){
                    isDead=true;
                }
                break;
            case TYPE_X://中空旋转混战模式
                if(mAngle>=360){
                    mAngle = 0;
                }
                mAngle++;
                mEnemyX+=mEnemySpeedX;
                mEnemyY+=mEnemySpeedY;
                if((mEnemyY>=screenH/2&&mEnemySpeedY>=0)||(mEnemyY<=0&&mEnemySpeedY<=0)){
                    mEnemySpeedY=-mEnemySpeedY;
                    if(mEnemySpeedY>=0){
                        mEnemySpeedY =mRandom.nextInt((int) mEnemySpeed);
                    }
                }
                if((mEnemyX>=screenW-mEnemy.getWidth()&&mEnemySpeedX>=0)||(mEnemyX<=0&&mEnemySpeedX<=0)){
                    mEnemySpeedX = -mEnemySpeedX;
                    if(mEnemySpeedX>=0){
                        mEnemySpeedX = mRandom.nextInt((int) mEnemySpeed);
                    }
                }
                break;
            case TYPE_Y://高级将领
                if(mAngle<=-360){
                    mAngle=0;
                }
                mAngle-=2;
                mEnemyX+=mEnemySpeedX;
                mEnemyY+=mEnemySpeedY;
                if((mEnemyY>=screenH-mEnemy.getHeight()&&mEnemySpeedY>=0)||(mEnemyY<=0&&mEnemySpeedY<=0)){
                    mEnemySpeedY=-mEnemySpeedY;
                }
                if((mEnemyX>=screenW-mEnemy.getWidth()&&mEnemySpeedX>=0)||(mEnemyX<=0&&mEnemySpeedX<=0)){
                    mEnemySpeedX = -mEnemySpeedX;
                }
                break;
            case TYPE_Z:
                mEnemyY+=mEnemySpeed;
                break;
        }
        //消失在屏幕以后敌机失效死亡
        if(mEnemyX>screenW||mEnemyY>screenH||mEnemyX<-1){
            isDead=true;
        }
    }

    /**
     * 计算敌机飞行速度
     */
    private void onSetSpeed(){
        float screenH = ScreenUtils.getScreenHeight(getContext());
        float screenW = ScreenUtils.getScreenWidth(getContext());
        switch (mEnemyType){
            case TYPE_E:
                float min = screenH/200;
                mEnemySpeed = new Random().nextInt(3)+min;
                break;
            case TYPE_R:
                mEnemySpeed  = screenH/160;
                mEnemySpeedX = 1;
                mEnemySpeedY = mEnemySpeed;
                break;
            case TYPE_S:
                mEnemySpeed = screenH/160;
                mEnemySpeedX = 1;
                mEnemySpeedY = mEnemySpeed;
                break;
            case TYPE_T:
                mEnemySpeed = 6;
                mEnemySpeedX = 2;
                mMarginLeft = mEnemy.getWidth()*3;
                mMarginTop = mEnemy.getHeight()*3;
                break;
            case TYPE_U:
                mEnemySpeed = 6;
                mEnemySpeedX = 2;
                mMarginLeft = screenW - mEnemy.getWidth()*4;
                mMarginTop = mEnemy.getHeight()*3;
                break;
            case TYPE_X:
                mEnemySpeed = screenH/80;
                mEnemySpeedX = mRandom.nextInt(10);
                if(mRandom.nextBoolean()){
                    mEnemySpeedX = -mEnemySpeedX;
                }
                mEnemySpeedY = screenH/160;
                break;
            case TYPE_Y:
                mEnemySpeed = screenH/160;
                mEnemySpeedX =screenH/160;
                mEnemySpeedY =screenH/160;
                break;
            case TYPE_Z:
                mEnemySpeed = screenH/220;
                break;
            default:
                mEnemySpeed = screenH/160f;
                break;
        }
    }
}
