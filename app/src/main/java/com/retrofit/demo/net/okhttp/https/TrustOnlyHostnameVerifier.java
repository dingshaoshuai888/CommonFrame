package com.retrofit.demo.net.okhttp.https;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class TrustOnlyHostnameVerifier implements HostnameVerifier {

    @Override
    public boolean verify(String hostname, SSLSession sslSession) {
        return hostname.equals(sslSession.getPeerHost());
    }
}
