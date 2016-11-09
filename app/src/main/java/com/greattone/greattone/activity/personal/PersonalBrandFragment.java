package com.greattone.greattone.activity.personal;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.Listener.TimePickerDismissCallback;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.adapter.LabelAdapter;
import com.greattone.greattone.adapter.PostGridAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.MyTimePickerDialog;
import com.greattone.greattone.entity.Label;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Picture;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.BitmapUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.MyGridView;
import com.kf_test.picselect.GalleryActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

public class PersonalBrandFragment extends BaseFragment {
	/** fragment 主布局 */
	private View rootView;
	List<Label> LabelList ;
	String url;
	String editurl;
	private TextView m_name;
	private EditText m_mobile;
	private EditText m_company;
	private EditText m_address;
	private EditText m_descr;
	protected UserInfo useMsg=Data.myinfo;
	private boolean isInitView;
	private LabelAdapter adapter;
	private GridView m_gridview;
	private TextView m_year;
	private MyGridView gv_pic;
	private PostGridAdapter picAdapter;
@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	Label();
//	getCname();
}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.rootView = inflater.inflate(R.layout.fragment_personal_brand,
				container, false);
		initView();
		return rootView;
	}

	private void initView() {
		this.m_name = ((TextView) rootView
				.findViewById(R.id.tv_name));
		this.m_company = ((EditText) rootView
				.findViewById(R.id.et_company));
		this.m_mobile = ((EditText) rootView
				.findViewById(R.id.et_mobile));
		this.m_year= ((TextView) rootView
				.findViewById(R.id.tv_year));
		  rootView
				.findViewById(R.id.ll_year).setOnClickListener(lis);;
		this.m_address = ((EditText) rootView
				.findViewById(R.id.et_address));
		this.m_descr = ((EditText) rootView
				.findViewById(R.id.et_descr));
		rootView.findViewById(R.id.btn_commit)
				.setOnClickListener(lis);
		this.m_gridview = ((GridView) rootView
				.findViewById(R.id.gv_content));
	

		this.LabelList = new ArrayList<Label>();
		adapter = new LabelAdapter(context, LabelList);
		m_gridview.setAdapter(adapter);
		gv_pic = (MyGridView)rootView. findViewById(R.id.gv_pic);
		if (Data.myinfo.getCked()==1) {//已认证
			picAdapter=new PostGridAdapter(context, GalleryActivity.TYPE_PICTURE,10);
		}else{//未认证
			picAdapter=new PostGridAdapter(context, GalleryActivity.TYPE_PICTURE,1);
		}
//		adapter.setOnItemDel(itemClickListener);
		gv_pic.setAdapter(picAdapter);
		isInitView=true;
		initViewData();
	}



	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_commit:
					commit();

				break;
			case R.id.ll_year:
				new MyTimePickerDialog(context, "",
						new TimePickerDismissCallback() {

							@Override
							public void finish(String dateTime) {
								String s[]=dateTime.split("\\-");
								m_year.setText(s[0]);
							}
						}).show();
				break;
			default:
				break;
			}
		}
	};
	private String photo;
	private ArrayList<Picture> pictureFileList=new ArrayList<Picture>();

	protected void initViewData() {
		m_name.setText(useMsg.getUsername());
		m_company.setText(useMsg.getCompany());
		m_mobile.setText(useMsg.getTelephone());
		m_year.setText(useMsg.getChusheng());
		m_address.setText(useMsg.getAddres());
		m_descr.setText(useMsg.getSaytext());
		
		String[] s=useMsg.getPhoto().split("\\::::::");
		pictureFileList.clear();
		for (String string : s) {
			if (!TextUtils.isEmpty(string)) {
				Picture picture=new Picture();
				picture.setPicUrl(string);
				picture.setType(1);
				pictureFileList.add(picture);
			}
		}
		picAdapter.setList2(pictureFileList);
	}

	/** 获取樂器信息 */
	private void Label() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "extend/brandType");
		map.put("field", "product");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						LabelList=new ArrayList<Label>();
						String  s[]=	message.getData().split("\\|");
						String  s2[]=	useMsg.getAihao().split("\\|");
						for (String string : s) {
							if (!TextUtils.isEmpty(string)) {
								Label label=new Label();
								label.setTitle(string);
								for (String string2 : s2) {
									if (string.equals(string2)) {
										label.setIscheck(true);
										break;
									}
								}
								LabelList.add(label);
							}
						}
						if (isInitView) {
							adapter = new LabelAdapter(context, LabelList);
							m_gridview.setAdapter(adapter);
						}
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
		String company = this.m_company.getText().toString();
		String mobile = this.m_mobile.getText().toString();
		String year = this.m_year.getText().toString();
		String address = this.m_address.getText().toString();
		String desc = this.m_descr.getText().toString();
		if (TextUtils.isEmpty(company)) {
			toast("请填写创建国家");
			return;
		}
		if (TextUtils.isEmpty(mobile)) {
			toast("请填写联系电话");
			return;
		}
		if (TextUtils.isEmpty(year)) {
			toast("请选择创建年份");
			return;
		}
		List<Label> mlist = adapter.getList();
		String aihao="|";
		for (Label label : mlist) {
			if (label.isIscheck()) {
				aihao=aihao+label.getTitle()+"|";
			}
		}
		if (mlist==null||mlist.size()==0) {
			toast("请选择创建年份");
			return;
		}
		if (TextUtils.isEmpty(address)) {
			toast(getResources().getString(R.string.请填写详细地址));
			return;
		}
		if (TextUtils.isEmpty(desc)) {
			toast("请填写品牌简介");
			return;
		}
		MyProgressDialog.show(context);
		 pictureFileList = picAdapter.getList();
		 photo=picAdapter.getUrlList();
			if ( pictureFileList.size()==0) {
				post1(photo,address,mobile,year,company,desc,aihao);
			}else {
				post(address,mobile,year,company,desc,aihao);
			}
		
	}
	// 发送图片
	private void post(final String address, final String mobile,final String year,final String company,final String desc,final String aihao) {

		for (int i = 0; i < pictureFileList.size(); i++) {
			final int num=i;
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("api", "post/upfile");
			map.put("uploadkey", "e7627f53d4712552f8d82c30267d9bb4");
			map.put("uptype", "photos");
			map.put("loginuid", Data.user.getUserid());
			map.put("logintoken", Data.user.getToken());
			HashMap<String, byte[]> bytes = new HashMap<String, byte[]>();
			bytes.put("file", BitmapUtil.getBytesFromFile(pictureFileList.get(num).getPicUrl()));
			String[] suffix=pictureFileList.get(num).getPicUrl().split("\\.");
			HttpUtil.httpConnectionByPostBytes(context, map,bytes,suffix[suffix.length - 1],true,
					new ResponseListener() {

						@Override
						public void setResponseHandle(Message2 message) {
							String picUrl = JSON.parseObject(message.getData())
									.getString("url");
							photo=photo+picUrl+"::::::";
							if (num+1== pictureFileList.size()) {
								photo.substring(0, photo.length()-6);
								post1(photo,address,mobile,year,company,desc,aihao);
							}
						}
					}, null);
		}
		
	}
	private void post1(String photo2, String address, String mobile, String year, String company, String desc,
			String aihao) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "user/editUserInfo");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		 map.put("addres", address);
		map.put("telephone",mobile);
		map.put("chusheng",year);
		map.put("company",company);
		map.put("saytext",desc);
		map.put("aihao",aihao);
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
