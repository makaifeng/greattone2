package com.greattone.greattone.activity.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.PayActivity;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.QianYuePackages;
import com.greattone.greattone.entity.Sign;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

public class SelectPackeageAct extends BaseActivity {
    List<QianYuePackages> qianYuePackagesList=new ArrayList<>();
    private TextView tv_content;
    private TextView tv_price_now;
    private TextView tv_price_old;
    private Button btn_buy;
    private TextView tv_remark;
    private LinearLayout ll_packages;

String price;
String packageid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_packeage);
        initView();
        getPackages();
    }


    private void initView() {
        setHead("好琴声琴行认证" ,true,true);
        ImageView iv_logo = (ImageView) findViewById(R.id.iv_logo);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(screenWidth /2, screenWidth /7);
        layoutParams.addRule(  RelativeLayout.CENTER_HORIZONTAL);
        iv_logo.setLayoutParams(layoutParams);
        iv_logo  . setScaleType(ImageView.ScaleType.FIT_XY);
        ll_packages = (LinearLayout) findViewById(R.id.ll_packages);
        addItemPackagesView();
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_price_now = (TextView) findViewById(R.id.tv_price_now);
        tv_price_old = (TextView) findViewById(R.id.tv_price_old);
        btn_buy = (Button) findViewById(R.id.btn_buy);
        tv_remark = (TextView) findViewById(R.id.tv_remark);
        btn_buy.setOnClickListener(lis);

    }

    private void initViewData(QianYuePackages qianYuePackages) {
        tv_content.setText(qianYuePackages.getContent());
        tv_price_now.setText(qianYuePackages.getPrice_now()+qianYuePackages.getUnit());
        if (qianYuePackages.getIsshow_old()==1) {
            tv_price_old.setVisibility(View.VISIBLE);
            tv_price_old.setText(qianYuePackages.getPrice_old() + qianYuePackages.getUnit());
        }else tv_price_old.setVisibility(View.GONE);
        tv_remark.setText(qianYuePackages.getRemark());
        price=qianYuePackages.getPrice_now();
    }

    private void initTextView(int position) {
        for (int i=0;i<ll_packages.getChildCount();i++){
            TextView textview= (TextView) ll_packages.getChildAt(i);
            if (position==i){
                textview.setTextColor(getResources().getColor(R.color.package_text_orange));
                textview.setBackgroundResource(R.drawable.qianyue_package_text_bg_cslect);
            }else{
                textview.setTextColor(getResources().getColor(R.color.black));
                textview.setBackgroundResource(R.drawable.qianyue_package_text_bg);
            }
        }
    }
    /**
     * 添加套餐选项
     */
    private void addItemPackagesView() {
        for (int i=0;i<qianYuePackagesList.size();i++             ) {
            TextView textview =new TextView(context);
            RadioGroup.  LayoutParams layout=new RadioGroup.LayoutParams( screenWidth*3/5, RadioGroup.LayoutParams.WRAP_CONTENT);
            layout.bottomMargin= DisplayUtil.dip2px(context,10);
            textview.setLayoutParams(layout);
            textview.setGravity(Gravity.CENTER);
            textview.setId(i);
            textview.setTextSize(18);
            textview.setTextColor(getResources().getColor(R.color.black));
            textview.setText(qianYuePackagesList.get(i).getName());
            textview.setBackgroundResource(R.drawable.qianyue_package_text_bg);
            textview.setOnClickListener(lis);
            ll_packages.addView(textview);
        }

    }
    private View.OnClickListener lis=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                if (v==btn_buy){//立即购买
                    getOrderData();
                }else{//套餐选择
                    try {
                        initViewData(qianYuePackagesList.get(v.getId()));
                        initTextView(v.getId());
                        packageid=qianYuePackagesList.get(v.getId()).getPackageid();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        }
    };

    private void getPackages() {
        HttpProxyUtil.getPackages(context, new HttpUtil.ResponseListener() {
            @Override
            public void setResponseHandle(Message2 message) {
                qianYuePackagesList= JSON.parseArray(message.getData(),QianYuePackages.class);
                addItemPackagesView();
                initViewData(qianYuePackagesList.get(0));
                initTextView(0);
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
    /**
     * 获取订单信息
     */
    private void getOrderData() {
        MyProgressDialog.show(context);
        HttpProxyUtil.getSignOrderData(context,packageid, new HttpUtil.ResponseListener() {

            @Override
            public void setResponseHandle(Message2 message) {
                if (message.getData() != null && message.getData().startsWith("{")) {
                    Sign order = JSON.parseObject(message.getData(), Sign.class);
                    Intent intent = new Intent(context,
                            PayActivity.class);
                    intent.putExtra("name",order.getShoptitle());
                    intent.putExtra("contant", order.getContent());
                    intent.putExtra("price",  order.getMoney());
//					 intent.putExtra("price",  "0.01");
                    intent.putExtra("orderId", "QY"+  order.getDdid());
                    intent.putExtra("bitype", "人民币");
                    startActivityForResult(intent, 1);
                }
                MyProgressDialog.Cancel();
            }

        }, null);
    }
}
