package com.taoQlegoupeisongduanandroid.delivery.module.base;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.sunflower.FlowerCollector;
import com.taoQlegoupeisongduanandroid.delivery.R;
import com.taoQlegoupeisongduanandroid.delivery.app.App;
import com.taoQlegoupeisongduanandroid.delivery.bean.HttpResult;
import com.taoQlegoupeisongduanandroid.delivery.service.NetChangeListener;
import com.taoQlegoupeisongduanandroid.delivery.utils.http.ApiManager;
import com.taoQlegoupeisongduanandroid.delivery.utils.http.OnRefreshListener;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.CommonUtils;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.SP;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.SystemBarTintManager;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.TS;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by changxing on 2016/7/26.
 */
public abstract class BaseActivity extends CheckPermissionsActivity implements
        OnRefreshListener {
    private static final String APPID = "appid=579f169d";
    private static final int LOCATION_INTERVAL = 20000;
    protected Context mContext;
    protected App app;
    protected Subscription subscription;
    protected ApiManager apiManager ;
    private NetChangeListener netListener;
    private NetReceiver receiver;
    private boolean IsRefreshing = false;
    private boolean IsRefreshEnable = false;
    private boolean BackIsExit = false;
    private boolean IsLocationEnable = false;
    protected  boolean netTost = false;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    // 语音合成对象
    private SpeechSynthesizer mTts;
    // 缓冲进度
    private int mPercentForBuffering = 0;
    // 播放进度
    private int mPercentForPlaying = 0;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    // 默认发音人
    private String voicer = "xiaoyan";

    @BindView(R.id.base_container)LinearLayout container;
    protected  @BindView(R.id.base_refresh)SwipeRefreshLayout refresh;
    public @BindView(R.id.toolbar)Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = this;
        app = App.getInstance();
        app.addActivity(this);
        if(apiManager == null && !App.BASE_URL.equals("")){
            apiManager = new ApiManager();
        }
        initConentView(savedInstanceState);

        refresh.setEnabled(IsRefreshEnable);
        setStatusColor();
        mTts = SpeechSynthesizer.createSynthesizer(mContext, mTtsInitListener);
    }

    protected abstract void initConentView(Bundle savedInstanceState);

    /**
     * 设置toolbar
     */
    protected void setToolBar(int titleId) {
        toolbar.setTitle(titleId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideRefresh();
                onBackPressed();
                if(BackIsExit)app.exit();
            }
        });
    }

    protected void setBackIsExit(boolean isExit){
        BackIsExit = isExit;
    }
    /**
     * 设置toolbar
     */
    protected void setToolBar(String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideRefresh();
                onBackPressed();
            }
        });
    }

    /**
     * 设置标题
     * @param title
     */
    protected void setToolTitle(String title){
        toolbar.setTitle(title);
    }

    /**
     * 设置标题
     * @param titleId
     */
    protected void setToolTitle(int titleId){
        toolbar.setTitle(titleId);
    }

    public void setNetListener(NetChangeListener netListener) {
        this.netListener = netListener;
        receiver = new NetReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("com.jpush.received");
        mContext.registerReceiver(receiver,filter);
    }

    /**
     * 隐藏toolbar
     */
    protected void hideToolBar(){
        toolbar.setVisibility(View.GONE);
    }


    protected SwipeRefreshLayout getRefresh(){
        if(refresh!= null)
        return refresh;
        else return null;
    }
    /**
     * 显示进度条
     */
    protected void showRefresh(){
        refresh.setRefreshing(true);
        IsRefreshing = true;
    }

    protected void setRefreshEnable(boolean enable){
        IsRefreshEnable = enable;
    }
    protected void setRefreshListener(SwipeRefreshLayout.OnRefreshListener listener){
        refresh.setOnRefreshListener(listener);
    }
    protected  boolean isRefreshing(){
        return IsRefreshing;
    }
    /**
     * 隐藏进度条
     */
    protected void hideRefresh(){
        refresh.setRefreshing(false);
        IsRefreshing = false;
    }

    /**
     * 解除绑定
     */
    protected void unSubscribe(){
        if(subscription != null && subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }


    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public int getVersionCode() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            return  info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 100;
        }
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public String getVersionName() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return  version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(mContext);

    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(mContext);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unSubscribe();
        if(receiver != null)
            mContext.unregisterReceiver(receiver);
    }

    @Override
    public void onRefreshing() {
        showRefresh();
    }
    @Override
    public void onRereshStop() {
        hideRefresh();
    }

    /**
     * 设置每个页面的导航栏和下面操作栏的颜色，这个只有19以后才有
     */
    private void setStatusColor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimary);//加载你的actionbar的颜色或者背景图
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setNavigationBarTintResource(R.color.colorPrimary);
        }
    }

    protected  <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
        subscription =  o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     * @param <>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    public class HttpResultFunc<T> implements Func1<HttpResult<T>,T> {

        @Override
        public T call(HttpResult<T> tHttpResult) {
            if(tHttpResult.getMessage().getResultCode() != 0){
                if(tHttpResult.getMessage().getResultCode() == -1){
                    throw new RuntimeException(tHttpResult.getMessage().getResultMessage());
                }else{
                    throw new RuntimeException("请求失败");
                }

            }
            return tHttpResult.getMessage().getData();
        }
    }

    public class NetReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            /**
             * 监听网络变化
             */
            if(intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")){
                if(CommonUtils.isNetworkAvailable(context)){
                    //如果从无网络变成有网络
                    netListener.onNetChange(true);
                }else{
                    if(netTost){
                        TS.show(mContext,"网络连接不可用");
                    }
                    netListener.onNetChange(false);
                }
            }

            if(intent.getAction().equals("com.jpush.received")){
                //震动
                if((boolean) SP.get(context,"vibrateNotice",true)){
                    Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
                    if(vib == null)TS.show(mContext,"未开启震动");
                    else vib.vibrate(new long[]{100,1000,100,1000,100,1000},-1);
                }

                //语音播报
                if((boolean)SP.get(context,"voiceNotice",true)){
                    JSONObject jsonObject = JSON.parseObject(intent.getStringExtra("extra"));
                    if(jsonObject != null){
                        try {
                            repeatCount = Integer.parseInt(jsonObject.getString("voice_play_nums"));
                            ttsContent = jsonObject.getString("voice_text");
                            // 移动数据分析，收集开始合成事件
                            FlowerCollector.onEvent(mContext, "tts_play");
                            // 设置参数
                            setParam();
                            mTts.startSpeaking(ttsContent, mTtsListener);
                        } catch (NumberFormatException e) {
                            repeatCount = 0;
                        }

                    }
                    //TS.show(mContext,ttsContent+"  "+repeatCount);
                }
            }
        }
    }


    /**
     * 参数设置
     * @return
     */
    private void setParam(){
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if(mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, "50");
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, "50");
        }else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");
            /**
             * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
             * 开发者如需自定义参数，请参考在线合成参数设置
             */
        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");
    }


    int repeatCount = 3;
    String ttsContent = ""; // 朗读内容
    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                TS.show(mContext,"初始化失败,错误码："+code);
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
        }

        @Override
        public void onSpeakPaused() {
        }

        @Override
        public void onSpeakResumed() {
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
            mPercentForBuffering = percent;
            /*TS.show(mContext,String.format("缓冲进度为%d%%，播放进度为%d%%",
                    mPercentForBuffering, mPercentForPlaying));*/
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
            mPercentForPlaying = percent;
            /*TS.show(mContext,String.format("缓冲进度为%d%%，播放进度为%d%%",
                    mPercentForBuffering, mPercentForPlaying));*/
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                //TS.show(mContext,"播放完成");
                repeatCount -- ;
                if(repeatCount > 0){
                    // 移动数据分析，收集开始合成事件
                    FlowerCollector.onEvent(mContext, "tts_play");
                    // 设置参数
                    setParam();
                    mTts.startSpeaking(ttsContent, mTtsListener);
                }else{
                    repeatCount = 3;
                }

            } else if (error != null) {
                TS.show(mContext,error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };
}
