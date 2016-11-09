package com.greattone.greattone.adapter;

import java.util.List;

import android.content.Context;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.entity.MyQA;

public class MyDraftAdapter extends Adapter<MyQA> {
	String type;

	public MyDraftAdapter(Context context, List<MyQA> list, int resId) {
		super(context, list, resId);
	}

	public void setType(String type) {
		this.type = type;
	}
	@Override
	public void getview(ViewHolder holder, int position, final MyQA myQA) {
		TextView tv_title = (TextView) holder.getView(R.id.tv_title);
		TextView tv_status = (TextView) holder.getView(R.id.tv_status);
		TextView tv_content = (TextView) holder.getView(R.id.tv_content);
		TextView tv_time = (TextView) holder.getView(R.id.tv_time);
		tv_content.setText("提问问题：" + myQA.getTitle());
		tv_time.setText(myQA.getNewspath());
		if (myQA.getQa_dingjia().equals("待标价")&&myQA.getCando().equals("已拒绝")) {
			tv_status.setText("已拒绝");
		}else if (myQA.getQa_dingjia().equals("待标价")&&myQA.getShopzhuang().equals("未付款")) {
			tv_status.setText("待支付");
		}else if (myQA.getQa_dingjia().equals("待标价")&&myQA.getShopzhuang().equals("已付款")) {
			tv_status.setText("待回答");
		}else if (myQA.getQa_dingjia().equals("待支付")) {
			if (myQA.getShopzhuang().equals("已付款")) {
				tv_status.setText(myQA.getShopzhuang());
			}else {
				tv_status.setText(myQA.getQa_dingjia());
			}
		}else{
			tv_status.setText(myQA.getQa_dingjia());
		}
		if (type.endsWith("myAsk")) {
			tv_title.setText("接收者：" + myQA.getQa_name());
		}else 	if (type.endsWith("receive")){
			tv_title.setText("提问者：" + myQA.getUsername());
		}else 	if (type.endsWith("finished")){
			if (myQA.getUserid().equals(Data.myinfo.getUserid())) {
				tv_title.setText("接收者：" + myQA.getQa_name());
			}else {
				tv_title.setText("提问者：" + myQA.getUsername());
			}
			tv_status.setText("已完成");
		}
	}

}
