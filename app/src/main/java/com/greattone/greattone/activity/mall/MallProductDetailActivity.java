package com.greattone.greattone.activity.mall;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.HttpConstants2;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.SharePopWindow;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.MusicalProduct;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.widget.MyBanner;
import com.greattone.greattone.widget.MyRadioButton;
import com.greattone.greattone.widget.MyTextView;

import java.util.ArrayList;
import java.util.List;

public class MallProductDetailActivity extends BaseActivity {
    protected MusicalProduct product;
    MyBanner mybanner;
    TextView name, tv_price, tv_address, tv_num;
    ImageView iv_share;
    LinearLayout ll_label;
    RadioGroup rg_color;RadioGroup radiogroup;
    String color;
    WebView webview;
    View.OnClickListener lis = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_botton://购买
                    Intent intent=new Intent(context,MallOrderEditerActivity.class);
                    intent.putExtra("id",product.getId());
                    intent.putExtra("name",product.getTitle());
                    intent.putExtra("type",product.getType());
                    intent.putExtra("model",product.getModel());
                    intent.putExtra("color",color);
                    intent.putExtra("freight",product.getFreight());
                    intent.putExtra("price",product.getMoney());
                    startActivityForResult(intent,3);
                    break;
                case R.id.iv_share://分享
                    SharePopWindow.build(context).setTitle(product.getTitle())
                            .setContent(product.getNewstext())
                            .setTOargetUrl(product.getShardurl())
                            .setIconPath(product.getShardpic()).show();
                    break;
            }
        }
    };
    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (group==rg_color){
                color=((RadioButton)group.getChildAt(checkedId)).getText().toString();
            }else if (group==radiogroup){
                switch (checkedId) {
                    case R.id.radioButton1:
                        webview.setVisibility(View.VISIBLE);
                        break;
//                    case R.id.radioButton2:
//                        webview.setVisibility(View.GONE);
//                        break;
                }
            }
        }
    };
    int isbusiness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            isbusiness=getIntent().getIntExtra("isbusiness",0);
            setContentView(R.layout.activity_mall_product_detail);
            initView();
            getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        setHead("乐器商城", true, true);//商城详情

        mybanner = (MyBanner) findViewById(R.id.mybanner);
        mybanner.setLayoutParams(new LinearLayout.LayoutParams(screenWidth,
                screenWidth));
        name = (TextView) findViewById(R.id.tv_name);
        name.setOnClickListener(lis);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_share.setOnClickListener(lis);
        ll_label = (LinearLayout) findViewById(R.id.ll_label);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_address = (TextView) findViewById(R.id.tv_address);
        rg_color = (RadioGroup) findViewById(R.id.rg_color);

        tv_num = (TextView) findViewById(R.id.tv_num);
        if (isbusiness==1){
            (findViewById(R.id.ll_botton)).setVisibility(View.GONE);
        }else {
            (findViewById(R.id.ll_botton)).setOnClickListener(lis);
        }
         radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        radiogroup.check(R.id.radioButton1);
        radiogroup.setOnCheckedChangeListener(onCheckedChangeListener);
        webview = (WebView) findViewById(R.id.webview);

    }

    private void initWebView() {

        WebSettings webSettings = webview.getSettings();
        // 设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
//        // 设置可以访问文件
//        webSettings.setAllowFileAccess(true);
//        // 设置支持缩放
//        webSettings.setBuiltInZoomControls(true);
        // 加载需要显示的网页
	 String urlPath =  HttpConstants2.SERVER_URL+"/app/product.php?id="+product.getId();
        webview.loadUrl(urlPath);
        // 设置Web视图
        webview.setWebViewClient(new webViewClient());
    }

    private void getData() {
        MyProgressDialog.show(context);
        HttpProxyUtil.getProduct(context, getIntent().getStringExtra("id"), new HttpUtil.ResponseListener() {

            @Override
            public void setResponseHandle(Message2 message) {
                if (message.getData() != null
                        && message.getData().startsWith("{")) {
                    product = JSON.parseObject(message.getData(),
                            MusicalProduct.class);
                }
                initViewData();
                MyProgressDialog.Cancel();
            }
        }, null);
    }


    /**
     * 加载数据
     */
    protected void initViewData() {
        // 轮播
        List<String> uriList = new ArrayList<String>();
        if (product.getTitlepic() != null && !TextUtils.isEmpty(product.getTitlepic())) {
            String pics[] = product.getTitlepic().split("\\::::::");
            for (int i = 0; i < pics.length; i++) {
                uriList.add(pics[i]);
            }
        }
        mybanner.setImageURI(uriList);
        mybanner.start();

        name.setText(product.getTitle() + " " + product.getModel());
//		ratingbar.setRating(5);
        String freight = "";

        if ( product.getFreight()>0) {
            freight = "邮费：￥" + product.getFreight();
        } else {
            addLabelView("包邮", ll_label);
        }
        String price = "价格：￥" + product.getMoney();
        SpannableString sps = new SpannableString(price + "    " + freight);
        sps.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(context, 15)), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//字体大小
        sps.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(context, 22)), 4,price.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sps.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.yellow_ffa200)), 3,price.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//字体颜色
        if (freight.length() > 0)
            sps.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(context, 15)),price.length()+ 4, price.length() + 4 + freight.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_price.setText(sps);
        String colors[] = product.getColour().split("\\,");
        rg_color.removeAllViews();
        for (int i=0 ;i<colors.length;i++) {
            addColorView(i,colors[i], rg_color);
        }
        rg_color.setOnCheckedChangeListener(onCheckedChangeListener);
        rg_color.check(0);
        String city = "发货地：" + product.getCity();
        String comms = "累计评价：" + product.getComms();
        sps = new SpannableString(city + "    " + comms);
        sps.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(context, 12)), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//字体大小
        sps.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.gray_8f8f8f)), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//字体颜色
        sps.setSpan(new AbsoluteSizeSpan(DisplayUtil.sp2px(context, 18)), 4, city.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sps.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.gray_8f8f8f)), city.length() + 4, city.length() + 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sps.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_b90006)), city.length() + 9, city.length() + comms.length() + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_address.setText(sps);
        initWebView();
    }

    private void addLabelView(String name, LinearLayout ll) {
        MyTextView textview = new MyTextView(context, null);
        textview.setTextColor(context.getResources().getColor(R.color.white));
        textview.setText(name);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(DisplayUtil.dip2px(context, 5), 0, 0, 0);
        textview.setLayoutParams(params);
        textview.setGravity(Gravity.CENTER);
        textview.setMinimumWidth(DisplayUtil.dip2px(context, 40));
        textview.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.mall_label_background));
        ll.addView(textview);
    }

    private void addColorView(int position,String name, RadioGroup rg) {
        MyRadioButton radioButton = new MyRadioButton(context, null);
        radioButton.setTextColor(context.getResources().getColor(R.color.white));
        radioButton.setText(name);
        radioButton.setId(position);
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(DisplayUtil.dip2px(context, 5), 0, 0, 0);
        radioButton.setLayoutParams(params);
        radioButton.setClickable(true);
        radioButton.setButtonDrawable(android.R.color.transparent);
        radioButton.setGravity(Gravity.CENTER);
        radioButton.setMinimumWidth(DisplayUtil.dip2px(context, 40));
        radioButton.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.mall_color_background));
        rg.addView(radioButton);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==3&&resultCode==RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }
    }
    @Override
    public void onRestart() {
        super.onRestart();
        mybanner.start();
    }

    @Override
    public void onStop() {
        mybanner.stop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mybanner.stop();
        super.onDestroy();
    }


    // Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            MyProgressDialog.Cancel();
            super.onPageFinished(view, url);
        }
    }
}
