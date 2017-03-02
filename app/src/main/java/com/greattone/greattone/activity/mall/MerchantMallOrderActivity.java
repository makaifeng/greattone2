package com.greattone.greattone.activity.mall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.MerchantMallOrderAdapter;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.MallOrder;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

/**
 * 商户的订单列表
 */
public class MerchantMallOrderActivity extends BaseActivity {
    RadioGroup radiogroup;
    PullToRefreshView pull_to_refresh;
    ListView lv_content;
    List<MallOrder> orderList=new ArrayList<>();
    int isbusiness=1;//是否是商家 1是，0否
    String type;
    MerchantMallOrderAdapter adapter;
    private int page = 1;
    private int pageSize = 10;
    private RadioGroup.OnCheckedChangeListener listener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            orderList.clear();
            page=1;
            switch (checkedId){
                case R.id.radioButton1://全部订单
                    type="";
                    getOrder();
                    break;
                case R.id.radioButton2://已经完成订单
                    type="finish";
                    getOrder();
                    break;
                case R.id.radioButton3://未完成订单
                    type="unfinish";
                    getOrder();
                    break;
            }
        }
    };

    AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent(context,MallOrderDetailActivity.class);
            intent.putExtra("data",orderList.get(position));
            startActivity(intent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mall_order);
        initView();
        getOrder();

    }

    private void getOrder() {
        MyProgressDialog.show(context);
        HttpProxyUtil.getMallOrders(context, isbusiness+"",type,  pageSize,page,new HttpUtil.ResponseListener() {
            @Override
            public void setResponseHandle(Message2 message) {
                if (message.getData()!=null&&message.getData().startsWith("[")){
                    List<MallOrder> mList= JSON.parseArray(
                            message.getData(), MallOrder.class);
                    if (mList.size()>0){
                        orderList.addAll(mList);
                    } else {
                        toast(getResources().getString(R.string.cannot_load_more));
                    }
                }
                pull_to_refresh.onHeaderRefreshComplete();
                pull_to_refresh.onFooterRefreshComplete();
                initContentAdapter();
                MyProgressDialog.Cancel();
            }
        }, new HttpUtil.ErrorResponseListener() {
            @Override
            public void setServerErrorResponseHandle(Message2 message) {

            }

            @Override
            public void setErrorResponseHandle(VolleyError error) {

            }
        });
    }

    private void initView() {
        setHead("我的订单",true,true);

        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        radiogroup.check(R.id.radioButton1);
        radiogroup.setOnCheckedChangeListener(listener);


        pull_to_refresh = (PullToRefreshView)findViewById(R.id.pull_to_refresh);//
        lv_content = (ListView) findViewById(R.id.lv_content);// 正文
        adapter =new  MerchantMallOrderAdapter(context,orderList);
        lv_content.setAdapter(adapter);

        lv_content.setOnItemClickListener(itemClickListener);
        lv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());


        pull_to_refresh
                .setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {

                    @Override
                    public void onHeaderRefresh(PullToRefreshView view) {
                        page = 1;
                        orderList.clear();
                        getOrder();
                    }
                });
        pull_to_refresh
                .setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {

                    @Override
                    public void onFooterRefresh(PullToRefreshView view) {
                        page++;
                        getOrder();
                    }
                });
    }
    protected void initContentAdapter() {
        Parcelable listState = lv_content.onSaveInstanceState();
        adapter.notifyDataSetChanged();
        lv_content.onRestoreInstanceState(listState);
    }
}
