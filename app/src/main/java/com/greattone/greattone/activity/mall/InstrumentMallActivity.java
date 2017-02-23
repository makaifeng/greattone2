package com.greattone.greattone.activity.mall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.InstrumentMallAdapter;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.MusicalProduct;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.widget.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

/**
 *琴行商城的产品列表
 */
public class InstrumentMallActivity extends BaseActivity {
    InstrumentMallAdapter adapter;
    List<MusicalProduct> productList =new ArrayList<>();

    private ListView lv_content;
    private PullToRefreshView pull_to_refresh;
    private int page = 1;
    private int pageSize = 10;
    int isbusiness;
    AdapterView.OnItemClickListener listener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent(InstrumentMallActivity.this,MallProductDetailActivity.class);
            intent.putExtra("id",productList.get(position).getId());
            intent.putExtra("isbusiness",isbusiness);
            startActivityForResult(intent,3);
        }
    };
    private PullToRefreshView.OnHeaderRefreshListener headerRefreshListener=new PullToRefreshView.OnHeaderRefreshListener() {

        @Override
        public void onHeaderRefresh(PullToRefreshView view) {
            page=1;
            productList.clear();
            getData();
        }
    };
    private PullToRefreshView.OnFooterRefreshListener footerRefreshListener=new PullToRefreshView.OnFooterRefreshListener() {

        @Override
        public void onFooterRefresh(PullToRefreshView view) {
            page++;
            getData();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
//            setContentView(R.layout.adapter_instrument_mall);
            isbusiness=getIntent().getIntExtra("isbusiness",0);
            setContentView(R.layout.layout_list);
            initView();
            getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        setHead("乐器商城",true,true);
        if(isbusiness==1) {
            setOtherText("发布", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivityForResult(new Intent(context, PostMallProductActivity.class), 22);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        lv_content = (ListView) findViewById(R.id.lv_content);
        adapter = new InstrumentMallAdapter(context, productList);
        lv_content.setAdapter(this.adapter);
        lv_content.setOnItemClickListener(listener);
        pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
        pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);

    }

    /**
     *
     */
    private void getData() {
        String userid="";
        if (isbusiness!=1){
            userid=getIntent().getStringExtra("userid");
        }
        HttpProxyUtil.getProducts(context, userid, pageSize,page,new HttpUtil.ResponseListener() {
                    @Override
                    public void setResponseHandle(Message2 message) {
                        if (message.getData() != null
                                && message.getData().startsWith("[")) {
                            List<MusicalProduct> mList = JSON.parseArray(
                                    message.getData(), MusicalProduct.class);
                            if (mList != null && !mList.isEmpty()) {
                                productList.addAll(mList);
                            } else {
                                toast(getResources().getString(R.string.cannot_load_more));
                            }
                        }
                        pull_to_refresh.onHeaderRefreshComplete();
                        pull_to_refresh.onFooterRefreshComplete();
                        initContentAdapter();
                        MyProgressDialog.Cancel();
                    }
                },
                new HttpUtil.ErrorResponseListener() {
                    @Override
                    public void setServerErrorResponseHandle(Message2 message) {
                    }

                    @Override
                    public void setErrorResponseHandle(VolleyError error) {
                    }
                });
    }
    /**
     * 加载ContentAdapter数据
     */
    protected void initContentAdapter() {
        Parcelable listState = lv_content.onSaveInstanceState();
        adapter.notifyDataSetChanged();
        lv_content.onRestoreInstanceState(listState);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==3&&resultCode==RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }
    }
}
