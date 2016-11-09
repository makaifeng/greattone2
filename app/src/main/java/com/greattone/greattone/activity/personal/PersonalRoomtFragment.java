package com.greattone.greattone.activity.personal;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.Listener.OnSelectCityListener;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.adapter.PostGridAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.CitySelectDialog;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Picture;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.BitmapUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.MessageUtil;
import com.greattone.greattone.widget.MyGridView;
import com.kf_test.picselect.GalleryActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** 教室信息编辑 */
public class PersonalRoomtFragment extends BaseFragment {
	/** fragment 主布局 */
	private View rootView;
	// private PictureAdapter adapter;
	private View cname_ll;
//	private List<File> files = new ArrayList<File>();
//	private List<String> files_path = new ArrayList<String>();
//	private List<File> life_pic = new ArrayList<File>();
	private EditText m_address;
	private TextView m_city;
	private TextView m_cname;
	private EditText m_descr;
	private EditText m_email;
	private EditText m_manager;
	private TextView m_mobile;
	private TextView m_realname;
	private EditText m_roomsrarea;
	private EditText m_tel;
	// private HorizontalListView pic_lv;
	private NormalPopuWindow popu;
//	private View view;
	protected UserInfo useMsg;
//	private TextView m_sex;
//	private LabelAdapter adapter;
	int type=GalleryActivity.TYPE_PICTURE;
	private MyGridView gv_pic;
	private PostGridAdapter adapter;
	private ArrayList<Picture> pictureFileList=new ArrayList<Picture>();
//	private EditText m_id;
	private String photo="";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.rootView = inflater.inflate(R.layout.fragment_room_fragment,
				container, false);
		initView();
		return rootView;
	}

	private void initView() {
//		this.rootView.findViewById(R.id.fragment_personal_teacher_sex_ll)
//				.setOnClickListener(lis);
//		this.m_sex = ((TextView) this.rootView
//				.findViewById(R.id.fragment_personal_teacher_sex));
		this.m_realname = ((TextView) this.rootView
				.findViewById(R.id.fra_room_realname));
		this.m_manager = ((EditText) this.rootView
				.findViewById(R.id.fra_room_manager));
//		m_manager.setVisibility(View.GONE);
		this.m_mobile = ((TextView) this.rootView
				.findViewById(R.id.fra_room_mobile));
		//营业电话
		this.m_tel = ((EditText) this.rootView
				.findViewById(R.id.fra_room_tel));
		//邮箱
		this.m_email = ((EditText) this.rootView
				.findViewById(R.id.fra_room_email));
		//营业面积
		this.m_roomsrarea = ((EditText) this.rootView
				.findViewById(R.id.fra_room_roomsArea));
		//城市
		this.m_city = ((TextView) this.rootView
				.findViewById(R.id.fra_room_city));
		this.m_city.setOnClickListener(lis);
		this.m_address = ((EditText) this.rootView
				.findViewById(R.id.fra_room_address));
		//证件号码
//		this.m_id= ((EditText) this.rootView
//				.findViewById(R.id.fra_room_id));
		//描述
		this.m_descr = ((EditText) this.rootView
				.findViewById(R.id.fra_room_descr));
		this.rootView.findViewById(R.id.fragment_personal_room_uploadtext)
				.setOnClickListener(lis);
		this.cname_ll = this.rootView.findViewById(R.id.fra_room_cname_ll);
		cname_ll.setOnClickListener(lis);
		//类型
		this.m_cname = ((TextView) this.rootView
				.findViewById(R.id.fra_room_cname));
		this.rootView.findViewById(R.id.fragment_personal_room_commit)
				.setOnClickListener(lis);
		gv_pic = (MyGridView)rootView. findViewById(R.id.gv_content);
		if (Data.myinfo.getCked()==1) {
			adapter=new PostGridAdapter(context, type,8);
//		adapter.setOnItemDel(itemClickListener);
			gv_pic.setAdapter(adapter);
			
		}else{
			adapter=new PostGridAdapter(context, type,4);
			gv_pic.setAdapter(adapter);
		}
		
		initViewData();
	}



	private void addPopuWindow() {
		List<String> arrayList=Data.filter_classroom.getClassname();
		 final List<String> mList =arrayList;
		popu = new NormalPopuWindow(context, arrayList,
				cname_ll);
		popu.setOnItemClickBack(new NormalPopuWindow.OnItemClickBack() {
			public void OnClick(int position, String text) {
				m_cname.setText(mList
						.get(position));
//				cid = localList.get(position).getId();
				popu.dismisss();
			}
		});
		popu.show();
		}
	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
//			case R.id.fragment_personal_teacher_sex_ll:
//				new SelectorDialog(context,
//						new SelectorDialog.SexDismissListener() {
//							public void finish(String text) {
//								m_sex.setText(text);
//							}
//						}).show();
//				return;
//			case 2131231888:
//				// this.ispic = 0;
//				// BitmapUtil.chosepicture(this.activity);
//				return;
//			case 2131231801:
//				// this.ispic = 1;
//				// BitmapUtil.chosepicture(this.activity);
//				return;
			case R.id.fra_room_city://城市
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
				return;
			case R.id.fra_room_cname_ll://身份
				addPopuWindow();
			
				break;
			case R.id.fragment_personal_room_commit://提交
					commit();
				break;
			default:
				return;
			}
		}
	};
	
	protected void initViewData() {
		useMsg=Data.myinfo;
		m_realname.setText(useMsg.getUsername());
		m_manager.setText(useMsg.getFuzeren());
		m_mobile.setText(useMsg.getPhone());
		m_email.setText(useMsg.getEmail());
		m_roomsrarea.setText(useMsg.getMeasure());
		m_tel.setText(useMsg.getTelephone());
//		m_id.setText(useMsg.getShenfenzheng());
		m_cname.setText(MessageUtil.getIdentity(useMsg));
		if (!useMsg.getAddress().equals(""))
			m_city.setText(useMsg.getAddress() + "," + useMsg.getAddress1() + ","
					+ useMsg.getAddress2());
		m_address.setText(useMsg.getAddres());
		m_descr.setText(useMsg.getSaytext());
		if (Data.myinfo.getCked()==1) {
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
			adapter.setList2(pictureFileList);
		}

	}
	/**
	 * 提交
	 * 
	 * @throws ParseException
	 */
	private void commit()  {
		String[] arrayOfString = {};
//		String str = this.m_birthday.getText().toString();
			arrayOfString = this.m_city.getText().toString().split("\\,");
			if ((arrayOfString== null) || (arrayOfString.length != 3)) {
				toast(getResources().getString(R.string.请选择城市));
				return;
			}
			MyProgressDialog.show(context);
			if (Data.myinfo.getCked()==1) {
				pictureFileList = adapter.getList();
				photo=adapter.getUrlList();
			}
			if ( pictureFileList.size()==0) {
				post1(photo,arrayOfString);
			}else {
				post(arrayOfString);
			}
	
	}
	// 发送图片
	private void post(final String[] arrayOfString) {
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
								post1(photo,arrayOfString);

							}
						}
					}, null);
		}
		}
	protected void post1(String photo, String[] arrayOfString) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "user/editUserInfo");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		// map.put("nickname", this.m_nickname.getText().toString());
		 map.put("fuzeren", this.m_manager.getText().toString());
		map.put("truename", this.m_realname.getText().toString());
		// map.put("email", this.m_email.getText().toString());
		 map.put("telephone", this.m_tel.getText().toString().trim());
		 map.put("measure", this.m_roomsrarea.getText().toString().trim());
//		 map.put("shenfenzheng", this.m_id.getText().toString().trim());
		// map.put("label", JSON.toJSON(localArrayList)+"");
		map.put("classroom_type", m_cname.getText().toString());
//		map.put("sex", this.m_sex.getText().toString());
//		map.put("chusheng", str);
		map.put("address", arrayOfString[0]);
		map.put("address1", arrayOfString[1]);
		map.put("address2", arrayOfString[2]);
		map.put("addres",  this.m_address.getText().toString().trim());
		map.put("photo", photo);
		// map.put("address", this.m_address.getText().toString());
		map.put("saytext", this.m_descr.getText().toString());
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