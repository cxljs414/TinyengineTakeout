package com.taoQlegoupeisongduanandroid.delivery.app;

import android.app.Activity;
import android.app.Application;

import com.iflytek.cloud.SpeechUtility;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.SP;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.TS;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by changxing on 2016/7/26.
 */
public class App extends Application {

    private static App app = null;
    private List<Activity> activityList = new ArrayList<>();
    private List<Activity> homeTopList = new ArrayList<>();
    public static String BASE_URL = "" ;
    public static String API_UNIACID = "";
    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 极光推送初始化
         */
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        /**
         * 讯飞语音
         */
        SpeechUtility.createUtility(App.this, "appid=" + "5be24801");// 579f169d

        /**
         * 判断是否有序列号
         */
        if(SP.containsSerial(getApplicationContext(),"serial_sn")){
            String port = (String) SP.getSerial(getApplicationContext(),"port","80");
            String url = (String) SP.getSerial(getApplicationContext(),"url","");
            if(port.equals("80")){
                this.BASE_URL = "http://"+url+"/";
            }else if(port.equals("443")){
                this.BASE_URL = "https://"+url+"/";
            }

            this.API_UNIACID = (String) SP.getSerial(getApplicationContext(),"uniacid","1");
        }
    }


    /**
     * 单例模式获取App实例
     * @return
     */
    public static App getInstance(){
        if(app == null){
            synchronized (App.class){
                if(app == null) app = new App();
            }
        }
        return app;
    }

    /**
     * 记录已经打开的页面
     * @param activity
     */
    public void addActivity(Activity activity){
        activityList.add(activity);
    }

    /**
     * 退出程序的时候关闭所有页面
     */
    public void exit(){
        for(Activity activity : activityList){
            activity.finish();
        }
    }

    public void addHomeTop(Activity activity){
        homeTopList.add(activity);
    }

    public void removeHomeTopActivity(){
        for(Activity activity : homeTopList){
            activity.finish();
        }
    }

}
