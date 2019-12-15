package com.retrofit.demo.net;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.retrofit.demo.net.demo.ApiUrl;
import com.retrofit.demo.net.demo.ConfigNet;

import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit封装
 */
public class RetrofitUtils {
    private volatile static ApiUrl mApiUrl;

    /**
     * 单例模式
     */
    public static ApiUrl getApiUrl() {
        if (mApiUrl == null) {
            synchronized (RetrofitUtils.class) {
                if (mApiUrl == null) {
                    mApiUrl = new RetrofitUtils().getRetrofit();
                }
            }
        }
        return mApiUrl;
    }

    private RetrofitUtils() {
    }

    private ApiUrl getRetrofit() {
        // 初始化Retrofit
        ApiUrl apiUrl = initRetrofit(initOkHttp()).create(ApiUrl.class);
        return apiUrl;
    }

    /**
     * 初始化Retrofit
     */
    @NonNull
    private Retrofit initRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(ConfigNet.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 初始化okhttp
     */
    @NonNull
    private OkHttpClient initOkHttp() {
        return new OkHttpClient().newBuilder()
                .readTimeout(ConfigNet.DEFAULT_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .connectTimeout(ConfigNet.DEFAULT_TIMEOUT, TimeUnit.SECONDS)//设置请求超时时间
                .writeTimeout(ConfigNet.DEFAULT_TIMEOUT, TimeUnit.SECONDS)//设置写入超时时间
                .addInterceptor(new LogInterceptor())//添加打印拦截器
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                .build();
    }
}

