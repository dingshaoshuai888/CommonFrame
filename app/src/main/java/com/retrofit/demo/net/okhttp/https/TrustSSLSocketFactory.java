package com.retrofit.demo.net.okhttp.https;

import com.retrofit.demo.config.LogTag;
import com.retrofit.demo.util.MLog;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class TrustSSLSocketFactory extends SSLSocketFactory {

    private SSLContext sslContext;
    private String[] protocols;
    private SSLSocket sslSocket;

    public TrustSSLSocketFactory(SSLContext sslContext, String[] protocols) {
        this.sslContext = sslContext;
        this.protocols = protocols;
        try {
            sslSocket = (SSLSocket) sslContext.getSocketFactory().createSocket();
        } catch (Exception e) {
            MLog.e(LogTag.ERROR, e.getMessage());
        }
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return new String[0];
    }

    @Override
    public Socket createSocket(Socket socket, String s, int i, boolean b) throws IOException {
        sslSocket.setEnabledProtocols(protocols);
        sslSocket.setEnabledCipherSuites(sslSocket.getSupportedCipherSuites());
        return sslSocket;
    }

    @Override
    public Socket createSocket(String s, int i) throws IOException {
        sslSocket.setEnabledProtocols(protocols);
        sslSocket.setEnabledCipherSuites(sslSocket.getSupportedCipherSuites());
        return sslSocket;
    }

    @Override
    public Socket createSocket(String s, int i, InetAddress inetAddress, int i1) throws IOException {
        sslSocket.setEnabledProtocols(protocols);
        sslSocket.setEnabledCipherSuites(sslSocket.getSupportedCipherSuites());
        return sslSocket;
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        sslSocket.setEnabledProtocols(protocols);
        sslSocket.setEnabledCipherSuites(sslSocket.getSupportedCipherSuites());
        return sslSocket;
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int i1) throws IOException {
        sslSocket.setEnabledProtocols(protocols);
        sslSocket.setEnabledCipherSuites(sslSocket.getSupportedCipherSuites());
        return sslSocket;
    }
}
