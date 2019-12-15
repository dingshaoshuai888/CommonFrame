package com.retrofit.demo;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.retrofit.demo.activity.TestActivity1;
import com.retrofit.demo.app.JumpHelp;
import com.retrofit.demo.base.BaseActivity;
import com.retrofit.demo.config.ConfigEvent;
import com.retrofit.demo.help.eventbus.Event;
import com.retrofit.demo.help.eventbus.EventBusHelp;
import com.retrofit.demo.util.MLog;

import androidx.annotation.Nullable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindString(R.string.app_name)
    String appName;
    @BindView(R.id.iv)
    ImageView iv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.btn1, R.id.btn2,R.id.btn3})
    void btnClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Glide.with(this).load("http://file02.16sucai.com/d/file/2014/0704/e53c868ee9e8e7b28c424b56afe2066d.jpg")
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                MLog.i("test",e.getMessage());
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(iv);
//                EventBusHelp.post(new Event(ConfigEvent.CODE_TEST_Main, "这是MainActivity发的普通事件"));
                break;
            case R.id.btn2:
                EventBusHelp.postSticky(new Event(ConfigEvent.CODE_TEST_Main, "这是MainActivity发的粘性事件"));
                break;
            case R.id.btn3:
                JumpHelp.startActivity(this, TestActivity1.class);
                break;
        }
    }

    @OnClick(R.id.btn1)
    void setTv1() {
        tv1.setText(appName);
    }

    @OnClick(R.id.btn2)
    void setTv2() {
        tv2.setText(appName);
    }
}
