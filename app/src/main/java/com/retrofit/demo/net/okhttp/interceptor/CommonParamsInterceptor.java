package com.retrofit.demo.net.okhttp.interceptor;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommonParamsInterceptor implements Interceptor {

    private Map<String, String> commonParams;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        RequestBody body = request.body();
        if (body instanceof FormBody) {
            FormBody.Builder newBody = new FormBody.Builder();
            for (int i = 0; i < ((FormBody) body).size(); i++) {
                newBody.addEncoded(((FormBody) body).encodedName(i), ((FormBody) body).encodedValue(i));
            }
            for (Map.Entry<String, String> entry : commonParams.entrySet()) {
                newBody.add(entry.getKey(), entry.getValue());
            }
            builder.method(request.method(), newBody.build());
        }
        return chain.proceed(builder.build());
    }

    public void setCommonParams(Map<String, String> commonParams) {
        this.commonParams = commonParams;
    }
}
