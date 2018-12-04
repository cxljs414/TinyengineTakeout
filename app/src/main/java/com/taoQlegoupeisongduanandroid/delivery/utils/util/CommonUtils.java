package com.taoQlegoupeisongduanandroid.delivery.utils.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class CommonUtils {

	/** 检查是否有网络 */
	public static boolean isNetworkAvailable(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		if (info != null) {
			return info.isAvailable();
		}
		return false;
	}

	/** 检查是否是WIFI */
	public static boolean isWifi(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		if (info != null) {
			if (info.getType() == ConnectivityManager.TYPE_WIFI)
				return true;
		}
		return false;
	}

	/** 检查是否是移动网络 */
	public static boolean isMobile(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		if (info != null) {
			if (info.getType() == ConnectivityManager.TYPE_MOBILE)
				return true;
		}
		return false;
	}

	private static NetworkInfo getNetworkInfo(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo();
	}

	/** 检查SD卡是否存在 */
	public static boolean checkSdCard() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	/** 获取版本名称 */
	public static String getVersionName(Context context) {
		String versionName = "暂无信息";
		try {
		 versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return  versionName;
	}
	/** 获取版本号 */
	public static int getVersionCode(Context context) {
		int versionCode = 1;
		try {
			versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return  versionCode;
	}

	public static boolean isRunningApp(Context context, String packageName) {
		boolean isAppRunning = false;
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
		for (ActivityManager.RunningTaskInfo info : list) {
			if (info.topActivity.getPackageName().equals(packageName) && info.baseActivity.getPackageName().equals(packageName)) {
				isAppRunning = true;
				break;
			}
		}
		return isAppRunning;
	}

	/**
	 * 获取栈顶的acitivity
	 * @param context
	 * @return
	 */
	public static String getTopActivity(Context context)
	{
		ActivityManager manager = (ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE) ;
		List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1) ;

		if(runningTaskInfos != null)
			return (runningTaskInfos.get(0).topActivity).toString() ;
		else
			return null ;
	}

	/**
	 * 拨打电话
	 * @param context
	 * @param tel
     */
	public static void telphoneCall(Context context,String tel){
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:"+tel));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 普通提示框
	 * @param mContext
	 * @param message
	 * @param listener
     */
	public static void showDialog(Context mContext,String message, DialogInterface.OnClickListener listener){
		new AlertDialog.Builder(mContext)
				.setMessage(message)
				.setNegativeButton("取消",null)
				.setPositiveButton("确定", listener).show();
	}

	/**
	 * 弹出可输入的对话框
	 * @param mContext
	 * @param childView
	 * @param title
	 * @param listener
     */
	public static void showInputDialog(Context mContext, View childView ,String title, DialogInterface.OnClickListener listener){
		new AlertDialog.Builder(mContext)
				.setTitle(title)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setView(childView)
				.setPositiveButton("确定", listener)
				.setNegativeButton("取消", null)
				.show();
	}

}
