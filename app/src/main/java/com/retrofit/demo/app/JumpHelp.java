package com.retrofit.demo.app;

import android.content.Context;
import android.content.Intent;

public class JumpHelp {

    public static void startActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }
}
