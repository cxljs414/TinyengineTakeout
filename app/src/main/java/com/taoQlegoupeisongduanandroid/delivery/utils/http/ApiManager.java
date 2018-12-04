package com.taoQlegoupeisongduanandroid.delivery.utils.http;

import com.taoQlegoupeisongduanandroid.delivery.app.App;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.SP;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by changxing on 2016/7/26.
 */
public class ApiManager {
    private static final int DEFAULT_TIMEOUT = 3000;


    private ApiService apiService;
    private Retrofit retrofit;

    private static ApiManager apiManager;
    /*public static ApiManager getInstance(){
        if(apiManager == null){
            synchronized (ApiManager.class){
                if(apiManager == null)apiManager = ;
            }
        }
        return apiManager;
    }*/

    /**
     * 私有构造方法
     */
    public ApiManager(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //创建一个httpclient，并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.addInterceptor(interceptor);
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(App.BASE_URL)
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService(){
        return apiService;
    }

}
