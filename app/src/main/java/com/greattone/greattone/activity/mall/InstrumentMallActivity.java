package com.greattone.greattone.activity.mall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;

/**
 *琴行商城的产品列表
 */
public class InstrumentMallActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_instrument_mall);
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        setHead("乐器商城",true,true);
        setOtherText("发布", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivityForResult(new Intent(context,PostMallProductActivity.class),22);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
