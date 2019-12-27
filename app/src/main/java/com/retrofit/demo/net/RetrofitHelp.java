package com.retrofit.demo.net;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.retrofit.demo.net.demo.ConfigNet;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.retrofit.demo.net.okhttp.interceptor.*;

/**
 * Retrofit封装
 */
public class RetrofitHelp {
    private volatile static Retrofit retrofit;
    private volatile static OkHttpClient okHttpClient;

    public static <T> T create(Class<T> service) {
        return getRetrofit().create(service);
    }

    private RetrofitHelp() {
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitHelp.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .client(getOkHttpClient())
                            .baseUrl(ConfigNet.BASE_URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            synchronized (RetrofitHelp.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient().newBuilder()
                            .readTimeout(ConfigNet.DEFAULT_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                            .connectTimeout(ConfigNet.DEFAULT_TIMEOUT, TimeUnit.SECONDS)//设置请求超时时间
                            .writeTimeout(ConfigNet.DEFAULT_TIMEOUT, TimeUnit.SECONDS)//设置写入超时时间
                            .addInterceptor(new LogInterceptor())//添加打印拦截器
                            .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                            .build();
                }
            }
        }
        return okHttpClient;
    }

}

