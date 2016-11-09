package com.greattone.greattone.activity.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.dialog.NormalPopuWindow.OnItemClickBack;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
/**报名课程*/
public class RegistrationActivity extends BaseActivity {
	private ImageView m_head;
	private TextView m_name;
	private TextView m_price;
	private TextView m_num;
	private TextView m_pay;
	private View pay_ll;
	private EditText et_name;
	private EditText et_phone;
private String classid="91";
private String mid="7";
private String price;
private String title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		price=getIntent().getStringExtra("price");
		title=getIntent().getStringExtra("title");
		initView();
	}

	private void initView() {
		setHead(getResources().getString(R.string.sign_up), true, true);//我要报名
		
		
	    this.m_head = ((ImageView)findViewById(R.id.act_registration_head));
	    this.m_name = ((TextView)findViewById(R.id.act_registration_name));
	    this.m_price = ((TextView)findViewById(R.id.act_registration_price));
	    this.m_num = ((TextView)findViewById(R.id.act_registration_num));
	    this.et_name = ((EditText)findViewById(R.id.et_name));
	    this.et_phone = ((EditText)findViewById(R.id.et_phone));
	    this.m_pay = ((TextView)findViewById(R.id.act_registration_applytype));
	    this.pay_ll = findViewById(R.id.act_registration_applytype_ll);
		this.pay_ll.setOnClickListener(lis);
	    findViewById(R.id.act_registration_commit).setOnClickListener(lis);
	    ImageLoaderUtil.getInstance().setImagebyurl(getIntent().getStringExtra("pic"), this.m_head);
	    this.m_name.setText(title);
	    this.m_price.setText("￥" +price);
	    this.m_num.setText(getIntent().getStringExtra("ke_hour") + "节");
	    et_name.setText(Data.myinfo.getUsername());
	    et_phone.setText(Data.myinfo.getPhone());
	}
	OnClickListener lis=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
				switch (v.getId()) {
				case R.id.act_registration_applytype_ll://支付方式\
					List<String > mlist=new ArrayList<String>();
					mlist.add(getResources().getString(R.string.支付宝));
//					mlist.add(getResources().getString(R.string.微信));
//					mlist.add(getResources().getString(R.string.银联));
					NormalPopuWindow normalPopuWindow=new NormalPopuWindow(context, mlist, pay_ll);
					normalPopuWindow.setOnItemClickBack(new OnItemClickBack() {
						
						@Override
						public void OnClick(int position, String text) {
							m_pay.setText(text);
						}
					});
					normalPopuWindow.show();
					break;
				case R.id.act_registration_commit://提交
					commit();
					break;

				default:
					break;
				}
		}
	};
/**提交*/
	protected void commit() {
			String name=et_name.getText().toString().trim();
			String phone=et_phone.getText().toString().trim();
			if (TextUtils.isEmpty(name)) {
				toast(getResources().getString(R.string.请输入姓名));
				return;
			}
			if (TextUtils.isEmpty(phone)) {
				toast(getResources().getString(R.string.请输入手机号));
				return;
			}
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("api", "post/ecms_bm");
			map.put("mid", mid);
			map.put("enews", "MAddInfo");
			map.put("classid", classid);
			map.put("bao_id", getIntent().getStringExtra("id"));
			map.put("bao_jin",price);
			map.put("bao_type","0");//课程
			map.put("title",title);
			map.put("bao_name",name);//选手姓名
			map.put("hai_phone", phone);//联系电话
			map.put("loginuid", Data.user.getUserid());
			map.put("logintoken", Data.user.getToken());
			addRequest(HttpUtil.httpConnectionByPost(context, map,
					new ResponseListener() {

						@Override
						public void setResponseHandle(Message2 message) {
							toast(getResources().getString(R.string.报名成功));
							MyProgressDialog.Cancel();
							finish();
						}
					}, null));
	}
}
