package com.tangyx.game.util;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class BitmapUtils {
	/**
	 * 处理游戏图片
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getBitmap(Bitmap bitmap,float sx,float sy){
		Matrix matrix = new Matrix();
		matrix.postScale(sx, sy);
		Bitmap tempBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
		return tempBmp;
	}
	/**
	 * 按照大小处理图片
	 */
	public static Bitmap getBitmap(Bitmap bitmap,int w,int h){
		Matrix matrix = new Matrix();
		float bw = bitmap.getWidth();
		float bh = bitmap.getHeight();
		float sw = w / bw;
		float sh = h / bh;
		matrix.postScale(sw,sh);
		return getBitmap(bitmap,sw,sh);
	}
	/**
	 * 镜像垂直翻转图片
	 */
	public static Bitmap getScaleMap(Bitmap bitmap){
		Matrix matrix = new Matrix();
		matrix.postScale(1, -1);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}
	/**
	 * 读取资源图片
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap ReadBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}
	/**
	 * 读取资源图片（Assets）
	 * @param context
	 * @param resname
	 * @return
	 */
	public static Bitmap ReadBitMap(Context context,String resname) {
		try {
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inPreferredConfig = Bitmap.Config.RGB_565;
			opt.inPurgeable = true;
			opt.inInputShareable = true;
			InputStream is = context.getAssets().open(resname);
			return BitmapFactory.decodeStream(is, null, opt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 按照比例处理图片
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getBitmap(Context context,Bitmap bitmap,int type){
		Bitmap tempBmp = null;
		try {
			Matrix matrix = new Matrix();
			float scaleW = (float) ScreenUtils.getScreenWidth(context)/bitmap.getWidth();
			float scaleH = (float) ScreenUtils.getScreenHeight(context)/bitmap.getHeight();
			if(type==1){
				matrix.postScale(scaleW, scaleW);
			}else if(type==2){
				scaleW = ((float)bitmap.getWidth())/2/100;
				matrix.postScale(scaleW, scaleW);
			}else if(type==3){
				scaleW = scaleW/2;
				matrix.postScale(scaleW, scaleW);
			}else if(type==4){
				matrix.postScale(1, scaleH);
			}else{
				matrix.postScale(scaleW, scaleH);
			}
			tempBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempBmp;
	}
	/**
	 * 图片切割器 按照宽度切割
	 */
	public static Bitmap[] widthSplit(Bitmap bitMap, int spite){
		Bitmap[] bits = new Bitmap[spite];
		int bw = bitMap.getWidth();
		int bh = bitMap.getHeight();
		int piecW = bw / spite;
		for (int j = 0; j <spite; j++) {
			int xv = j * piecW;
			bits[j] = Bitmap.createBitmap(bitMap,
					xv,
					0,
					piecW,
					bh);
		}
		return bits;
	}
	/**
	 * 图片切割器 按照高度切割
	 */
	public static Bitmap[] heightSpite(Bitmap bitMap,int spite){
		Bitmap[] bits = new Bitmap[spite];
		int bw = bitMap.getWidth();
		int bh = bitMap.getHeight();
		int piecH = bh / spite;
		int xy=0;
		for (int j = 0; j <spite; j++) {
			xy = j*piecH;
			bits[j] = Bitmap.createBitmap(bitMap,
					0,
					xy,
					bw,
					piecH);
		}
		return bits;
	}
}
