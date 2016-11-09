package com.greattone.greattone.activity.celebrity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.activity.classroom.ClassRoomActivity;
import com.greattone.greattone.activity.teacher.TeacherActivity;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Friend;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** 名人的基本信息 */
public class CelebrityBasicInfoFragment extends BaseFragment {
	private View rootView;
//	private MyGridView gv_similarity;
	private MyGridView gv_attention;
	private MyGridView gv_fans;
	private LinearLayout ll_hisfans;
	private LinearLayout ll_info;
	private LinearLayout ll_hisactivity;
	private TextView tv_isvip;
	private TextView tv_desc;
//	private TextView tv_title;
//	private TextView tv_time;
//	private ImageView iv_pic;
	private UserInfo userInfo;
	private List<Friend> feedList = new ArrayList<Friend>();
	private List<Friend> followList = new ArrayList<Friend>();
	private boolean isInitView;

	public void setData(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getData1();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_celebrity_basic_info,
				container, false);// 关联布局文件
		initView();
		return rootView;
	}

	private void initView() {
//		this.gv_similarity = ((MyGridView) this.rootView
//				.findViewById(R.id.gv_similarity));
		this.gv_attention = ((MyGridView) this.rootView
				.findViewById(R.id.gv_attention));
		this.gv_fans = ((MyGridView) this.rootView.findViewById(R.id.gv_fans));
		this.ll_hisfans = ((LinearLayout) this.rootView
				.findViewById(R.id.ll_hisfans));
		this.ll_info = ((LinearLayout) this.rootView.findViewById(R.id.ll_info));
		this.ll_hisactivity = ((LinearLayout) this.rootView
				.findViewById(R.id.ll_hisactivity));
		ll_hisactivity.setVisibility(View.GONE);
		this.ll_hisactivity.setOnClickListener(lis);
		this.ll_info.setOnClickListener(lis);
		this.ll_hisfans.setOnClickListener(lis);
		this.rootView.findViewById(R.id.ll_his_attention).setOnClickListener(
				lis);
//		this.rootView.findViewById(R.id.ll_his_similar).setOnClickListener(lis);
		this.rootView.findViewById(R.id.ll_his_similar).setVisibility(View.GONE);;
		this.tv_isvip = ((TextView) this.rootView.findViewById(R.id.tv_isvip));
		this.tv_desc = ((TextView) this.rootView.findViewById(R.id.tv_desc));
//		this.tv_title = ((TextView) this.rootView.findViewById(R.id.tv_title));
//		this.tv_time = ((TextView) this.rootView.findViewById(R.id.tv_time));
//		this.iv_pic = ((ImageView) this.rootView.findViewById(R.id.iv_pic));
		isInitView = true;
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent;
			switch (v.getId()) {
			case R.id.ll_info:// 他的信息
				 intent = new Intent(context, CelebrityInfoActivity.class);
//				 Bundle bundle=new Bundle();
//				 bundle.putSerializable("info", userInfo);
//				 intent.putExtra("info", bundle);
				 intent.putExtra("info", userInfo);
				 startActivity(intent);
				break;
			case R.id.ll_hisactivity:// 他的活动
				// intent = new Intent(context, HisActivityActivity.class);
				// intent.putExtra("id", id);
				// startActivity(intent);
				break;
			case R.id.ll_hisfans:// 他的粉丝
				intent = new Intent(getActivity(), CelebrityfansActivity.class);
				intent.putExtra("type", "follow");
				intent.putExtra("id", userInfo.getUserid());
				startActivity(intent);
				break;
			case R.id.ll_his_attention:// 他的关注
				intent = new Intent(getActivity(), CelebrityfansActivity.class);
				intent.putExtra("type", "feed");
				intent.putExtra("id", userInfo.getUserid());
				startActivity(intent);

				break;
//			case R.id.ll_his_similar:// 与他相似的人
//				// intent = new Intent(getActivity(),
//				// CelebrityfansActivity.class);
//				// intent.putExtra("type", "与他相似的人");
//				// intent.putExtra("url", HttpConstants.TARENTO_FOCUS_LIST_URL);
//				// intent.putExtra("id", userInfo.getUserid());
//				// startActivity(intent);
//				break;

			default:
				break;
			}
		}
	};

	/** 获取我的关注和粉丝 */
	private void getData1() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "extend/getFollowList");
		map.put("userid", userInfo.getUserid());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						feedList = JSON.parseArray(
								JSON.parseObject(message.getData()).getString(
										"feed"), Friend.class);
						followList = JSON.parseArray(
								JSON.parseObject(message.getData()).getString(
										"follow"), Friend.class);
						initViewData();
						MyProgressDialog.Cancel();
					}
				}, null));
	}

	protected void initViewData() {
		if (isInitView) {
			if (userInfo.getVerification() == 1)
				this.tv_isvip.setText(getResources().getString(R.string.已认证));
			else
				this.tv_isvip.setText(getResources().getString(R.string.未认证));
			//
			this.tv_desc.setText(userInfo.getSaytext());

			// this.tv_title.setText(tarentoInfo.getActivity().getTitle());
			// this.tv_time.setText(TimeUtil.format("yyyy-MM-dd", tarentoInfo
			// .getActivity().getTime()));
			// ImageLoaderUtil.getInstance().setImagebyurl(
			// tarentoInfo.getActivity().getPic(), this.iv_pic);
			// this.gv_similarity.setAdapter(new Adapter_grid(context,
			// tarentoInfo
			// .getSimi()));
			this.gv_attention.setAdapter(new Adapter_grid(context, feedList));
			this.gv_fans.setAdapter(new Adapter_grid(context, followList));
		}
	}

	class Adapter_grid extends BaseAdapter {
		List<Friend> list;
		Context context;

		public Adapter_grid(Context context, List<Friend> list) {
			this.context = context;
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
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
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(this.context).inflate(
						R.layout.adapter_grid, parent, false);
				holder.tv_title = ((TextView) convertView
						.findViewById(R.id.tv_title));
				holder.iv_pic = ((ImageView) convertView
						.findViewById(R.id.iv_pic));
				holder.tv_level = ((TextView) convertView
						.findViewById(R.id.tv_level));
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_title.setText(list.get(position).getUsername());
			holder.tv_level.setText(list.get(position).getGroupname());
			ImageLoaderUtil.getInstance().setImagebyurl(
					list.get(position).getUserpic(), holder.iv_pic);
			final int num=position;
			holder.iv_pic.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
//					Intent intent=new Intent(context,CelebrityActivity.class);
//					intent.putExtra("id", list.get(num).getUserid());
//					intent.putExtra("groupid", list.get(num).getGroupid());
//					startActivity(intent);
					
					int group =Integer.valueOf( list.get(num).getGroupid());
					Intent intent = new Intent();
					if (group == 1 || group == 2) {// 普通会员和名人
						intent.setClass(context, CelebrityActivity.class);
						intent.putExtra("id",  list.get(num).getUserid() + "");
						intent.putExtra("groupid",group);
					} else if (group == 3) {// 老师
						intent.setClass(context, TeacherActivity.class);
						intent.putExtra("id",   list.get(num).getUserid() + "");
					} else if (group == 4) {// 教室
						intent.setClass(context, ClassRoomActivity.class);
						intent.putExtra("id",   list.get(num).getUserid() + "");
					}
					context.startActivity(intent);
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView iv_pic;
			TextView tv_level;
			TextView tv_title;
		}
	}
}
