package com.greattone.greattone.activity.mall;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.MyMallOrderAdapter;
import com.greattone.greattone.entity.MallOrder;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

public class MyMallOrderActivity extends BaseActivity {
    RadioGroup radiogroup;
    PullToRefreshView pull_to_refresh;
    ListView lv_content;
    List<MallOrder> orderList=new ArrayList<>();
    private RadioGroup.OnCheckedChangeListener listener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mall_order);
        initView();
    }

    private void initView() {
        setHead("我的订单",true,true);

        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        radiogroup.setOnCheckedChangeListener(listener);

        pull_to_refresh = (PullToRefreshView)findViewById(R.id.pull_to_refresh);//
        lv_content = (ListView) findViewById(R.id.lv_content);// 正文
        MyMallOrderAdapter adapter =new  MyMallOrderAdapter(context,orderList);
        lv_content.setAdapter(adapter);
//        lv_content.setOnItemClickListener(itemClickListener);
        lv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());
        pull_to_refresh
                .setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {

                    @Override
                    public void onHeaderRefresh(PullToRefreshView view) {
//                        page = 1;
//                        blogsList.clear();
//                        getBlogs();
                    }
                });
        pull_to_refresh
                .setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {

                    @Override
                    public void onFooterRefresh(PullToRefreshView view) {
//                        page++;
//                        getBlogs();
                    }
                });
    }
}
