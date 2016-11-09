package com.greattone.greattone.activity.haixuan_and_activitise;

import java.util.HashMap;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.Listener.OnSelectCityListener;
import com.greattone.greattone.Listener.TimePickerDismissCallback;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.PostGridAdapter;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.CitySelectDialog;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.MyTimePickerDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.MyGridView;
import com.kf_test.picselect.GalleryActivity;

/** 发布活动 */
public class ReleaseActivityAct extends BaseActivity {
	// private TextView m_type;
	private EditText m_name;
	private TextView m_startime;
	private TextView m_endtime;
	private EditText m_address;
	private EditText m_issuer;
	private EditText m_num;
	private TextView m_city;
	private MyGridView gv_pic;
	private EditText m_desc;
	// private EditText m_content;
	private EditText m_price;
	private String filepass;
	private String classid = ClassId.音乐教室_活动_ID + "";
	private String mid = "18";
	private PostGridAdapter adapter;
	int type = GalleryActivity.TYPE_PICTURE;
//	private ArrayList<String> pictureFileList=new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_release_activity);
		initView();
		// getData(true);
	}

	private void initView() {
		setHead(getResources().getString(R.string.发布活动), true, true);

		findViewById(R.id.act_release_activity_typell).setOnClickListener(lis);
		// this.m_type = ((TextView)
		// findViewById(R.id.act_release_activity_type));
		this.m_name = ((EditText) findViewById(R.id.act_release_activity_name));
		// findViewById(R.id.act_release_activity_timell).setOnClickListener(lis);
		this.m_startime = ((TextView) findViewById(R.id.act_release_activity_startime));
		this.m_startime.setOnClickListener(lis);
		this.m_endtime = ((TextView) findViewById(R.id.act_release_activity_endtime));
		this.m_endtime.setOnClickListener(lis);
		this.m_address = ((EditText) findViewById(R.id.act_release_activity_address));
		this.m_issuer = ((EditText) findViewById(R.id.act_release_activity_issuer));
		this.m_num = ((EditText) findViewById(R.id.act_release_activity_num));
		findViewById(R.id.act_release_activity_cityll).setOnClickListener(lis);
		this.m_city = ((TextView) findViewById(R.id.act_release_activity_city));
		this.gv_pic = ((MyGridView) findViewById(R.id.gv_pic));
		adapter=new PostGridAdapter(context, type,1);
//		adapter.setOnItemDel(itemClickListener);
		this.		gv_pic.setAdapter(adapter);
		findViewById(R.id.act_release_activity_commit).setOnClickListener(lis);
		this.m_desc = ((EditText) findViewById(R.id.act_release_activity_descr));
		// this.m_content = ((EditText)
		// findViewById(R.id.act_release_activity_content));
		this.m_price = ((EditText) findViewById(R.id.act_release_activity_price));

	}
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
			case R.id.act_release_activity_typell:// 类型

				break;
			// case R.id.act_release_activity_timell://
			//
			// break;
			case R.id.act_release_activity_startime:// 开始时间
				showTimeDialog(m_startime);
				break;
			case R.id.act_release_activity_endtime:// 结束时间
				showTimeDialog(m_endtime);
				break;
			case R.id.act_release_activity_cityll:// 城市
				showCitySelectDialog();
				break;
			case R.id.act_release_activity_commit:// 提交
				send();
				break;

			default:
				break;
			}
		}
	};

	// private void getData(boolean isShowDialog) {
	// if (isShowDialog) {
	// MyProgressDialog.show(context);
	// }
	// HashMap<String, String> map = new HashMap<String, String>();
	// map.put("api", "message/getChatRecord");
	// map.put("to_username", name);
	// map.put("pageSize", pageSize + "");
	// map.put("pageIndex", page + "");
	// map.put("loginuid", Data.user.getUserid());
	// map.put("logintoken", Data.user.getToken());
	// addRequest(HttpUtil2.httpConnectionByPost(context, map,
	// new ResponseListener() {
	//
	// @Override
	// public void setResponseHandle(Message2 message) {
	// if (message.getData() != null
	// && message.getData().startsWith("[")) {
	// List<Chat> mList = JSON.parseArray(
	// message.getData(), Chat.class);
	// // Collections.reverse(chatList);
	// if (mList != null) {
	// chatList.addAll(mList);
	// // Collections.reverse(chatList);
	// pull_to_refresh.onHeaderRefreshComplete();
	// Parcelable listState = lv_content
	// .onSaveInstanceState();
	// adapter.notifyDataSetChanged();
	// if (mList.size() > 0) {
	// lv_content
	// .onRestoreInstanceState(listState);
	// } else {
	// toast(getResources().getString(R.string.cannot_load_more));
	// }
	// }
	// }
	// MyProgressDialog.Cancel();
	// }
	// }, null));
	// }
	HashMap<String, String> postMap = new HashMap<String, String>();

	/** 发布 */
	protected void send() {
		String name = m_name.getText().toString().trim();
		// String type = m_type.getText().toString().trim();
		String starttime = m_startime.getText().toString().trim();
		String endtime = m_endtime.getText().toString().trim();
		String address = m_address.getText().toString().trim();
		String issuer = m_issuer.getText().toString().trim();
		String num = m_num.getText().toString().trim();
		String city = m_city.getText().toString().trim();
		String desc = m_desc.getText().toString().trim();
		String price = m_price.getText().toString().trim();
		if (TextUtils.isEmpty(name)) {
			toast(getResources().getString(R.string.请选择赛事名称));
			return;
		}
		// if (TextUtils.isEmpty(type)) {
		// toast("请选择类型");
		// return;
		// }
		if (TextUtils.isEmpty(desc)) {
			toast(getResources().getString(R.string.请输入活动简介));
			return;
		}
		if (TextUtils.isEmpty(starttime)) {
			toast(getResources().getString(R.string.请选择开始时间));
			return;
		}
		if (TextUtils.isEmpty(endtime)) {
			toast(getResources().getString(R.string.请选择结束时间));
			return;
		}
		if (TextUtils.isEmpty(address)) {
			toast(getResources().getString(R.string.请选择活动地址));
			return;
		}
		if (TextUtils.isEmpty(issuer)) {
			toast(getResources().getString(R.string.请选择活动发布人));
			return;
		}
		if (TextUtils.isEmpty(num)) {
			toast(getResources().getString(R.string.请选择活动参加人数));
			return;
		}
		if (TextUtils.isEmpty(city)) {
			toast(getResources().getString(R.string.请选择地址));
			return;
		}
		if (adapter.getList().size()==0) {
			toast(getResources().getString(R.string.请选择封面图));
			return;
		}
		String[] arrayOfString;
		arrayOfString = city.split(",");
		if (arrayOfString != null && arrayOfString.length == 3) {
			postMap.put("ke_province", arrayOfString[0]);
			postMap.put("ke_city", arrayOfString[1]);
			postMap.put("ke_area", arrayOfString[2]);
		}
		postMap.put("title", name);
		postMap.put("newstext", name);
		postMap.put("faname", issuer);
		postMap.put("dizhi", address);
		postMap.put("huodong_1", starttime);
		postMap.put("huodong_2", endtime);
		postMap.put("pmaxnum", num);
		postMap.put("price", price);
		picFile=adapter.getList().get(0).getPicUrl();
		// 发送图片
		MyProgressDialog.show(context);
		HttpProxyUtil.updatePictureByCompress2(context, city, desc, picFile, new ResponseListener() {
	
	@Override
	public void setResponseHandle(Message2 message) {
		String picUrl = JSON.parseObject(message.getData())
				.getString("url");
		postMap.put("titlepic", picUrl);
		post2();
		
	}
},null);
	}

	/** 发布 */
	protected void post2() {
		postMap.put("api", "post/ecms");
		postMap.put("mid", mid);
		postMap.put("enews", "MAddInfo");
		postMap.put("classid", classid);
		postMap.put("filepass", filepass);
		postMap.put("loginuid", Data.user.getUserid());
		postMap.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, postMap,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						toast(getResources().getString(R.string.等待审核));
						MyProgressDialog.Cancel();
						setResult(RESULT_OK);
						finish();
					}
				}, null));
	}

//	/** 添加封面图 */
//	protected void addPic() {
//		BitmapUtil.getPictures(context, 1, postGridAdapter);
//		MyIosDialog.ShowBottomDialog(context, "",
//				new String[] { "去拍照", "去相册" }, new DialogItemClickListener() {
//
//					@Override
//					public void itemClick(String result, int position) {
//						if (result.equals("去拍照")) {
//							Intent intent = new Intent();
//							// 指定开启系统相机的Action
//							intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//							intent.addCategory(Intent.CATEGORY_DEFAULT);
//							// 根据文件地址创建文件
//							File file = new File(FileUtil.getLocalImageUrl(
//									context, "icon.png"));
//							// 设置系统相机拍摄照片完成后图片文件的存放地址
//							intent.putExtra(MediaStore.EXTRA_OUTPUT,
//									Uri.fromFile(file));
//							((Activity) context).startActivityForResult(intent,
//									0);
//						} else if (result.equals("去相册")) {
//							Intent intent = new Intent(context,
//									GalleryActivity.class);
//							intent.putExtra(Constants.EXTRA_PHOTO_LIMIT, 1);// 最大选择数
//							intent.putExtra("type",
//									GalleryActivity.TYPE_PICTURE);// 选择类型
//							startActivityForResult(intent, 1);
//						}
//					}
//				});
//	}

	/** 选择城市 */
	protected void showCitySelectDialog() {
		CitySelectDialog dialog = new CitySelectDialog(context, "", "", "");
		dialog.setonClickSureListener(new OnSelectCityListener() {

			@Override
			public void ClickSure(String province, String city, String district) {
				m_city.setText(province + "," + city + "," + district);
			}
		});
		dialog.show();
	}

	/** 选择时间 */
	protected void showTimeDialog(final TextView timeText) {
		new MyTimePickerDialog(context, timeText.getText().toString().trim(),
				new TimePickerDismissCallback() {

					@Override
					public void finish(String dateTime) {
						timeText.setText(dateTime);
					}
				}).show();
	}

	private String picFile;

//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (resultCode == RESULT_OK && requestCode == 1) {// 图片
//			pictureFileList = data
//					.getStringArrayListExtra(Constants.EXTRA_PHOTO_PATHS);
//			adapter.setList(pictureFileList);
//		} else if (resultCode == RESULT_OK && requestCode == 0) {// 拍照片
//			ArrayList<String>  mList=new ArrayList<String>();
//			mList.add(FileUtil.getLocalImageUrl(context,  "icon.png"));
//			pictureFileList.addAll(mList);
//			adapter.setList(pictureFileList);
//		}
//	}
}