package com.retrofit.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindString(R.string.app_name)
    String appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn1, R.id.btn2})
    void btnClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Toast.makeText(this, "点击了button1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn2:
                Toast.makeText(this, "点击了button2", Toast.LENGTH_SHORT).show();
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
