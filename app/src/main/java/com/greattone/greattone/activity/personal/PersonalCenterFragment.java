package com.greattone.greattone.activity.personal;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.greattone.greattone.Listener.UpdateListener;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.activity.LoginActivity;
import com.greattone.greattone.activity.UpdateVideoAct;
import com.greattone.greattone.activity.WebActivity;
import com.greattone.greattone.activity.brand.BrandNewsActivity;
import com.greattone.greattone.activity.brand.ConnectWayActivityCenter;
import com.greattone.greattone.activity.brand.LeaveMessageActivityCenter;
import com.greattone.greattone.activity.brand.ProductArticleActivity;
import com.greattone.greattone.activity.brand.SalesChannelsActivityCenter;
import com.greattone.greattone.activity.classroom.ClassRoomActivity;
import com.greattone.greattone.activity.classroom.NoticeActivity;
import com.greattone.greattone.activity.course.MyCoursesActivity;
import com.greattone.greattone.activity.haixuan_and_activitise.MyActivityActivity;
import com.greattone.greattone.activity.order.MyOrderActivity;
import com.greattone.greattone.activity.qa.MyQAActivity;
import com.greattone.greattone.activity.rent.MyRoomRentActivity;
import com.greattone.greattone.activity.student.MyStudentsActivity;
import com.greattone.greattone.activity.teacher.MyTeacherActivity;
import com.greattone.greattone.activity.teacher.TeacherManageActivity;
import com.greattone.greattone.activity.timetable.TimeTablesActivity;
import com.greattone.greattone.activity2.MyRecomendationActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyIosDialog;
import com.greattone.greattone.dialog.MyIosDialog.DialogItemClickListener;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.BitmapUtil;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.FileUtil;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.MessageUtil;
import com.greattone.greattone.util.Permission;
import com.greattone.greattone.util.PhotoUtil;
import com.greattone.greattone.util.UpdateObjectToOSSUtil;

import java.io.File;
import java.util.HashMap;

/**
 * 个人中心
 */
@SuppressWarnings("deprecation")
public class PersonalCenterFragment extends BaseFragment {
	/** 是否完善信息 */
	boolean isCompleteInfo;
	/**
	 * fragment 主布局
	 */
	private View rootView;
	// /**
	// * 屏幕宽度
	// */
	// private int screenWidth;

	String text;
	private ListView lv_content;
	String names[];
	private String imgName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Data.user.getGroupid() == 1) {// 普通会员
			names = getResources().getStringArray(R.array.personal_center_items1);
		} else if (Data.user.getGroupid() == 2) {// 达人
			names = getResources().getStringArray(R.array.personal_center_items1);
		} else if (Data.user.getGroupid() == 3) {// 老师
			names = getResources().getStringArray(R.array.personal_center_items3);
		} else if (Data.user.getGroupid() == 4) {// 教室
			names = getResources().getStringArray(R.array.personal_center_items4);
		} else if (Data.user.getGroupid() == 5) {// 品牌
			names = getResources().getStringArray(R.array.personal_center_items5);
		} else {
			names = getResources().getStringArray(R.array.personal_center_items1);
		}
		if (Data.myinfo.getSign() == 1) {
			isCompleteInfo = true;
		} else {
			// MyHintPopupWindow.build(context,
			// getResources().getString(R.string.请尽快完善信息), true).show();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_personal_center, container, false);// 关联布局文件
		// screenWidth = ((BaseActivity) getActivity()).screenWidth;
		initView();
		initContentAdapter();
		return rootView;
	}

	/**
	 * 加载视图
	 */
	private void initView() {
		lv_content = (ListView) rootView.findViewById(R.id.lv_content);
		lv_content.setOnItemClickListener(listener);
		// lv_content.setAdapter(new MyBaseAdapter());
	}

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (position >= 3) {
				startIntent(position);
			}
		}
	};
	// private String filePath;

	public ImageView icon;
	// private String imgName;

	/**
	 * 加载Adapter()
	 */
	private void initContentAdapter() {
		Parcelable listState = lv_content.onSaveInstanceState();
		lv_content.setAdapter(new MyBaseAdapter());
		lv_content.onRestoreInstanceState(listState);
	}

	/**
	 * 点击跳转
	 * 
	 * @param position
	 */
	protected void startIntent(int position) {
		int listPosition;
		Intent intent;
		if (position >= 7) {
			listPosition = position - 4;
		} else {
			listPosition = position - 3;
		}
		if (position != 6) {
			if (names[listPosition].equals(getResources().getString(R.string.friend_dynamics))) {// 知音动态
				startActivity(new Intent(context, FriendDynamicActivity.class));
			} else if (names[listPosition].equals(getResources().getString(R.string.我的发帖))) {// 我的发帖
				startActivity(new Intent(context, MyPostActitvity.class));
			} else if (names[listPosition].equals(getResources().getString(R.string.通讯录))) {// 通讯录
				startActivity(new Intent(context, DirectoryActivity.class));
			} else if (names[listPosition].equals(getResources().getString(R.string.我的问答))) {// 我的Q&A
				if (Data.myinfo.getCked() != 1&&Data.myinfo.getGroupid()==4) {// 未认证教室
					toast("未签约用户不能使用该功能");
					return;
				}
				startActivity(new Intent(context, MyQAActivity.class));
			} else if (names[listPosition].equals(getResources().getString(R.string.我的收藏))) {// 我的收藏
				startActivity(new Intent(context, MyCollectActitvity.class));
			} else if (names[listPosition].equals(getResources().getString(R.string.我的活动))
					|| names[listPosition].equals(getResources().getString(R.string.音乐活动))) {// 我的活动/音乐活动
				startActivity(new Intent(context, MyActivityActivity.class));
			} else if (names[listPosition].equals(getResources().getString(R.string.我的老师))) {// 我的老师
				intent = new Intent(context, MyTeacherActivity.class);
				intent.putExtra("type", "teacher");
				startActivity(intent);
			} else if (names[listPosition].equals(getResources().getString(R.string.我的教室))) {// 我的教室
				if (Data.myinfo.getGroupid() == 4) {
					intent = new Intent(context, ClassRoomActivity.class);
					intent.putExtra("id", Data.myinfo.getUserid() + "");
					startActivity(intent);
				} else {
					intent = new Intent(context, MyTeacherActivity.class);
					intent.putExtra("type", "room");
					startActivity(intent);
				}
			} else if (names[listPosition].equals(getResources().getString(R.string.我的订单))
					|| names[listPosition].equals(getResources().getString(R.string.教室订单))) {// 我的订单/教室订单
				startActivity(new Intent(context, MyOrderActivity.class));
			} else if (names[listPosition].equals(getResources().getString(R.string.我的账户))) {// 我的账户
				intent = new Intent(context, MyAccountActivity.class);
				intent.putExtra("money", Data.myinfo.getMoney());
				startActivity(intent);
			} else if (names[listPosition].equals(getResources().getString(R.string.我的课程))) {// 我的课程
				startActivity(new Intent(context, MyCoursesActivity.class));
			} else if (names[listPosition].equals(getResources().getString(R.string.我的课表))) {// 我的课表
				startActivity(new Intent(context,TimeTablesActivity.class));
			} else if (names[listPosition].equals(getResources().getString(R.string.学生管理))) {// 学生管理
				startActivity(new Intent(context, MyStudentsActivity.class));
			} else if (names[listPosition].equals(getResources().getString(R.string.推荐管理))) {// 推荐管理
				startActivity(new Intent(context, MyRecomendationActivity.class));
			} else if (names[listPosition].equals(getResources().getString(R.string.room_lease))) {// 琴房租赁
				if (Data.myinfo.getCked() == 1) {// 已认证
					startActivity(new Intent(context, MyRoomRentActivity.class));
				}else{
					toast("未签约用户不能使用该功能");
				}
			} else if (names[listPosition].equals(getResources().getString(R.string.教师管理))) {// 教师管理
				startActivity(new Intent(context, TeacherManageActivity.class));
			} else if (names[listPosition].equals(getResources().getString(R.string.正在上传))) {// 正在上传
				startActivity(new Intent(context, UpdateVideoAct.class));
			} else if (names[listPosition].equals(getResources().getString(R.string.内部公告))) {// 我的动态
				if (Data.myinfo.getCked() != 1&&Data.myinfo.getGroupid()==4) {// 未认证教室
					toast("未签约用户不能使用该功能");
					return;
				}
				intent = new Intent(context, NoticeActivity.class);
				intent.putExtra("userid", Data.myinfo.getUserid());
				startActivity(intent);
			} else if (names[listPosition].equals(getResources().getString(R.string.琴行动态))) {// 琴行动态
				if (Data.myinfo.getCked() != 1&&Data.myinfo.getGroupid()==4) {// 未认证教室
					toast("未签约用户不能使用该功能");
					return;
				}
				intent = new Intent(context, NoticeActivity.class);
				intent.putExtra("userid", Data.myinfo.getUserid());
				startActivity(intent);

			} else if (names[listPosition].equals(getResources().getString(R.string.退出登录))) {// 退出登录
				((BaseActivity) context).preferences.edit().putString("name", "").putString("password", "").commit();
				startActivity(new Intent(context, LoginActivity.class));
				((Activity) context).finish();
			} else if (names[listPosition].equals(getResources().getString(R.string.品牌介绍))) {// 品牌介绍
				intent = new Intent(context, WebActivity.class);
				intent.putExtra("title", "品牌介绍");
				intent.putExtra("urlPath", FileUtil.getBrondH5Url("113", Data.myinfo.getUserid() + ""));
				startActivity(intent);
			} else if (names[listPosition].equals(getResources().getString(R.string.产品中心))) {// 产品中心
				startActivity(new Intent(context, ProductArticleActivity.class));
			} else if (names[listPosition].equals(getResources().getString(R.string.公司新闻))) {// 公司新闻
				startActivity(new Intent(context, BrandNewsActivity.class));
			} else if (names[listPosition].equals(getResources().getString(R.string.销售渠道))) {// 销售渠道
				if (Data.myinfo.getCked() == 1) {// 已认证
					startActivity(new Intent(context, SalesChannelsActivityCenter.class));
				} else {// 未认证
					startActivity(new Intent(context, SignActivity.class).putExtra("content",
							"销售渠道功能仅向签约用户开放，在这里可以编辑并展示您的线下销售渠道，扩大您品牌的知名度并引导用户前往您的线下销售渠道购买产品！"));
				}
			} else if (names[listPosition].equals(getResources().getString(R.string.留言板))) {// 留言板
				startActivity(new Intent(context, LeaveMessageActivityCenter.class));
			} else if (names[listPosition].equals(getResources().getString(R.string.联系我们))) {// 联系我们
				startActivity(new Intent(context, ConnectWayActivityCenter.class));
			}
		}

	}

	class MyBaseAdapter extends BaseAdapter {

		public MyBaseAdapter() {

		}

		@Override
		public int getCount() {
			return names.length + 4;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (position == 0) {// 个人信息
				convertView = getFristView(parent);
			} else if (position == 1) {// 关注和粉丝
				convertView = getSecondView(parent);
			} else if (position == 2 || position == 6) {// 空白条
				convertView = getBlankView();
			} else {// 列表
				int listPosition;
				if (position >= 7) {
					listPosition = position - 4;
				} else {
					listPosition = position - 3;
				}
				convertView = getOtherView(listPosition, parent);

			}
			return convertView;
		}

		/**
		 * 加载其他列表
		 * 
		 * @param listPosition
		 * @param parent
		 */
		private View getOtherView(int listPosition, ViewGroup parent) {
			View convertView = LayoutInflater.from(context).inflate(R.layout.adapter_personal_center, parent, false);
			TextView name = (TextView) convertView.findViewById(R.id.tv_name);//
			ImageView icon = (ImageView) convertView.findViewById(R.id.iv_icon);//
			icon.setLayoutParams(
					new LinearLayout.LayoutParams(DisplayUtil.dip2px(context, 25), DisplayUtil.dip2px(context, 25)));
			name.setText(names[listPosition]);
			icon.setImageResource(getImageId(listPosition));
			return convertView;
		}

		/**
		 * 获取图片资源Id
		 * 
		 * @param position
		 * @return
		 */
		private int getImageId(int position) {
			if (names[position].equals(getResources().getString(R.string.friend_dynamics))) {// 知音动态
				return R.drawable.center_dt;
			} else if (names[position].equals(getResources().getString(R.string.我的发帖))) {
				return R.drawable.center_post;
			} else if (names[position].equals(getResources().getString(R.string.通讯录))) {
				return R.drawable.center_txl;
			} else if (names[position].equals(getResources().getString(R.string.我的问答))) {
				return R.drawable.center_qa;
			} else if (names[position].equals(getResources().getString(R.string.退出登录))) {
				return R.drawable.center_zhuxiao;
			} else if (names[position].equals(getResources().getString(R.string.我的收藏))) {
				return R.drawable.center_sc;
			} else if (names[position].equals(getResources().getString(R.string.我的活动))
					|| names[position].equals(getResources().getString(R.string.音乐活动))) {
				return R.drawable.center_map;
			} else if (names[position].equals(getResources().getString(R.string.我的老师))) {
				return R.drawable.center_liketeacher;
			} else if (names[position].equals(getResources().getString(R.string.我的教室))) {
				return R.drawable.center_likerm;
			} else if (names[position].equals(getResources().getString(R.string.我的订单))
					|| names[position].equals(getResources().getString(R.string.教室订单))) {
				return R.drawable.center_dd;
			} else if (names[position].equals(getResources().getString(R.string.我的账户))) {
				return R.drawable.center_zhu;
			} else if (names[position].equals(getResources().getString(R.string.我的课程))) {
				return R.drawable.center_kc;
			} else if (names[position].equals(getResources().getString(R.string.学生管理))) {
				return R.drawable.center_gli;
			} else if (names[position].equals(getResources().getString(R.string.推荐管理))) {
				return R.drawable.center_tjgl;
			} else if (names[position].equals(getResources().getString(R.string.room_lease))) {// 琴房租赁
				return R.drawable.center_zlin;
			} else if (names[position].equals(getResources().getString(R.string.教室管理))) {
				return R.drawable.center_teachergl;
			} else if (names[position].equals(getResources().getString(R.string.正在上传))) {
				return R.drawable.center_teachergl;
			} else if (names[position].equals(getResources().getString(R.string.内部公告))) {
				return R.drawable.center_fb;
			} else if (names[position].equals(getResources().getString(R.string.品牌介绍))) {// 品牌介绍
				return R.drawable.center_ppjs;
			} else if (names[position].equals(getResources().getString(R.string.产品中心))) {// 产品中心
				return R.drawable.center_cp;
			} else if (names[position].equals(getResources().getString(R.string.公司新闻))) {// 公司新闻
				return R.drawable.center_xw;
			} else if (names[position].equals(getResources().getString(R.string.销售渠道))) {// 销售渠道
				return R.drawable.center_xsqd;
			} else if (names[position].equals(getResources().getString(R.string.留言板))) {// 留言板
				return R.drawable.center_lyb;
			} else if (names[position].equals(getResources().getString(R.string.联系我们))) {// 联系我们
				return R.drawable.center_lxwm;
			}
			return R.drawable.center_post;
		}

		/**
		 * 加载第一条
		 * 
		 * @param parent
		 */
		public View getFristView(ViewGroup parent) {
			View convertView = LayoutInflater.from(context).inflate(R.layout.personal_center_title1, parent, false);
			TextView name = (TextView) convertView.findViewById(R.id.tv_name);//
			icon = (ImageView) convertView.findViewById(R.id.iv_icon);//
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DisplayUtil.dip2px(context, 60),
					DisplayUtil.dip2px(context, 60));
			layoutParams.setMargins(0, 0, DisplayUtil.dip2px(context, 10), DisplayUtil.dip2px(context, 10));
			icon.setLayoutParams(layoutParams);
			// 点击头像
			icon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					MyIosDialog.ShowBottomDialog(context, "", new String[] { "拍照", "相册" },
							new DialogItemClickListener() {

								@Override
								public void itemClick(String result, int position) {
									if (result.equals("拍照")) {
										toCamera();
									} else if (result.equals("相册")) {
										toAlbum();
									}
								}
							});
				}
			});
			TextView sign = (TextView) convertView.findViewById(R.id.tv_sign);// 等级
			TextView identity = (TextView) convertView.findViewById(R.id.tv_identity);// 身份
			TextView level = (TextView) convertView.findViewById(R.id.tv_level);// 等级
			// ImageView level2 = (ImageView) convertView
			// .findViewById(R.id.iv_level);//
			// TextView rule = (TextView)
			// convertView.findViewById(R.id.tv_rule);// 规则
			// //点击规则
			// rule.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// Intent intent = new Intent(context, WebActivity.class);
			// intent.putExtra("urlPath",
			// "http://www.greattone.net/appwap/detail/integral");
			// startActivity(intent);
			// }
			// });
			if (Data.myinfo != null) {
				name.setText(Data.myinfo.getUsername());
				ImageLoaderUtil.getInstance().setImagebyurl(Data.myinfo.getUserpic(), icon);
				identity.setText(MessageUtil.getIdentity(Data.myinfo));
				// ImageLoaderUtil.getInstance().setImagebyurl(
				// Data.myinfo.getLevel().getPic(), level2);
				level.setText(Data.myinfo.getLevel().getName());
//				if (Data.myinfo.getGroupid()>=5) {//当是品牌时，显示签约按钮，否则隐藏
					if (Data.myinfo.getGroupid()>=4) {//当是品牌或教师时，显示签约按钮，否则隐藏
					sign.setVisibility(View.VISIBLE);
					if (Data.myinfo.getCked() == 1) {
						sign.setText("已签约");
						sign.setTextColor(context.getResources().getColor(R.color.red_b90006));
						sign.setBackgroundColor(Color.WHITE);
					} else {
						sign.setTextColor(context.getResources().getColor(R.color.white));
						sign.setBackgroundResource(R.drawable.button_bg3);
						sign.setText("签约");
						sign.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {// 签约
								startActivity(new Intent(context,ToSignActivity.class));
							}
						});
					}
				} else {
					sign.setVisibility(View.GONE);
				}
			} else {
				sign.setVisibility(View.GONE);
			}
			// if (Data.personalCenter != null) {
			// name.setText(Data.personalCenter.getName());
			// ImageLoaderUtil.getInstance().setImagebyurl(
			// Data.personalCenter.getPic(), icon);
			// // imageloader.displayImage(Data.personalCenter.getPic(), ircon,
			// // ((BaseActivity) context).options);
			// identity.setText(Data.personalCenter.getIdentity());
			// level.setText(Data.personalCenter.getLevel());
			// }
			// 点击第一条
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					startActivity(new Intent(context, PersonalDetailsActivity.class));// 修改用户信息
				}
			});
			return convertView;
		}

		/**
		 * 加载第二条
		 * 
		 */
		public View getSecondView(ViewGroup parent) {
			View convertView = LayoutInflater.from(context).inflate(R.layout.personal_center_title2, parent, false);
			TextView focus = (TextView) convertView.findViewById(R.id.tv_focus);//
			TextView fans = (TextView) convertView.findViewById(R.id.tv_fans);//
			if (Data.myinfo != null) {
				focus.setText(Data.myinfo.getFeednum());
				fans.setText(Data.myinfo.getFollownum());
			}
			return convertView;
		}

		/**
		 * 加载空白条
		 */
		private View getBlankView() {
			View convertView = new View(context);
			convertView.setLayoutParams(
					new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(context, 40)));
			convertView.setBackgroundColor(getResources().getColor(R.color.gray_aaaaaa));
			return convertView;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == PhotoUtil.PHOTOGRAPH) {// 拍照
				String filePath = FileUtil.getLocalImageFile(context) + "/" + imgName;
				sendPicture(filePath);
//				File temp = new File(filePath);
//				PhotoUtil.startPhotoZoom(context, Uri.fromFile(temp), 1, 1, 600, 600);
			} else if (requestCode == PhotoUtil.ALBUM) {// 相册
				String filePath = BitmapUtil.getFileFromALBUM(context, data);
				sendPicture(filePath);
//				PhotoUtil.startPhotoZoom(context, data.getData(), 1, 1, 600, 600);
			} else if (requestCode == PhotoUtil.PHOTO_REQUEST_CUT) {// 裁剪
//				Bundle extras = data.getExtras();
//				if (extras != null) {
//					Bitmap photo = extras.getParcelable("data");
//					sendPicture(photo);
//				}
			}
		}
	}

	/***
	 * 去拍照
	 */
	private void toCamera() {
		if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M) {
			int checkCallPhonePermission = ContextCompat.checkSelfPermission(getContext().getApplicationContext(),
					Manifest.permission.CAMERA);
			if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions((Activity) getContext(), new String[] { Manifest.permission.CAMERA },
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
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			int checkCallPhonePermission = ContextCompat.checkSelfPermission(getContext().getApplicationContext(),
					Manifest.permission.WRITE_EXTERNAL_STORAGE);
			if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions((Activity) getContext(),
						new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
						Permission.REQUEST_CODE_READ_EXTERNAL_STORAGE);
				toast("无权限使用，请打开权限");
				return;
			}
		}
		PhotoUtil.setalbum(context);
	}

	/** 发送图片 */
	protected void sendPicture(String photo) {
		final	ProgressDialog pd=new ProgressDialog(context);
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
				String picUrl = UpdateObjectToOSSUtil.getInstance().getUrl(request.getBucketName(),request.getObjectKey());
				editMyPic(picUrl);
//				MyProgressDialog.Cancel();
				pd.dismiss();
			}

			@Override
			public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
//				MyProgressDialog.Cancel();
				pd.dismiss();
			}
		});
	}

	/** 修改我的头像 */
	protected void editMyPic(final String picUrl) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "user/editUserInfo");
		map.put("userpic", picUrl);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map, new ResponseListener() {

			@Override
			public void setResponseHandle(Message2 message) {
				toast(message.getInfo());
				ImageLoaderUtil.getInstance().setImagebyurl(picUrl, icon);
				MyProgressDialog.Cancel();
			}

		}, null));
	}

	/**
	 * 获取个人信息
	 * 
	 * @return
	 */
	private void getUserMsg() {
		HttpProxyUtil.getUserInfo(context, null, null, new ResponseListener() {

			@Override
			public void setResponseHandle(Message2 message) {
				Data.myinfo = JSON.parseObject(message.getData(), UserInfo.class);
				((BaseActivity)context).preferences.edit().putInt("cked", Data.myinfo.getCked()).commit();
				initContentAdapter();
			}

		}, null);
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

	@Override
	public void onResume() {
		super.onResume();
		getUserMsg();
	}
}
