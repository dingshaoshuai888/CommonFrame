package com.retrofit.demo.net.okhttp.https;

import android.content.Context;
import android.text.TextUtils;

import com.retrofit.demo.config.LogTag;
import com.retrofit.demo.util.MLog;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;

public class OkHttpsClient {

    private static OkHttpsClient okHttpsClient;

    public static OkHttpsClient getInstance() {
        if (null == okHttpsClient) {
            synchronized (OkHttpsClient.class) {
                if (okHttpsClient == null) {
                    okHttpsClient = new OkHttpsClient();
                }
            }
        }
        return okHttpsClient;
    }

    public KeyStore initKeyStore(String type) {
        try {
            if (TextUtils.isEmpty(type)) {
                return KeyStore.getInstance(KeyStore.getDefaultType());
            } else {
                return KeyStore.getInstance(type);
            }
        } catch (KeyStoreException e) {
            MLog.e(LogTag.ERROR, e.getMessage());
        }
        return null;
    }

    public InputStream getCertificateStream(Context context, String certificateName) {
        try {
            return context.getAssets().open(certificateName);
        } catch (IOException e) {
            MLog.e(LogTag.ERROR, e.getMessage());
        }
        return null;
    }

    public Certificate getCertificateObject(Context context, String certificateName) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream in = getCertificateStream(context, certificateName);
            Certificate certificate = cf.generateCertificate(in);
            in.close();
            return certificate;
        } catch (CertificateException | IOException e) {
            MLog.e(LogTag.ERROR, e.getMessage());
        }
        return null;
    }

    public List<Certificate> getCertificateList(Context context, List<String> certificateNames) {
        List<Certificate> certificateList = new ArrayList<>();
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            for (String certificateName : certificateNames) {
                InputStream in = getCertificateStream(context, certificateName);
                certificateList.add(cf.generateCertificate(in));
                in.close();
            }
            return certificateList;
        } catch (CertificateException | IOException e) {
            MLog.e(LogTag.ERROR, e.getMessage());
        }
        return null;
    }

    public KeyManager[] initKeyManagers(String keyStoreType, InputStream ins, String password) {
        try {
            if (ins == null) return null;
            KeyStore keyStore = initKeyStore(keyStoreType);
            keyStore.load(ins, TextUtils.isEmpty(password) ? null : password.toCharArray());
            ins.close();
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory
                    .getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, TextUtils.isEmpty(password) ? null : password.toCharArray());
            return keyManagerFactory.getKeyManagers();
        } catch (IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException |
                KeyStoreException e) {
            MLog.e(LogTag.ERROR, e.getMessage());
        }
        return null;
    }

    public TrustManager[] initTrustManagers(String keyStoreType, InputStream ins, String password) {
        try {
            if (ins == null) return null;
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory
                    .getDefaultAlgorithm());
            KeyStore keyStore = initKeyStore(keyStoreType);
            keyStore.load(ins, TextUtils.isEmpty(password) ? null : password.toCharArray());
            ins.close();
            trustManagerFactory.init(keyStore);
            return trustManagerFactory.getTrustManagers();
        } catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException e) {
            MLog.e(LogTag.ERROR, e.getMessage());
        }
        return null;
    }

    public TrustManager[] initTrustManagers(String keyStoreType, Certificate certificate) {
        try {
            if (certificate == null) return null;
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory
                    .getDefaultAlgorithm());
            KeyStore keyStore = initKeyStore(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", certificate);
            trustManagerFactory.init(keyStore);
            return trustManagerFactory.getTrustManagers();
        } catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException e) {
            MLog.e(LogTag.ERROR, e.getMessage());
        }
        return null;
    }

    public TrustManager[] initTrustManagers(String keyStoreType, List<Certificate> certificates) {
        try {
            if (certificates == null || certificates.size() <= 0) return null;
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory
                    .getDefaultAlgorithm());
            KeyStore keyStore = initKeyStore(keyStoreType);
            keyStore.load(null, null);
            for (Certificate certificate : certificates) {
                keyStore.setCertificateEntry(String.valueOf(certificates.indexOf(certificate)), certificate);
            }
            trustManagerFactory.init(keyStore);
            return trustManagerFactory.getTrustManagers();
        } catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException e) {
            MLog.e(LogTag.ERROR, e.getMessage());
        }
        return null;
    }

    public X509TrustManager chooseTrustManager(TrustManager[] trustManagers) {
        for (TrustManager trustManager : trustManagers) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }

    public SSLSocketFactory getSSLSocketFactory(KeyManager[] keyManagers, TrustManager[] trustManagers) {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagers, trustManagers, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            MLog.e(LogTag.ERROR, e.getMessage());
        }
        return null;
    }

    public SSLSocketFactory getSSLSocketFactory(KeyManager[] keyManagers, TrustManager[] trustManagers,
                                                String[] protocols) {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagers, trustManagers, new SecureRandom());
            return new TrustSSLSocketFactory(sslContext, protocols);
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            MLog.e(LogTag.ERROR, e.getMessage());
        }
        return null;
    }

    public ConnectionSpec setConnectionSpec(ConnectionSpec connectionSpec, TlsVersion[] tlsVersions) {
        ConnectionSpec.Builder builder = new ConnectionSpec.Builder(connectionSpec);
        if (tlsVersions != null && tlsVersions.length > 0) {
            builder.tlsVersions(tlsVersions);
        }
        return builder.build();
    }

    public void configTrust(OkHttpClient.Builder builder, SSLSocketFactory sslSocketFactory) {
        builder.socketFactory(sslSocketFactory);
    }

    public void configTrust(OkHttpClient.Builder builder, SSLSocketFactory sslSocketFactory,
                            HostnameVerifier hostnameVerifier) {
        builder.socketFactory(sslSocketFactory);
        builder.hostnameVerifier(hostnameVerifier);
    }

    public void configTrust(OkHttpClient.Builder builder, SSLSocketFactory sslSocketFactory,
                            HostnameVerifier hostnameVerifier, ConnectionSpec spec) {
        builder.socketFactory(sslSocketFactory);
        builder.hostnameVerifier(hostnameVerifier);
        builder.connectionSpecs(Collections.singletonList(spec));
    }
}
