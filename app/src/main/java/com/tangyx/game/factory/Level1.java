package com.tangyx.game.factory;

import android.content.Context;
import android.graphics.Bitmap;

import com.tangyx.game.R;
import com.tangyx.game.holder.DrawEnemy;
import com.tangyx.game.holder.DrawEnemyBullet;
import com.tangyx.game.holder.DrawPlayer;
import com.tangyx.game.util.BitmapUtils;
import com.tangyx.game.util.ScreenUtils;
import com.tangyx.game.util.SizeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by tangyx on 2016/12/28.
 *
 */

public class Level1 extends BaseLevel {
    /**
     * 当前关卡的敌方战机资源
     */
    private static Bitmap mEnemyA;
    private static Bitmap mEnemyB;
    private static Bitmap mEnemyC;
    private static Bitmap mEnemyD;
    private static Bitmap mEnemyE;
    private static Bitmap mEnemyF;
    private static Bitmap mEnemyG;
    private static Bitmap mEnemyH;
    private static Bitmap mEnemyI;
    private static Bitmap mEnemyJ;
    private static Bitmap mEnemyK;
    private static Bitmap mEnemyY;
    private static Bitmap mEnemyZ;
    /**
     * 敌机子弹
     */
    private static Bitmap mEnemyBullet;
    /**
     * 控制敌机加入的时间间隔
     */
    private int mCountEnemy;
    private int createEnemyTime=100;
    /**
     * 当前是第几波敌机
     */
    private int enemyArrayIndex;
    /**
     * 当前关卡最多出现多少波敌机
     */
    private int maxEnemy=40;
    /**
     * 当前屏幕所有敌机的集合
     */
    private List<DrawEnemy> mEnemyList;

    /**
     * 因为有追踪模式，追踪模式就是去追杀主角。
     * 主角
     */
    private DrawPlayer mPlayer;
    /**
     * 敌机的位置
     */
    private float mEnX;
    private float mEnY;
    private float mEnCx;
    private float mEnCy;
    /**
     * 记录前一个敌机的位置
     */
    private float tempX;
    private float tempY;
    /**
     * 敌机子弹
     */
    private List<DrawEnemyBullet> mEnemyBullets;

    public Level1(Context context,Object... objects) {
        super(context,objects);
    }

    @Override
    public void initialize(Object... objects) {
        mEnemyList = new ArrayList<>();
        mEnemyBullets = new ArrayList<>();
        int w = SizeUtils.dp2px(getContext(),20);
        int h = SizeUtils.dp2px(getContext(),20);
        //敌机资源
        mEnemyA = BitmapUtils.ReadBitMap(getContext(), R.drawable.enemy01);
        mEnemyA = BitmapUtils.getBitmap(mEnemyA,w,h);

        mEnemyB = BitmapUtils.ReadBitMap(getContext(), R.drawable.enemy02);
        mEnemyB = BitmapUtils.getBitmap(mEnemyB,w,h);

        mEnemyC = BitmapUtils.ReadBitMap(getContext(), R.drawable.enemy03);
        mEnemyC = BitmapUtils.getBitmap(mEnemyC,w,h);

        mEnemyD = BitmapUtils.ReadBitMap(getContext(), R.drawable.enemy04);
        mEnemyD = BitmapUtils.getBitmap(mEnemyD,w,h);

        mEnemyE = BitmapUtils.ReadBitMap(getContext(), R.drawable.enemy05);
        mEnemyE = BitmapUtils.getBitmap(mEnemyE,w,h);

        mEnemyF = BitmapUtils.ReadBitMap(getContext(), R.drawable.enemy06);
        mEnemyF = BitmapUtils.getBitmap(mEnemyF,w,h);

        mEnemyG = BitmapUtils.ReadBitMap(getContext(), R.drawable.enemy07);
        mEnemyG = BitmapUtils.getBitmap(mEnemyG,w,h);

        mEnemyH = BitmapUtils.ReadBitMap(getContext(), R.drawable.enemy08);
        mEnemyH = BitmapUtils.getBitmap(mEnemyH,w,h);

        mEnemyI = BitmapUtils.ReadBitMap(getContext(), R.drawable.enemy09);
        mEnemyI = BitmapUtils.getBitmap(mEnemyI,w,h);

        mEnemyJ = BitmapUtils.ReadBitMap(getContext(), R.drawable.enemy10);
        mEnemyJ = BitmapUtils.getBitmap(mEnemyJ,w,h);

        mEnemyK = BitmapUtils.ReadBitMap(getContext(), R.drawable.enemy12);
        mEnemyK = BitmapUtils.getBitmap(mEnemyK,w,h);

        mEnemyY = BitmapUtils.ReadBitMap(getContext(), R.drawable.enemytop06);

        mEnemyZ = BitmapUtils.ReadBitMap(getContext(), R.drawable.enemytop02);

        mEnemyBullet = BitmapUtils.ReadBitMap(getContext(),R.drawable.enemybullet1);
        w = SizeUtils.dp2px(getContext(),5);
        h = SizeUtils.dp2px(getContext(),5);
        mEnemyBullet = BitmapUtils.getBitmap(mEnemyBullet,w,h);

        mPlayer = (DrawPlayer) objects[0];
    }

    /**
     * 获取每一波分配好的敌机集合
     * @param enemyArrayIndex
     * @return
     */
    @Override
    public Map<Integer,Integer> enemyLevelArray(int enemyArrayIndex) {
        Map<Integer,Integer> tempMap = new HashMap<>();
        switch (enemyArrayIndex){
            case 0:
                tempMap.put(DrawEnemy.TYPE_E,3);
                tempMap.put(DrawEnemy.TYPE_A,4);
                break;
            case 1:
                tempMap.put(DrawEnemy.TYPE_E,3);
                tempMap.put(DrawEnemy.TYPE_D,4);
                break;
            case 2:
                tempMap.put(DrawEnemy.TYPE_E,3);
                tempMap.put(DrawEnemy.TYPE_B,4);
                break;
            case 3:
                tempMap.put(DrawEnemy.TYPE_E,3);
                tempMap.put(DrawEnemy.TYPE_C,4);
                break;
            case 4:
                tempMap.put(DrawEnemy.TYPE_E,3);
                tempMap.put(DrawEnemy.TYPE_F,4);
                break;
            case 5:
                tempMap.put(DrawEnemy.TYPE_E,4);
                tempMap.put(DrawEnemy.TYPE_G,3);
                break;
            case 6:
                tempMap.put(DrawEnemy.TYPE_E,4);
                tempMap.put(DrawEnemy.TYPE_H,4);
                break;
            case 7:
                tempMap.put(DrawEnemy.TYPE_E,4);
                tempMap.put(DrawEnemy.TYPE_I,4);
                break;
            case 8:
                tempMap.put(DrawEnemy.TYPE_E,4);
                tempMap.put(DrawEnemy.TYPE_A,2);
                tempMap.put(DrawEnemy.TYPE_J,4);
                break;
            case 9:
                tempMap.put(DrawEnemy.TYPE_E,4);
                tempMap.put(DrawEnemy.TYPE_D,2);
                tempMap.put(DrawEnemy.TYPE_K,4);
                break;
            case 10:
                tempMap.put(DrawEnemy.TYPE_L,4);
                break;
            case 11:
                tempMap.put(DrawEnemy.TYPE_M,4);
                break;
            case 12:
                tempMap.put(DrawEnemy.TYPE_G,4);
                break;
            case 13:
                tempMap.put(DrawEnemy.TYPE_E,4);
                break;
            case 14:
                tempMap.put(DrawEnemy.TYPE_E,4);
                tempMap.put(DrawEnemy.TYPE_N,4);
                break;
            case 15:
                tempMap.put(DrawEnemy.TYPE_E,4);
                tempMap.put(DrawEnemy.TYPE_O,4);
                break;
            case 16:
                tempMap.put(DrawEnemy.TYPE_E,4);
                break;
            case 17:
                tempMap.put(DrawEnemy.TYPE_P,4);
                break;
            case 18:
                tempMap.put(DrawEnemy.TYPE_Q,4);
                break;
            case 19:
                tempMap.put(DrawEnemy.TYPE_E,4);
                tempMap.put(DrawEnemy.TYPE_R,4);
                break;
            case 20:
                tempMap.put(DrawEnemy.TYPE_E,4);
                tempMap.put(DrawEnemy.TYPE_S,4);
                break;
            case 21:
                tempMap.put(DrawEnemy.TYPE_T,1);
                tempMap.put(DrawEnemy.TYPE_U,1);
                break;
            case 22:
                tempMap.put(DrawEnemy.TYPE_A,4);
//                tempMap.put(DrawEnemy.TYPE_V,4);
                break;
            case 23:
                tempMap.put(DrawEnemy.TYPE_D,4);
//                tempMap.put(DrawEnemy.TYPE_W,4);
                break;
            case 24:
                tempMap.put(DrawEnemy.TYPE_X,4);
                break;
            case 25:
                tempMap.put(DrawEnemy.TYPE_Y,1);
                break;
            case 26:
                tempMap.put(DrawEnemy.TYPE_Z,1);
                break;
            case 27:
                tempMap.put(DrawEnemy.TYPE_T,1);
                tempMap.put(DrawEnemy.TYPE_U,1);
                break;
            case 28:
                tempMap.put(DrawEnemy.TYPE_E,4);
                tempMap.put(DrawEnemy.TYPE_Y,4);
                break;
            case 29:
                tempMap.put(DrawEnemy.TYPE_H,4);
                tempMap.put(DrawEnemy.TYPE_I,4);
                break;
            case 30:
                tempMap.put(DrawEnemy.TYPE_J,4);
                tempMap.put(DrawEnemy.TYPE_K,4);
                break;
            case 31:
                tempMap.put(DrawEnemy.TYPE_L,4);
                tempMap.put(DrawEnemy.TYPE_M,4);
                break;
            case 32:
                tempMap.put(DrawEnemy.TYPE_B,4);
                tempMap.put(DrawEnemy.TYPE_C,4);
                break;
            case 33:
                tempMap.put(DrawEnemy.TYPE_L,4);
                tempMap.put(DrawEnemy.TYPE_M,4);
                break;
            case 34:
                tempMap.put(DrawEnemy.TYPE_P,5);
                break;
            case 35:
                tempMap.put(DrawEnemy.TYPE_Q,5);
                break;
            case 36:
                tempMap.put(DrawEnemy.TYPE_N,5);
                break;
            case 37:
                tempMap.put(DrawEnemy.TYPE_O,5);
                break;
            case 38:
                tempMap.put(DrawEnemy.TYPE_N,5);
                tempMap.put(DrawEnemy.TYPE_O,5);
                break;
            case 39:
                tempMap.put(DrawEnemy.TYPE_B,5);
                tempMap.put(DrawEnemy.TYPE_C,5);
                break;
            case 40:
                tempMap.put(DrawEnemy.TYPE_J,5);
                tempMap.put(DrawEnemy.TYPE_K,5);
                tempMap.put(DrawEnemy.TYPE_H,5);
                tempMap.put(DrawEnemy.TYPE_I,5);
                break;
        }
        return tempMap;
    }

    /**
     * 添加敌机
     */
    public void addEnemy(Map<Integer,Integer> enemyMap){
        mCountEnemy++;
        if(mCountEnemy %createEnemyTime==0&&enemyArrayIndex<=maxEnemy){
            enemyArrayIndex++;
            addShowEnemy(enemyMap);
            mCountEnemy =0;
        }
    }

    /**
     * enemy的出场顺序
     * @param enemyMap 敌机的集合
     */
    private void addShowEnemy(Map<Integer,Integer> enemyMap){
        float screenW = ScreenUtils.getScreenWidth(getContext());
        float screenH = ScreenUtils.getScreenHeight(getContext());
        for(Map.Entry<Integer,Integer> entry:enemyMap.entrySet()){
            int life=1;//敌机血量
            int enemyType = entry.getKey();
            int number = enemyMap.get(enemyType);
            Bitmap mEnemy=mEnemyA;//敌机样式
            switch (enemyType) {
                case DrawEnemy.TYPE_A:
                    mEnX = mEnemy.getWidth();
                    mEnY = 0;
                    mEnCx =0;
                    mEnCy =-(mEnemy.getHeight()*2);
                    break;
                case DrawEnemy.TYPE_B:
                    mEnemy = mEnemyB;
                    mEnX = 0;
                    mEnY = 0;
                    mEnCx = -(mEnemy.getWidth());
                    mEnCy = -(mEnemy.getHeight());
                    break;
                case DrawEnemy.TYPE_C:
                    mEnemy = mEnemyB;
                    mEnX = screenW;
                    mEnY = 0;
                    mEnCx = mEnemy.getWidth();
                    mEnCy = -(mEnemy.getHeight());
                    break;
                case DrawEnemy.TYPE_D:
                    mEnX = screenW-mEnemy.getWidth()*2;
                    mEnY = 0;
                    mEnCx = 0;
                    mEnCy = -(mEnemy.getHeight()*2);
                    break;
                case DrawEnemy.TYPE_E:
                    mEnemy = mEnemyK;
                    mEnX = mEnemy.getWidth()*2;
                    mEnY = 0;
                    mEnCx = (screenW+mEnemy.getWidth())/ number;
                    mEnCy = 0;
                    break;
                case DrawEnemy.TYPE_F:
                    mEnemy = mEnemyC;
                    mEnX = 0;
                    mEnY = 0;
                    mEnCx = 0;
                    mEnCy = -(mEnemy.getHeight()+mEnemy.getHeight()/2);
                    break;
                case DrawEnemy.TYPE_G:
                    mEnemy = mEnemyC;
                    mEnX = screenW - mEnemy.getWidth()*2;
                    mEnY = 0;
                    mEnCx = 0;
                    mEnCy = -(mEnemy.getHeight()+mEnemy.getHeight()/2);
                    break;
                case DrawEnemy.TYPE_H:
                    mEnemy = mEnemyE;
                    mEnX = 0;
                    mEnY = 0;
                    mEnCx = -mEnemy.getWidth();
                    mEnCy = -mEnemy.getHeight();
                    break;
                case DrawEnemy.TYPE_I:
                    mEnemy = mEnemyE;
                    mEnX = screenW- mEnemy.getWidth()*2;
                    mEnY = 0;
                    mEnCx = mEnemy.getWidth();
                    mEnCy = -mEnemy.getHeight();
                    break;
                case DrawEnemy.TYPE_J:
                    mEnX = 0;
                    mEnY = 0;
                    mEnCx = -mEnemy.getWidth();
                    mEnCy = -mEnemy.getHeight();
                    break;
                case DrawEnemy.TYPE_K:
                    mEnX = screenW- mEnemy.getWidth()*2;
                    mEnY = 0;
                    mEnCx = mEnemy.getWidth();
                    mEnCy = -mEnemy.getHeight();
                    break;
                case DrawEnemy.TYPE_L:
                    mEnemy = mEnemyF;
                    mEnX = mEnemy.getWidth()*2;
                    mEnY = 0;
                    mEnCx = (screenW-mEnemy.getWidth())/ number;
                    mEnCy = 0;
                    break;
                case DrawEnemy.TYPE_M:
                    mEnemy = mEnemyF;
                    mEnX = mEnemy.getWidth()*2;
                    mEnY = 0;
                    mEnCx = (screenW-mEnemy.getWidth())/ number;
                    mEnCy = 0;
                    break;
                case DrawEnemy.TYPE_N:
                    mEnemy = mEnemyG;
                    mEnX = mEnemy.getWidth();
                    mEnY = 0;
                    mEnCx = 0;
                    mEnCy = -mEnemy.getHeight()*2;
                    break;
                case DrawEnemy.TYPE_O:
                    mEnemy = mEnemyG;
                    mEnX = screenW - mEnemy.getWidth()*2;
                    mEnY = 0;
                    mEnCx = 0;
                    mEnCy = -mEnemy.getHeight()*2;
                    break;
                case DrawEnemy.TYPE_P:
                    mEnX = -mEnemy.getWidth();
                    mEnY = screenH/2+screenW/3;
                    mEnCx = -mEnemy.getWidth();
                    mEnCy = 0;
                    break;
                case DrawEnemy.TYPE_Q:
                    mEnX = screenW - mEnemy.getWidth()*2;
                    mEnY = screenH/2+screenW/3;
                    mEnCx = mEnemy.getWidth();
                    mEnCy = 0;
                    break;
                case DrawEnemy.TYPE_R:
                    mEnemy = mEnemyH;
                    mEnX = 0;
                    mEnY = screenH/2;
                    mEnCx = -mEnemy.getWidth();
                    mEnCy = mEnemy.getHeight();
                    break;
                case DrawEnemy.TYPE_S:
                    mEnemy = mEnemyH;
                    mEnX = screenW - mEnemy.getWidth()*2;
                    mEnY = screenH/2;
                    mEnCx = mEnemy.getWidth();
                    mEnCy = mEnemy.getHeight();
                    break;
                case DrawEnemy.TYPE_T:
                    mEnemy = mEnemyZ;
                    mEnX = -mEnemy.getWidth();
                    mEnY = mEnemy.getHeight()*2;
                    mEnCx = 0;
                    mEnCy = 0;
                    life=10;
                    break;
                case DrawEnemy.TYPE_U:
                    mEnemy = mEnemyZ;
                    mEnX = screenW;
                    mEnY = mEnemy.getHeight()*2;
                    mEnCx = 0;
                    mEnCy = 0;

                    life=10;
                    break;
                case DrawEnemy.TYPE_V:
                    mEnemy = mEnemyI;
                    mEnX = mEnemy.getWidth();
                    mEnY = 0;
                    mEnCx = 0;
                    mEnCy = -mEnemy.getHeight()*2;
                    break;
                case DrawEnemy.TYPE_W:
                    mEnemy = mEnemyI;
                    mEnX = screenW-mEnemy.getWidth()*2;
                    mEnY = 0;
                    mEnCx = 0;
                    mEnCy = -mEnemy.getHeight()*2;
                    break;
                case DrawEnemy.TYPE_X:
                    mEnemy = mEnemyJ;
                    mEnX = screenW/2;
                    mEnY = 0;
                    mEnCx = 0;
                    mEnCy = -mEnemy.getHeight();
                    break;
                case DrawEnemy.TYPE_Y:
                    mEnemy = mEnemyD;
                    mEnX = screenW/2;
                    mEnY = 0;
                    mEnCx = mEnemy.getWidth()/2;
                    mEnCy = mEnemy.getHeight()/2;
                    life=10;
                    break;
                case DrawEnemy.TYPE_Z:
                    mEnemy = mEnemyY;
                    mEnX = random(screenW-mEnemy.getWidth(),0);
                    mEnY = 0;
                    mEnCx = 0;
                    mEnCy = 0;
                    life=10;
                    break;
            }
            for (int j = 0; j < number; j++) {
                if(j>0){
                    mEnX = tempX+ mEnCx;
                    mEnY = tempY+ mEnCy;
                }
                tempY = mEnY;
                tempX = mEnX;
                if(enemyType==DrawEnemy.TYPE_T||enemyType==DrawEnemy.TYPE_U){//高级敌机
                    mEnemyList.add(new DrawEnemy(getContext(),mEnemy,mEnX,mEnY,life,enemyType));
                }else if(enemyType==DrawEnemy.TYPE_V||enemyType==DrawEnemy.TYPE_W){//追踪的敌机
                    mEnemyList.add(new DrawEnemy(getContext(),mEnemy, mPlayer.getPlayerX(),mPlayer.getPlayerY(), mEnX, mEnY -mEnemy.getHeight()*3,life,enemyType));
                }else{//自定义敌机 普通模式
                    mEnemyList.add(new DrawEnemy(getContext(),mEnemy, mEnX, mEnY -mEnemy.getHeight()*3,life,enemyType));
                }
            }
        }
    }

    /**
     * 添加Enemy子弹
     */
    public void addEnemyBullet(DrawEnemy en){
        switch (en.getEnemyType()) {
            case DrawEnemy.TYPE_A:
            case DrawEnemy.TYPE_D:
                mEnemyBullets.add(new DrawEnemyBullet(getContext(),mEnemyBullet,mPlayer,en.getEnemyX()+(en.getWidth()/2),en.getEnemyY()+(en.getHeight()/2), DrawEnemyBullet.BULLET_C));
                break;
            case DrawEnemy.TYPE_T:
            case DrawEnemy.TYPE_U:
                mEnemyBullets.add(new DrawEnemyBullet(getContext(),mEnemyBullet,mPlayer,en.getEnemyX()+en.getWidth()/2,en.getEnemyY()+en.getHeight(), DrawEnemyBullet.BULLET_C));
                break;
            case DrawEnemy.TYPE_Y:
                mEnemyBullets.add(new DrawEnemyBullet(getContext(),mEnemyBullet,mPlayer,en.getEnemyX()+(en.getWidth()/2),en.getEnemyY()+(en.getHeight()/2), DrawEnemyBullet.BULLET_C));
                break;
            case DrawEnemy.TYPE_Z:
                mEnemyBullets.add(new DrawEnemyBullet(getContext(),mEnemyBullet,mPlayer,en.getEnemyX()+en.getWidth()/2,en.getEnemyY()+en.getHeight(), DrawEnemyBullet.BULLET_C));//中间
                mEnemyBullets.add(new DrawEnemyBullet(getContext(),mEnemyBullet,mPlayer,en.getEnemyX()+en.getWidth()/2- mEnemyBullet.getWidth()*2,en.getEnemyY()+en.getHeight(),DrawEnemyBullet.BULLET_C));//左边
                mEnemyBullets.add(new DrawEnemyBullet(getContext(),mEnemyBullet,mPlayer,en.getEnemyX()+en.getWidth()/2+ mEnemyBullet.getWidth()*2,en.getEnemyY()+en.getHeight(),DrawEnemyBullet.BULLET_C));//右边
                break;
            default:
                mEnemyBullets.add(new DrawEnemyBullet(getContext(),mEnemyBullet,en, DrawEnemyBullet.BULLET_A));
                break;
        }
    }

    /**
     * @param num 随机范围
     * @return
     */
    public int random(float num,int min){
        int m;
        m = new Random().nextInt((int) num);
        if(m<min){
            m=min;
        }
        return m;
    }

    public int getEnemyArrayIndex() {
        return enemyArrayIndex;
    }

    public List<DrawEnemy> getEnemyList() {
        return mEnemyList;
    }

    public List<DrawEnemyBullet> getEnemyBullets() {
        return mEnemyBullets;
    }
}
