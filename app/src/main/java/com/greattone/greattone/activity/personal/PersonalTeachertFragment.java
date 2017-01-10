package com.greattone.greattone.activity.personal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.greattone.greattone.Listener.OnSelectCityListener;
import com.greattone.greattone.Listener.TimePickerDismissCallback;
import com.greattone.greattone.Listener.UpdateListener;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.data.Constants;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.CitySelectDialog;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.MyTimePickerDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.dialog.SelectorDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Picture;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.BitmapUtil;
import com.greattone.greattone.util.FileUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.MessageUtil;
import com.greattone.greattone.util.UpdateObjectToOSSUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** 个人中心的老师信息修改 */
public class PersonalTeachertFragment extends BaseFragment {
	/** fragment 主布局 */
	private View rootView;
	// private LabelAdapter adapter;
	private NormalPopuWindow popu1;
	String url;
	String editurl;
	private TextView m_sex;
	private TextView m_realname;
	private TextView m_nickname;
	private EditText m_idcard;
	private TextView m_city;
	private EditText m_email;
	private EditText m_address;
	private EditText m_descr;
	private TextView m_mobile;
	private EditText m_school;
	private TextView m_cname;
	private TextView m_birthday;
	private LinearLayout cname_ll;
	private ImageView m_idpic;
	// private ImageView m_pic;
	protected UserInfo useMsg = Data.myinfo;
	protected boolean isUplaod;
	HashMap<String, String> map = new HashMap<String, String>();
	ArrayList<Picture> pictureFileList1 = new ArrayList<Picture>();
	String filepass;
String imgName;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.rootView = inflater.inflate(R.layout.fragment_personal_teacher,
				container, false);
		initView();
		return rootView;
	}

	private void initView() {
		this.rootView.findViewById(R.id.fragment_personal_teacher_sex_ll)
				.setOnClickListener(lis);
		this.m_sex = ((TextView) this.rootView
				.findViewById(R.id.fragment_personal_teacher_sex));
		this.rootView.findViewById(R.id.fragment_personal_details_jg_ll).setVisibility(View.GONE);
		this.m_realname = ((TextView) this.rootView
				.findViewById(R.id.fragment_personal_teacher_realname));
		this.m_nickname = ((TextView) this.rootView
				.findViewById(R.id.fragment_personal_teacher_nickname));
		this.m_idcard = ((EditText) this.rootView
				.findViewById(R.id.fragment_personal_teacher_idCard));
		this.m_email = ((EditText) this.rootView
				.findViewById(R.id.fragment_personal_teacher_email));
		this.m_city = ((TextView) this.rootView
				.findViewById(R.id.fragment_personal_teacher_city));
		this.m_address = ((EditText) this.rootView
				.findViewById(R.id.fragment_personal_teacher_address));
		this.m_descr = ((EditText) this.rootView
				.findViewById(R.id.fragment_personal_teacher_descr));
		this.m_mobile = ((TextView) this.rootView
				.findViewById(R.id.fragment_personal_teacher_mobile));
		this.m_school = ((EditText) this.rootView
				.findViewById(R.id.fragment_personal_teacher_school));
		this.m_birthday = ((TextView) this.rootView
				.findViewById(R.id.fragment_personal_teacher_birthday));
		this.m_cname = ((TextView) this.rootView
				.findViewById(R.id.fragment_personal_teacher_cname));
		this.cname_ll = (LinearLayout) this.rootView
				.findViewById(R.id.fragment_personal_teacher_cname_ll);
		this.cname_ll.setOnClickListener(lis);
		this.rootView.findViewById(R.id.fragment_personal_teacher_upload)
				.setOnClickListener(lis);
		this.rootView.findViewById(R.id.fragment_personal_teacher_birthday_ll)
		.setOnClickListener(lis);
		this.m_idpic = ((ImageView) this.rootView
				.findViewById(R.id.editteacher_idpic));
		m_idpic.setOnClickListener(lis);
		// this.m_pic = ((ImageView) this.rootView
		// .findViewById(R.id.upload_life_pic));
		this.rootView.findViewById(R.id.fragment_personal_teacher_city_ll)
				.setOnClickListener(lis);
		// this.pic_lv =
		// ((HorizontalListView)this.rootView.findViewById(R.id.fragment_teacher_addpic_lv));
		// this.rootView.findViewById(R.id.).setOnClickListener(lis);
		this.rootView.findViewById(R.id.fragment_personal_teacher_commit)
				.setOnClickListener(lis);
		initViewData();
	}

	private void addPopuWindow() {
		List<String> arrayList = Data.filter_teacher.getClassname();
		final List<String> mList = arrayList;
		popu1 = new NormalPopuWindow(context, arrayList, cname_ll);
		popu1.setOnItemClickBack(new NormalPopuWindow.OnItemClickBack() {
			public void OnClick(int position, String text) {
				m_cname.setText(mList.get(position));
				// cid = localList.get(position).getId();
				popu1.dismisss();
			}
		});
		popu1.show();
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.fragment_personal_teacher_sex_ll://性别
				new SelectorDialog(context,
						new SelectorDialog.SexDismissListener() {

							public void finish(String text) {
								m_sex.setText(text);
							}
						}).show();
				return;
			case R.id.fragment_personal_teacher_birthday_ll://出生日期
				new MyTimePickerDialog(context, m_birthday.getText().toString().trim(),
						new TimePickerDismissCallback() {

							@Override
							public void finish(String dateTime) {
								m_birthday.setText(dateTime);
							}
						}).show();
				return;
			case R.id.fragment_personal_teacher_upload://头像
				isUplaod = true;
				BitmapUtil.getPictures(context, imgName,1,pictureFileList1);
				return;
				// case R.id.upload_life_pic:
				// isUplaod=false;
				// BitmapUtil.getPictures(context, 6-pictureFileList1.size());
				// return;
			case R.id.fragment_personal_teacher_city_ll://城市
				CitySelectDialog selectDialog = new CitySelectDialog(context,
						useMsg.getAddress(), useMsg.getAddress1(),
						useMsg.getAddress2());
				selectDialog.setonClickSureListener(new OnSelectCityListener() {

					@Override
					public void ClickSure(String province, String city,
							String district) {
						m_city.setText(province + "," + city + "," + district);
					}
				});
				selectDialog.show();
				return;
			case R.id.fragment_personal_teacher_cname_ll://身份
				addPopuWindow();
				break;
			case R.id.fragment_personal_teacher_commit://提交
				commit();
				break;
			case R.id.editteacher_idpic://图片
				isUplaod = true;
			BitmapUtil.getPictures(context,imgName,1,pictureFileList1);
				break;
			default:
				return;
			}
		}
	};

	protected void initViewData() {
		m_sex.setText(useMsg.getSex());
		m_realname.setText(useMsg.getTruename());
		m_nickname.setText(useMsg.getUsername());
		m_idcard.setText(useMsg.getShenfenzheng());
		m_email.setText(useMsg.getEmail());
		m_city.setText(useMsg.getAddress() + "," + useMsg.getAddress1() + ","
				+ useMsg.getAddress2());
		m_address.setText(useMsg.getAddres());
		m_descr.setText(useMsg.getSaytext());
		m_mobile.setText(useMsg.getPhone());
		m_school.setText(useMsg.getBiye());
		m_birthday.setText(useMsg.getChusheng());
		m_cname.setText(MessageUtil.getIdentity(useMsg));
		String img[]=useMsg.getPhoto().split("\\::::::");
		if (img.length>0) {
			ImageLoaderUtil.getInstance().setImagebyurl(img[0], m_idpic);
		}
		// ImageLoaderUtil.getInstance().setImagebyurl(useMsg.getPhotoID(),
		// m_pic);
	}

	/**
	 * 提交
	 * 
	 * @throws ParseException
	 */
	private void commit() {
//		String truename = m_realname.getText().toString().trim();
		String cname = m_cname.getText().toString().trim();
		String sex = m_sex.getText().toString().trim();
		String birthday = this.m_birthday.getText().toString();
		String address = this.m_address.getText().toString();
		String idcard = this.m_idcard.getText().toString();
		String desc = this.m_descr.getText().toString();
		String school = this.m_school.getText().toString();
//		if (TextUtils.isEmpty(truename)) {
//			toast(getResources().getString(R.string.请输入真实姓名));
//			return;
//		}
		if (TextUtils.isEmpty(cname)) {
			toast(getResources().getString(R.string.请选择身份));
			return;
		}
		if (TextUtils.isEmpty(sex)) {
			toast(getResources().getString(R.string.请选择性别));
			return;
		}
		String[] arrayOfString = {};
		if (TextUtils.isEmpty(birthday)) {
			toast(getResources().getString(R.string.请选择生日));
			return;
		}
		arrayOfString = this.m_city.getText().toString().trim().split("\\,");
		if ((arrayOfString== null) || (arrayOfString.length != 3)) {
			toast(getResources().getString(R.string.请选择城市));
			return;
		}
		if (TextUtils.isEmpty(idcard)) {
			toast(getResources().getString(R.string.请输入证件号码));
			return;
		}
		if (TextUtils.isEmpty(address)) {
			toast(getResources().getString(R.string.请填写详细地址));
			return;
		}
		String img[]=useMsg.getPhoto().split("\\::::::");
		if (pictureFileList1.size()==0&&img.length<1) {
			toast(getResources().getString(R.string.请添加身份证照));
			return;
		}
		// if (TextUtils.isEmpty(desc)) {
		// toast(getResources().getString(R.string.请输入描述));
		// return;
		// }
		MyProgressDialog.show(context);
		map = new HashMap<String, String>();
		map.put("api", "user/editUserInfo");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		map.put("username", this.m_nickname.getText().toString());
//		map.put("truename", truename);
		map.put("shenfenzheng", idcard);
		// map.put("email", this.m_email.getText().toString());
		map.put("teacher_type", cname);
		map.put("sex", sex);
		map.put("chusheng", birthday);
		if (!TextUtils.isEmpty(school)) {
			map.put("biye", school);
		}
		map.put("address", arrayOfString[0]);
		map.put("address1", arrayOfString[1]);
		map.put("address2", arrayOfString[2]);
		map.put("addres", address);
		map.put("saytext", desc);
		if ( pictureFileList1.size()==0) {
			post1();//直接发送
		}else {
			post();//先发送图片
		}

	}
	ProgressDialog pd;
	UpdateObjectToOSSUtil updateObjectToOSSUtil;
	// 发送图片
	private void post() {
		updateObjectToOSSUtil= UpdateObjectToOSSUtil.getInstance();
		pd=new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("上传中...");
		pd.setCancelable(false);
		pd.show();
		pd.setMessage("上传视频缩略图");
		updateObjectToOSSUtil.uploadImage_iamge(context,pictureFileList1.get(0).getPicUrl(), new UpdateListener() {
			@Override
			public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
				pd.setMax((int)totalSize);
				pd.setProgress((int)currentSize);
			}

			@Override
			public void onSuccess(PutObjectRequest request, PutObjectResult result) {
			String	picUrl=updateObjectToOSSUtil.getUrl(request.getBucketName(),request.getObjectKey());
				map.put("photo", picUrl+"::::::");
				Message.obtain(handler,0).sendToTarget();
			}

			@Override
			public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
				MyProgressDialog.Cancel();
				pd.dismiss();
			}
		});

//		HashMap<String, String> pmap = new HashMap<String, String>();
//		pmap.put("api", "post/upfile");
//		pmap.put("uploadkey", "e7627f53d4712552f8d82c30267d9bb4");
//		pmap.put("uptype", "photos");
//		pmap.put("loginuid", Data.user.getUserid());
//		pmap.put("logintoken", Data.user.getToken());
//		HashMap<String, byte[]> bytes = new HashMap<String, byte[]>();
//		bytes.put("file", BitmapUtil.getBytesFromFile(pictureFileList1.get(0).getPicUrl()));
//		String[] suffix=pictureFileList1.get(0).getPicUrl().split("\\.");
//		HttpUtil.httpConnectionByPostBytes(context, map,bytes,suffix[suffix.length - 1],true,
//				new ResponseListener() {
//
//			@Override
//			public void setResponseHandle(Message2 message) {
//				String picUrl = JSON.parseObject(message.getData())
//						.getString("url");
//				map.put("photo", picUrl+"::::::");
//				post1();
//
//			}
//		}, null);

	}
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message message) {
			switch (message.what){
				case 0:
					post1();
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
	protected void post1() {
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						toast(message.getInfo());
						((BaseActivity) context).finish();
						Message.obtain(handler,2).sendToTarget();
						MyProgressDialog.Cancel();
					}
				}, null));
	}



	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		pictureFileList1.clear();
		if (resultCode == Activity.RESULT_OK && requestCode == 1) {// 图片
			ArrayList< String> mlist=new ArrayList<String>();
			mlist = data
					.getStringArrayListExtra(Constants.EXTRA_PHOTO_PATHS);
			Picture picture=new Picture();
			picture.setPicUrl(mlist.get(0));
			picture.setType(0);
			pictureFileList1.add(picture);
	
			setImagebyurl();
			if (isUplaod) {// 手持身份照
			} else {// 生活照

			}
			// adapter.setList(pictureFileList);
		} else if (resultCode == Activity.RESULT_OK && requestCode == 0) {// 拍照片
//			ArrayList<String> mList = new ArrayList<String>();
			Picture picture=new Picture();
			picture.setPicUrl(FileUtil.getLocalImageUrl(context,imgName));
			picture.setType(0);
			pictureFileList1.add(picture);
	
//			pictureFileList1.addAll(mList);
			setImagebyurl();
		
			if (isUplaod) {// 手持身份照
			} else {// 生活照
			// pictureFileList1.addAll(mList);
			}
			// adapter.setList(pictureFileList);

		}
	}
/**显示图片*/
	private void setImagebyurl() {
		if (pictureFileList1.get(0).getType()==1) {
			ImageLoaderUtil.getInstance().setImagebyurl(pictureFileList1.get(0).getPicUrl(), m_idpic);
		}else
		ImageLoaderUtil.getInstance().setImagebyurl(
				"file://" + pictureFileList1.get(0).getPicUrl(), m_idpic);
		
	}
}