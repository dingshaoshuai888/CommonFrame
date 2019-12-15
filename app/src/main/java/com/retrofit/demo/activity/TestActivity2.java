package com.retrofit.demo.activity;

import android.view.View;
import android.widget.Toast;

import com.retrofit.demo.R;
import com.retrofit.demo.app.JumpHelp;
import com.retrofit.demo.base.BaseActivity;
import com.retrofit.demo.config.ConfigEvent;
import com.retrofit.demo.config.LogTag;
import com.retrofit.demo.help.JsonHelp;
import com.retrofit.demo.help.eventbus.Event;
import com.retrofit.demo.help.eventbus.EventBusHelp;
import com.retrofit.demo.util.MLog;

import butterknife.OnClick;

//此Activity没有注册EventBus事件
public class TestActivity2 extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test2;
    }


    @OnClick({R.id.btn1, R.id.btn2,R.id.btn3})
    public void send(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                EventBusHelp.post(new Event(ConfigEvent.CODE_TEST_2, "这是TestActivity2发的普通事件"));
                break;
            case R.id.btn2:
                EventBusHelp.postSticky(new Event(ConfigEvent.CODE_TEST_2, "这是TestActivity2发的粘性事件"));
                break;
            case R.id.btn3:
                JumpHelp.startActivity(this, TestActivity3.class);
                break;
        }
    }

    @Override
    protected void onReceiveEvent(Event event) {
        MLog.i(LogTag.TEST, "Activity2 接收到了Event事件：" + JsonHelp.toJsonString(event));
    }

    @Override
    protected void onReceiveStickyEvent(Event event) {
        MLog.i(LogTag.TEST, "Activity2 接收到了Event事件：" + JsonHelp.toJsonString(event));
    }


}
