package com.tangyx.game.util;

import android.util.Log;

public class SLog {
	private final static String TAG=SLog.class.getName();
	public final static boolean debug = true;
	/**
	 * log输出
	 */
	public static void d(String msg){
		Log.d(TAG, msg);
	}
	public static void e(String msg){
		Log.e(TAG, msg);
	}
	public static void v(String msg){
		Log.v(TAG, msg);
	}
	public static void w(String msg){
		Log.w(TAG, msg);
	}
	public static void i(String msg){
		Log.i(TAG, msg);
	}
}
