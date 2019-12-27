package com.retrofit.demo.net.okhttp.exception;

import java.security.cert.CertificateException;

public class SSLException extends CertificateException {

    public SSLException() {
        super();
    }

    public SSLException(String detailMessage) {
        super(detailMessage);
    }

    public SSLException(Throwable throwable) {
        super(throwable);
    }

    public SSLException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
