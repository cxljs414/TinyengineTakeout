package com.taoQlegoupeisongduanandroid.delivery.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.taoQlegoupeisongduanandroid.delivery.app.App;
import com.taoQlegoupeisongduanandroid.delivery.bean.HttpResult;
import com.taoQlegoupeisongduanandroid.delivery.bean.SendLocationBean;
import com.taoQlegoupeisongduanandroid.delivery.utils.http.ApiManager;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.SP;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.WakeLockUtil;

import java.util.LinkedHashMap;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class LocationService extends Service implements AMapLocationListener{
    private WakeLockUtil wakeLockUtil;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private static final int LOCATION_INTERVAL = 20000;
    private Messenger messenger;
    protected ApiManager apiManager ;
    //private NetReceiver receiver;
    private Long times = 0L;

    public LocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        apiManager = new ApiManager();
        //receiver = new NetReceiver();
        wakeLockUtil = new WakeLockUtil(this);
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        registerReceiver(receiver,filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new MyIBinder();
    }

    /**
     * 开启定位
     */
    public void startLocation() {
            System.out.println("开始定位");
            wakeLockUtil.acquireWakeLock();
            locationClient = new AMapLocationClient(this.getApplicationContext());
            locationOption = new AMapLocationClientOption();
            // 设置定位模式为高精度模式
            locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            // 设置定位监听
            locationClient.setLocationListener(this);
            initOption();

    }
    // 根据控件的选择，重新设置定位参数
    private void initOption() {
        // 设置是否需要显示地址信息
        //locationOption.setNeedAddress(true);
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
        //locationOption.setGpsFirst(true);
        // 设置是否开启缓存
        //locationOption.setLocationCacheEnable(true);
        //设置是否等待设备wifi刷新，如果设置为true,会自动变为单次定位，持续定位时不要使用
        //locationOption.setOnceLocationLatest(false);
        // 设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算
        locationOption.setInterval(Long.valueOf(LOCATION_INTERVAL));

        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        //上传位置
        if(null != aMapLocation){
//            Calendar calendar = Calendar.getInstance();
//            long curTimes = calendar.getTimeInMillis();
//            if(curTimes-times < LOCATION_INTERVAL)return;
//            else times = curTimes;

            if (aMapLocation.getErrorCode() == 0) {

                Bundle b = new Bundle();
                b.putString("msg","定位成功service,"+"经度："+aMapLocation.getLongitude()+"  纬度："+aMapLocation.getLatitude());
                Message msg = new Message();
                msg.what = 1;
                msg.setData(b);
                try {
                    messenger.send(msg);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
                sendLocation(aMapLocation);
            }
        }
    }

    /**
     * 上传位置
     */
    private void sendLocation(AMapLocation aMapLocation) {
        //i="+API_UNIACID+"&c=entry&do=dyset&op=location&m=we7_wmall
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("i", App.API_UNIACID);
        map.put("c","entry");
        map.put("m","we7_wmall");
        map.put("do","dyset");
        map.put("op","location");
        map.put("token", (String) SP.get(this,"token",""));
        map.put("location_x",aMapLocation.getLatitude()+"");
        map.put("location_y",aMapLocation.getLongitude()+"");
        Log.i("Location","x:"+aMapLocation.getLatitude()+"  y:"+aMapLocation.getLongitude());
        apiManager.getApiService().sendLocation(map).map(new HttpResultFunc<SendLocationBean>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SendLocationBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                       /* Bundle b = new Bundle();
                        b.putString("msg","发送位置失败service"+e.getMessage());
                        Message msg = new Message();
                        msg.what = 1;
                        msg.setData(b);
                        try {
                            messenger.send(msg);
                        } catch (RemoteException e1) {
                            e1.printStackTrace();
                        }*/
                    }

                    @Override
                    public void onNext(SendLocationBean sendLocationBean) {
                       /* Bundle b = new Bundle();
                        b.putString("msg","发送位置成功service");
                        Message msg = new Message();
                        msg.what = 1;
                        msg.setData(b);
                        try {
                            messenger.send(msg);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }*/
                    }
                });
    }

    public void setHandle(Handler handle){
        messenger = new Messenger(handle);
    }

//    public class NetReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            /**
//             * 监听网络变化
//             */
//            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
//                if (CommonUtils.isNetworkAvailable(context)) {
//                    //如果从无网络变成有网络
//                    startLocation();
//                    System.out.println("开始定位22");
//                }
//            }
//        }
//    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     * @param <>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    public class HttpResultFunc<T> implements Func1<HttpResult<T>,T> {

        @Override
        public T call(HttpResult<T> tHttpResult) {
            if(tHttpResult.getMessage().getResultCode() != 0){
                throw new RuntimeException(tHttpResult.getMessage().getResultMessage());
            }
            return tHttpResult.getMessage().getData();
        }
    }

    public class MyIBinder extends Binder {

        public LocationService getService(){
            return LocationService.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public synchronized void stopListenLocation() {
        if (locationClient != null ) {
            // alocationlistener=null;

            locationClient.stopLocation();
            locationClient.onDestroy();
            locationClient = null;
            locationClient = null;
            wakeLockUtil.releaseWakeLock();
        }
    }
}
