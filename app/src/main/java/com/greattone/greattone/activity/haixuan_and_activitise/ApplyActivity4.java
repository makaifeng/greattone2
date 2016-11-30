package com.greattone.greattone.activity.haixuan_and_activitise;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.UpdateVideoAct;
import com.greattone.greattone.adapter.PostGridAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.entity.HaiXuanFilter;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Picture;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.MyGridView;
import com.kf_test.picselect.GalleryActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** 海选报名-人气琴行 */
public class ApplyActivity4 extends BaseActivity {
//	private ArrayList<String> videoFileList = new ArrayList<String>();
	private String price;
	private String id;
	private TextView tv_price;
	private TextView tv_sing_up1;
	private TextView tv_sing_up2;
	private TextView tv_game_area;
	private EditText et_music,et_address,et_phone,et_name;
	private MyGridView gv_pic;
	private PostGridAdapter adapter;
	boolean isShowPic=false;
String filepass;
String mid = "20";
String classid = "73";//海选 73
private View ll_game_area;
private View ll_sing_up;
List<String> groupList1=new ArrayList<String>();
List<String> groupList2=new ArrayList<String>();
Map<String , List<String>> map=new HashMap<String, List<String>>();
private String bitype;
int baotype;
private RadioGroup radiogroup;
private EditText et_desc;
private View ll_desc;
private TextView tv_upload;
public static HaiXuanFilter haiXuanFilter = new HaiXuanFilter();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply4);
		this.id = getIntent().getStringExtra("id");
		this.price = getIntent().getStringExtra("price");
		this.bitype = getIntent().getStringExtra("bitype");//货币类型
String		type = getIntent().getStringExtra("baotype");//报名上传类型
		 baotype= getBaoType(type);//报名上传类型
		initView();
		getGroup();
	}
	/**获取分组*/
private void getGroup() {
	MyProgressDialog.show(context);
	HashMap<String, String> localHashMap = new HashMap<String, String>();
	localHashMap.put("api", "extend/haixuanType");
	localHashMap.put("titleid", id);
	addRequest(HttpUtil.httpConnectionByPost(context, localHashMap,
			new ResponseListener() {

				@Override
				public void setResponseHandle(Message2 message) {
					MyProgressDialog.Cancel();
					try {
						haiXuanFilter = JSON.parseObject(
							message.getData(), HaiXuanFilter.class);
					} catch (JSONException e) {
					}
					initGroups();
				}

			}, null));
		
	}
/**
 * 加载分组
 */
	private void initGroups() {
		if(haiXuanFilter.getGroup()!=null&&haiXuanFilter.getGroup().startsWith("{")){
		JSONObject jsonobject = JSON.parseObject(haiXuanFilter.getGroup());
		 Set<String> set =jsonobject.keySet();
		 groupList1=new ArrayList<String>(set);
		 map=new HashMap<String, List<String>>();
		 for (String string : set) {
			 map.put(string,JSON.parseArray( jsonobject.getString(string),String.class));
		}
		}
	}

	/**
	 * 加载视图
	 */
	private void initView() {
		setHead(getResources().getString(R.string.sign_up), true, true);//我要报名
		
		this.radiogroup = ((RadioGroup) findViewById(R.id.radiogroup));
		radiogroup.setOnCheckedChangeListener(onCheckedChangeListener);
		ll_game_area=findViewById(R.id.ll_apply_game_area);
		ll_game_area	.setOnClickListener(lis);
		ll_sing_up=findViewById(R.id.ll_apply_sign_up);
		findViewById(R.id.activity_apply_commit).setOnClickListener(lis);
		findViewById(R.id.ll_apply_sign_up).setOnClickListener(lis);
		findViewById(R.id.ll_apply_game_area).setOnClickListener(lis);
		this.tv_price = ((TextView) findViewById(R.id.activity_apply_paymoney));
		this.tv_sing_up1 = ((TextView) findViewById(R.id.activity_apply_sign_up1));
		tv_sing_up1	.setOnClickListener(lis);
		this.tv_sing_up2 = ((TextView) findViewById(R.id.activity_apply_sign_up2));
		tv_sing_up2	.setOnClickListener(lis);
		this.tv_game_area = ((TextView) findViewById(R.id.activity_apply_game_area));
		this.et_music = ((EditText) findViewById(R.id.activity_apply_music));
		this.et_address = ((EditText) findViewById(R.id.activity_apply_address));
		this.et_phone = ((EditText) findViewById(R.id.activity_apply_phone));
		this.et_name = ((EditText) findViewById(R.id.activity_apply_name));
		this.tv_upload = ((TextView) findViewById(R.id.activity_apply_upload));
		this.ll_desc = ( findViewById(R.id.ll_desc));
		this.et_desc = ((EditText) findViewById(R.id.et_desc));
		gv_pic = (MyGridView) findViewById(R.id.gv_pic);

		initViewData();
	}

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
		this.et_name.setText(Data.myinfo.getUsername());
		this.et_phone.setText(Data.myinfo.getPhone());
		this.et_address.setText(Data.myinfo.getAddres());
		this.et_music.setText("");
		this.tv_game_area.setText(getIntent().getStringExtra("title"));
	}
/**
 * 上传视频
 */
	private void showUpdateVideo() {
		isShowPic=false;
		ll_desc.setVisibility(View.GONE);
		tv_upload.setText("上传参赛视频");
		et_desc.setHint("视频描述");
		et_music.setVisibility(View.GONE);
		adapter=new PostGridAdapter(context, GalleryActivity.TYPE_VIDEO,1);
		gv_pic.setAdapter(adapter);
	}
	/**
	 * 上传图片
	 */
	private void showUpdatePicture() {
		isShowPic=true;
		ll_desc.setVisibility(View.VISIBLE);
		tv_upload.setText("选择图片");
		et_music.setVisibility(View.VISIBLE);
		et_music.setHint("图片主题");
		et_desc.setHint("图片描述");
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
			case R.id.ll_apply_game_area://赛区
				  final List<String> mList1 =new ArrayList<String>();
				  mList1.add(getIntent().getStringExtra("title"));
				NormalPopuWindow		popu1 = new NormalPopuWindow(context, mList1,
						ll_game_area);
				popu1.setOnItemClickBack(new NormalPopuWindow.OnItemClickBack() {
					public void OnClick(int position, String text) {
						tv_game_area.setText(mList1
								.get(position));
					}
				});
				 popu1.show();
				break;
			case R.id.activity_apply_sign_up1://组别1
				 if (groupList1.size() > 0) {
					 final NormalPopuWindow		popu2= new NormalPopuWindow(context, groupList1,
							ll_sing_up);
					popu2.setOnItemClickBack(new NormalPopuWindow.OnItemClickBack() {
						public void OnClick(int position, String text) {
							tv_sing_up1.setText(groupList1
									.get(position));
							groupList2=map.get(tv_sing_up1.getText().toString().trim());
							popu2.dismisss();
						}
					});
					 popu2.show();
				 }else{
					 toast(getResources().getString(R.string.暂无组别) );//暂无组别
				 }
				break;
			case R.id.activity_apply_sign_up2://组别2
				if (groupList2.size() > 0) {
					final NormalPopuWindow		popu2= new NormalPopuWindow(context, groupList2,
							ll_sing_up);
					popu2.setOnItemClickBack(new NormalPopuWindow.OnItemClickBack() {
						public void OnClick(int position, String text) {
							tv_sing_up2.setText(groupList2
									.get(position));
							popu2.dismisss();
						}
					});
					popu2.show();
				}else{
					toast(getResources().getString(R.string.暂无组别) );//暂无组别
				}
				break;
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
		String phone = et_phone.getText().toString().trim();
		String address = et_address.getText().toString().trim();
		String music = et_music.getText().toString().trim();
		String desc = et_desc.getText().toString().trim();
		String game_area = tv_game_area.getText().toString().trim();
		String sing_up1 = tv_sing_up1.getText().toString().trim();
		String sing_up2 = tv_sing_up2.getText().toString().trim();
		ArrayList<Picture> videoFileList = adapter.getList();
		if (TextUtils.isEmpty(name)) {
			toast(getResources().getString(R.string.请输入姓名));
			return;
		}
		if (TextUtils.isEmpty(phone)) {
			toast(getResources().getString(R.string.请输入手机号));
			return;
		}
		if (TextUtils.isEmpty(address)) {
			toast(getResources().getString(R.string.请填写详细地址));
			return;
		}
		if (isShowPic&&TextUtils.isEmpty(music)) {
			toast("请填写图片主题");
			return;
		}
		if (TextUtils.isEmpty(game_area)) {
			toast(getResources().getString(R.string.请选择赛区));
			return;
		}
		if (TextUtils.isEmpty(sing_up1)) {
			toast(getResources().getString(R.string.请选择组别));
			return;
		}
		if (isShowPic&&TextUtils.isEmpty(desc)) {
			toast("请填写图片描述");
			return;
		}
		if (videoFileList.size()==0) {
			toast(getResources().getString(R.string.请选择上传视频));
			return;
		}
		 filepass = System.currentTimeMillis() + "";
		 String [] msg={name,phone,address,music,game_area,sing_up1,sing_up2,desc};
			MyProgressDialog.show(context);
		 if (isShowPic) {
			 postPic(msg,videoFileList,0);
		}else{
			postVideo(msg,videoFileList);
		}
	}
	private ArrayList<String> pictureUrlList=new ArrayList<String>();
	/**
	 * 图片报名
	 */
	private void postPic(final String [] msg, final ArrayList<Picture> videoFileList,final int num) {
		//发送图片
//		for (int i = 0; i < videoFileList.size(); i++) {
			HttpProxyUtil.updatePictureByCompress2(context, filepass, classid, videoFileList.get(num).getPicUrl(),	new ResponseListener() {

				@Override
				public void setResponseHandle(Message2 message) {
			String picUrl = JSON.parseObject(message.getData()).getString(
							"url");
			pictureUrlList.add(picUrl);
			if (pictureUrlList.size()==videoFileList.size()) {
				post1(msg);
			}else{
				postPic(msg, videoFileList,num+1);
			}
				}
			}, null);
//		}
	}
	/**
	 * 视频报名
	 */
	private void postVideo(final String [] msg, final ArrayList<Picture> videoFileList) {
		postVideoPic(msg, videoFileList);
	}
	/**	发送视频的缩略图
	 * @param videoFileList */
	protected void postVideoPic(final String [] msg, final ArrayList<Picture> videoFileList) {
		HttpProxyUtil.updatePictureByByte(context, filepass, classid, videoFileList.get(0).getPicUrl(), 	false,new ResponseListener() {

			@Override
			public void setResponseHandle(Message2 message) {
				String imgUrl = JSON.parseObject(message.getData()).getString(
						"url");
				updateVideo(msg,videoFileList,imgUrl);
			}
		}, null );
	}
	/**
	 * 添加到preferences和启动服务
	 * @param videoFileList 
	 */
	private void updateVideo(String [] msg,  ArrayList<Picture> videoFileList, String imgUrl) {
		preferences.edit().putString("updateTitle", msg[0])//选手姓名
		.putString("updateUrl", imgUrl)
		.putString("updatePath", videoFileList.get(0).getPicUrl())
		.putString("updateContent", msg[7])
		.putString("updateClassid", classid)
		.putString("updateId", id)
		.putString("updateFilepass", filepass)
		.putString("updateHai_phone",msg[1])//联系电话
		.putString("updateHai_address",  msg[2])//详细地址
		.putString("updateHai_division",   msg[4])//比赛赛区
		.putString("updateHai_grouping",   msg[5])//选择分组1
		.putString("updateHai_grouping2",  msg[6])//选择分组2
		.putString("updateHai_petition",   "")//清空
		.putString("updateHai_mend",   "")//清空
		.putString("updateHai_piano",   "")//清空
		.putString("updateHai_age",   "")//清空
				.putString("updatepPintype",   "")//乐器分类
		.putInt("updateState", 0).commit();
		Intent intent=new Intent(context, UpdateVideoAct.class);
		intent.putExtra("isSee", 1);
		startActivity(intent);
		MyProgressDialog.Cancel();
		finish();
	}
	/**	报名*/
	protected void post1( String [] msg) {
		String data="api=post/ecms_bm&enews=MAddInfo&mid="+mid
				+"&classid="+104
				+"&bao_type=3"
				+"&hai_id="+id
				+"&hai_photo="+pictureUrlList.get(0)//标题图片
				+"&hai_name="+msg[0]//选手姓名
				+"&hai_phone="+msg[1]//联系电话
				+"&hai_address="+msg[2]//详细地址
				+"&hai_petition="+msg[3]//参赛曲目
				+"&hai_division="+msg[4]//比赛赛区
				+"&hai_grouping="+msg[5]//选择分组1
				+"&hai_grouping2="+msg[6]//选择分组2
				+"&smalltext="+msg[7]//图片描述
				+"&loginuid="+Data.user.getUserid()
				+"&logintoken="+Data.user.getToken()
				+"&filepass="+filepass;
		for (int i = 0; i < pictureUrlList.size(); i++) {//图片路径
			data=data+"&msmallpic[]="+pictureUrlList.get(i);
		}
		addRequest(HttpUtil.httpConnectionByPost(context, data,
				new ResponseListener() {
			
			@Override
			public void setResponseHandle(Message2 message) {
				toast(getResources().getString(R.string.报名成功));
				MyProgressDialog.Cancel();
				finish();
			}
		}, null));
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
}
