package com.taoQlegoupeisongduanandroid.delivery.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.taoQlegoupeisongduanandroid.delivery.R;
import com.taoQlegoupeisongduanandroid.delivery.app.App;
import com.taoQlegoupeisongduanandroid.delivery.module.init.HomeActivity;
import com.taoQlegoupeisongduanandroid.delivery.module.init.LoginActivity;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.CommonUtils;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.SP;

import java.util.Random;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by changxing on 2016/8/1.
 */
public class JpushReceiver extends BroadcastReceiver {

    NotificationManager nm;
    @Override
    public void onReceive(Context context, Intent intent) {

        if(nm == null){
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        Log.d("MyReceiver", "onReceive - " + intent.getAction());

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
        }else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Bundle msgbundle = intent.getExtras();
            Log.d("MyReceiver","收到自定义消息");
            //showNotification(context, msgbundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            System.out.println("收到了通知");
            Bundle notbundle = intent.getExtras();
            String msg = notbundle.getString(JPushInterface.EXTRA_EXTRA);

            Intent intent1 = new Intent("com.jpush.received");
            intent1.putExtra("extra",msg);
            context.sendBroadcast(intent1);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            System.out.println("用户点击打开了通知");
            /**
             * 首先判断是否开启了应用，若未开启应用则点击通知只是打开应用
             * 如果已经开启应用而放在了后台，则判断是否已经登陆，如果未登陆则跳入登陆页面,如果已经在登陆页面则无跳转
             * 如果已经开启，并且已经登陆，则跳入主页，如果已经在主页，无跳转
             */
            if(CommonUtils.isRunningApp(context,"com.taoQlegoupeisongduanandroid.delivery")){
                if(!SP.contains(context,"token")
                        || SP.get(context,"token","").equals("")){
                    if(!CommonUtils.getTopActivity(context)
                            .equals("ComponentInfo{com.taoQlegoupeisongduanandroid.delivery/com.taoQlegoupeisongduanandroid.delivery.module.init.LoginActivity}")) {
                        Intent intent1 = new Intent(context, LoginActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        context.startActivity(intent1);
                    }
                }else {
                    Bundle notbundle = intent.getExtras();
                    String msg = notbundle.getString(JPushInterface.EXTRA_EXTRA);
                    if(!CommonUtils.getTopActivity(context)
                            .equals("ComponentInfo{com.taoQlegoupeisongduanandroid.delivery/com.taoQlegoupeisongduanandroid.delivery.module.init.HomeActivity}")){
                        App.getInstance().removeHomeTopActivity();
                        Intent intent1 = new Intent(context,HomeActivity.class);
                        intent1.putExtra("extra",msg);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        context.startActivity(intent1);
                    }else{
                        Intent intent1 = new Intent(context,HomeActivity.class);
                        intent1.putExtra("extra",msg);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        context.startActivity(intent1);
                    }
                }

            }else{
                Intent lunch = new Intent();
                lunch.setComponent(new ComponentName("com.taoQlegoupeisongduanandroid.delivery","com.taoQlegoupeisongduanandroid.delivery.module.init.MainActivity"));
                lunch.setAction(Intent.ACTION_VIEW);
                lunch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(lunch);
            }


        } else {
            Log.d("MyReceiver", "Unhandled intent - " + intent.getAction());
        }
    }

    /**
     * 显示自定义通知
     */
    private void showNotification(Context context,Bundle msgbundle) {
        int noid = new Random().nextInt(100);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.logo)
                .setWhen(System.currentTimeMillis()).setAutoCancel(true)
                .setContentTitle(msgbundle.getString(JPushInterface.EXTRA_TITLE))
                .setContentText(msgbundle.getString(JPushInterface.EXTRA_MESSAGE))
                .setTicker("BHAF消息")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo))
                .setLights(0xff0000ff, 300, 0);

        Intent intent = new Intent(context, HomeActivity.class);  //自定义打开的界面
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("notificationId", noid);
        intent.putExtra("json", msgbundle.getString(JPushInterface.EXTRA_EXTRA));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        Notification notification = mBuilder.build();
        nm.notify(noid, notification);


        /**
         * 实现更高高度的通知栏
         */
       /* RemoteViews bigView = new RemoteViews(context.getApplicationContext().getPackageName(),
                R.layout.notification_layout_big);

        // bigView.setOnClickPendingIntent() etc..

        Notification.Builder mNotifyBuilder = new Notification.Builder(context);
        notification = mNotifyBuilder.setContentTitle("some string")
                .setContentText("Slide down on note to expand")
                .setSmallIcon(R.)
                .setLargeIcon(bigIcon)
                .build();

        notification.bigContentView = bigView;*/
    }
}
