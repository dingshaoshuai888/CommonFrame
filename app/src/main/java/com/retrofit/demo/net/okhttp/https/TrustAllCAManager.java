package com.retrofit.demo.net.okhttp.https;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class TrustAllCAManager implements X509TrustManager {

    private X509TrustManager defaultTrustManager;

    public TrustAllCAManager() throws NoSuchAlgorithmException, KeyStoreException {
        TrustManagerFactory tf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tf.init((KeyStore) null);
        defaultTrustManager = filterTrustManager(tf.getTrustManagers());
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        defaultTrustManager.checkServerTrusted(chain, authType);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        defaultTrustManager.checkServerTrusted(chain, authType);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    private X509TrustManager filterTrustManager(TrustManager[] trustManagers) {
        for (TrustManager trustManager : trustManagers) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }
}
