package com.greattone.greattone.activity.course;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.greattone.greattone.Listener.TimePickerDismissCallback;
import com.greattone.greattone.Listener.UpdateListener;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.PostGridAdapter;
import com.greattone.greattone.adapter.WeekAdapter;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.MyTimePickerDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.dialog.NormalPopuWindow.OnItemClickBack;
import com.greattone.greattone.entity.Course;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Picture;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.UpdateObjectToOSSUtil;
import com.greattone.greattone.widget.MyGridView;
import com.kf_test.picselect.GalleryActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 发布课程 */
public class PostCourseActivity extends BaseActivity {
	private EditText et_name;
	private TextView tv_cate;
	private EditText et_teacher;
	private TextView tv_time;
	// private TextView tv_city;
	// private EditText et_address;
	private EditText et_price;
	private EditText et_num;
	private EditText et_hour;
	private EditText et_duration;// 单课时常
	private EditText et_content;
	private MyGridView gv_week;
	private MyGridView gv_pic;
	int type = GalleryActivity.TYPE_PICTURE;

	private List<String> typeList = new ArrayList<String>();
	private List<String> paymentMethodList = new ArrayList<String>();
	private Map<String, String> postMap = new HashMap<String, String>();
	private WeekAdapter adapter;
	Course course = new Course();
	private PostGridAdapter gvAdapter;
	private View ll_cate;
	// private View ll_city;
	// private View ll_time;
	private View ll_payment_method;
	private TextView tv_payment_method;
	int num, all = 1;
	NormalPopuWindow popu1, popu2;
	String filepass;
	String classid = ClassId.音乐教室_课程_ID + "", mid = "16";
	String enews="MAddInfo";
	private LinearLayout ll_week;

	//	int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_course);
		initView();
		MyProgressDialog.show(context);
//		id=getIntent().getIntExtra("id", 0);
		if ("edit".equals(getIntent().getStringExtra("type"))) {
			enews="MEditInfo";
			all++;
			getData();
		}
		getType();
	}

	/** 获取类型 */
	private void getType() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "extend/courseType");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& message.getData().startsWith("{")) {
							paymentMethodList = JSON.parseArray(
									JSON.parseObject(message.getData())
											.getString("feetype"), String.class);
							typeList = JSON.parseArray(
									JSON.parseObject(message.getData())
											.getString("classname"),
									String.class);
						}
						num++;
						MyProgressDialog.Cancel(num, all);
					}

				}, null));
	}

	private void getData() {
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
							course = JSON.parseObject(
									JSON.parseObject(message.getData())
											.getString("content"), Course.class);
								initViewData();
						}
						num++;
						MyProgressDialog.Cancel(num, all);
					}

				}, null));
	}

	private void initView() {
		setHead(getResources().getString(R.string.发布课程), true, true);

		this.et_name = ((EditText) findViewById(R.id.et_name));
		this.tv_cate = ((TextView) findViewById(R.id.tv_cate));
		this.tv_cate.setOnClickListener(lis);
		this.et_teacher = ((EditText) findViewById(R.id.tv_teacher));
		if (Data.myinfo.getGroupid()==3){
			et_teacher.setVisibility(View.GONE);
		}
		ll_cate = findViewById(R.id.ll_cate);
		this.tv_time = ((TextView) findViewById(R.id.tv_time));
		// this.tv_city = ((TextView) findViewById(R.id.tv_city));
		// this.et_address = ((EditText) findViewById(R.id.et_address));
		this.et_price = ((EditText) findViewById(R.id.et_price));
		this.et_num = ((EditText) findViewById(R.id.et_num));
		this.et_hour = ((EditText) findViewById(R.id.et_hour));
		this.et_duration = ((EditText) findViewById(R.id.et_duration));
		this.et_content = ((EditText) findViewById(R.id.et_content));
		findViewById(R.id.ll_time).setOnClickListener(lis);
		// ll_city = findViewById(R.id.ll_city);
		// ll_city.setOnClickListener(lis);
		this.gv_week = ((MyGridView) findViewById(R.id.gv_week));
		this.ll_week = ((LinearLayout) findViewById(R.id.ll_week));
//		ll_week.removeAllViews();

		this.adapter = new WeekAdapter(this, null);
		this.gv_week.setAdapter(this.adapter);
		ll_payment_method = findViewById(R.id.ll_payment_method);
		tv_payment_method = (TextView) findViewById(R.id.tv_payment_method);
		ll_payment_method.setOnClickListener(lis);
		this.gv_pic = ((MyGridView) findViewById(R.id.gv_pic));
		gvAdapter = new PostGridAdapter(context, type, 1);
		gv_pic.setAdapter(gvAdapter);
		findViewById(R.id.btn_commit).setOnClickListener(lis);
	}
//	private List<WeekEntity> weeklist=new ArrayList<WeekEntity>();
//	private void addWeekView(LinearLayout ll) {
//		addWeekList();
//		LinearLayout layout1=new LinearLayout(context);
//		layout1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//		addCheckbox(layout1,0);
//	}

//	private void addWeekList() {
//		String s[] = null;
//		if (course.getKe_week() != null && !course.getKe_week().isEmpty()) {
//			s = course.getKe_week().split("\\|");
//		}
//		WeekEntity[] arrayOfWeekEntity = new WeekEntity[8];
//		arrayOfWeekEntity[0] = new WeekEntity("上课时间");
//		arrayOfWeekEntity[1] = new WeekEntity("周一");
//		arrayOfWeekEntity[2] = new WeekEntity("周二");
//		arrayOfWeekEntity[3] = new WeekEntity("周三");
//		arrayOfWeekEntity[4] = new WeekEntity("周四");
//		arrayOfWeekEntity[5] = new WeekEntity("周五");
//		arrayOfWeekEntity[6] = new WeekEntity("周六");
//		arrayOfWeekEntity[7] = new WeekEntity("周日");
//		this.weeklist = Arrays.asList(arrayOfWeekEntity);
//		if (s != null) {
//			for (int i = 0; i < weeklist.size(); i++) {
//				for (int j = 0; j < s.length; j++) {
//					if (weeklist.get(i).getName().equals(s[j])) {
//						weeklist.get(i).setSelect(true);
//					}
//				}
//			}
//		}
//	}

//	private void addCheckbox(LinearLayout ll,final int position) {
//		CheckBox checkBox = new CheckBox(context);
//		checkBox.setButtonDrawable(R.drawable.register_checkbox_style);
//		checkBox.setTextSize(14);
//		checkBox.setPadding(10, 0, 0, 0);
//		checkBox.setLayoutParams(new GridView.LayoutParams(
//				GridView.LayoutParams.WRAP_CONTENT, DisplayUtil.dip2px(context,
//				20)));
//		if (position == 0)
//			checkBox.setButtonDrawable(new ColorDrawable(0));
//		checkBox.setText(weeklist.get(position).getName());
//		if (weeklist.get(position).isSelect()) {
//			checkBox.setChecked(true);
//		} else {
//			checkBox.setChecked(false);
//		}
//		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView,
//										 boolean isChecked) {
//				weeklist.get(position).setSelect(isChecked);
//			}
//		});
//		ll.addView(checkBox);
//	}

	protected void initViewData() {
		et_name.setText(course.getTitle());
		et_teacher.setText(course.getKe_teacher());
		// et_address.setText(course.getDizhi());
		et_price.setText(course.getPrice());
		et_num.setText(course.getPmaxnum());
		et_hour.setText(course.getKe_hour());
		et_content.setText(course.getIntro());
		et_duration.setText(course.getKe_fen());
		tv_cate.setText(course.getKe_type());
		tv_time.setText(course.getHuodong_1());
		tv_payment_method.setText(course.getKe_jiaofei());
		// tv_city.setText(course.getKe_province() + "," + course.getKe_city()
		// + "," + course.getKe_area());
		if (course.getTitlepic() != null && !course.getTitlepic().isEmpty()) {
			ArrayList<Picture> videoFileList = new ArrayList<Picture>();
			Picture picture=new Picture();
			picture.setPicUrl(course.getTitlepic());
			picture.setType(1);
			videoFileList.add(picture);
			gvAdapter.setList2(videoFileList);
		}
		adapter=new WeekAdapter(context, course.getKe_week());
		gv_week.setAdapter(adapter);
//		addWeekView(ll_week);
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_cate:// 类型
				showType();
				break;
			case R.id.ll_time:// 上课时间
				showTime();
				break;
			case R.id.ll_payment_method:// 缴费方式
				showMethod();
				break;
			case R.id.btn_commit:// 发布
				commit();
				break;
			// case R.id.ll_city:// 城市
			// CitySelectDialog citySelectDialog = new CitySelectDialog(
			// context, course.getKe_province() , course.getKe_city()
			// , course.getKe_area());
			// citySelectDialog
			// .setonClickSureListener(new OnSelectCityListener() {
			// public void ClickSure(String province, String city,
			// String district) {
			// course.setKe_area(district);
			// course.setKe_province(province);
			// course.setKe_city(city);
			// tv_city.setText(province + "," + city + ","
			// + district);
			// }
			// });
			// citySelectDialog.show();
			// break;

			default:
				break;
			}
		}
	};

	/** 选择类型 */
	protected void showType() {
		popu1 = new NormalPopuWindow(context, typeList, ll_cate);
		popu1.setOnItemClickBack(new OnItemClickBack() {

			@Override
			public void OnClick(int position, String text) {
				course.setKe_type(text);
				tv_cate.setText(text);
			}
		});
		popu1.show();
	}

	protected void commit() {
		String name = et_name.getText().toString().trim();
		String cate = tv_cate.getText().toString().trim();
		String teacher = et_teacher.getText().toString().trim();
		String time = tv_time.getText().toString().trim();
		String price = et_price.getText().toString().trim();
		String num = et_num.getText().toString().trim();
		String hour = et_hour.getText().toString().trim();
		String content = et_content.getText().toString().trim();
		String duration = et_duration.getText().toString().trim();
		String method = tv_payment_method.getText().toString().trim();
		String week =adapter.getWeek();
		if (TextUtils.isEmpty(name)) {
			toast(getResources().getString(R.string.请输入名称));
			return;
		}
		if (TextUtils.isEmpty(cate)) {
			toast(getResources().getString(R.string.请选择类型));
			return;
		}
		if (TextUtils.isEmpty(week)) {
			toast(getResources().getString(R.string.请选择上课时间));
			return;
		}
		if (TextUtils.isEmpty(price)) {
			toast(getResources().getString(R.string.请输入价格));
			return;
		}
		if (TextUtils.isEmpty(num)) {
			toast(getResources().getString(R.string.请输入上课人数));
			return;
		}
		if (TextUtils.isEmpty(hour)) {
			toast(getResources().getString(R.string.请输入课时));
			return;
		}
		if (TextUtils.isEmpty(duration)) {
			toast(getResources().getString(R.string.请输入单课时长));
			return;
		}
//		if (TextUtils.isEmpty(content)) {
//			toast(getResources().getString(R.string.请输入简介));
//			return;
//		}
		if (TextUtils.isEmpty(method)) {
			toast(getResources().getString(R.string.请选择缴费方式));
			return;
		}
		List<Picture> fileList = gvAdapter.getList();
		if (fileList.size() == 0&&!"edit".equals(getIntent().getStringExtra("type"))) {
			toast(getResources().getString(R.string.请选择封面图));
			return;
		}
		postMap.put("title", name);
		postMap.put("intro", content);
		postMap.put("ke_teacher", teacher);
		postMap.put("ke_type", cate);
		postMap.put("huodong_1", time);
		postMap.put("ke_week", week);
		postMap.put("price", price);
		postMap.put("pmaxnum", num);
		postMap.put("ke_hour", hour);
		postMap.put("ke_fen", duration);
		postMap.put("ke_jiaofei", method);
		filepass = System.currentTimeMillis() + "";
		// 发送图片
		MyProgressDialog.show(context);
		if ( fileList.size()==0) {
			post2();//直接发送
		}else {
			updatePicture(fileList.get(0).getPicUrl());//先发送图片
		}
		
	
	}
	ProgressDialog pd;
/**
 *上传图片
 * @param filePath
 */
	private void updatePicture(String filePath) {
		pd=new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("上传中...");
		pd.setCancelable(false);
		pd.show();
		pd.setMessage("上传视频缩略图");
		UpdateObjectToOSSUtil.getInstance().uploadImage_iamge(context, filePath, new UpdateListener() {
			@Override
			public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
				pd.setMax((int)totalSize);
				pd.setProgress((int)currentSize);
			}

			@Override
			public void onSuccess(PutObjectRequest request, PutObjectResult result) {
			String	picUrl=UpdateObjectToOSSUtil.getInstance().getUrl(request.getBucketName(),request.getObjectKey());
				postMap.put("titlepic", picUrl);
				post2();
				pd.dismiss();
			}

			@Override
			public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
				MyProgressDialog.Cancel();
				pd.dismiss();
			}
		});
//		HttpProxyUtil.updatePictureByCompress2(context, filepass, classid,
//				fileList.get(0).getPicUrl(), new ResponseListener() {
//
//					@Override
//					public void setResponseHandle(Message2 message) {
//						String picUrl = JSON.parseObject(message.getData())
//								.getString("url");
//						postMap.put("titlepic", picUrl);
//						post2();
//
//					}
//				},null);
	}

	/** 发布 */
	protected void post2() {
		postMap.put("api", "post/ecms");
		postMap.put("mid", mid);
		postMap.put("enews", enews);
		postMap.put("classid", classid);
		if ("edit".equals(getIntent().getStringExtra("type"))) {
			postMap.put("id", getIntent().getStringExtra("id"));
		}
		postMap.put("filepass", filepass);
		postMap.put("loginuid", Data.user.getUserid());
		postMap.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, postMap,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if ("edit".equals(getIntent().getStringExtra("type"))) {
							toast("修改成功");
						}else {
							toast("发布成功");
						}
						MyProgressDialog.Cancel();
						setResult(RESULT_OK);
						finish();
					}
				}, null));
	}

	/** 缴费方式 */
	protected void showMethod() {
		popu2 = new NormalPopuWindow(context, paymentMethodList,
				ll_payment_method);
		popu2.setOnItemClickBack(new OnItemClickBack() {

			@Override
			public void OnClick(int position, String text) {
				course.setKe_type(text);
				tv_payment_method.setText(text);
			}
		});
		popu2.show();

	}

	/** 选择时间 */
	protected void showTime() {
		MyTimePickerDialog dialog = new MyTimePickerDialog(context, null,
				new TimePickerDismissCallback() {

					@Override
					public void finish(String dateTime) {
						tv_time.setText(dateTime);
					}
				});
		dialog.show();
	}

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// if (resultCode == RESULT_OK && requestCode == 1) {// 图片
	// videoFileList = data
	// .getStringArrayListExtra(Constants.EXTRA_PHOTO_PATHS);
	// gvAdapter.setList(videoFileList);
	// } else if (resultCode == RESULT_OK && requestCode == 0) {// 拍照片
	// videoFileList.clear();
	// videoFileList.add(FileUtil.getLocalImageUrl(context, "icon.png"));
	// gvAdapter.setList(videoFileList);
	// }
	// }
}
