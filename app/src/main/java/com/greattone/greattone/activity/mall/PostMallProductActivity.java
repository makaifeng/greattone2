package com.greattone.greattone.activity.mall;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.dialog.NormalPopuWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * 琴行发布商城的产品
 */
public class PostMallProductActivity extends BaseActivity {
    EditText et_name,et_other_name,et_model;
    TextView tv_type;
    GridView gv_pic;
    Button btn_send;
    View ll_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_mall_product);
        initView();
    }

    private void initView() {
        setHead("发布产品", true, true);//发布产品
//        setOtherText(getResources().getString(R.string.添加), new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(context, AddNewFriendActivity.class));
//            }
//        });
        et_name = (EditText) findViewById(R.id.et_name);
        et_other_name = (EditText) findViewById(R.id.et_other_name);
        tv_type = (TextView) findViewById(R.id.tv_type);
        ll_type = findViewById(R.id.ll_type);
        et_model = (EditText) findViewById(R.id.et_model);
        gv_pic = (GridView) findViewById(R.id.gv_pic);
        btn_send = (Button) findViewById(R.id.btn_send);

        btn_send.setOnClickListener(lis);
        tv_type.setOnClickListener(lis);

    }
    View.OnClickListener lis =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_type://类型
                    List<String> list=new ArrayList<>();
                    list.add("sss");
                    new NormalPopuWindow(context,list,ll_type).show();
                    break;
                case R.id.btn_send://发送

                    break;
                default:
                    break;
            }
        }
    };
}
