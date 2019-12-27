package com.retrofit.demo.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.retrofit.demo.R;
import com.retrofit.demo.app.JumpHelp;
import com.retrofit.demo.base.BaseActivity;
import com.retrofit.demo.base.baseadapter.CommonAdapter;
import com.retrofit.demo.base.baseadapter.MultiItemTypeAdapter;
import com.retrofit.demo.base.baseadapter.base.ViewHolder;
import com.retrofit.demo.config.ConfigEvent;
import com.retrofit.demo.config.LogTag;
import com.retrofit.demo.help.JsonHelp;
import com.retrofit.demo.help.eventbus.Event;
import com.retrofit.demo.help.eventbus.EventBusHelp;
import com.retrofit.demo.help.image.ImageHelp;
import com.retrofit.demo.util.MLog;
import com.retrofit.demo.util.MToast;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class TestActivity1 extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test1;
    }

    @Override
    protected void initData() {
        super.initData();
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("条目 " + i);
        }
        CommonAdapter<String> commonAdapter = new CommonAdapter<String>(this, R.layout.item_test, data) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv, s);
                ImageView iv = holder.getView(R.id.iv);
                ImageHelp.load(TestActivity1.this, iv, R.mipmap.ic_launcher);
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(commonAdapter);
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                MToast.showShort("onItemClick：" + data.get(position));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                MToast.showLong("onItemLongClick：" + data.get(position));
                return true;
            }
        });
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    public void send(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                EventBusHelp.post(new Event(ConfigEvent.CODE_TEST_1, "这是TestActivity1发的普通事件"));
                break;
            case R.id.btn2:
                EventBusHelp.postSticky(new Event(ConfigEvent.CODE_TEST_1, "这是TestActivity1发的粘性事件"));
                break;
            case R.id.btn3:
                JumpHelp.startActivity(this, TestActivity2.class);
                break;
        }
    }

    @Override
    protected void onReceiveEvent(Event event) {
        MLog.i(LogTag.TEST, "Activity1 接收到了Event事件：" + JsonHelp.toJsonString(event));
    }

    @Override
    protected void onReceiveStickyEvent(Event event) {
        MLog.i(LogTag.TEST, "Activity1 接收到了Event事件：" + JsonHelp.toJsonString(event));
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }
}
