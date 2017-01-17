package com.wzf.com.sample.util;

import android.content.Context;
import android.widget.Toast;

public class Tools {
	
	private static Toast mtoast;//短toast
	private static Toast longToast;//长toast
	private static Toast customToast;//自定义显示时间
	
	public static void showInfo(Context context,String info) {
		if(mtoast == null) {
			mtoast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
		}else {
			mtoast.setText(info);
		}
		mtoast.show();
	}
	public static void showLongInfo(Context context, String info) {
		if(longToast == null) {
			longToast = Toast.makeText(context, info, Toast.LENGTH_LONG);
		}else {
			longToast.setText(info);
		}
		longToast.show();
	}
	public static void showCustomTimeToast(Context context, String info, int showTime) {
		if(customToast == null) {
			customToast = Toast.makeText(context, info, showTime);
		}else {
			customToast.cancel();
			customToast = Toast.makeText(context, info, showTime);
		}
		customToast.show();
	}
}
