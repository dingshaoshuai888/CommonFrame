package com.retrofit.demo.net.okhttp.interceptor;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ContentTypeInterceptor implements Interceptor {

    private String contentType;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (TextUtils.isEmpty(contentType)) {
            return chain.proceed(request);
        } else {
            Request.Builder builder = request.newBuilder()
                    .removeHeader("Content-Type")
                    .addHeader("Content-Type", contentType);
            return chain.proceed(builder.build());
        }
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
