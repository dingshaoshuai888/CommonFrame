package com.retrofit.demo.activity

import com.retrofit.demo.R
import com.retrofit.demo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_test4.*

class TestActivity4 : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_test4
    }

    override fun initClick() {
        super.initClick()
        btn.setOnClickListener{
            btn.setText("哈哈，这样也行")
        }
    }
}