package com.greattone.greattone.activity.brand;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.personal.ToSignActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyIosDialog;
import com.greattone.greattone.dialog.MyIosDialog.DialogItemClickListener;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.ConnectWay;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.BitmapUtil;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.FileUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.Permission;
import com.greattone.greattone.util.PhotoUtil;
import com.greattone.greattone.widget.MyRoundImageView;

import java.io.File;
import java.util.HashMap;

/**
 * 添加或修改联系方式
 * 
 * @author makaifeng
 *
 */
public class AddConnectWayAct extends BaseActivity {

	private ConnectWay main;
	private RadioGroup radiogroup;

	private EditText et_name, et_address, et_phone, et_email, et_url, et_pic_name;

	int check;

	private ImageView iv_del;

	private RelativeLayout rl_view;

	private ImageView iv_play;

	private MyRoundImageView iv_pic;

	private View ll_url;

	private TextView tv_upload;

	private View ll_pic_name;
	private LinearLayout ll_sign;
	private View scrollView;
	private TextView tv_content;
	private Button btn_sign;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_connect_way);
		main = (ConnectWay) getIntent().getSerializableExtra("main");
		initView();
	}

	private void initView() {
		setHead("添加地址", true, true);

		this.scrollView = findViewById(R.id.scrollView);
		this.ll_sign = ((LinearLayout) findViewById(R.id.ll_sign));
		tv_content=(TextView)findViewById(R.id.tv_content);
		btn_sign=(Button)findViewById(R.id.btn_sign);
		
		this.radiogroup = ((RadioGroup) findViewById(R.id.radiogroup));
		radiogroup.setOnCheckedChangeListener(onCheckedChangeListener);

		this.et_name = ((EditText) findViewById(R.id.et_name));
		this.et_phone = ((EditText) findViewById(R.id.et_phone));
		this.et_email = ((EditText) findViewById(R.id.et_email));
		this.et_url = ((EditText) findViewById(R.id.et_url));
		this.ll_url = findViewById(R.id.ll_url);
		this.et_address = ((EditText) findViewById(R.id.et_address));
		this.et_pic_name = ((EditText) findViewById(R.id.et_pic_name));
		this.ll_pic_name = findViewById(R.id.ll_pic_name);
		this.tv_upload = ((TextView) findViewById(R.id.tv_upload));
		findViewById(R.id.btn_commit).setOnClickListener(lis);

		iv_del = ((ImageView) findViewById(R.id.iv_del));
		rl_view = ((RelativeLayout) findViewById(R.id.rl_view));
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				(screenWidth - DisplayUtil.dip2px(context, 10)) / 4,
				(screenWidth - DisplayUtil.dip2px(context, 10)) / 4);
		rl_view.setLayoutParams(params);
		rl_view.setPadding(DisplayUtil.dip2px(context, 5), DisplayUtil.dip2px(context, 5),
				DisplayUtil.dip2px(context, 5), DisplayUtil.dip2px(context, 5));
		iv_play = ((ImageView) findViewById(R.id.iv_play));
		iv_pic = ((MyRoundImageView) findViewById(R.id.iv_pic));
		iv_pic.setOnClickListener(lis);
		iv_pic.setRadius(15);
		iv_pic.setImageResource(R.drawable.icon_sc);
		iv_del.setVisibility(View.GONE);
		iv_del.setOnClickListener(lis);
		iv_play.setVisibility(View.GONE);

		radiogroup.check(R.id.radioButton1);
		// initViewData();
	}

	protected void initMainData() {
		if (main != null) {
			et_name.setText(main.getTitle());
			et_address.setText(main.getAddress());
			et_phone.setText(main.getPhone());
			et_email.setText(main.getMail());
			et_url.setText(main.getUrl());
			et_pic_name.setText(main.getPhp_name());
			ImageLoaderUtil.getInstance().setImagebyurl(main.getPhoto(), iv_pic);
			picUrl = main.getPhoto();
			iv_del.setVisibility(View.VISIBLE);
		}
		scrollView.setVisibility(View.VISIBLE);
		ll_sign.setVisibility(View.GONE);
	}

	protected void initBranchData() {
		if (Data.myinfo.getCked()==1) {//已认证
			et_name.setText("");
			et_address.setText("");
			et_phone.setText("");
			et_email.setText("");
			et_url.setText("");
			et_pic_name.setText("");
			iv_pic.setImageResource(R.drawable.icon_sc);
			iv_del.setVisibility(View.GONE);
		}else{//未认证
			scrollView.setVisibility(View.GONE);
			ll_sign.setVisibility(View.VISIBLE);
			btn_sign.setOnClickListener(lis);
			tv_content.setText("增加分支功能仅向签约用户开放");
		}
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_sign://签约
				startActivity(new Intent(context,ToSignActivity.class));
				break;
			case R.id.btn_commit:
				commit(check);

				break;
			case R.id.iv_pic:
				MyIosDialog.ShowBottomDialog(context, "", new String[] { "拍照", "相册" }, new DialogItemClickListener() {

					@Override
					public void itemClick(String result, int position) {
						if (result.equals("拍照")) {
							toCamera();
						} else if (result.equals("相册")) {
							toAlbum();
						}
					}
				});
				break;
			case R.id.iv_del://
				iv_del.setVisibility(View.GONE);
				picUrl = null;
				iv_pic.setImageResource(R.drawable.icon_sc);
				break;
			default:
				break;
			}
		}
	};

	OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radioButton1:// 总部管理
				check = 0;
				ll_url.setVisibility(View.VISIBLE);
				ll_pic_name.setVisibility(View.VISIBLE);
				rl_view.setVisibility(View.VISIBLE);
				et_name.setHint("总部名称");
				initMainData();
				break;
			case R.id.radioButton2:// 增加分支
				check = 1;
				et_name.setHint("分支名称");
				ll_url.setVisibility(View.GONE);
				tv_upload.setVisibility(View.GONE);
				ll_pic_name.setVisibility(View.GONE);
				rl_view.setVisibility(View.GONE);
				initBranchData();
				break;

			default:
				break;
			}
		}
	};

	private String imgName;

	protected String picUrl;

	/**
	 * 提交
	 */
	private void commit(int check) {

		String name = this.et_name.getText().toString();
		String phone = this.et_phone.getText().toString();
		String email = this.et_email.getText().toString();
		String url = this.et_url.getText().toString();
		String pic_name = this.et_pic_name.getText().toString();
		String address = this.et_address.getText().toString();
		if (check == 1) {// 增加分支
			if (TextUtils.isEmpty(name)) {
				toast("请填写分支名称");
				return;
			}
		} else {/// 总部管理
			if (TextUtils.isEmpty(name)) {
				toast("请填写总部名称");
				return;
			}
			if (TextUtils.isEmpty(url)) {
				toast("请填写官方网站");
				return;
			}
			if (TextUtils.isEmpty(pic_name)) {
				toast("请填写二维码名称");
				return;
			}
			if (TextUtils.isEmpty(picUrl)) {
				toast("请选择图片");
				return;
			}

		}
		if (TextUtils.isEmpty(phone)) {
			toast("请填写联系电话");
			return;
		}

		if (TextUtils.isEmpty(email)) {
			toast("请填写电子邮箱");
			return;
		}

		if (TextUtils.isEmpty(address)) {
			toast("请填写详细地址");
			return;
		}

		if (check == 1) {// 增加分支
			addOther(name, email, phone, address);
		} else {/// 总部管理
			// if (id==0) {//总部不存在，添加
			addMain(name, address, phone, email, url, pic_name);
			// }else{//总部存在，修改
			// editMain(name,address,phone,email,url,pic_name);
			// }
		}

	}

	//
	// private void editMain(String name, String address, String phone, String
	// email, String url, String pic_name) {
	//
	// }
	/**
	 * 总部管理
	 * 
	 * @param name
	 * @param address
	 * @param phone
	 * @param email
	 * @param url
	 * @param pic_name
	 */
	private void addMain(String name, String address, String phone, String email, String url, String pic_name) {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "brand/add_headquarters");
		map.put("title", name);// 总部名称
		map.put("userid", Data.user.getUserid());// 品牌id
		map.put("php_name", pic_name);// 二维码名称
		map.put("photo", picUrl);// 二维码图片
		map.put("url", url);// 官方网站
		map.put("mail", email);// 电子邮箱
		map.put("phone", phone);// 联系电话
		map.put("address", address);// 详细地址
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map, new ResponseListener() {

			@Override
			public void setResponseHandle(Message2 message) {
				toast(message.getInfo());
				finish();
			}
		}, null));
	}

	/**
	 * 添加分支
	 * 
	 * @param name
	 *            分支名称
	 * @param email
	 *            电子邮箱
	 * @param phone
	 *            联系电话
	 * @param address
	 *            详细地址
	 */
	private void addOther(String name, String email, String phone, String address) {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "brand/add_branch");
		map.put("title", name);// 分支名称
		map.put("userid", Data.user.getUserid());// 品牌id
		map.put("mail", email);// 电子邮箱
		map.put("phone", phone);// 联系电话
		map.put("address", address);// 详细地址
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map, new ResponseListener() {

			@Override
			public void setResponseHandle(Message2 message) {
				toast(message.getInfo());
				finish();
			}
		}, null));

	}

	/***
	 * 去拍照
	 */
	private void toCamera() {
		if (Build.VERSION.SDK_INT >= 23) {
			int checkCallPhonePermission = ContextCompat.checkSelfPermission(context.getApplicationContext(),
					Manifest.permission.CAMERA);
			if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA },
						Permission.REQUEST_CODE_CAMERA);
				toast("无权限使用，请打开权限");
				return;
			}
		}
		imgName = System.currentTimeMillis() + ".png";
		PhotoUtil.setPhotograph(context, new File(FileUtil.getLocalImageUrl(context, imgName)));
	}

	/**
	 * 去相册
	 */
	private void toAlbum() {
		if (Build.VERSION.SDK_INT >= 23) {
			int checkCallPhonePermission = ContextCompat.checkSelfPermission(context.getApplicationContext(),
					Manifest.permission.WRITE_EXTERNAL_STORAGE);
			if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
						Permission.REQUEST_CODE_READ_EXTERNAL_STORAGE);
				toast("无权限使用，请打开权限");
				return;
			}
		}
		PhotoUtil.setalbum(context);
	}

	@SuppressLint("Override")
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == Permission.REQUEST_CODE_READ_EXTERNAL_STORAGE) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				toAlbum();
			} else {
				toast("无法打开相册");
			}
		} else if (requestCode == Permission.REQUEST_CODE_CAMERA) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				toCamera();
			} else {
				toast("无法打开相机");
			}
		}
	}

	/** 发送图片 */
	protected void sendPicture(Bitmap photo) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "extend/upfile");
		map.put("uploadkey", "e7627f53d4712552f8d82c30267d9bb4");
		map.put("uptype", "userpic");
		HashMap<String, byte[]> bytes = new HashMap<String, byte[]>();
		bytes.put("file", BitmapUtil.Bitmap2Bytes(photo));
		HttpUtil.httpConnectionByPostBytes(context, map, bytes, "png", false,

				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						picUrl = JSON.parseObject(message.getData()).getString("url");
					}

				}, null);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == PhotoUtil.PHOTOGRAPH) {// 拍照
				String filePath = FileUtil.getLocalImageFile(context) + "/" + "icon.png";
				// sendPicture();
				File temp = new File(filePath);
				PhotoUtil.startPhotoZoom(context, Uri.fromFile(temp), 1, 1, 200, 200);
			} else if (requestCode == PhotoUtil.ALBUM) {// 相册
				PhotoUtil.startPhotoZoom(context, data.getData(), 1, 1, 200, 200);
				// filePath = BitmapUtil.getFileFromALBUM(context, data);
				// sendPicture();
			} else if (requestCode == PhotoUtil.PHOTO_REQUEST_CUT) {// 裁剪
				Bundle extras = data.getExtras();
				if (extras != null) {
					Bitmap photo = extras.getParcelable("data");
					sendPicture(photo);
					iv_pic.setImageBitmap(photo);
					iv_del.setVisibility(View.VISIBLE);
				}
			}
		}
	}
}
