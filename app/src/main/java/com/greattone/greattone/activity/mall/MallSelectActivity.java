package com.greattone.greattone.activity.mall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.util.DisplayUtil;

public class MallSelectActivity extends BaseActivity {
    private ListView lv_content;
    String names[]={"我的产品","我的订单"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_select);
        initView();
        initContentAdapter();
    }
    /**
     * 加载视图
     */
    private void initView() {
        setHead("乐器商城",true,true);
        lv_content = (ListView) findViewById(R.id.lv_content);
        lv_content.setOnItemClickListener(listener);
        // lv_content.setAdapter(new MyBaseAdapter());
    }
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startIntent(position);
        }
    };

    /**
     * 点击跳转
     *
     * @param position
     */
    private void startIntent(int position) {
        if (names[position].equals("我的产品")) {//我的产品
            Intent intent=new Intent(context,InstrumentMallActivity.class);
            intent.putExtra("isbusiness",1);
            startActivity(intent);
        }else if (names[position].equals("我的订单")){
            Intent intent=new Intent(context,MerchantMallOrderActivity.class);
            intent.putExtra("isbusiness",1);
            startActivity(intent);
        }
    }

    private void initContentAdapter() {
        Parcelable listState = lv_content.onSaveInstanceState();
        lv_content.setAdapter(new MyBaseAdapter());
        lv_content.onRestoreInstanceState(listState);
    }
    class MyBaseAdapter extends BaseAdapter {

        public MyBaseAdapter() {

        }

        @Override
        public int getCount() {
            return names.length ;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getOtherView(position, parent);
            return convertView;
        }

        /**
         * 加载其他列表
         *
         * @param listPosition
         * @param parent
         */
        private View getOtherView(int listPosition, ViewGroup parent) {
            View convertView = LayoutInflater.from(context).inflate(R.layout.adapter_personal_center, parent, false);
            TextView name = (TextView) convertView.findViewById(R.id.tv_name);//
            ImageView icon = (ImageView) convertView.findViewById(R.id.iv_icon);//
            icon.setLayoutParams(
                    new LinearLayout.LayoutParams(DisplayUtil.dip2px(context, 25), DisplayUtil.dip2px(context, 25)));
            name.setText(names[listPosition]);
            icon.setVisibility(View.GONE);
            return convertView;
        }



    }
}
