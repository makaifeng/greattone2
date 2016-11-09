package com.greattone.greattone.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.entity.Order;

public class MyOrderAdapter extends Adapter<Order> {

	public MyOrderAdapter(Context context, List<Order> list, int resId) {
		super(context, list, resId);
	}

	@Override
	public void getview(ViewHolder holder, int position, final Order order) {
		TextView tv_num = (TextView)holder.getView(R.id.tv_num);
	    TextView tv_type = (TextView)holder.getView(R.id.tv_type);
	    TextView tv_content = (TextView)holder.getView(R.id.tv_content);
	    TextView tv_time = (TextView)holder.getView(R.id.tv_time);
//	    TextView tv_date = (TextView)holder.getView(R.id.tv_date);
	    TextView tv_status = (TextView)holder.getView(R.id.tv_status);
	    TextView tv_price = (TextView)holder.getView(R.id.tv_price);
	    LinearLayout ll_del = (LinearLayout)holder.getView(R.id.ll_del);
	    
		ll_del.setOnClickListener(lis);
	    tv_num.setText(order.getDdid());
	    tv_type.setText("订单类型：课程订单");
	    tv_content.setText("课程名称：" + order.getShoptitle());
//	      textview4.setText("上课时间：" + order.getStime());
	      tv_time.setText("下单时间：" + order.getDdtime());
	      tv_price.setText("￥" + order.getMoney());
	      tv_status.setText(order.getShopzhuang());
//	    if (order.getStime() != null)
//	    {
//	      textview1.setText(order.getOrderId());
//	      textview2.setText("订单类型：课程订单");
//	      textview3.setText("课程名称：" + order.getName());
//	      textview4.setText("上课时间：" + order.getStime());
//	      textview5.setText("下单时间：" + DateUtil.dateToString(order.getTime()));
//	      textview7.setText("￥" + order.getPrice());
//	      if (!order.getStatus().equals("0"))
//	        break label363;
//	      textview6.setText("待支付");
//	    }
//	    while (true)
//	    {
//	      localLinearLayout.setOnClickListener(new View.OnClickListener()
//	      {
//	        public void onClick(View paramAnonymousView)
//	        {
////	          HashMap localHashMap = new HashMap();
////	          localHashMap.put("uid", BaseActivity.user.getUid());
////	          localHashMap.put("token", BaseActivity.user.getToken());
////	          localHashMap.put("id", order.getId());
////	          if (RoomcenterorderAdapter.this.type == 0);
////	          for (String str = "http://www.greattone.net/app/centre/delcourse"; ; str = "http://www.greattone.net/app/centre/delroom")
////	          {
////	            HttpSender localHttpSender = new HttpSender(str, "删除课程订单", localHashMap, new MyOnHttpResListener()
////	            {
////	              public void doSuccess(String paramAnonymous2String1, String paramAnonymous2String2, String paramAnonymous2String3, int paramAnonymous2Int)
////	              {
////	                RoomcenterorderAdapter.this.mlist.remove(this.val$position);
////	                RoomcenterorderAdapter.this.notifyDataSetChanged();
////	              }
////	            });
////	            localHttpSender.setContext(RoomcenterorderAdapter.this.mactivity);
////	            localHttpSender.send();
////	            return;
//	          }
//	        }
//	      });
//	      return;
//	      textview1.setText(order.getOrderId());
//	      textview2.setText("订单类型：租赁订单");
//	      textview3.setText("租赁标题：" + order.getName());
//	      textview4.setText("机构名称：" + order.getAgency());
//	      textview5.setText("下单日期：" + DateUtil.dateToString(order.getTime()));
//	      textview7.setText("￥" + order.getPrice());
//	      break;
//	      label363: if (order.getStatus().equals("1"))
//	        textview6.setText("已支付");
//	      else if (order.getStatus().equals("2"))
//	        textview6.setText("已完成");
//	      else if (order.getStatus().equals("3"))
//	        textview6.setText("已结算");
//	    }
	      
	  }

	  public void updateAdapter(int type)
	  {
//	    this.type = paramInt;
//	    notifyDataSetChanged();
	  }

	  OnClickListener lis=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
		}
	};
}
