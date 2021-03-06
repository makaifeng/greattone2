package com.greattone.greattone.activity.haixuan_and_activitise;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.android.volley.VolleyError;
import com.greattone.greattone.Listener.UpdateListener;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.UpdateVideoAct;
import com.greattone.greattone.adapter.PostGridAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.HaiXuanFilter;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Picture;
import com.greattone.greattone.util.FileUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.Permission;
import com.greattone.greattone.util.PhotoUtil;
import com.greattone.greattone.util.UpdateObjectToOSSUtil;
import com.greattone.greattone.widget.MyGridView;
import com.kf_test.picselect.GalleryActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 海选报名-乐器品牌 */
public class ApplyActivity5 extends BaseActivity {
//	private ArrayList<String> videoFileList = new ArrayList<String>();
	private String price;
	private String id;
	private TextView tv_price;
//	private TextView tv_sing_up1;
//	private TextView tv_sing_up2;
	private EditText et_company,et_name;
	private MyGridView gv_pic;
	private PostGridAdapter adapter;
	boolean isShowPic=false;
String filepass;
String mid = "20";
String classid = "73";//海选 73
//private View ll_game_area;
List<String> groupList1=new ArrayList<String>();
List<String> groupList2=new ArrayList<String>();
Map<String , List<String>> map=new HashMap<String, List<String>>();
private String bitype;
int baotype;
private RadioGroup radiogroup;
private EditText et_desc,et_phone;
private View ll_desc;
private TextView tv_upload;
//private ImageView iv_titlepic;
//private TextView tv_titlepic;
//private CheckBoxListView cl_content;
public static HaiXuanFilter haiXuanFilter = new HaiXuanFilter();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply5);
		this.id = getIntent().getStringExtra("id");
		this.price = getIntent().getStringExtra("price");
		this.bitype = getIntent().getStringExtra("bitype");//货币类型
		String	type = getIntent().getStringExtra("baotype");//报名上传类型
		 baotype= getBaoType(type);//报名上传类型
		initView();
//		getGroup();
	}
		

	/**
	 * 加载视图
	 */
	private void initView() {
		setHead(getResources().getString(R.string.sign_up), true, true);//我要报名
		
		this.radiogroup = ((RadioGroup) findViewById(R.id.radiogroup));
		radiogroup.setOnCheckedChangeListener(onCheckedChangeListener);
//		ll_game_area=findViewById(R.id.ll_apply_game_area);
//		ll_game_area	.setOnClickListener(lis);
		findViewById(R.id.activity_apply_commit).setOnClickListener(lis);
		this.et_phone = ((EditText) findViewById(R.id.activity_apply_phone));
//		this.cl_content = ((CheckBoxListView) findViewById(R.id.cl_content));
		this.et_name = ((EditText) findViewById(R.id.activity_apply_name));
		this.et_company = ((EditText) findViewById(R.id.activity_apply_company));
		this.tv_upload = ((TextView) findViewById(R.id.activity_apply_upload));
//		this.iv_titlepic = ((ImageView) findViewById(iv_titlepic));
//		LayoutParams params =(LayoutParams) iv_titlepic.getLayoutParams();
//		params.width=	(screenWidth - DisplayUtil.dip2px(context, 10)) / 4;
//		params.height=	(screenWidth - DisplayUtil.dip2px(context, 10)) / 4;
//		iv_titlepic.setLayoutParams(params);
//		iv_titlepic.setOnClickListener(lis);
//		this.tv_titlepic = ((TextView) findViewById(tv_titlepic));
		this.tv_price = ((TextView) findViewById(R.id.activity_apply_paymoney));
		this.ll_desc = ( findViewById(R.id.ll_desc));
		this.et_desc = ((EditText) findViewById(R.id.et_desc));
		gv_pic = (MyGridView) findViewById(R.id.gv_pic);

		initViewData();
	}
///**
// * 构建乐器分类选择框
// */
//	private void addlist() {
//		List<String> mList=JSON.parseArray(	 JSON.parseObject(haiXuanFilter.getGroup()).getString("乐器品牌"),String.class);
//		List<Label> labelList=new ArrayList<Label>();
//		for (String string : mList) {
//		Label label=new Label();
//		label.setTitle(string);
//		label.setIscheck(false);
//		labelList.add(label);
//		}
//		cl_content.setList(labelList);
//
//	}


	private void initViewData() {
		if (baotype==2) {
			radiogroup.setVisibility(View.VISIBLE);
			radiogroup.check(R.id.radioButton1);
		}else if (baotype==1){//上传图片
			showUpdatePicture();
		}else if (baotype==0){//上传视频
			showUpdateVideo();
		}

		if (bitype.endsWith("人民币")) {
			this.tv_price.setText(price);
		}else{
			this.tv_price.setText(bitype+"$" +price);
		}
	}
/**
 * 上传视频
 */
	private void showUpdateVideo() {
		isShowPic=false;
		tv_upload.setText("上传品牌视频");
//		tv_titlepic.setText("标题图片");
		adapter=new PostGridAdapter(context, GalleryActivity.TYPE_VIDEO,1);
		gv_pic.setAdapter(adapter);
	}
	/**
	 * 上传图片
	 */
	private void showUpdatePicture() {
		isShowPic=true;
		tv_upload.setText("上传品牌图片");
//		tv_titlepic.setText("标题图片");
		adapter=new PostGridAdapter(context, GalleryActivity.TYPE_PICTURE,9);
		gv_pic.setAdapter(adapter);
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.activity_apply_commit://提交
				submiitData();
				break;
//			case R.id.iv_titlepic://标题图片
//				MyIosDialog.ShowBottomDialog(context, "", new String[] { "拍照", "相册" },
//						new DialogItemClickListener() {
//
//							@Override
//							public void itemClick(String result, int position) {
//								if (result.equals("拍照")) {
//									toCamera();
//								} else if (result.equals("相册")) {
//									toAlbum();
//								}
//							}
//						});
//				break;
//			case R.id.ll_apply_game_area://乐器分类
//				  final List<String> mList1=		JSON.parseArray(	 JSON.parseObject(haiXuanFilter.getGroup()).getString("乐器品牌"),String.class);
//				NormalPopuWindow		popu1 = new NormalPopuWindow(context, mList1,
//						ll_game_area);
//				popu1.setOnItemClickBack(new NormalPopuWindow.OnItemClickBack() {
//					public void OnClick(int position, String text) {
//						tv_game_area.setText(mList1
//								.get(position));
//					}
//				});
//				 popu1.show();
//				break;
			default:
				break;
			}
		}
	};

	OnCheckedChangeListener onCheckedChangeListener=new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radioButton1://上传视频
				showUpdateVideo();
				break;
			case R.id.radioButton2://上传图片
				showUpdatePicture();
				break;

			default:
				break;
			}
		}
	};

	/** 提交 */
	protected void submiitData() {
		String name = et_name.getText().toString().trim();
		String company = et_company.getText().toString().trim();
		String phone = et_phone.getText().toString().trim();
		String desc = et_desc.getText().toString().trim();
		 fileList = adapter.getList();
		if (TextUtils.isEmpty(name)) {
			toast(getResources().getString(R.string.请输入姓名));
			return;
		}
		if (TextUtils.isEmpty(company)) {
			toast("请输入创立国家");
			return;
		}
//		List<String> mlist=cl_content.getCheckList();
//		if (mlist==null||mlist.size()==0) {
//			toast("请选择分类");
//			return;
//		}
		if (TextUtils.isEmpty(phone)) {
			toast(getResources().getString(R.string.请输入手机号));
			return;
		}
//		String game_area=cl_content.getCheckList("|");
		if (isShowPic&&TextUtils.isEmpty(desc)) {
			toast("请填写品牌描述");
			return;
		}
		if (fileList.size()==0) {
			if (isShowPic){
				toast("请选择品牌图片");
			}else {
				toast(getResources().getString(R.string.请选择上传视频));
			}
			return;
		}
		 filepass = System.currentTimeMillis() + "";
		 String [] msg={name,company,phone,desc};
		updateObjectToOSSUtil= UpdateObjectToOSSUtil.getInstance();
		pd=new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("上传中...");
		pd.setCancelable(false);
		pd.show();
		 if (isShowPic) {
			 postPic(msg);
		}else{
			postVideo(msg,fileList);
		}
	}
	private ArrayList<Picture> fileList;
	ProgressDialog pd;
	UpdateObjectToOSSUtil updateObjectToOSSUtil;
	int num;String photos;
	HashMap<String, String> updateMap;
	String filePath;
	/**
	 * 图片报名
	 */
	private void postPic(final String [] msg) {
		updateMap = new HashMap<>();
		updateMap.put("api", "post/ecms_bm");
		updateMap.put("mid", mid);
		updateMap.put("enews", "MAddInfo");
		updateMap.put("classid", "104");
		updateMap.put("bao_type", "3");// 海选
		updateMap.put("hai_id", id);
		updateMap.put("hai_name", msg[0]);//品牌名称
		updateMap.put("hai_address", msg[1]);//创立国家
		updateMap.put("hai_phone",msg[2]);// 联系电话
//		updateMap.put("hai_petition", msg[2]);// 乐器分类:
		updateMap.put("hai_division", msg[3]);//品牌描述:
//		updateMap.put("hai_grouping",msg[5]);//选择分组1
//		updateMap.put("hai_grouping2",msg[6]);//选择分组2
//		updateMap.put("smalltext",msg[7]);//图片描述
		updateMap.put("filepass",filepass);
		updateMap.put("loginuid", Data.user.getUserid());
		updateMap.put("logintoken", Data.user.getToken());
		updatePic();//发送图片
	}
	/**
	 * 发送图片
	 */
	private void updatePic() {
		pd.setMessage("上传第"+(num+1)+"张");
//		String path;
//		if (num==0){
//			path=filePath;
//		}else {
//			path= fileList.get(num-1).getPicUrl();
//		}

		String path= fileList.get(num).getPicUrl();
		updateObjectToOSSUtil.uploadImage_folder(context,path, new UpdateListener() {
			@Override
			public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
				pd.setMax((int)totalSize);
				pd.setProgress((int)currentSize);
			}

			@Override
			public void onSuccess(PutObjectRequest request, PutObjectResult result) {
				String pic=updateObjectToOSSUtil.getUrl(request.getBucketName(),request.getObjectKey());
				if (num==0){
					updateMap.put("hai_photo",pic);// 标题图片
				}else{
//					photos=photos+pic+"::::::";
				}
				photos=photos+pic+"::::::";
				num++;
				if (num==fileList.size()) {
					post1();
				}else {
					Message.obtain(handler,0).sendToTarget();
				}
			}

			@Override
			public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
				MyProgressDialog.Cancel();
				pd.dismiss();
			}
		});
	}
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message message) {
			switch (message.what){
				case 0:
					updatePic();
					break;
				case 1:
					pd.setMessage((CharSequence) message.obj);
					break;
				case 2:
					pd.dismiss();
					break;
				default:
					super.handleMessage(message);
					break;
			}
		}
	};
	/**
	 * 视频报名
	 */
	private void postVideo(final String [] msg, final ArrayList<Picture> videoFileList) {
		updateVideo(msg,videoFileList);
//		postVideoPic(msg, videoFileList);
	}
//	/**	发送视频的缩略图
//	 * @param videoFileList */
//	protected void postVideoPic(final String [] msg, final ArrayList<Picture> videoFileList) {
//		MyProgressDialog.show(context);
//		HttpProxyUtil.updatePictureByByte(context, filepass, classid, videoFileList.get(0).getPicUrl(), 	false,new ResponseListener() {
//
//			@Override
//			public void setResponseHandle(Message2 message) {
//				String imgUrl = JSON.parseObject(message.getData()).getString(
//						"url");
//				updateVideo(msg,videoFileList,imgUrl);
//			}
//		}, null );
//	}

	/**
	 * 添加到preferences和启动服务
	 * @param videoFileList 
	 */
	private void updateVideo(String [] msg,  ArrayList<Picture> videoFileList) {
		preferences.edit().putString("updateTitle", "")//联系人
			.putString("updateUrl", "")
			.putString("updatePath", videoFileList.get(0).getPicUrl())
			.putString("updateContent", msg[3])//品牌描述
			.putString("updateClassid", classid)
			.putString("updateId", id)
			.putString("updateFilepass", filepass)
			.putString("updateHai_petition",  msg[0])//品牌名称
			.putString("updateHai_address",  msg[1])//创立国家
			.putString("updateHai_age",   "")//创立年份
			.putString("updateHai_phone",  msg[2])//联系电话
			.putString("updateHai_division",   "")//
			.putString("updatepPintype","" )//乐器分类
			.putString("updateHai_mend",   "")//清空
			.putString("updateHai_piano",   "")//清空
			.putString("updateHai_grouping",   "")//清空
			.putString("updateHai_grouping2",   "")//清空
			.putString("updateHai_teaname","")// 所推荐的老师
			.putString("updateHai_teaphone","")// 老师电话
			.putInt("updateState", 0).commit();
		Intent intent=new Intent(context, UpdateVideoAct.class);
		intent.putExtra("isSee", 1);
		startActivity(intent);
		MyProgressDialog.Cancel();
		finish();
	}
	/**	报名*/
	protected void post1( ) {
//		String data="api=post/ecms_bm&enews=MAddInfo&mid="+mid
//				+"&classid="+104
//				+"&bao_type=3"
//				+"&hai_id="+id
//				+"&hai_photo="+picUrl//标题图片
//				+"&hai_petition="+msg[0]//品牌名称
//				+"&hai_address="+msg[1]//创立国家
//				+"&pintype="+msg[2]//乐器分类
//				+"&smalltext="+msg[3]//品牌描述
//				+"&loginuid="+Data.user.getUserid()
//				+"&logintoken="+Data.user.getToken()
//				+"&filepass="+filepass;
//		for (int i = 0; i < pictureUrlList.size(); i++) {//图片路径
//			data=data+"&msmallpic[]="+pictureUrlList.get(i);
//		}
		updateMap.put("new_photo", photos);
		addRequest(HttpUtil.httpConnectionByPost(context, updateMap,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						toast(getResources().getString(R.string.报名成功));
						Message.obtain(handler,2).sendToTarget();
						finish();
					}
				},  new HttpUtil.ErrorResponseListener() {
					@Override
					public void setServerErrorResponseHandle(Message2 message) {
						Message.obtain(handler,2).sendToTarget();
					}

					@Override
					public void setErrorResponseHandle(VolleyError error) {
						Message.obtain(handler,2).sendToTarget();
					}
				}));
	}
	private int getBaoType(String type){
		boolean  isvideo = false;
		boolean  ispicture = false;
		if (TextUtils.isEmpty(type)) {//上传视频
			return 0;
		}else{
			String s[]=type.split("\\|");
			if (isHave(s, "视频")) {//能上传视频
				isvideo=true;
			}
			if (isHave(s, "图片")) {//能上传图片
				ispicture=true;
			}
			
				if (isvideo&&!ispicture) {//上传视频
					return 0;
				}else
				if (!isvideo&&ispicture) {//上传图片
					return 1;
				}else
				if (isvideo&&ispicture) {//上传视频和图片
					return 2;
				}
		}
		return 0;
	}
	/**
	 * 数组中有该字符串
	 * @param s
	 * @param str
	 * @return
	 */
private boolean isHave(String[] s,String str){
	for (String string : s) {
		if (str.equals(string)) {
			return true;
		}
	}
	return false;
}
/**获取分组*/
//private void getGroup() {
//MyProgressDialog.show(context);
//HashMap<String, String> localHashMap = new HashMap<String, String>();
//localHashMap.put("api", "extend/haixuanType");
//localHashMap.put("titleid", id);
//addRequest(HttpUtil.httpConnectionByPost(context, localHashMap,
//		new ResponseListener() {
//
//			@Override
//			public void setResponseHandle(Message2 message) {
//				MyProgressDialog.Cancel();
//				try {
//					haiXuanFilter = JSON.parseObject(
//						message.getData(), HaiXuanFilter.class);
//					addlist();
//				} catch (JSONException e) {
//				}
//			}
//		}, null));
//
//}
/***
 * 去拍照
 */
private void toCamera() {
	if (Build.VERSION.SDK_INT >= 23) {
		int checkCallPhonePermission = ContextCompat.checkSelfPermission(getApplicationContext(),
				Manifest.permission.CAMERA);
		if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA },
					Permission.REQUEST_CODE_CAMERA);
			toast("无权限使用，请打开权限");
			return;
		}
	}
	String imgName = System.currentTimeMillis() + ".png";
	PhotoUtil.setPhotograph(context, new File(FileUtil.getLocalImageUrl(context, imgName)));
}

/**
 * 去相册
 */
private void toAlbum() {
	if (Build.VERSION.SDK_INT >= 23) {
		int checkCallPhonePermission = ContextCompat.checkSelfPermission(this.getApplicationContext(),
				Manifest.permission.WRITE_EXTERNAL_STORAGE);
		if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this,
					new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
					Permission.REQUEST_CODE_READ_EXTERNAL_STORAGE);
			toast("无权限使用，请打开权限");
			return;
		}
	}
	PhotoUtil.setalbum(context);
}

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
//@Override
//public void onActivityResult(int requestCode, int resultCode, Intent data) {
//	super.onActivityResult(requestCode, resultCode, data);
//	if (resultCode == Activity.RESULT_OK) {
//		if (requestCode == PhotoUtil.PHOTOGRAPH) {// 拍照
//			 filePath = FileUtil.getLocalImageFile(context) + "/" + "icon.png";
//		} else if (requestCode == PhotoUtil.ALBUM) {// 相册
//			 filePath = BitmapUtil.getFileFromALBUM(context, data);
//		}
////		String picpath="";
////		if (Build.VERSION.SDK_INT>Build.VERSION_CODES.N){
////			picpath=filePath;
////		}else {
////
////		}
//		ImageLoaderUtil.getInstance().setImagebyurl(filePath,iv_titlepic);
//	}
//}
}
