package com.greattone.greattone.activity.mall;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.WebActivity;
import com.greattone.greattone.data.HttpConstants2;
import com.greattone.greattone.entity.MallOrder;
import com.greattone.greattone.util.ImageLoaderUtil;

public class MallOrderDetailActivity extends BaseActivity {
TextView tv_orderid,tv_state,tv_title,tv_price,tv_type,tv_total,tv_seller_name,tv_seller_phone,tv_seller_city,
        tv_logistics_name,tv_logistics_number,tv_logistics_show,tv_receipt_name,tv_receipt_phone,tv_receipt_address
        ,tv_pay_state,tv_pay_type,tv_invoice;
    ImageView iv_pic;
    View ll_logistics2;
    MallOrder order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_mall_order_detail);
            initView();
            getIntentData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        setHead("订单详情",true,true);
        tv_orderid=(TextView)findViewById(R.id.tv_orderid);
        tv_state=(TextView)findViewById(R.id.tv_state);
        tv_title=(TextView)findViewById(R.id.tv_title);
        tv_price=(TextView)findViewById(R.id.tv_price);
        tv_type=(TextView)findViewById(R.id.tv_type);
        tv_total=(TextView)findViewById(R.id.tv_total);
        tv_seller_name=(TextView)findViewById(R.id.tv_seller_name);
        tv_seller_phone=(TextView)findViewById(R.id.tv_seller_phone);
        tv_seller_city=(TextView)findViewById(R.id.tv_seller_city);
        tv_logistics_name=(TextView)findViewById(R.id.tv_logistics_name);
        tv_logistics_number=(TextView)findViewById(R.id.tv_logistics_number);
        tv_logistics_show=(TextView)findViewById(R.id.tv_logistics_show);
        tv_receipt_name=(TextView)findViewById(R.id.tv_receipt_name);
        tv_receipt_phone=(TextView)findViewById(R.id.tv_receipt_phone);
        tv_receipt_address=(TextView)findViewById(R.id.tv_receipt_address);
        tv_pay_state=(TextView)findViewById(R.id.tv_pay_state);
        tv_pay_type=(TextView)findViewById(R.id.tv_pay_type);
        tv_invoice=(TextView)findViewById(R.id.tv_invoice);
        iv_pic= (ImageView) findViewById(R.id.iv_pic);
        ll_logistics2=findViewById(R.id.ll_logistics2);

    }

    private void getIntentData() {
        order=(MallOrder)getIntent().getSerializableExtra("data");
        if (order==null) return;

        tv_orderid.setText("订单号："+order.getOrderid());
        String pic[]=order.getTitlepic().split("\\::::::");
        if (pic.length>=1) {
            ImageLoaderUtil.getInstance().setImagebyurl(
                    pic[0], iv_pic);
        }
        tv_title.setText(order.getTitle()+"  "+order.getModel());
        tv_type.setText("产品分类："+order.getColour());
        tv_total.setText("共1件商品  合计："+order.getMoney());
        tv_price.setText("￥"+order.getPrice());
        //卖家信息
        tv_seller_name.setText("店铺名称："+order.getSellername());
        tv_seller_phone.setText("售后电话："+order.getTelephone());
        tv_seller_city.setText("发货城市："+order.getCity());

        //物流信息
        ll_logistics2.setVisibility(View.VISIBLE);
        tv_logistics_name.setText("物流公司："+order.getLogisticsname());
        tv_logistics_number.setText("货运单号："+order.getLogisticsnumber());
        String  showtext="物流跟踪：点击查询";
        SpannableString sps=new SpannableString(showtext);
//        sps.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange_f97706)), 5,showtext.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//字体颜色
        sps.setSpan(clickableSpan, 5,showtext.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//点击事件
//        sps.setSpan(new UnderlineSpan(), 5,showtext.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//下划线
        tv_logistics_show.setText(sps);
        tv_logistics_show.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件

        //收货信息
        tv_receipt_name.setText("联系人："+order.getName());
        tv_receipt_phone.setText("联系方式："+order.getPhone());
        tv_receipt_address.setText("收货地址：："+order.getAddress());
        //支付及发票
        tv_pay_state.setText("支付状态：已支付");
        if (order.getInvoice()==null||order.getInvoice().equals(""))
        tv_invoice.setText("发票类型：不要发票");
        else  tv_invoice.setText("发票抬头："+order.getInvoice());

        showState();

    }



    private void showState() {
        //1=未付款 2=已付款 3=已取消 4=以发货 5=已收货
        if (order.getState()==1){
            tv_state.setText("未付款");
            tv_pay_state.setText("支付状态：未支付");
            ll_logistics2.setVisibility(View.GONE);
        }else if (order.getState()==2){
            tv_state.setText("待发货");
            ll_logistics2.setVisibility(View.GONE);
        }else if (order.getState()==3){
            tv_state.setText("已取消");
            ll_logistics2.setVisibility(View.GONE);
        }else if (order.getState()==4){
            tv_state.setText("已发货");
        }else if (order.getState()==5){
            tv_state.setText("交易完成");
        }
    }

    ClickableSpan clickableSpan=new ClickableSpan(){
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(getResources().getColor(R.color.orange_f97706));
            ds.setUnderlineText(true);
        }
        @Override
        public void onClick(View widget) {
            //查看物流
            Intent intent=new Intent(context, WebActivity.class);
            String urlPath = HttpConstants2.SERVER_URL+"/app/kuaidi.php?num="+order.getLogisticsnumber();
            intent.putExtra("urlPath",urlPath);
            intent.putExtra("title","查看物流");
            startActivity(intent);
        }
    };

}
