package com.retrofit.demo.net.okhttp.interceptor;

import android.text.TextUtils;

import com.retrofit.demo.config.LogTag;
import com.retrofit.demo.util.MLog;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * TODO Log拦截器代码
 */
public class LogInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        synchronized (LogInterceptor.class) {
            Request request = chain.request();
            printRequest(request);
            return printResponse(chain.proceed(request));
        }
    }

    private void printRequest(Request request) throws IOException {
        Headers headers = request.headers();
        RequestBody body = request.body();
        MLog.i(LogTag.OKHTTP, "=========================== Request url:\n" + request.url().toString());
        MLog.i(LogTag.OKHTTP, "=========================== Request method:\n" + request.method());
        MLog.i(LogTag.OKHTTP, "=========================== Request headers:\n" + (headers == null ? "no headers" : headers.toString()));
        if (body == null) {
            MLog.i(LogTag.OKHTTP, "=========================== Request body(no body)");
        } else if (isString(body.contentType())) {
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            MLog.i(LogTag.OKHTTP, "=========================== Request body(string body)\n" + buffer.readUtf8());
        } else if (body instanceof FormBody) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < ((FormBody) body).size(); i++) {
                builder.append(((FormBody) body).name(i)).append(": ").append(((FormBody) body).value(i)).append("\n");
            }
            if (builder.length() > 0) {
                MLog.i(LogTag.OKHTTP, "=========================== Request body(form body)\n" + builder.substring(0, builder.lastIndexOf("\n")));
            }
        } else {
            MLog.i(LogTag.OKHTTP, "=========================== Request body(other body)");
        }
    }

    private Response printResponse(Response response) throws IOException {
        ResponseBody body = response.body();
        MLog.i(LogTag.OKHTTP, "=========================== Response protocol\n" + response.protocol());
        MLog.i(LogTag.OKHTTP, "=========================== Response url\n" + response.request().url().toString());
        MLog.i(LogTag.OKHTTP, "=========================== Response code\n" + response.code());
        MLog.i(LogTag.OKHTTP, "=========================== Response message\n" + (TextUtils.isEmpty(response.message()) ? "no message" : response.message()));
        if (response.isSuccessful()) {
            if (body == null) {
                MLog.i(LogTag.OKHTTP, "=========================== Response body(no body)");
            } else if (isString(body.contentType())) {
                String bodyString = body.string();
                MLog.i(LogTag.OKHTTP, "=========================== Response body(string body)\n" + bodyString);
                body = ResponseBody.create(body.contentType(), bodyString);
            } else {
                MLog.i(LogTag.OKHTTP, "=========================== Response body(no String body)");
            }
        }
        return response.newBuilder().body(body).build();
    }

    private boolean isString(MediaType mediaType) {
        if (mediaType == null) return false;
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json")
                    || mediaType.subtype().equals("xml")
                    || mediaType.subtype().equals("html")
                    || mediaType.subtype().equals("webviewhtml"))
                return true;
        }
        return false;
    }
}
