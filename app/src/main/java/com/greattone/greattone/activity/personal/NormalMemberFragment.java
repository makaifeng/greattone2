package com.greattone.greattone.activity.personal;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
	/** 手机号 */
	private EditText et_phone_num;
	/** 邮箱 */
	private EditText et_email;
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
//		tv_phone_district_num.setText("+86");
		tv_phone_district_num.setOnClickListener(lis);
		et_phone_num = (EditText) rootView.findViewById(R.id.et_phone_num);
		et_phone_num.setOnFocusChangeListener(onFocusChangeListener);
		et_email = (EditText) rootView.findViewById(R.id.et_email);
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
		initGroupView();
	}

	/** 加载各种身份的界面 */
	private void initGroupView() {
		et_name.setText("");
		et_password.setText("");
		et_double_password.setText("");
		et_phone_num.setText("");
		et_email.setText("");
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
				commit();
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
			case R.id.tv_phone_district_num:// 区号
				List<String> mlist=new  ArrayList<String>();
	final String[] code=context.getResources().getStringArray(R.array.AREA_CODE);
	final String[] codes=context.getResources().getStringArray(R.array.area_codes);
	for(String str:code){
		mlist.add(str);
	}
				 final NormalPopuWindow		popu1 = new NormalPopuWindow(context, mlist,
						 tv_phone_district_num);
					popu1.setOnItemClickBack(new NormalPopuWindow.OnItemClickBack() {
					

						public void OnClick(int position, String text) {
							code_num=codes[position];
							tv_phone_district_num.setText(text);
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
						checkname();
						break;
					case R.id.et_password://密码
						checkpassword();
						break;
					case R.id.et_double_password://二次密码
						checkdoublepassword();
						break;
					case R.id.et_phone_num://手机
						checkphone();
						break;
					case R.id.et_code://验证码
						checkcode();
						break;
					default:
						break;
				}
			}
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
					showPrompt(et_code,true);
				}
			}, new HttpUtil.ErrorResponseListener() {
				@Override
				public void setServerErrorResponseHandle(Message2 message) {
					showPrompt(et_code,false);
				}

				@Override
				public void setErrorResponseHandle(VolleyError error) {

				}
			});
		}else showPrompt(et_phone_num,false);
	}

	/**检查手机号*/
	private void checkphone() {
		String phone=et_phone_num.getText().toString().trim();
		if (code_num==null){
			showPrompt(et_phone_num,false);
			toast("请选择区域");
			return;
		}
		if (phone.length()>6) {
			HttpProxyUtil.checkphone(context, phone, new ResponseListener() {
				@Override
				public void setResponseHandle(Message2 message) {
					showPrompt(et_phone_num,true);
				}
			}, new HttpUtil.ErrorResponseListener() {
				@Override
				public void setServerErrorResponseHandle(Message2 message) {
					showPrompt(et_phone_num,false);
				}

				@Override
				public void setErrorResponseHandle(VolleyError error) {

				}
			});
		}else {
			showPrompt(et_phone_num, false);
		}
	}


	/**检查二次密码*/
	private void checkdoublepassword() {
		String password=et_password.getText().toString().trim();
		String password2=et_double_password.getText().toString().trim();
		if (password2.length()>=6) {
			if (password.equals(password2)) {
				showPrompt(et_double_password, true);
			} else {
				showPrompt(et_double_password, false);
				toast("两次密码不一样");
			}
		}else{
			showPrompt(et_double_password, false);
			toast("密码长度不得小于6位");
		}
	}


	/**检查密码*/
	private void checkpassword() {
		String password=et_password.getText().toString().trim();
		if (password.length()>=6){
			showPrompt(et_password,true);
		}else {
			showPrompt(et_password,false);
			toast("密码长度不得小于6位");
		}
	}
	/**检查用户名*/
	private void checkname() {
		String name=et_name.getText().toString().trim();
		if (name.length()>0) {
			HttpProxyUtil.checkuser(context, name, new ResponseListener() {
				@Override
				public void setResponseHandle(Message2 message) {
					showPrompt(et_name, true);
				}
			}, new HttpUtil.ErrorResponseListener() {
				@Override
				public void setServerErrorResponseHandle(Message2 message) {
					showPrompt(et_name, false);
				}

				@Override
				public void setErrorResponseHandle(VolleyError error) {

				}
			});
		}else {
			showPrompt(et_name, false);
		}
	}

	/**
	 * 显示提示图标
	 * @param editview
	 * @param istrue
     */
	private void showPrompt(EditText editview,boolean istrue){
		Drawable drawable;
		if (istrue) drawable= getResources().getDrawable(R.drawable.duihaode);
		else drawable= getResources().getDrawable(R.drawable.chahaoyuan);
		drawable.setBounds(1,1, DisplayUtil.dip2px(context,20),DisplayUtil.dip2px(context,20));
		editview.setCompoundDrawables(null,null,drawable,null);
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
//	OnCheckedChangeListener listener = new OnCheckedChangeListener() {
//
//		@Override
//		public void onCheckedChanged(RadioGroup group, int checkedId) {
//			switch (checkedId) {
//			case R.id.radioButton1:
//				type = 1;
////				ll_phone.setVisibility(View.VISIBLE);
//				et_email.setVisibility(View.GONE);
//				break;
//			case R.id.radioButton2:
//				type = 2;
////				ll_phone.setVisibility(View.GONE);
//				et_email.setVisibility(View.VISIBLE);
//				break;
//
//			default:
//				break;
//			}
//		}
//	};

	/** 获取验证码 */
	protected void getCode() {
		String str1 = this.et_phone_num.getText().toString();
		if (TextUtils.isEmpty(str1)) {
			toast(getResources().getString(R.string.请输入手机号));
			return;
		}
		MyProgressDialog.show(context);
		String msg = "api=user/sendSms" + "&phone=" + str1+"&Area=" + code_num;
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
		final String str6 = this.et_email.getText().toString();
		final String str7 = this.et_code.getText().toString();
		if (bitmap == null) {
			toast(getResources().getString(R.string.请选择头像));
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
			return;
		}
		if (password.isEmpty()) {
			toast(getResources().getString(R.string.请输入密码));
			return;
		}
		if (str3.isEmpty()) {
			toast(getResources().getString(R.string.请再次输入密码));
			return;
		}
		if (!password.equals(str3)) {
			toast(getResources().getString(R.string.二次密码不相同));
			return;
		}
//		if (groupid !=4) {
			if (type == 1) {
				if (TextUtils.isEmpty(code_num)) {
					toast(getResources().getString(R.string.请输入区号));
					return;
				}
				if (str5.isEmpty()) {
					toast(getResources().getString(R.string.请输入手机号));
					return;
//				} else {
//					if (str5.length() != 11) {
//						toast(getResources().getString(R.string.请输入正确的手机号));
//						return;
//					}
				}
			} else {
				if (str6.isEmpty()) {
					toast(getResources().getString(R.string.请输入邮箱));
					return;
				}
			}
			if (str7.isEmpty()) {
				toast(getResources().getString(R.string.请输入验证码));
				return;
			}
//		}
		if (groupid !=5) {
			if (province == null || city == null || district == null) {
				toast(getResources().getString(R.string.请选择地址));
			}
		}

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
			       File temp = new File(filePath);    
			     PhotoUtil.startPhotoZoom(context,Uri.fromFile(temp),1,1,600,600);
//				iv_icon.setImageBitmap(BitmapUtil.getBitmapFromPHOTOGRAPH(
//						context, filePath));
			} else if (requestCode == PhotoUtil.ALBUM) {// 相册
			    PhotoUtil.startPhotoZoom(context,data.getData(),1,1,600,600);
				filePath = BitmapUtil.getFileFromALBUM(context, data);
//				Bitmap bitmap = BitmapUtil.getBitmapFromFile( filePath);
			} else if (requestCode == PhotoUtil.PHOTO_REQUEST_CUT) {// 裁剪
				Bundle extras = data.getExtras();    
		        if (extras != null) {    
		            bitmap = extras.getParcelable("data");    
		            iv_icon.setImageBitmap(bitmap);
		        }
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
