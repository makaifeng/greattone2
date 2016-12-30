package com.greattone.greattone.activity.qa;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.PayActivity;
import com.greattone.greattone.activity.VideoPlayActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Order;
import com.greattone.greattone.entity.QAOrderDetail;
import com.greattone.greattone.util.FileUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;

import java.util.LinkedHashMap;

/** 我的Q&A详情 */
public class ProblemDetails extends BaseActivity {
//	private String orderid;
//	private String backUrl;
	private QAOrderDetail QAdetail;
	private String state;
	private int type;
	private String id;
	private View mPrice_ll;
	private View reply_ll;
	private View mManage_ll;
//	private View mCurrent_ll;
	private TextView currentState;
	private TextView goOperate;
	private TextView goOperate2;
	private TextView m_price;
	private TextView m_biaojia;
	private TextView m_reply;
	private TextView m_time;
//	private TextView m_etime;
	private TextView m_name;
	private TextView m_content;
	private ImageView m_head2;
	private ImageView m_head1;
	private ImageView m_head3;
	private String userpic;
Order orderinfo=new Order();
private TextView goOperate3;
//private String cando;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_problem_details);
//		this.state = getIntent().getStringExtra("state");
//		this.cando = getIntent().getStringExtra("cando");
		this.type = getIntent().getIntExtra("type", 0);
		this.id = getIntent().getStringExtra("id");
		this.userpic = getIntent().getStringExtra("userpic");
		initView();
	}

	private void initView() {
		if (type == 0) {
			setHead(getResources().getString(R.string.我的问题), true, true);
		} else if (type == 1) {
			setHead(getResources().getString(R.string.收到的问题), true, true);
		} else if (type == 2) {
			setHead("完成的问题", true, true);
		}
		this.mPrice_ll = findViewById(R.id.activity_problem_details_price_ll);
		this.reply_ll = findViewById(R.id.activity_problem_details_huifu_ll);
		this.mManage_ll = findViewById(R.id.activity_problem_details_manage_ll);
//		this.mCurrent_ll = findViewById(R.id.activity_problem_details_state_ll);
		this.currentState = ((TextView) findViewById(R.id.activity_problem_details_current));
		this.goOperate = ((TextView) findViewById(R.id.activity_problem_details_go1));
		this.goOperate2 = ((TextView) findViewById(R.id.activity_problem_details_go2));
		this.goOperate3 = ((TextView) findViewById(R.id.activity_problem_details_go3));
		this.m_price = ((TextView) findViewById(R.id.activity_problem_details_price));
		this.m_biaojia = ((TextView) findViewById(R.id.activity_problem_details_biaojia));
		this.m_reply = ((TextView) findViewById(R.id.activity_problem_details_reply));
		this.m_time = ((TextView) findViewById(R.id.activity_problem_details_ctime));
//		this.m_etime = ((TextView) findViewById(R.id.activity_problem_details_etime));
		this.m_name = ((TextView) findViewById(R.id.activity_problem_details_name));
		this.m_content = ((TextView) findViewById(R.id.activity_problem_details_content));
//		this.price_time = ((TextView) findViewById(R.id.activity_problem_details_price_time));
		this.m_head2 = ((ImageView) findViewById(R.id.activity_problem_details_head2));
		this.m_head2.setOnClickListener(lis);
		this.m_head1 = ((ImageView) findViewById(R.id.activity_problem_details_head1));
		this.m_head3 = ((ImageView) findViewById(R.id.activity_problem_details_head3));
		getData();
	}

	private void getData() {
		MyProgressDialog.show(context);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("api", "QA/detail");
		map.put("classid", getIntent().getStringExtra("classid"));
		map.put("id", getIntent().getStringExtra("id"));
		map.put("extra", "qa_huifu");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& message.getData().startsWith("{")) {
							 QAdetail = JSON.parseObject(
							 JSON.parseObject(message.getData())
							 .getString("content"),
							 QAOrderDetail.class);
							initViewData();
						}
						MyProgressDialog.Cancel();
					}
				}, null));
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			 Intent intent = new Intent(context, VideoPlayActivity.class);
			 intent.putExtra("url",FileUtil.getNetWorkUrl( QAdetail.getQa_video()));
			 startActivity(intent);
		}
	};

	protected void initViewData() {
//		orderid = QAdetail.getId();
		if (QAdetail.getOrderinfo()!=null&&QAdetail.getOrderinfo().startsWith("{")) {
			orderinfo=JSON.parseObject(QAdetail.getOrderinfo(), Order.class);
		}
//		backUrl = HttpConstants2.ALIPAY_BACK_URL;
		m_time.setText(QAdetail.getNewstime());
		if (type==1) {//收到的提问
			m_name.setText(getResources().getString(R.string.提问对象) + QAdetail.getUsername());
			ImageLoaderUtil.getInstance().setImagebyurl(userpic,
					m_head1);
			ImageLoaderUtil.getInstance().setImagebyurl(Data.myinfo.getUserpic(),
					m_head3);
		}else if (type==0){//我的提问
			m_name.setText(getResources().getString(R.string.提问对象) + QAdetail.getQa_name());
			ImageLoaderUtil.getInstance().setImagebyurl(Data.myinfo.getUserpic(),
					m_head1);
			ImageLoaderUtil.getInstance().setImagebyurl(userpic,
					m_head3);
		}else if (type==2){//完成的提问
			m_name.setText(getResources().getString(R.string.提问对象) + QAdetail.getUserinfo().getUsername());
			ImageLoaderUtil.getInstance().setImagebyurl(QAdetail.getUserinfo().getUserpic(),
					m_head1);
			
		}
		m_content.setText(getResources().getString(R.string.提问问题) + QAdetail.getTitle());
		m_price.setText(orderinfo.getMoney() + "元");
		m_reply.setText(QAdetail.getQa_hui());
//		state = Integer.parseInt(QAdetail.getStatus());
//		price_time.setText(TimeUtil.format("yyyy-MM-dd hh:mm",
//				QAdetail.getBidtime()));
//		m_etime.setText(TimeUtil.format("yyyy-MM-dd hh:mm", QAdetail.getEtime()));
		ImageLoaderUtil.getInstance()
				.setImagebyurl(QAdetail.getTitlepic(), m_head2);
		if (orderinfo.getShopzhuang()==null) {
//			if (cando!=null&&cando.equals("以拒绝")) {
//				state="已拒绝";
//			}else {
				state="待标价";
//			}
		}else {
			state=orderinfo.getShopzhuang();
		}
		if(!TextUtils.isEmpty(QAdetail.getQa_jujue())){
			state="已拒绝";
		}
		if (type == 1) {//  收到的问题
			if (state.equals("待标价")) {
				currentState.setText(getResources().getString(R.string.待标价));
				goOperate.setText(getResources().getString(R.string.去标价));
				goOperate.setOnClickListener(new Click(0));//标价
				goOperate2.setVisibility(View.VISIBLE);
				goOperate2.setText(getResources().getString(R.string.免费回答));
				goOperate2.setOnClickListener(new Click(4));//免费回答
				goOperate3.setVisibility(View.VISIBLE);
				goOperate3.setText(getResources().getString(R.string.拒绝标价));
				goOperate3.setOnClickListener(new Click(2));//拒绝标价
				mPrice_ll.setVisibility(View.GONE);
				reply_ll.setVisibility(View.GONE);
			}else if (state.equals("未付款")) {
				currentState.setText(getResources().getString(R.string.待支付));
				mManage_ll.setVisibility(View.GONE);
				reply_ll.setVisibility(View.GONE);
				m_price.setText("￥" + orderinfo.getMoney());
				m_biaojia.setText(getResources().getString(R.string.已标价));
			}else if (state.equals("已付款")) {
				currentState.setText(getResources().getString(R.string.待回答));
				goOperate2.setVisibility(View.GONE);
				goOperate.setText(getResources().getString(R.string.去回答));
				goOperate.setOnClickListener(new Click(1));//回答
				reply_ll.setVisibility(View.GONE);
				m_price.setText("￥" + orderinfo.getMoney());
				m_biaojia.setText(getResources().getString(R.string.已支付));
			}else if (state.equals("已完成")) {
				reply_ll.setVisibility(View.VISIBLE);
				mManage_ll.setVisibility(View.GONE);
				currentState.setText(getResources().getString(R.string.已完成));
				m_price.setText("￥" + orderinfo.getMoney());
				m_biaojia.setText(getResources().getString(R.string.已支付));
				m_reply.setText(QAdetail.getQa_hui());
			}else if (state.equals("已拒绝")) {
				reply_ll.setVisibility(View.GONE);
				mManage_ll.setVisibility(View.GONE);
				currentState.setText(getResources().getString(R.string.已拒绝));
				mPrice_ll.setVisibility(View.GONE);
			}
		} else if (type == 0) {//我的问题
			if (state.equals("待标价")) {
				currentState.setText(getResources().getString(R.string.待标价));
				mManage_ll.setVisibility(View.GONE);
				mPrice_ll.setVisibility(View.GONE);
				reply_ll.setVisibility(View.GONE);
			}else if (state.equals("未付款")) {
				currentState.setText(getResources().getString(R.string.待支付));
				goOperate.setText(getResources().getString(R.string.去支付));
				goOperate.setOnClickListener(new Click(3));//支付
				reply_ll.setVisibility(View.GONE);
				m_price.setText("￥" + orderinfo.getMoney());
				m_biaojia.setText(getResources().getString(R.string.已标价));
			}else if (state.equals("已付款")) {
				mManage_ll.setVisibility(View.GONE);
				reply_ll.setVisibility(View.GONE);
				currentState.setText(getResources().getString(R.string.待回答));
				m_price.setText("￥" + orderinfo.getMoney());
				m_biaojia.setText(getResources().getString(R.string.待回答));
//			}else if (state.equals("已完成")) {
//				reply_ll.setVisibility(0);
//				mManage_ll.setVisibility(8);
//				currentState.setText(getResources().getString(R.string.已完成));
//				m_price.setText("￥" + orderinfo.getMoney());
//				m_biaojia.setText(getResources().getString(R.string.已支付));
//				m_reply.setText(QAdetail.getQa_hui());
			}else if (state.equals("已拒绝")) {
				reply_ll.setVisibility(View.GONE);
				mManage_ll.setVisibility(View.GONE);
				mPrice_ll.setVisibility(View.GONE);
				currentState.setText(getResources().getString(R.string.已拒绝));
			}
		}else {
			reply_ll.setVisibility(View.VISIBLE);
			mManage_ll.setVisibility(View.GONE);
			currentState.setText(getResources().getString(R.string.已完成));
			m_price.setText("￥" + orderinfo.getMoney());
			m_biaojia.setText(getResources().getString(R.string.已支付));
			m_reply.setText(QAdetail.getQa_hui());
		}
	}

	private class Click implements OnClickListener {
		private int tag;

		public Click(int i) {
			this.tag = i;
		}

		public void onClick(View paramView) {
			if (this.tag == 0) {//标价
				 Intent intent = new Intent(ProblemDetails.this,
				 MarkThePriceAct.class);
				 intent.putExtra("id", id);
				 startActivityForResult(intent, 0);
			} else if (this.tag == 1) {//回答
				 Intent intent = new Intent(ProblemDetails.this,
				 AnswerPriceAct.class);
				 intent.putExtra("id", id);
				 intent.putExtra("type", 0);
				 startActivityForResult(intent, 2);
			} else if (this.tag == 2) {//拒绝标价
				 Intent intent = new Intent(ProblemDetails.this,
				 AnswerPriceAct.class);
				 intent.putExtra("id", id);
				 intent.putExtra("type", 1);
				 startActivityForResult(intent, 3);
			} else if (this.tag == 3) {//支付
				toast("支付");
				 Intent intent = new Intent(ProblemDetails.this,
						 PayActivity.class);
				 intent.putExtra("name", "qa订单");
				 intent.putExtra("contant", QAdetail.getTitle());
				 intent.putExtra("price",  orderinfo.getMoney());
				 intent.putExtra("orderId",  orderinfo.getDdno());
				 intent.putExtra("bitype", "人民币");
				 startActivityForResult(intent, 1);
			} else if (this.tag == 4) {//免费回答
				 Intent intent = new Intent(ProblemDetails.this,
						 AnswerPriceAct.class);
						 intent.putExtra("id", id);
						 intent.putExtra("type", 2);
						 startActivityForResult(intent, 4);
			}
		}
	}

	/**
	 * 点击返回按钮
	 */
	@Override
	public void setBackClick() {
		super.setBackClick();
		setResult(RESULT_CANCELED, getIntent());
	}
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		getData();
		if (arg0==RESULT_OK&&arg1==0) {//标价
			
		}else if (arg0==RESULT_OK&&arg1==1) {//支付
		}else if (arg0==RESULT_OK&&arg1==2) {//回答
		}else if (arg0==RESULT_OK&&arg1==3) {//拒绝标价
		}else if (arg0==RESULT_OK&&arg1==4) {//免费回答
			
		}
	}
	
}
