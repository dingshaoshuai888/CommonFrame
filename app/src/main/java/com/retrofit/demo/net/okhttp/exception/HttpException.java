package com.retrofit.demo.net.okhttp.exception;

public class HttpException extends Exception {

	private static final long serialVersionUID = 1L;

	public HttpException() {
		super();
	}

	public HttpException(String detailMessage) {
		super(detailMessage);
	}

	public HttpException(Throwable throwable) {
		super(throwable);
	}

	public HttpException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}
}
