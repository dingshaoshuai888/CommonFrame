package com.retrofit.demo.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.retrofit.demo.R;
import com.retrofit.demo.app.JumpHelp;
import com.retrofit.demo.base.BaseActivity;
import com.retrofit.demo.base.baseadapter.CommonAdapter;
import com.retrofit.demo.base.baseadapter.MultiItemBean;
import com.retrofit.demo.base.baseadapter.MultiItemTypeAdapter;
import com.retrofit.demo.base.baseadapter.base.ItemViewDelegate;
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

//此Activity没有注册EventBus事件
public class TestActivity2 extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test2;
    }

    @Override
    protected void initData() {
        super.initData();
        List<MultiItemBean> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(new MultiItemBean(i % 3, "条目 " + i));
        }
        MultiItemTypeAdapter<MultiItemBean> adapter = new MultiItemTypeAdapter<>(this, data);
        adapter.addItemViewDelegate(new ItemViewDelegate<MultiItemBean>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_test;
            }

            @Override
            public boolean isForViewType(MultiItemBean item, int position) {
                if (item.getViewType() == 0) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void convert(ViewHolder holder, MultiItemBean itemBean, int position) {
                holder.setText(R.id.tv, itemBean.getName());
            }
        });
        adapter.addItemViewDelegate(new ItemViewDelegate<MultiItemBean>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_test2;
            }

            @Override
            public boolean isForViewType(MultiItemBean item, int position) {
                if (item.getViewType() == 1) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void convert(ViewHolder holder, MultiItemBean itemBean, int position) {
                holder.setText(R.id.tv, itemBean.getName());
                holder.setText(R.id.tv2, itemBean.getName());
            }
        });
        adapter.addItemViewDelegate(new ItemViewDelegate<MultiItemBean>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_test3;
            }

            @Override
            public boolean isForViewType(MultiItemBean item, int position) {
                if (item.getViewType() == 2) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void convert(ViewHolder holder, MultiItemBean itemBean, int position) {
                holder.setText(R.id.tv, itemBean.getName());
                holder.setText(R.id.tv2, itemBean.getName());
                holder.setText(R.id.tv3, itemBean.getName());
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                MToast.showShort("onItemClick：" + JsonHelp.toJsonString(data.get(position)));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                MToast.showLong("onItemLongClick：" + JsonHelp.toJsonString(data.get(position)));
                return true;
            }
        });
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
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
