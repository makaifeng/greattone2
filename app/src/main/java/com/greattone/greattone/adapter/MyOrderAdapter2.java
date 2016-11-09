package com.greattone.greattone.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.PayActivity;
import com.greattone.greattone.entity.Order;

/** 收入 */
public class MyOrderAdapter2 extends Adapter<Order> {

	public MyOrderAdapter2(Context context, List<Order> list, int resId) {
		super(context, list, resId);
	}

	@Override
	public void getview(ViewHolder holder, int position, final Order order) {
		TextView tv_id = (TextView) holder.getView(R.id.tv_id);//
		TextView tv_status = (TextView) holder.getView(R.id.tv_status);//
		TextView tv_price = (TextView) holder.getView(R.id.tv_price);//
		TextView tv_content = (TextView) holder.getView(R.id.tv_content);//
		TextView tv_time = (TextView) holder.getView(R.id.tv_time);//
		TextView tv_btn = (TextView) holder.getView(R.id.tv_btn);//
		TextView tv_title = (TextView) holder.getView(R.id.tv_title);//
		tv_id.setText("订单号：" + order.getDdno());
		tv_content.setText("内容：" + order.getShoptitle());
		tv_status.setText(order.getShopzhuang());
		if (order.getMoney().equals("免费")) {
			tv_price.setText(order.getMoney());
		} else {
			if (order.getBitype().endsWith("人民币")) {
				tv_price.setText("￥" + order.getMoney());
			}else{
				tv_price.setText(order.getBitype()+"$" + order.getMoney());
				
			}
		}
		tv_title.setText("类型：" + order.getLaiyuan());
		tv_time.setText(order.getDdtime());
		if (order.getMyorder().equals("1")&&order.getShopzhuang().equals("未付款")) {
			tv_btn.setVisibility(View.VISIBLE);
			tv_btn.setOnClickListener( new OnClickListener() {

				@Override
				public void onClick(View v) {
//					if (order.getBitype().endsWith("人民币")) {
					Intent intent = new Intent(context, PayActivity.class);
					intent.putExtra("name", order.getShoptitle());
					intent.putExtra("contant", order.getLaiyuan());
					intent.putExtra("price", order.getMoney());
					intent.putExtra("bitype", order.getBitype());
					intent.putExtra("orderId", order.getDdno());
					((Activity) context).startActivityForResult(intent, 3);
//					}else{
//						//欧付宝
//					AllPay.pay(context, order.getShoptitle(), order.getLaiyuan(), order.getMoney(), order.getDdno());
//						
//					}
				}
			});
		} else {
			tv_btn.setVisibility(View.GONE);
		}
	}

	public void updateAdapter(int type) {
		// this.type = paramInt;
		// notifyDataSetChanged();
	}


}
