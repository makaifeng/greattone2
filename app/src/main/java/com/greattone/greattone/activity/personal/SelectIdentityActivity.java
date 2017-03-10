package com.greattone.greattone.activity.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;

import static com.greattone.greattone.util.DisplayUtil.dip2px;

public class SelectIdentityActivity extends BaseActivity {
TextView tv_identity1,tv_identity2,tv_identity3,tv_identity4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_identity);
        initView();

    }

    private void initView() {
        setHead("会员注册",true,true);
        tv_identity1 = (TextView) findViewById(R.id.tv_identity1);
        tv_identity2 = (TextView) findViewById(R.id.tv_identity2);
        tv_identity3= (TextView) findViewById(R.id.tv_identity3);
        tv_identity4 = (TextView) findViewById(R.id.tv_identity4);
        int width=(screenWidth- dip2px(context,10+16*2))/2;
        RelativeLayout.LayoutParams layoutParams1=new RelativeLayout.LayoutParams(width,width*50/35);
        tv_identity1.setLayoutParams(layoutParams1);
        RelativeLayout.LayoutParams layoutParams2=new RelativeLayout.LayoutParams(width,width*50/35);
        layoutParams2.setMargins(dip2px(context,10),0,0,0);
        layoutParams2.addRule(RelativeLayout.RIGHT_OF,R.id.tv_identity1);
        tv_identity2.setLayoutParams(layoutParams2);
        RelativeLayout.LayoutParams layoutParams3=new RelativeLayout.LayoutParams(width,width*50/35);
        layoutParams3.setMargins(0,dip2px(context,10),0,0);
        layoutParams3.addRule(RelativeLayout.BELOW,R.id.tv_identity1);
        tv_identity3.setLayoutParams(layoutParams3);
        RelativeLayout.LayoutParams layoutParams4=new RelativeLayout.LayoutParams(width,width*50/35);
        layoutParams4.setMargins(dip2px(context,10),dip2px(context,10),0,0);
        layoutParams4.addRule(RelativeLayout.RIGHT_OF,R.id.tv_identity1);
        layoutParams4.addRule(RelativeLayout.BELOW,R.id.tv_identity1);
        tv_identity4.setLayoutParams(layoutParams4);

        tv_identity1.setOnClickListener(lis);
        tv_identity2.setOnClickListener(lis);
        tv_identity3.setOnClickListener(lis);
        tv_identity4.setOnClickListener(lis);
    }
    View.OnClickListener lis=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           if (v==tv_identity1){
               startActivityForResult(new Intent(context,
                       RegisterActivity.class).putExtra("groupid",1), 1);
           }else   if (v==tv_identity2){
               startActivityForResult(new Intent(context,
                       RegisterActivity.class).putExtra("groupid",3), 1);
           }else   if (v==tv_identity3){
               startActivityForResult(new Intent(context,
                       RegisterActivity.class).putExtra("groupid",4), 1);
           }else   if (v==tv_identity4){
            startActivityForResult(new Intent(context,
                    RegisterActivity.class).putExtra("groupid",5), 1);

           }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                setResult(RESULT_OK,data);
                finish();
            }
        }
    }
}
