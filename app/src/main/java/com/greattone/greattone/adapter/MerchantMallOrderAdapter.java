package com.greattone.greattone.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.WebActivity;
import com.greattone.greattone.data.HttpConstants2;
import com.greattone.greattone.dialog.MyIosDialog;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.MallOrder;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.ImageLoaderUtil;

import java.util.List;

public class MerchantMallOrderAdapter extends BaseAdapter {
	private Context context;
	private List<MallOrder> orderList;
	private int screenWidth;

	public MerchantMallOrderAdapter(Context context, List<MallOrder> orderList) {
		this.context = context;
		this.orderList = orderList;
		screenWidth = ((BaseActivity) context).screenWidth;
	}

	@Override
	public int getCount() {
		return orderList.size();
//		return 2;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder(); 
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_my_mall_adapter, group, false);
			holder.name = (TextView) convertView.findViewById(R.id.tv_name);//
			holder.orderid = (TextView) convertView.findViewById(R.id.tv_orderid);//
			holder.state = (TextView) convertView.findViewById(R.id.tv_state);//
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);//
			holder.price = (TextView) convertView.findViewById(R.id.tv_price);//
			holder.type = (TextView) convertView.findViewById(R.id.tv_type);//
			holder.total = (TextView) convertView.findViewById(R.id.tv_total);//
			holder.button = (TextView) convertView.findViewById(R.id.tv_button1);//
			holder.button2 = (TextView) convertView.findViewById(R.id.tv_button2);//
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_pic);//
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		TextView name;
		TextView orderid;
		TextView state;
		TextView title;
		TextView type;
		TextView price;
		TextView total;
		TextView button;
		TextView button2;
		ImageView icon;
		MallOrder order;


		public void setPosition(int position) {
			button.setVisibility(View.VISIBLE);
			button2.setVisibility(View.VISIBLE);
			order=orderList.get(position);
			String pic[]=order.getTitlepic().split("\\::::::");
			if (pic.length>=1) {
				ImageLoaderUtil.getInstance().setImagebyurl(
						pic[0], icon);
			}
			name.setText(order.getBuyersname());
			orderid.setText("订单号："+order.getOrderid());
			showState();
			title.setText(order.getTitle()+"  "+order.getModel());
			type.setText("产品分类："+order.getColour());
			total.setText("共1件商品  合计："+order.getMoney());
			price.setText("￥"+order.getPrice());
			button.setOnClickListener(lis);
			button2.setOnClickListener(lis);
		}
		private  void showState(){
			button.setVisibility(View.GONE);
			button2.setVisibility(View.GONE);
			//1=未付款 2=已付款 3=已取消 4=以发货 5=已收货
			if (order.getState()==1){
				state.setText("未付款");
//				button.setVisibility(View.VISIBLE);
//				button2.setVisibility(View.GONE);
//				button.setText("付 款");
			}else if (order.getState()==2){
				state.setText("待发货");
//				button.setVisibility(View.GONE);
//				button2.setVisibility(View.GONE);
			}else if (order.getState()==3){
//				button.setVisibility(View.GONE);
//				button2.setVisibility(View.GONE);
				state.setText("已取消");
			}else if (order.getState()==4){
				state.setText("已发货");
//				button.setVisibility(View.VISIBLE);
//				button2.setVisibility(View.VISIBLE);
//				button.setText("确认收货");
			}else if (order.getState()==5){
				state.setText("已收货");
//				button.setVisibility(View.VISIBLE);
//				button2.setVisibility(View.VISIBLE);
//				button.setText("评 价");
			}
		}
		private View.OnClickListener lis=new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()){
					case R.id.tv_button1:
						//1=未付款 2=已付款 3=已取消 4=以发货 5=已收货
						if (order.getState()==1){
							//付款
						}else if (order.getState()==2){
						}else if (order.getState()==3){
						}else if (order.getState()==4){
							 MyIosDialog.showAlertView(context,0,null,"确认收货吗？","确认",new String[]{"取消"},new MyIosDialog.OnAlertViewClickListener(){

								@Override
								public void confirm(String result) {
									//取消
								}

								@Override
								public void cancel() {
									//确认
									//确认收货
									confirmReceipt();
								}
							}).show();

						}else if (order.getState()==5){
							//评价
						}
						break;
					case R.id.tv_button2:
						//查看物流
						Intent intent=new Intent(context, WebActivity.class);
						String urlPath = HttpConstants2.SERVER_URL+"/app/kuaidi.php?num="+order.getLogisticsnumber();
						intent.putExtra("urlPath",urlPath);
						intent.putExtra("title","查看物流");
						context.startActivity(intent);
						break;
					default:
						break;
				}
			}

			/**
			 * 确认收货
			 */
			private void confirmReceipt() {
				MyProgressDialog.show(context);
				HttpProxyUtil.confirmReceipt(context, order.getId()+"", new HttpUtil.ResponseListener() {
					@Override
					public void setResponseHandle(Message2 message) {
						order.setState(5);
						notifyDataSetChanged();
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
		};
	}

}
