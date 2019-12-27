package com.retrofit.demo.util;

import android.widget.Toast;

import com.retrofit.demo.base.BaseApplication;

public class MToast {

    private static Toast mToast;

    public static final void showShort(String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(BaseApplication.getInstance(), message,
                Toast.LENGTH_SHORT);
        mToast.show();
    }


    public static final void showLong(String message) {

        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(BaseApplication.getInstance(), message,
                Toast.LENGTH_LONG);
        mToast.show();
    }
}
