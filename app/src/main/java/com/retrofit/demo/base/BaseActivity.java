package com.retrofit.demo.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.retrofit.demo.R;
import com.retrofit.demo.help.eventbus.Event;
import com.retrofit.demo.help.eventbus.EventBusHelp;
import com.trello.rxlifecycle2.components.RxActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;

public abstract class BaseActivity extends RxActivity {

    protected void setContentViewBefore() {
    }

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewBefore();
        super.setContentView(R.layout.activity_base_view);
        setContentView(getLayoutId());
        bindButterKnife();
        initView();
        initClick();
        initData();
        registerEventBus();
    }

    @Override
    public void setContentView(int layoutId) {
        setContentView(View.inflate(this, layoutId, null));
    }

    @Override
    public void setContentView(View view) {
        LinearLayout rootLayout = findViewById(R.id.root_layout);
        if (rootLayout == null) {
            return;
        }
        rootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
    }

    protected void initView() {
    }

    protected void initClick() {
    }

    protected void initData() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (event != null) {
            onReceiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(Event event) {
        if (event != null) {
            onReceiveStickyEvent(event);
        }
    }

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    protected void onReceiveEvent(Event event) {

    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected void onReceiveStickyEvent(Event event) {

    }


    private void registerEventBus() {
        if (isRegisterEventBus()) {
            EventBusHelp.register(this);
        }
    }

    private void unregisterEventBus() {
        if (isRegisterEventBus()) {
            EventBusHelp.unregister(this);
        }
    }

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    private void bindButterKnife() {
        ButterKnife.bind(this);
    }

}
