package com.wzf.com.sample.util;

import android.util.Log;

/**
 * 对所有java文档打印log信息进行控制，如果是debug模式则进行log信息打印，如果是正常模式，则不打印log信息
 * @author wzf
 *
 */
public class L {

	private static boolean isDebug = true;//判断是否为debug模式
	private static String TAG = "Sample";

	public static void i(String msg) {
		if(isDebug)
			Log.i(TAG, msg);
	}
	public static void iTag(String tag, String msg) {
		if(isDebug)
			Log.i(tag, msg);
	}
	public static void e(String msg) {
		if(isDebug)
			Log.e(TAG, msg);
	}
	public static void eTag(String tag, String msg) {
		if(isDebug)
			Log.e(tag, msg);
	}
	public static void d(String msg) {
		if(isDebug)
			Log.d(TAG, msg);
	}
	public static void dTag(String tag, String msg) {
		if(isDebug)
			Log.d(tag, msg);
	}
	public static void v(String msg) {
		if(isDebug)
			Log.v(TAG, msg);
	}
	public static void vTag(String tag, String msg) {
		if(isDebug)
			Log.v(tag, msg);
	}
	public static void w(String msg) {
		if(isDebug)
			Log.w(TAG, msg);
	}
	public static void wTag(String tag, String msg) {
		if(isDebug)
			Log.w(tag, msg);
	}
}
