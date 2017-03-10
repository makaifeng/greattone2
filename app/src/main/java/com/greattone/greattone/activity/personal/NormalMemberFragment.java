package com.greattone.greattone.activity.personal;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.android.volley.VolleyError;
import com.greattone.greattone.Listener.OnSelectCityListener;
import com.greattone.greattone.Listener.UpdateListener;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.dialog.CitySelectDialog;
import com.greattone.greattone.dialog.MyIosDialog;
import com.greattone.greattone.dialog.MyIosDialog.DialogItemClickListener;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.entity.LoginInfo;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.BitmapUtil;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.FileUtil;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.Permission;
import com.greattone.greattone.util.PhotoUtil;
import com.greattone.greattone.util.UpdateObjectToOSSUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 注册
 */
@SuppressWarnings("deprecation")
public class NormalMemberFragment extends BaseFragment {
	boolean canreg1,canreg2,canreg3,canreg4,canreg5;
	int focus=0;//焦点在哪个按钮上面
	String showtoast1,showtoast2,showtoast3,showtoast4,showtoast5;
	/**
	 * fragment 主布局
	 */
	private View rootView;

	String url;
	String filePath;
	String province;
	String city;
	String district;
	int type = 1;
	/** 名字 */
	private EditText et_name;
	/** 密码 */
	private EditText et_password;
	/** 确认密码 */
	private EditText et_double_password;
//	/***/
//	private RadioGroup radiogroup;
//	/** 手机界面 */
//	private View ll_phone;
	/** 区号 */
	private TextView tv_phone_district_num;
	private TextView tv_phone_district;
	/** 手机号 */
	private LinearLayout ll_phone_number;
	private EditText et_phone_num;
	/** 验证码 */
	private EditText et_code;
	/** 获取验证码 */
	private TextView sendcode;
	/** 确认 */
	private TextView tv_sure;
	/** 头像 */
	private ImageView iv_icon;
	/** 选择地址 */
	private TextView tv_address;
	protected LoginInfo user;
	private CountDownTimer downTimer;
	String	username;
	String	password;
	private int groupid;
	private String code_num;
//	private View ll_code;
	String imgName="icon.png";
	private HashMap<String, String> map;
	private ProgressDialog pd;
boolean isCommit;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_register, container,
				false);// 关联布局文件
		// screenWidth = ((BaseActivity) getActivity()).screenWidth;
		initView();
		return rootView;
	}

	/**
	 * 加载视图
	 */
	private void initView() {
		iv_icon = (ImageView) rootView.findViewById(R.id.iv_icon);
		iv_icon.setOnClickListener(lis);
		et_name = (EditText) rootView.findViewById(R.id.et_name);
		et_name.setOnFocusChangeListener(onFocusChangeListener);
		et_password = (EditText) rootView.findViewById(R.id.et_password);
		et_password.setOnFocusChangeListener(onFocusChangeListener);
		et_double_password = (EditText) rootView
				.findViewById(R.id.et_double_password);
		et_double_password.setOnFocusChangeListener(onFocusChangeListener);
//		ll_phone = rootView.findViewById(R.id.ll_phone);
		tv_phone_district_num = (TextView) rootView
				.findViewById(R.id.tv_phone_district_num);
		tv_phone_district = (TextView) rootView.findViewById(R.id.tv_phone_district);
//		tv_phone_district_num.setText("+86");
		tv_phone_district.setOnClickListener(lis);
		et_phone_num = (EditText) rootView.findViewById(R.id.et_phone_num);

		ll_phone_number = (LinearLayout) rootView.findViewById(R.id.ll_phone_number);

		et_phone_num.setOnFocusChangeListener(onFocusChangeListener);
//		ll_code =  rootView.findViewById(R.id.ll_code);
		et_code = (EditText) rootView.findViewById(R.id.et_code);
		et_code.setOnFocusChangeListener(onFocusChangeListener);
		sendcode = (TextView) rootView.findViewById(R.id.tv_get_code);
		sendcode.setOnClickListener(lis);
		tv_address = (TextView) rootView.findViewById(R.id.tv_address);
		tv_address.setOnClickListener(lis);

//		radiogroup = (RadioGroup) rootView.findViewById(R.id.radiogroup);
//		radiogroup.check(R.id.radioButton1);
//		radiogroup.setOnCheckedChangeListener(listener);

		tv_sure = (TextView) rootView.findViewById(R.id.tv_sure);
		tv_sure.setOnClickListener(lis);
		initGroupView();
	}

	/** 设置身份类型 */
	public void setGroupId(int groupid) {
		this.groupid = groupid;
//		initGroupView();
	}

	/** 加载各种身份的界面 */
	private void initGroupView() {
		et_name.setText("");
		et_password.setText("");
		et_double_password.setText("");
		et_phone_num.setText("");
		et_code.setText("");
		tv_address.setVisibility(View.VISIBLE);
		if (groupid == 4) {// 音乐教室
//			ll_phone.setVisibility(View.GONE);
//			ll_code.setVisibility(View.GONE);
			et_name.setHint(getResources().getString(R.string.机构名称));
		} else if (groupid == 5) {
			et_name.setHint("品牌名");
			tv_address.setVisibility(View.GONE);
		} else {
//			ll_code.setVisibility(View.VISIBLE);
//			ll_phone.setVisibility(View.VISIBLE);
			et_name.setHint(getResources().getString(R.string.真实姓名));
			if (groupid == 1) {// 普通会员
			} else if (groupid == 2) {// 音乐之星
			} else if (groupid == 3) {// 音乐老师
			}

		}
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_get_code:// 获取验证码
				getCode();
				break;
			case R.id.tv_sure:// 确认
				isCommit=true;
				if (focus==0){//当所有edittext都没获取焦点时，直接提交
					commit();
				}else {//清楚焦点
					focus = 0;
					clearFocus();
				}
				break;
			case R.id.iv_icon:// 头像
				MyIosDialog.ShowBottomDialog(context, "", new String[] { getResources().getString(R.string.拍照),
						getResources().getString(R.string.相册) }, new DialogItemClickListener() {

					@Override
					public void itemClick(String result, int position) {
						if (result.equals( getResources().getString(R.string.拍照))) {
							toCamera();
						
						} else if (result.equals(getResources().getString(R.string.相册))) {
							toAlbum();
						}
					}
				});

				break;
			case R.id.tv_address:// 地址
				CitySelectDialog citySelectDialog = new CitySelectDialog(
						context);
				citySelectDialog
						.setonClickSureListener(new OnSelectCityListener() {
							public void ClickSure(String province, String city,
									String district) {
								tv_address.setText(province + "," + city + ","
										+ district);
								NormalMemberFragment.this.province = province;
								NormalMemberFragment.this.city = city;
								NormalMemberFragment.this.district = district;
							}
						});
				citySelectDialog.show();
				break;
			case R.id.tv_phone_district:// 区号
				List<String> mlist=new  ArrayList<String>();
				final String[] code=context.getResources().getStringArray(R.array.AREA_CODE);
				final String[] codes=context.getResources().getStringArray(R.array.area_codes);
				for(String str:code){
					mlist.add(str);
				}
				 final NormalPopuWindow		popu1 = new NormalPopuWindow(context, mlist,
						 tv_phone_district);
					popu1.setOnItemClickBack(new NormalPopuWindow.OnItemClickBack() {
					

						public void OnClick(int position, String text) {
							code_num=codes[position];
							tv_phone_district_num.setText(code_num);
							tv_phone_district_num.setVisibility(View.VISIBLE);
							tv_phone_district.setText(text);
							tv_phone_district.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_border));
							popu1.dismisss();
							checkphone();
						}
					});
					 popu1.show();
				break;

			default:
				break;
			}
		}
	};
	View.OnFocusChangeListener onFocusChangeListener=new View.OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {

			if (hasFocus==false){
				switch (v.getId()){
					case R.id.et_name://姓名
						focus=1;
						checkname();
						break;
					case R.id.et_password://密码
						focus=2;
						checkpassword();
						break;
					case R.id.et_double_password://二次密码
						focus=3;
						checkdoublepassword();
						break;
					case R.id.et_phone_num://手机
						focus=4;
						checkphone();
						break;
					case R.id.et_code://验证码
						checkcode();
						break;
					default:
						break;
				}
			}else {focus=v==et_name?1:v==et_password?2:v==et_double_password?3:v==et_phone_num?4:v==et_code?5:0;}
		}
	};

	/**检查验证码*/
	private void checkcode() {
		String phone=et_phone_num.getText().toString().trim();
		String code=et_code.getText().toString().trim();
		if (code.length()>0) {
			HttpProxyUtil.checkcode(context, phone, code, new ResponseListener() {
				@Override
				public void setResponseHandle(Message2 message) {
					canreg5=true;
					showPrompt(et_code,true,et_code);
				}
			}, new HttpUtil.ErrorResponseListener() {
				@Override
				public void setServerErrorResponseHandle(Message2 message) {
					canreg5=false;
					showtoast5=message.getInfo();
					showPrompt(et_code,false,et_code);
				}

				@Override
				public void setErrorResponseHandle(VolleyError error) {

				}
			});
		}else {
			canreg5=false;
			showtoast5="验证码不能为空";
			showPrompt(et_code,false,et_code);	}
	}

	/**检查手机号*/
	private void checkphone() {
		String phone=et_phone_num.getText().toString().trim();
//		if (code_num==null){
//			showPrompt(et_phone_num,false,ll_phone_number);
//			toast("请选择区域");
//			showtoast4="请选择区域";
//			canreg4=false;
//			return;
//		}
		if (phone.length()>6) {
			HttpProxyUtil.checkphone(context, phone, new ResponseListener() {
				@Override
				public void setResponseHandle(Message2 message) {
					canreg4=true;
					showPrompt(et_phone_num,true,ll_phone_number);
				}
			}, new HttpUtil.ErrorResponseListener() {
				@Override
				public void setServerErrorResponseHandle(Message2 message) {
					canreg4=false;
					showtoast4=message.getInfo();
					showPrompt(et_phone_num,false,ll_phone_number);
				}

				@Override
				public void setErrorResponseHandle(VolleyError error) {

				}
			});
		}else {
			showtoast4="手机号过短";
			canreg4=false;
			showPrompt(et_phone_num, false,ll_phone_number);
		}
	}
/**	清楚焦点*/
	private void clearFocus(){

			et_code.clearFocus();
			et_name.clearFocus();
			et_password.clearFocus();
			et_double_password.clearFocus();
			et_phone_num.clearFocus();
	}
	/**检查二次密码*/
	private void checkdoublepassword() {
		String password=et_password.getText().toString().trim();
		String password2=et_double_password.getText().toString().trim();
		if (password2.length()>=6) {
			if (password.equals(password2)) {
				canreg3=true;
				showPrompt(et_double_password, true,et_double_password);
			} else {
				toast("两次密码不一样");
				showtoast3="两次密码不一样";
				canreg3=false;
				showPrompt(et_double_password, false,et_double_password);
			}
		}else{
			toast("密码长度不得小于6位");
			showtoast3="密码长度不得小于6位";
			canreg3=false;
			showPrompt(et_double_password, false,et_double_password);
		}
	}


	/**检查密码*/
	private void checkpassword() {
		String password=et_password.getText().toString().trim();
		String password2=et_double_password.getText().toString().trim();
		if (password.length()>=6){
			if (password.equals(password2)) {//两次密码一样
				et_double_password.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_border));
			}
			canreg2=true;
			showPrompt(et_password,true,et_password);
		}else {
			toast("密码长度不得小于6位");
			showtoast2="密码长度不得小于6位";
			canreg2=false;
			showPrompt(et_password,false,et_password);
		}
	}
	/**检查用户名*/
	private void checkname() {
		String name=et_name.getText().toString().trim();
		if (name.length()>0) {
			HttpProxyUtil.checkuser(context, name, new ResponseListener() {
				@Override
				public void setResponseHandle(Message2 message) {
					canreg1=true;
					showPrompt(et_name, true,et_name);
				}
			}, new HttpUtil.ErrorResponseListener() {
				@Override
				public void setServerErrorResponseHandle(Message2 message) {
					showtoast1=message.getInfo();
					canreg1=false;
					showPrompt(et_name, false,et_name);
				}

				@Override
				public void setErrorResponseHandle(VolleyError error) {

				}
			});
		}else {
			showtoast1="名称不能为空";
			canreg1=false;
			showPrompt(et_name, false,et_name);


		}
	}

	/**
	 * 显示提示图标
	 * @param editview
	 * @param istrue
     */
	private void showPrompt(EditText editview,boolean istrue,View viewOfChangeBackgroud){
		Drawable drawable;
		if (istrue){
			drawable= getResources().getDrawable(R.drawable.duihaode);
			viewOfChangeBackgroud.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_border));
		}		else {
			drawable= getResources().getDrawable(R.drawable.chahaoyuan);
			viewOfChangeBackgroud.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_border_error));
		}
		drawable.setBounds(1,1, DisplayUtil.dip2px(context,20),DisplayUtil.dip2px(context,20));
		editview.setCompoundDrawables(null,null,drawable,null);
		if (isCommit) {isCommit=false;
			commit();};
	}
	private Bitmap bitmap;
	/***
	 * 去拍照
	 */
	private void toCamera() {
		if (Build.VERSION.SDK_INT >= 23) {
			int checkCallPhonePermission = ContextCompat.checkSelfPermission(
				getContext().	getApplicationContext(),
					Manifest.permission.CAMERA);
			if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat
						.requestPermissions(
								(Activity) getContext(),
								new String[] { Manifest.permission.CAMERA },
								Permission. REQUEST_CODE_CAMERA);
				toast("无权限使用，请打开权限");
				return;
			}			
		}
		imgName=System.currentTimeMillis()+".png";
		PhotoUtil.setPhotograph(
				context,
				new File(FileUtil.getLocalImageUrl(context,
						imgName)));
	}
	/**
	 * 去相册
	 */
	private void toAlbum() {
		if (Build.VERSION.SDK_INT >= 23) {
			int checkCallPhonePermission = ContextCompat.checkSelfPermission(
				getContext().	getApplicationContext(),
					Manifest.permission.WRITE_EXTERNAL_STORAGE);
			if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat
						.requestPermissions(
								(Activity) getContext(),
								new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
								Permission. REQUEST_CODE_READ_EXTERNAL_STORAGE);
				toast("无权限使用，请打开权限");
				return;
			}
		}
		PhotoUtil.setalbum(context);
	}


	/** 获取验证码 */
	protected void getCode() {
		String str1 = this.et_phone_num.getText().toString();
		if (TextUtils.isEmpty(str1)) {
			toast(getResources().getString(R.string.请输入手机号));
			et_phone_num.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_border_error));
			return;
		}
		if (TextUtils.isEmpty(code_num)) {
			toast("请选择区域");
			tv_phone_district.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_border_error));
			return;
		}
		MyProgressDialog.show(context);
		String msg = "api=user/sendSms" + "&phone=" + str1+"&Area=" + code_num+"&username=" + username;
		addRequest(HttpUtil.httpConnectionByPost(context, msg,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						toast(message.getInfo());
						MyProgressDialog.Cancel();
						CountDownTime();

					}

				}, null));
	}
	/** 注册 */
	protected void commit() {
		username = this.et_name.getText().toString().trim();
		password = this.et_password.getText().toString();
		final String str3 = this.et_double_password.getText().toString();
//		final String str4 = this.tv_phone_district_num.getText().toString();
		final String str5 = this.et_phone_num.getText().toString().trim();
		final String str7 = this.et_code.getText().toString();

		if (filePath == null) {
			toast(getResources().getString(R.string.请选择头像));
			iv_icon.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_border_error));
			return;
		}
		if (username.isEmpty()) {
			if (groupid == 4) {
				toast(getResources().getString(R.string.请输入机构名称));
			} else if (groupid == 5) {
				toast("请输入品牌名称");
			} else {
				toast(getResources().getString(R.string.请输入真实姓名));
			}
			et_name.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_border_error));
			return;
		}
		if (password.isEmpty()) {
			toast(getResources().getString(R.string.请输入密码));
			et_password.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_border_error));
			return;
		}
		if (str3.isEmpty()) {
			toast(getResources().getString(R.string.请再次输入密码));
			et_double_password.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_border_error));
			return;
		}
		if (!password.equals(str3)) {
			toast(getResources().getString(R.string.二次密码不相同));
			et_double_password.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_border_error));
			return;
		}
//		if (groupid !=4) {
				if (TextUtils.isEmpty(code_num)) {
					toast(getResources().getString(R.string.请输入区号));
					tv_phone_district.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_border_error));
					return;
				}
				if (str5.isEmpty()) {
					toast(getResources().getString(R.string.请输入手机号));
					ll_phone_number.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_border_error));
					return;
//				} else {
//					if (str5.length() != 11) {
//						toast(getResources().getString(R.string.请输入正确的手机号));
//						return;
//					}
				}
			if (str7.isEmpty()) {
				toast(getResources().getString(R.string.请输入验证码));
				et_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_border_error));
				return;
			}
//		}
		if (groupid !=5) {
			if (province == null || city == null || district == null) {
				toast(getResources().getString(R.string.请选择地址));
			}
		}
		if (canreg1==false){toast(showtoast1);	return;}
		if (canreg2==false){toast(showtoast2);	return;}
		if (canreg3==false){toast(showtoast3);	return;}
		if (canreg4==false){toast(showtoast4);	return;}
		if (canreg5==false){toast(showtoast5);	return;}

		map = new HashMap<String, String>();
		map.put("api", "user/createUser");
		map.put("password", password);
		map.put("username", username);
		map.put("groupid", groupid+"");
		map.put("phone", str5);
		map.put("smscode", str7);
		if (groupid==1) {
			map.put("putong_shenfen","爱乐人");
		}
		map.put("address", province);
		map.put("address1", city);
		map.put("address2", district);
		sendPicture(filePath);
	}
	/** 发送图片 */
	protected void sendPicture(String photo) {
		pd = new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("上传中...");
		pd.setCancelable(false);
		pd.show();
		UpdateObjectToOSSUtil.getInstance().uploadImage_userPic(context, photo, new UpdateListener() {
			@Override
			public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
				pd.setMax((int) totalSize);
				pd.setProgress((int) currentSize);
			}

			@Override
			public void onSuccess(PutObjectRequest request, PutObjectResult result) {
				
				String picUrl = UpdateObjectToOSSUtil.getInstance().getUrl(request.getBucketName(), request.getObjectKey());
//				editMyPic(picUrl);

				map.put("userpic", picUrl);
			Message.obtain(handler,0).sendToTarget();

//				MyProgressDialog.Cancel();

			}

			@Override
			public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
//				MyProgressDialog.Cancel();
				pd.dismiss();
			}
		});
	}
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message message) {
			switch (message.what){
				case 0:
					register();
					break;
				default:
					super.handleMessage(message);
					break;
			}
		}
	};
	/** 调用注册接口 */
	protected void register() {
						// HashMap<String, File> files = new HashMap<String,
						// File>();
						// files.put("userpicfile", new File(filePath));
					addRequest(	HttpUtil.httpConnectionByPost(context, map,
								new ResponseListener() {

									@Override
									public void setResponseHandle(
											Message2 message) {
										user = JSON.parseObject(JSON
												.parseObject(message.getData())
												.getString("user"),
												LoginInfo.class);
										((BaseActivity) context).toast(message
												.getInfo());
										Intent data = new Intent();
										data.putExtra("username", username);
										data.putExtra("password", password);
										((BaseActivity) context).setResult(
												Activity.RESULT_OK, data);
										pd.dismiss();
										((BaseActivity) context).finish();
										MyProgressDialog.Cancel();
									}
								}, null));
	}
	/** 倒计时 */
	protected void CountDownTime() {
		downTimer = new CountDownTimer(60 * 1000L, 1000L) {
			public void onFinish() {
				sendcode.setText(getResources().getString(R.string.发送验证码));
				sendcode.setEnabled(true);
				sendcode.setBackgroundColor(getResources().getColor(
						R.color.red_c30000));
			}

			public void onTick(long millisUntilFinished) {
				sendcode.setText(getResources().getString(R.string.剩余)+ millisUntilFinished / 1000L + getResources().getString(R.string.秒));
				sendcode.setEnabled(false);
				sendcode.setBackgroundColor(getResources().getColor(
						R.color.gray_7e7c7d));
			}
		}.start();

	}

//	/** 发送图片 */
//	protected void sendPicture(Bitmap bitmap) {
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("api", "extend/upfile");
//		map.put("uploadkey", "e7627f53d4712552f8d82c30267d9bb4");
//		map.put("uptype", "userpic");
//		HashMap<String, File> files = new HashMap<String, File>();
//		files.put("file", new File(filePath));
//		addRequest(HttpUtil2.httpConnectionByPostFile(context, map, files,
//				new ResponseListener() {
//
//					@Override
//					public void setResponseHandle(Message2 message) {
//						url = JSON.parseObject(message.getData()).getString(
//								"url");
//						MyProgressDialog.Cancel();
//					}
//				}, null));
//	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == PhotoUtil.PHOTOGRAPH) {// 拍照
				filePath = FileUtil.getLocalImageUrl(context,imgName) ;
				iv_icon.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_border));
			       File temp = new File(filePath);    
//			     PhotoUtil.startPhotoZoom(context,Uri.fromFile(temp),1,1,600,600);
//				iv_icon.setImageBitmap(BitmapUtil.getBitmapFromPHOTOGRAPH(
//						context, filePath));
				ImageLoaderUtil.getInstance().setImagebyurl("file://"+filePath,iv_icon);
			} else if (requestCode == PhotoUtil.ALBUM) {// 相册
//			    PhotoUtil.startPhotoZoom(context,data.getData(),1,1,600,600);
				filePath = BitmapUtil.getFileFromALBUM(context, data);
				ImageLoaderUtil.getInstance().setImagebyurl("file://"+filePath,iv_icon);
				iv_icon.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_border));
//				Bitmap bitmap = BitmapUtil.getBitmapFromFile( filePath);
//			} else if (requestCode == PhotoUtil.PHOTO_REQUEST_CUT) {// 裁剪
//				Bundle extras = data.getExtras();
//		        if (extras != null) {
//		            bitmap = extras.getParcelable("data");
//		            iv_icon.setImageBitmap(bitmap);
//		        }
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	  @Override
	  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
	      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	      if (requestCode ==  	Permission.REQUEST_CODE_READ_EXTERNAL_STORAGE) {
	          if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
	        	  toAlbum();
	          } else {
	            toast("无法打开相册");
	          }
	      }else if (requestCode ==  	Permission.REQUEST_CODE_CAMERA) {
	          if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
	             toCamera();
	          } else {
	        	  toast("无法打开相机");
	          }
		  }
	  }

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onDestroy() {
		if (downTimer != null) {
			downTimer.cancel();
		}
		super.onDestroy();
	}

}
