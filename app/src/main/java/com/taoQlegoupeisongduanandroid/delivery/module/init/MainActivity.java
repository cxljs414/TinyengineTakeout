package com.taoQlegoupeisongduanandroid.delivery.module.init;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.taoQlegoupeisongduanandroid.delivery.R;
import com.taoQlegoupeisongduanandroid.delivery.app.App;
import com.taoQlegoupeisongduanandroid.delivery.bean.SerialBean;
import com.taoQlegoupeisongduanandroid.delivery.module.base.BaseActivity;
import com.taoQlegoupeisongduanandroid.delivery.utils.http.ApiService;
import com.taoQlegoupeisongduanandroid.delivery.utils.http.RequestSubscriber;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.SP;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.TS;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_image)ImageView imageView;
    @BindView(R.id.serial_container)LinearLayout serialLayout;
    @BindView(R.id.serial_et)EditText serialEt;

    private String logs;
    @Override
    public void initConentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        apiManager = null;
        setToolBar(R.string.app_name);
        hideToolBar();
        if(SP.containsSerial(mContext,"serial_sn")){

            AlphaAnimation alphaAnimation = new AlphaAnimation(1,1);
            alphaAnimation.setDuration(3000);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    checkLogin();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            imageView.startAnimation(alphaAnimation);
        }else{
            serialLayout.setVisibility(View.VISIBLE);
        }


    }

    public void serialCommit(View view){
        String serial = serialEt.getText().toString();
        if(serial.equals("")){
            TS.show(mContext,"请输入序列号");
            return;
        }
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //创建一个httpclient，并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(3000, TimeUnit.MILLISECONDS);
        builder.addInterceptor(interceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://up.hao071.com/")
                .build();
        ApiService apiService = retrofit.create(ApiService.class);


        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("serial_sn",serial);
        map.put("type","deliveryer");
        map.put("client","android");
        toSubscribe(apiService.getSerial(map).map(new HttpResultFunc<SerialBean>()),
                new RequestSubscriber<SerialBean>(this,this){
                    @Override
                    public void onNext(SerialBean bean) {
                        super.onNext(bean);
                        if(bean.getPort().equals("80")){
                            App.BASE_URL = "http://"+bean.getUrl()+"/";
                        }else if(bean.getPort().equals("443")){
                            App.BASE_URL = "https://"+bean.getUrl()+"/";
                        }

                        App.API_UNIACID = bean.getUniacid();
                        SP.putSerial(mContext,"url",bean.getUrl());
                        SP.putSerial(mContext,"uniacid",bean .getUniacid());
                        SP.putSerial(mContext,"serial_sn",bean.getSerial_sn());
                        SP.putSerial(mContext,"port",bean.getPort());
                        Intent intent = new Intent(mContext,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    /**
     * 检查是否已经登陆
     */
    private void checkLogin() {
        Intent intent = null;
        if(SP.contains(mContext,"token") && !((String)SP.get(mContext,"token","")).equals("")){
            //已经登录
            intent = new Intent(mContext,HomeActivity.class);
        }else{
            intent = new Intent(mContext,LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }

}
