package com.greattone.greattone.activity.personal;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.Listener.OnSelectCityListener;
import com.greattone.greattone.Listener.TimePickerDismissCallback;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.CitySelectDialog;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.MyTimePickerDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.dialog.SelectorDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.MessageUtil;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public class PersonalDetailsFragment extends BaseFragment {
	/** fragment 主布局 */
	private View rootView;
//	private List<Label> LabelList;
	List<String> aihaolist ;
//	private LabelAdapter adapter;
	private NormalPopuWindow popu1;
	String url;
	String editurl;
//	private TextView m_realname;
	private EditText m_nickname;
	private TextView m_realnames;
	private TextView m_sex;
//	private TextView m_mobile;
//	private EditText m_email;
	private TextView m_cname;
//	private MyGridView m_gridview;
	private TextView m_birthday;
	private TextView m_city;
	private EditText m_address;
	private EditText m_descr;
	private LinearLayout cname_ll;
	private LinearLayout birthday_ll;
	private LinearLayout city_ll;
	protected UserInfo useMsg=Data.myinfo;
	private LinearLayout sex_ll;
	protected int num;
//	private boolean isInitView;
	private LinearLayout aihao_ll;
	private TextView m_aihao;
@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	num=0;
	Label();
//	getCname();
}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.rootView = inflater.inflate(R.layout.fragment_personal_details,
				container, false);
		initView();
		return rootView;
	}

	private void initView() {
		// if (this.type == 1) {
		// rootView.findViewById(R.id.fragment_personal_details_jg_ll)
		// .setVisibility(View.GONE);
		// }
//		this.m_realname = ((TextView) rootView
//				.findViewById(R.id.fragment_personal_details_realname));
		this.m_nickname = ((EditText) rootView
				.findViewById(R.id.fragment_personal_details_nickname));
		this.m_realnames = ((TextView) rootView
				.findViewById(R.id.et_company));
		this.m_sex = ((TextView) rootView
				.findViewById(R.id.fragment_personal_details_sex));
//		this.m_mobile = ((TextView) rootView
//				.findViewById(R.id.fragment_personal_details_mobile));
//		this.m_email = ((EditText) rootView
//				.findViewById(R.id.fragment_personal_details_email));
		this.m_cname = ((TextView) rootView
				.findViewById(R.id.fragment_personal_details_cname));
//		this.m_gridview = ((MyGridView) rootView
//				.findViewById(R.id.fragment_personal_detail_gridview));
		this.m_birthday = ((TextView) rootView
				.findViewById(R.id.fragment_personal_details_birthday));
		this.m_aihao = ((TextView) rootView
				.findViewById(R.id.fragment_personal_details_aihao));
		this.m_city = ((TextView) rootView
				.findViewById(R.id.fragment_personal_details_city));
		this.m_address = ((EditText) rootView
				.findViewById(R.id.fragment_personal_details_address));
		this.m_descr = ((EditText) rootView
				.findViewById(R.id.fragment_personal_details_descr));
		rootView.findViewById(R.id.fragment_personal_details_descr)
				.setOnClickListener(lis);
		rootView.findViewById(R.id.frag_personal_details_renzhen)
				.setOnClickListener(lis);
		rootView.findViewById(R.id.fragment_personal_details_commit)
				.setOnClickListener(lis);
		this.cname_ll = ((LinearLayout) rootView
				.findViewById(R.id.fragment_personal_details_cname_ll));
		this.cname_ll.setOnClickListener(lis);
		this.birthday_ll = ((LinearLayout) rootView
				.findViewById(R.id.fragment_personal_details_birthday_ll));
		this.birthday_ll.setOnClickListener(lis);
		this.aihao_ll = ((LinearLayout) rootView
				.findViewById(R.id.fragment_personal_details_aihao_ll));
		this.aihao_ll.setOnClickListener(lis);
		this.city_ll = ((LinearLayout) rootView
				.findViewById(R.id.fragment_personal_details_city_ll));
		this.city_ll.setOnClickListener(lis);
		this.sex_ll = ((LinearLayout) rootView
				.findViewById(R.id.fragment_personal_details_sex_ll));
		this.sex_ll.setOnClickListener(lis);
//		this.LabelList = new ArrayList<Label>();
//		this.adapter = new LabelAdapter(context, this.LabelList);
//		this.m_gridview.setAdapter(this.adapter);

		rootView.findViewById(R.id.fragment_personal_details_nc_ll)
				.setVisibility(View.GONE);
		rootView.findViewById(R.id.fragment_personal_details_name_ll)
				.setVisibility(View.GONE);
//		isInitView=true;
		initViewData();
	}


private void addPopuWindow() {
	List<String> arrayList = null;
	if (arrayList==null) {
		if (Data.myinfo.getGroupid()==1) {//普通会员
			arrayList=Data.filter_putong.getClassname();
		}else if (Data.myinfo.getGroupid()==2){//名人
			arrayList=Data.filter_star.getClassname();
		}
	}
	 final List<String> mList =arrayList;
	popu1 = new NormalPopuWindow(context, arrayList,
			cname_ll);
	popu1.setOnItemClickBack(new NormalPopuWindow.OnItemClickBack() {
		public void OnClick(int position, String text) {
			m_cname.setText(mList
					.get(position));
//			cid = localList.get(position).getId();
			popu1.dismisss();
		}
	});
	popu1.show();
	}
private void addAihaoPopuWindow() {
	final NormalPopuWindow popu = new NormalPopuWindow(context, aihaolist,
			aihao_ll);
	popu.setOnItemClickBack(new NormalPopuWindow.OnItemClickBack() {
		public void OnClick(int position, String text) {
			m_aihao.setText(text);
			popu.dismisss();
		}
	});
	popu.show();
}



	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.fragment_personal_details_sex_ll:// 性别
				SelectorDialog localSelectorDialog = new SelectorDialog(
						context, new SelectorDialog.SexDismissListener() {
							public void finish(String str) {
								m_sex.setText(str);
							}
						});
				localSelectorDialog.setLocation(false);
				localSelectorDialog.show();
				break;
			case R.id.fragment_personal_details_cname_ll://身份
				addPopuWindow();
				break;
			case R.id.fragment_personal_details_aihao_ll://爱好的乐器
				addAihaoPopuWindow();
				break;
			case R.id.fragment_personal_details_birthday_ll:// 生日
				new MyTimePickerDialog(context, m_birthday.getText().toString().trim(),
						new TimePickerDismissCallback() {

							@Override
							public void finish(String dateTime) {
								m_birthday.setText(dateTime);
							}
						}).show();
				break;
			case R.id.fragment_personal_details_city_ll:// 城市
				CitySelectDialog citySelectDialog = new CitySelectDialog(
						context, useMsg.getAddress(), useMsg.getAddress1(),
						useMsg.getAddress2());
				citySelectDialog
						.setonClickSureListener(new OnSelectCityListener() {
							public void ClickSure(String province, String city,
									String district) {
								m_city.setText(province + "," + city + ","
										+ district);
							}
						});
				citySelectDialog.show();
				break;
			case R.id.fragment_personal_details_commit:
					commit();
				break;
			case R.id.frag_personal_details_renzhen:
				// Intent localIntent = new Intent(this.activity,
				// ApplyingAct.class);
				// localIntent.putExtra("nickname",
				// PersonalDetailsAct.nickname);
				// startActivity(localIntent);
				break;
			default:
				break;
			}
		}
	};

	protected void initViewData() {
//		cid = useMsg.getCid();
		m_nickname.setVisibility(View.GONE);
//		m_realname.setVisibility(View.GONE);
//		m_realnames.setVisibility(View.GONE);
//		if (Data.myinfo.getGroupid()==1) {//普通会员
//			m_nickname.setVisibility(View.VISIBLE);
//			m_realnames.setVisibility(View.VISIBLE);
//			m_nickname.setText(useMsg.getUsername());
//			m_realnames.setText(useMsg.getUsername());
//		}else if (Data.myinfo.getGroupid()==2){//达人
//			m_realnames.setVisibility(View.VISIBLE);
			m_realnames.setText(useMsg.getTruename());
//		}
		m_sex.setText(useMsg.getSex());
//		m_mobile.setText(useMsg.getPhone());
//		m_email.setText(useMsg.getEmail());
		m_cname.setText(MessageUtil.getIdentity(useMsg));
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
//				Locale.CHINA);
		String birthday = useMsg.getChusheng();
		if (birthday != null&&!TextUtils.isEmpty(birthday)) {
				m_birthday.setText(birthday);
		}
		m_city.setText(useMsg.getAddress()+ "," + useMsg.getAddress1() + ","
				+ useMsg.getAddress2());
		m_address.setText(useMsg.getAddress());
		m_descr.setText(useMsg.getSaytext());
		m_aihao.setText(useMsg.getAihao());
	}

	/** 获取樂器信息 */
	private void Label() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "extend/getAihao");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
					 aihaolist = JSON.parseArray(
					message.getData(),String.class);
						
//						for (int i = 0; i < mlist.size(); i++) {
//							Label label=new Label();
//							label.setTitle(mlist.get(i));
//							LabelList.add(label);
//						}
//						if (isInitView) {
//						adapter.notifyDataSetChanged();
//						}
//						num++;
						MyProgressDialog.Cancel();;
					}
				}, null));
	}
	/**
	 * 提交
	 * 
	 * @throws ParseException
	 */
	private void commit()  {
		String truename=m_realnames.getText().toString().trim();
		String cname=m_cname.getText().toString().trim();
		String sex=m_sex.getText().toString().trim();
		String birthday = this.m_birthday.getText().toString();
		String address = this.m_address.getText().toString();
		String desc = this.m_descr.getText().toString();
		String aihao = this.m_aihao.getText().toString();
//		if (TextUtils.isEmpty(truename)) {
//			toast(getResources().getString(R.string.请输入真实姓名));
//			return;
//		}
		if (TextUtils.isEmpty(cname)) {
			toast(getResources().getString(R.string.请选择身份));
			return;
		}
		if (TextUtils.isEmpty(sex)) {
			toast(getResources().getString(R.string.请选择性别));
			return;
		}
		String[] arrayOfString = {};
		if (TextUtils.isEmpty(birthday)) {
			toast(getResources().getString(R.string.请选择生日));
			return;
		}
		arrayOfString = this.m_city.getText().toString().trim().split("\\,");
		if ((arrayOfString== null) || (arrayOfString.length != 3)) {
			toast(getResources().getString(R.string.请选择城市));
			return;
		}
		if (TextUtils.isEmpty(address)) {
			toast(getResources().getString(R.string.请填写详细地址));
			return;
		}
//		if (TextUtils.isEmpty(desc)) {
//			toast(getResources().getString(R.string.请输入描述));
//			return;
//		}
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "user/editUserInfo");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
//		 map.put("username", this.m_nickname.getText().toString());
		map.put("username", truename);
		// map.put("email", this.m_email.getText().toString());
		// map.put("label", JSON.toJSON(localArrayList)+"");
		if (Data.myinfo.getGroupid()==1) {
			map.put("putong_shenfen", cname);
		}else if (Data.myinfo.getGroupid()==2) {
			map.put("music_star",cname);
		}
		map.put("aihao",aihao);
		map.put("sex",sex);
		map.put("chusheng", birthday);
		map.put("address", arrayOfString[0]);
		map.put("address1", arrayOfString[1]);
		map.put("address2", arrayOfString[2]);
		 map.put("addres", address);
		map.put("saytext",desc);
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						toast(message.getInfo());
						((BaseActivity) context).finish();
						MyProgressDialog.Cancel();
					}
				}, null));
	}
}
