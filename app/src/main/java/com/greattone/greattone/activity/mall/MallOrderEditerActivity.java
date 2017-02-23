package com.greattone.greattone.activity.mall;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.PayActivity;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.entity.MallOrder;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 商城订单信息编辑-联系人，收货地址等
 */
public class MallOrderEditerActivity extends BaseActivity {
TextView tv_commodity_name,tv_color_type,tv_quantity,tv_pay_type,tv_shipping_methods,tv_invoice,text,tv_price,tv_agreement,tv_invoice_title_hint;
    EditText et_receiving_address,et_contact_way,et_linkman,et_invoice_title;
    CheckBox cb_agreement;
    View ll_botton;
    String receiving_address,contact_way,linkman;
    boolean isNeedInvoice;
    private View.OnClickListener lis=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_botton:
                    creatOrder();
                    break;
                case R.id.tv_invoice:
                    showPopWindow(v);
                    break;
            }

        }
    };

    private TextWatcher watcher1=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            receiving_address=s.toString();
            ShowText();
        }
    };



    private TextWatcher watcher2=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        @Override
        public void afterTextChanged(Editable s) {
            contact_way=s.toString();
            ShowText();
        }
    };
    private TextWatcher watcher3=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            linkman=s.toString();
            ShowText();
        }
    };
    private String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_order_editer);
        initView();
        getIntentData();
    }

    private void getIntentData() {
        String name=getIntent().getStringExtra("name");
        String type=getIntent().getStringExtra("type");
        String model=getIntent().getStringExtra("model");
         color=getIntent().getStringExtra("color");
        int freight=getIntent().getIntExtra("freight",0);
        int price=getIntent().getIntExtra("price",0);
        tv_commodity_name.setText(name+" "+model);
        tv_color_type.setText(color);
        int money=price+freight;
        String s="应付总额：￥"+money;
        SpannableString str=new SpannableString(s);
        str.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(context, 22)), 6, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//字体大小
        str.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_b90006)), 5,s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//字体颜色
        tv_price.setText(str);
        if (freight>0){
            tv_shipping_methods.setText("快递配送（运费："+freight+"）");
        }

    }

    private void initView() {
        setHead("订单信息", true, true);//订单信息

        tv_commodity_name= (TextView) findViewById(R.id.tv_commodity_name);
        tv_color_type= (TextView) findViewById(R.id.tv_color_type);
        tv_quantity= (TextView) findViewById(R.id.tv_quantity);
        tv_pay_type= (TextView) findViewById(R.id.tv_pay_type);
        tv_shipping_methods= (TextView) findViewById(R.id.tv_shipping_methods);
        tv_invoice= (TextView) findViewById(R.id.tv_invoice);
        tv_invoice_title_hint= (TextView) findViewById(R.id.tv_invoice_title_hint);
        et_invoice_title= (EditText) findViewById(R.id.et_invoice_title);
        tv_invoice= (TextView) findViewById(R.id.tv_invoice);
        tv_invoice.setOnClickListener(lis);
        et_receiving_address= (EditText) findViewById(R.id.et_receiving_address);
//        et_receiving_address.addTextChangedListener(watcher1);
        et_contact_way= (EditText) findViewById(R.id.et_contact_way);
//        et_contact_way.addTextChangedListener(watcher2);
        et_linkman= (EditText) findViewById(R.id.et_linkman);
//        et_linkman.addTextChangedListener(watcher3);
        text= (TextView) findViewById(R.id.text);
        tv_price= (TextView) findViewById(R.id.tv_price);
        cb_agreement= (CheckBox) findViewById(R.id.cb_agreement);
        cb_agreement.setChecked(true);
        tv_agreement= (TextView) findViewById(R.id.tv_agreement);
        tv_agreement.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线

        ll_botton=  findViewById(R.id.ll_botton);
        ll_botton.setOnClickListener(lis);
    }

    /**
     * 生成订单
     */
    private void creatOrder() {

        String invoice=et_invoice_title.getText().toString().trim();
        if (TextUtils.isEmpty(invoice)){
            toast("请填写发票抬头");
            return;
        }
        if (linkman==null||linkman.length()==0){
            toast("请填写联系人");
            return;
        }
        if (receiving_address==null||receiving_address.length()==0){
            toast("请填写收货地址");
            return;
        }
        if (contact_way==null||contact_way.length()==0){
            toast("请填写联系方式");
            return;
        }
        if (linkman==null||linkman.length()==0){
            toast("");
            return;
        }
        if (!cb_agreement.isChecked()){
            toast("请确认协议");
            return;
        }
        MyProgressDialog.show(context);
        HashMap<String ,String> map=new HashMap<>();
        map.put("id",getIntent().getStringExtra("id"));
        map.put("name",linkman);
        map.put("phone",contact_way);
        map.put("address",receiving_address);
        if (isNeedInvoice){
            map.put("invoice",invoice);//发票抬头
        }

        map.put("colour",color);
        HttpProxyUtil.creatMallOrder(context, map, new HttpUtil.ResponseListener() {
            @Override
            public void setResponseHandle(Message2 message) {
                if (message.getData()!=null&&message.getData().startsWith("{")){
                    MallOrder order= JSON.parseObject(
                            message.getData(), MallOrder.class);
                    Intent intent = new Intent(context, PayActivity.class);
                    intent.putExtra("name", order.getTitle());
                    intent.putExtra("contant", order.getTitle());
                    intent.putExtra("price", order.getPrice());
                    intent.putExtra("orderId", order.getOrderid());
                    intent.putExtra("type", "shangcheng");
                    ((Activity) context).startActivityForResult(intent, 3);
                    setResult(RESULT_OK);
                    MyProgressDialog.Cancel();
                    finish();
                }
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

    private void showPopWindow(View v) {
        List<String> list=new ArrayList<>();
        list.add("不要发票");
        list.add("需要发票");
        NormalPopuWindow pop=new NormalPopuWindow(context,list,v);
        pop.setOnItemClickBack(new NormalPopuWindow.OnItemClickBack() {
            @Override
            public void OnClick(int position, String text) {
                tv_invoice.setText(text+" ▼");
                isNeedInvoice=false;
                if (position==1){
                    isNeedInvoice=true;
                    et_invoice_title.setVisibility(View.VISIBLE);
                    tv_invoice_title_hint.setVisibility(View.INVISIBLE);
                }else {
                    isNeedInvoice=false;
                    et_invoice_title.setVisibility(View.GONE);
                    tv_invoice_title_hint.setVisibility(View.GONE);
                }
            }
        });
        pop.show();
    }
    private void ShowText() {
        String ttt="";
        if (receiving_address!=null&&receiving_address.length()>0){
            ttt="寄送至："+receiving_address+"\n";
        }
        if (linkman!=null&&linkman.length()>0){
            ttt+="联系人："+linkman+"\t";
        }
        if (contact_way!=null&&contact_way.length()>0){
            ttt+=contact_way;
        }
        text.setText(ttt);
    }

}
