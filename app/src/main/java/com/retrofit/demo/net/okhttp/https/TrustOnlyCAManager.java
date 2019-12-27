package com.retrofit.demo.net.okhttp.https;

import com.retrofit.demo.net.okhttp.exception.SSLException;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class TrustOnlyCAManager implements X509TrustManager {

    private X509TrustManager defaultTrustManager;
    private X509TrustManager localTrustManager;

    public TrustOnlyCAManager(X509TrustManager x509TrustManager) throws NoSuchAlgorithmException,
            KeyStoreException {
        TrustManagerFactory tf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tf.init((KeyStore) null);
        defaultTrustManager = filterTrustManager(tf.getTrustManagers());
        localTrustManager = x509TrustManager;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        boolean isDefaultCATrust = false;
        try {
            defaultTrustManager.checkServerTrusted(chain, authType);
            isDefaultCATrust = true;
            localTrustManager.checkServerTrusted(chain, authType);
        } catch (CertificateException ce) {
            if (isDefaultCATrust) {
                throw ce;
            } else {
                throw new SSLException("Self-Signed Certificate-->" + ce.getMessage());
            }
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        boolean isDefaultCATrust = false;
        try {
            defaultTrustManager.checkServerTrusted(chain, authType);
            isDefaultCATrust = true;
            localTrustManager.checkServerTrusted(chain, authType);
        } catch (CertificateException ce) {
            if (isDefaultCATrust) {
                throw ce;
            } else {
                throw new SSLException("Self-Signed Certificate-->" + ce.getMessage());
            }
        }
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
