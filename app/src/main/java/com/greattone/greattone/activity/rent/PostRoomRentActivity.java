package com.greattone.greattone.activity.rent;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.PostGridAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Lease;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Picture;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.MyGridView;
import com.kf_test.picselect.GalleryActivity;

/** 发布租赁 */
public class PostRoomRentActivity extends BaseActivity {
	ArrayList<String> pictureUrlList = new ArrayList<String>();
	ArrayList<Picture> pictureFileList = new ArrayList<Picture>();
	Lease lease;
	private EditText et_name;
	private EditText et_price;
//	private EditText et_linkman;
	private EditText et_phone;
//	private TextView et_city;
	private EditText et_peoplenum;
	private EditText et_content;
//	private EditText et_address;
	int type=GalleryActivity.TYPE_PICTURE;
	private MyGridView gv_pic;
	private PostGridAdapter adapter;
	private String id;
	private String filepass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_room);
		initView();
		id = getIntent().getStringExtra("id");
		if (id != null) {
			getData();
		}
	}

	private void initView() {
		setHead(getResources().getString(R.string.发布租赁), true, true);

		this.et_name = ((EditText) findViewById(R.id.et_name));
		this.et_price = ((EditText) findViewById(R.id.et_price));
//		this.et_linkman = ((EditText) findViewById(R.id.et_linkman));
		this.et_phone = ((EditText) findViewById(R.id.et_phone));
//		this.et_city = ((TextView) findViewById(R.id.et_city));
		this.et_peoplenum = ((EditText) findViewById(R.id.et_peoplenum));
		findViewById(R.id.ll_city).setOnClickListener(lis);
//		this.et_address = ((EditText) findViewById(R.id.et_address));
		this.et_content = ((EditText) findViewById(R.id.et_content));
		gv_pic = (MyGridView) findViewById(R.id.gv_pic);
		adapter=new PostGridAdapter(context, type,1);
//		adapter.setOnItemDel(itemClickListener);
		gv_pic.setAdapter(adapter);
		findViewById(R.id.btn_commit).setOnClickListener(lis);
	}
//	//图片删除按钮
//	OnBtnItemClickListener itemClickListener=new OnBtnItemClickListener() {
//		
//		@Override
//		public void onItemClick(View v, int position) {
//			pictureFileList.remove(position);
//			adapter.setList(pictureFileList);
//		}
//	};
	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_commit:// 提交
				commit();
				break;

			default:
				break;
			}
		}
	};

	private void getData() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "info/detail");
		map.put("id", getIntent().getStringExtra("id"));
		map.put("classid", getIntent().getStringExtra("classid"));
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& message.getData().startsWith("{")) {
							lease = JSON.parseObject(
									JSON.parseObject(message.getData())
											.getString("content"), Lease.class);
							initViewData();
						}
						MyProgressDialog.Cancel();
					}

				}, null));
	}

	protected void commit() {
		final String title = et_name.getText().toString().trim();
		final String price = et_price.getText().toString().trim();
		final String content = et_content.getText().toString().trim();
		final String phone = et_phone.getText().toString().trim();
		final String peoplenum = et_peoplenum.getText().toString().trim();
		filepass = System.currentTimeMillis() + "";
	 pictureFileList = adapter.getList();
		if (pictureFileList.size() == 0) {
			if (id==null) {
				toast(getResources().getString(R.string.请选择图片));
				return;
			}else {
				if (lease.getTitlepic()==null||lease.getTitlepic().length()==0) {
					toast(getResources().getString(R.string.请选择图片));
					return;
				}
			}
		}
		if (title.isEmpty()) {
			toast(getResources().getString(R.string.请输入名称));
			return;
		}
		if (price.isEmpty()) {
			toast(getResources().getString(R.string.请输入价格));
			return;
		}
		if (phone.isEmpty()) {
			toast("请输入联系电话");
			return;
		}
		if (peoplenum.isEmpty()) {
			toast("请输入人数上限");
			return;
		}
		if (pictureFileList.size() == 0) {
			pictureUrlList.add(lease.getTitlepic());
			post1(title, price,content,phone, peoplenum,true);
		} else {
			post(title, price,content,phone,peoplenum);
		}
	}
	// 发送图片
	private void post(final String title, final String price,final String content,final String phone,final String peoplenum) {
		MyProgressDialog.show(context);
		for (int i = 0; i < pictureFileList.size(); i++) {
			HttpProxyUtil.updatePictureByCompress2(context, filepass, getIntent().getStringExtra("classid"), pictureFileList.get(i).getPicUrl(),	new ResponseListener() {

				@Override
				public void setResponseHandle(Message2 message) {
							String picUrl = JSON.parseObject(message.getData())
									.getString("url");
							pictureUrlList.add(picUrl);
							if (pictureUrlList.size() == pictureFileList.size()) {
								post1(title, price, content,phone,peoplenum,false);

							}
						}
					}, null);
		}
	}
	// 提交
	protected void post1(String title, String price, String content,String phone,String peoplenum, boolean showDialog) {
		if (showDialog) {
			MyProgressDialog.show(context);
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "post/ecms");
		if (id!=null) {
			map.put("enews", "MEditInfo");
			map.put("id", id);
		}else {
			map.put("enews", "MAddInfo");
			map.put("filepass", filepass);
		}
		map.put("mid", 15+"");
		map.put("title",title);
		map.put("price", price);
		map.put("pbrand", phone);
		map.put("pmaxnum", peoplenum);
		map.put("price", price);
		map.put("titlepic", pictureUrlList.get(0));
		map.put("intro", content);//简介
		map.put("classid", getIntent().getStringExtra("classid"));
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {
			
			@Override
			public void setResponseHandle(Message2 message) {
				toast(message.getInfo());
				finish();
				setResult(RESULT_OK);
				MyProgressDialog.Cancel();
			}
		}, null));;
	}

	private void initViewData() {
		et_name.setText(lease.getTitle());
		et_price.setText( lease.getPrice());
		// et_linkman.setText(lease.getUsername());
		 et_phone.setText(lease.getPbrand());
		// et_address.setText(lease.getAddress());
		 et_content.setText(lease.getIntro());
		 et_peoplenum.setText(lease.getPmaxnum()+ "人");
		// et_city.setText(lease.getProvince() + "," + lease.getCity() + ","
		// + lease.getArea());
		ArrayList<Picture> fileList = new ArrayList<Picture>();
		Picture picture=new Picture();
		picture.setPicUrl(lease.getTitlepic());
		picture.setType(1);
		fileList.add(picture);
		 adapter.setList2(fileList);
		// Bitmap localBitmap =
		// ImageLoaderUtil.getInstance().getBitMap(room.getPic());
		// if (localBitmap != null)
		// file = FileUtil.saveBitmap(self, localBitmap);
	}

//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (resultCode==RESULT_OK&&requestCode==1) {//图片
//			 ArrayList<String>  mList=data.getStringArrayListExtra(Constants.EXTRA_PHOTO_PATHS);
//		 pictureFileList.addAll(mList);
//		 adapter.setList(pictureFileList);
//		}else if (resultCode==RESULT_OK&&requestCode==0) {//拍照片
//			ArrayList<String>  mList=new ArrayList<String>();
//			mList.add(FileUtil.getLocalImageUrl(context,  "icon.png"));
//			pictureFileList.addAll(mList);
//			adapter.setList(pictureFileList);
//		}
//	}
}
