package com.greattone.greattone.activity.qa;

import java.util.LinkedHashMap;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Order;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
/**Q&A订单详情*/
public class QAOrderDetails extends BaseActivity implements OnClickListener{
	private int type;//1.教师订单 0.Q&A订单
	private String id;
//	,url;
	private TextView m_manage,m_orderid,m_problem,m_price,m_name,
//	m_mobile,
	m_mark,m_ids,m_time,m_money,m_paystatus;
//	,m_status,m_state;
//	private ImageView m_head;
//	private LinearLayout m_mamagell;
	private Order orderinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qaorder_details);
		setHead(getResources().getString(R.string.订单详情), true, true);
		
		type = getIntent().getIntExtra("type",0);//0.QA订单  1.课程、租赁订单
//		url = getIntent().getStringExtra("url");
		id=getIntent().getStringExtra("id");
		initView();
	}

	private void initView(){
//		m_head = (ImageView)findViewById(R.id.activity_qaorder_details_head);
		m_orderid = (TextView)findViewById(R.id.act_qaorder_detail_id);
		 m_problem=(TextView)findViewById(R.id.act_qaorder_detail_problem);
		 m_price=(TextView)findViewById(R.id.act_qaorder_detail_price);
		 m_name=(TextView)findViewById(R.id.act_qaorder_detail_name);
//		 m_mobile=(TextView)findViewById(R.id.act_qaorder_detail_mobile);
		 m_mark=(TextView)findViewById(R.id.act_qaorder_detail_mark);
		 m_ids=(TextView)findViewById(R.id.act_qaorder_detail_ids);
		 m_time=(TextView)findViewById(R.id.act_qaorder_detail_time);
		 m_money=(TextView)findViewById(R.id.act_qaorder_detail_money);
		 m_paystatus=(TextView)findViewById(R.id.act_qaorder_detail_paystatus);
//		 m_status=(TextView)findViewById(R.id.act_qaorder_detail_status);
//		 m_state=(TextView)findViewById(R.id.act_qaorder_detail_state);
//		 m_mamagell = (LinearLayout)findViewById(R.id.act_qaorder_detail_manages);
		 m_manage=(TextView)findViewById(R.id.act_qaorder_detail_manage);
		 m_manage.setOnClickListener(this);
		 if(type==1){
				findViewById(R.id.activity_qaorder_details_head).setVisibility(View.VISIBLE);
			 getData1();
		 }else{
			 getData();
		 }
	}
	
	private void getData(){
		MyProgressDialog.show(context);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		//app.umecn.com/e/appapi/?api=order/detail&ddid=xxx
		map.put("api", "QA/detail");
		map.put("classid", "85");
		map.put("id", id);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& message.getData().startsWith("{")) {
							orderinfo = JSON.parseObject(
							 JSON.parseObject(message.getData())
							 .getString("content"),
							 Order.class);
							initViewData();
						}
						MyProgressDialog.Cancel();
					}
				}, null));
//		baseMap.clear();
//		baseMap.put("uid", user.getUid());
//		baseMap.put("token", user.getToken());
//		baseMap.put("id", id);
//		HttpSender sender=new HttpSender(uurl.qainfo, "QA订单详情", baseMap ,new MyOnHttpResListener() {
//			//	 TextView m_orderid,m_problem,m_price,m_name,m_mobile,m_mark,m_ids,m_time,m_money,m_paystatus,m_status,m_state;
//			@Override
//			public void doSuccess(String data, String name, String info, int status) {
//				// TODO Auto-generated method stub
//				QaInfoEntity get=gsonUtil.getInstance().json2Bean(data, QaInfoEntity.class);
//				if(orderinfo == null){
//					try {
//						JSONObject joObject = new JSONObject(data);
//						orderinfo = gsonUtil.getInstance().json2Bean(joObject.getJSONObject("order").toString(), OrderEntity.class);
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//				}
//				m_orderid.setText("订单编号："+get.getOrderId());
//				m_problem.setText("问题："+get.getTitle());
//				m_price.setText("￥"+get.getPrice());
//				m_name.setText("回答人："+get.getName());
//				m_mobile.setText("手机号码："+get.getMobile());
//				m_mark.setText("备注：");//
//				m_ids.setText("订单编号："+get.getOrderId());
//				m_time.setText("下单时间："+DateUtil.dateToString("yyyy-MM-dd hh:mm",get.getTime()));
//				m_money.setText("订单总额："+get.getPrice());
//				m_paystatus.setText("支付方式：支付宝");//暂时写死支付宝,后面可能会加其他支付方式
//				
//				if(get.getStatus().equals("0")){
//					m_status.setText("订单状态：待标价");
//					m_state.setText("待标价");
//				}else if(get.getStatus().equals("1")){
//					m_status.setText("订单状态：待支付");
//					m_state.setText("待支付");
//					m_mamagell.setVisibility(View.VISIBLE);
//				}else if(get.getStatus().equals("2")){
//					m_status.setText("订单状态：待回答");
//					m_state.setText("待回答");
//				}else if(get.getStatus().equals("3")){
//					m_status.setText("订单状态：已完成");
//					m_state.setText("已完成");
//				}else if(get.getStatus().equals("4")){
//					m_status.setText("订单状态：已拒绝");
//					m_state.setText("已拒绝");
//				}
//			}
//		});
//		sender.setContext(this);
//		sender.send();
	}

	protected void initViewData() {

		m_orderid.setText(getResources().getString(R.string.订单编号)+orderinfo.getQa_id());
		m_problem.setText(getResources().getString(R.string.问题)+orderinfo.getTitle());
		m_price.setText("￥"+orderinfo.getPrice());
		m_name.setText(getResources().getString(R.string.回答人)+orderinfo.getQa_name());
//		m_mobile.setText(getResources().getString(R.string.手机号码)+orderinfo.getMobile());
		m_mark.setText(getResources().getString(R.string.备注));//
		m_ids.setText(getResources().getString(R.string.订单编号)+orderinfo.getQa_id());
		m_time.setText(getResources().getString(R.string.下单时间)+orderinfo.getNewstime());
		m_money.setText(getResources().getString(R.string.订单总额)+orderinfo.getPrice());
		m_paystatus.setText(getResources().getString(R.string.支付方式));//暂时写死支付宝,后面可能会加其他支付方式
		
//		if(orderinfo.getStatus().equals("0")){
//			m_status.setText(getResources().getString(R.string.订单状态：待标价");
//			m_state.setText(getResources().getString(R.string.待标价");
//		}else if(orderinfo.getStatus().equals("1")){
//			m_status.setText(getResources().getString(R.string.订单状态：待支付");
//			m_state.setText(getResources().getString(R.string.待支付");
//			m_mamagell.setVisibility(View.VISIBLE);
//		}else if(orderinfo.getStatus().equals("2")){
//			m_status.setText(getResources().getString(R.string.订单状态：待回答");
//			m_state.setText(getResources().getString(R.string.待回答");
//		}else if(orderinfo.getStatus().equals("3")){
//			m_status.setText(getResources().getString(R.string.订单状态：已完成");
//			m_state.setText(getResources().getString(R.string.已完成");
//		}else if(orderinfo.getStatus().equals("4")){
//			m_status.setText(getResources().getString(R.string.订单状态：已拒绝");
//			m_state.setText(getResources().getString(R.string.已拒绝");
//		}
	
		
	}

	private void getData1(){
//		baseMap.clear();
//		baseMap.put("uid", user.getUid());
//		baseMap.put("token", user.getToken());
//		baseMap.put("oid", id);
//		HttpSender sender=new HttpSender(url, "课程订单详情()", baseMap, new MyOnHttpResListener() {
//			
//			@Override
//			public void doSuccess(String data, String name, String info, int status) {
//				// TODO Auto-generated method stub
//				CourseOrderInfo get=gsonUtil.getInstance().json2Bean(data, CourseOrderInfo.class);
//				orderinfo = get.getOrder();
//				m_head.setVisibility(View.VISIBLE);
//				ImageLoaderUtil.getInstance().setImagebyurl(get.getPic(), m_head);
//				m_orderid.setText(getResources().getString(R.string.订单编号：" + get.getOrder().getOrderid());
//				m_problem.setText(getResources().getString(R.string.标题："+get.getTitle());
//				m_price.setText("￥"+get.getPrice());
//				m_name.setText("购买人："+get.getName());
//				m_mobile.setText("手机号码："+get.getMobile());
//				m_mark.setText("备注："+get.getRemark());
//				m_ids.setText("订单编号：" + get.getOrder().getOrderid());
//				m_time.setText("下单时间："+DateUtil.dateToString("yyyy-MM-dd hh:mm",get.getTime()));
//				m_money.setText("订单总额："+get.getSumprice());
//				m_paystatus.setText("支付方式："+get.getPayname());
//				
//				if(get.getStatus().equals("0")){
//					m_status.setText("订单状态：未支付");
//					m_mamagell.setVisibility(View.VISIBLE);
//					m_state.setText("未支付");
//				}else if(get.getStatus().equals("1")){
//					m_status.setText("订单状态：已支付");
//					m_mamagell.setVisibility(View.GONE);
//					m_state.setText("已支付");
//				}else if(get.getStatus().equals("2")){
//					m_status.setText("订单状态：已完成");
//					m_state.setText("已完成");
//				}else if(get.getStatus().equals("3")){
//					m_status.setText("订单状态：佣金已结算");
//					m_state.setText("佣金已结算");
//				}
//			}
//		});
//		sender.setContext(this);
//		sender.send();
	}

	@Override
	public void onClick(View paramView) {
		 
		switch(paramView.getId()){
		case R.id.act_qaorder_detail_manage:
//			Orderpay();
			break;
		}
	}
	
//	private void Orderpay(){
//		if(orderinfo == null){
//			toast("支付失败");
//			return;
//		}
//		String payurl=null;
//		baseMap.clear();
//		baseMap.put("uid", user.getUid());
//		baseMap.put("token", user.getToken());
//		baseMap.put("payid", "1");
//		baseMap.put("money", orderinfo.getPrice());
//		baseMap.put("order", orderinfo.getOrderid());
////		if(url.equals(uurl.getcourseorder)){
//		if(type==0){
//			payurl=uurl.coursecheck;//课程支付
//		}else{
//			payurl=uurl.roomcheck;//、教室支付
//		}
//		HttpSender sender=new HttpSender(payurl, "课程订单支付(租赁)",baseMap, new MyOnHttpResListener() {
//			
//			@Override
//			public void doSuccess(String data, String name, String info, int status) {
//				// TODO Auto-generated method stub
//				String istrue=(String)gsonUtil.getInstance().getValue(data, "istrue");
//				String backurl=(String)gsonUtil.getInstance().getValue(data, "url");
//				if(istrue.equals("1")){
//					toast("余额支付成功！");
//					getData1();
//				}else{
//					AlipayUtil pay=new AlipayUtil(QAOrderDetails.this, new PayCallBack() {
//						
//						@Override
//						public void succee() {
//							// TODO Auto-generated method stub
//							toast("支付成功！");
//							getData1();
//						}
//						
//						@Override
//						public void paying() {
//							// TODO Auto-generated method stub
//							
//						}
//						
//						@Override
//						public void failure() {
//							 
//							
//						}
//					});
//					pay.pay("课程（租赁）订单", "课程（租赁）订单", orderinfo.getPrice(), orderinfo.getOrderid(), backurl);
//				}
//			}
//		});
//		sender.setContext(self);
//		sender.send();
//	}
}
