package com.greattone.greattone.activity.mall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.adapter.MyMallOrderAdapter;
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
 * 用户自己的订单列表
 */
public class MyMallOrderFragment extends BaseFragment{
    /**
     * fragment 主布局
     */
    private View rootView;
    PullToRefreshView pull_to_refresh;
    ListView lv_content;
    List<MallOrder> orderList=new ArrayList<>();
    int isbusiness=0;//是否是商家 1是，0否
    String type;//不传表示全部  finish 完成订单 unfinish  未完成订单
    MyMallOrderAdapter adapter;
    private int page = 1;
    private int pageSize = 10;

    AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent(context,MallOrderDetailActivity.class);
            intent.putExtra("data",orderList.get(position));
            startActivity(intent);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getOrder();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.layout_list, container,
                    false);// 关联布局文件
            // screenWidth = ((BaseActivity) getActivity()).screenWidth;
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }
    private void getOrder() {
        HttpProxyUtil.getMallOrders(context, isbusiness+"",type,  pageSize,page, new HttpUtil.ResponseListener() {
            @Override
            public void setResponseHandle(Message2 message) {
                if (message.getData()!=null&&message.getData().startsWith("[")){
                    List<MallOrder> mList= JSON.parseArray(
                            message.getData(), MallOrder.class);
                    if (mList.size()>0){
                        orderList.clear();
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

        pull_to_refresh = (PullToRefreshView)rootView.findViewById(R.id.pull_to_refresh);//
        lv_content = (ListView) rootView.findViewById(R.id.lv_content);// 正文
        adapter =new  MyMallOrderAdapter(context,orderList);
        lv_content.setAdapter(adapter);

        lv_content.setOnItemClickListener(itemClickListener);
        lv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());
        pull_to_refresh.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {

                    @Override
                    public void onHeaderRefresh(PullToRefreshView view) {
                        page = 1;
                        orderList.clear();
                        getOrder();
                    }
                });
        pull_to_refresh.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {

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
