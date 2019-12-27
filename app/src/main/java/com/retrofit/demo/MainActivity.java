package com.retrofit.demo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.retrofit.demo.activity.TestActivity1;
import com.retrofit.demo.app.JumpHelp;
import com.retrofit.demo.base.BaseActivity;
import com.retrofit.demo.config.ConfigEvent;
import com.retrofit.demo.help.eventbus.Event;
import com.retrofit.demo.help.eventbus.EventBusHelp;
import com.retrofit.demo.help.image.ImageHelp;
import com.retrofit.demo.kotlin.TestActivity4;

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
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.iv3)
    ImageView iv3;
    @BindView(R.id.iv4)
    ImageView iv4;

    private String url = "http://file02.16sucai.com/d/file/2014/0704/e53c868ee9e8e7b28c424b56afe2066d.jpg";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    void btnClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                ImageHelp.load(this, iv1, url);
                ImageHelp.loadCircle(this, iv2, url);
                ImageHelp.loadRound(this, iv3, url, 30);
                ImageHelp.load(this, iv4, R.mipmap.ic_launcher);
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
    @OnClick(R.id.btn4)
    void jumpKotlinActivity(){
        JumpHelp.startActivity(this, TestActivity4.class);
    }
}
