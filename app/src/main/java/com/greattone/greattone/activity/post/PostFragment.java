package com.greattone.greattone.activity.post;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.entity.ImageData;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;

import java.util.HashMap;
import java.util.List;

/**
 * 发帖
 */
public class PostFragment extends BaseFragment {
	/**
	 * fragment 主布局
	 */
	private View rootView;
	private ImageView iv_gg;
	private LinearLayout ll_video;
	private LinearLayout ll_music;
	private LinearLayout ll_picture;
	private LinearLayout ll_yuepu;
//	private GridView gv_content;
	private int screenWidth;
	private LinearLayout rl_post;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		getChangeClass();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_post, container, false);// 关联布局文件
		 screenWidth = ((BaseActivity) getActivity()).screenWidth;
		initView();
		getAdvList(11);
		return rootView;
	}

	/**
	 * 加载视图
	 */
	private void initView() {
		rl_post = (LinearLayout) rootView.findViewById(R.id.rl_post);//
		rl_post.setLayoutParams(new LinearLayout.LayoutParams(screenWidth*2/3, screenWidth*2/3));
//				gv_content.setAdapter(new MyAdapter());
		ll_video = (LinearLayout) rootView.findViewById(R.id.ll_video);//
//		RelativeLayout.LayoutParams layoutParams1=new RelativeLayout.LayoutParams(screenWidth/3, screenWidth/3);
//		ll_video.setLayoutParams(layoutParams1);
		ll_video.setOnClickListener(lis);
		ll_music = (LinearLayout) rootView.findViewById(R.id.ll_music);//
//		RelativeLayout.LayoutParams	 layoutParams2=new RelativeLayout.LayoutParams(screenWidth/3, screenWidth/3);
//		layoutParams2.addRule(RelativeLayout.ALIGN_RIGHT, R.id.ll_video);
//		ll_music.setLayoutParams(layoutParams2);
		ll_music.setOnClickListener(lis);
		ll_picture = (LinearLayout) rootView.findViewById(R.id.ll_picture);//
//		RelativeLayout.LayoutParams	 layoutParams3=new RelativeLayout.LayoutParams(screenWidth/3, screenWidth/3);
//		layoutParams3.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.ll_video);
//		ll_picture.setLayoutParams(layoutParams3);
		ll_picture.setOnClickListener(lis); 
		ll_yuepu = (LinearLayout) rootView.findViewById(R.id.ll_yuepu);//
//		RelativeLayout.LayoutParams	 layoutParams4=new RelativeLayout.LayoutParams(screenWidth/3, screenWidth/3);
//		layoutParams4.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.ll_video);
//		layoutParams4.addRule(RelativeLayout.ALIGN_RIGHT, R.id.ll_picture);
//		ll_yuepu.setLayoutParams(layoutParams4);
		ll_yuepu.setOnClickListener(lis);
		iv_gg = (ImageView) rootView.findViewById(R.id.iv_gg);//
		iv_gg.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, screenWidth/4));
	}

	private OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
//			if (Data.myinfo.getSign() == 1) {
			Intent intent=new Intent();
			if (imageUrlList!=null&&imageUrlList.size()>0) {
				int position= (int) (Math.random()*imageUrlList.size());
			intent.putExtra("ggUrl",imageUrlList.get(position).getPic());
			}
			if (v == ll_video) {
				intent.setClass(context, PostVideoActivity.class);
				startActivity(intent);
			} else if (v == ll_music) {
				intent.setClass(context, PostMusicActivity.class);
				startActivity(intent);
			} else if (v == ll_picture) {
				intent.setClass(context, PostPictureActivity.class);
				startActivity(intent);
			} else if (v == ll_yuepu) {
				intent.setClass(context, PostYuePuActivity.class);
				startActivity(intent);
			} 
//			}else {
//				toast(getResources().getString(R.string.请先完善资料));
//			}
		}
	};
	protected List<ImageData> imageUrlList;
//
	/**
	 * 获取广告图
	 * 
	 * @return
	 */
	private void getAdvList( int classid) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "extend/getAdvList");
		map.put("classid", classid+"");
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {
					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& !TextUtils.isEmpty(message.getData())) {
							imageUrlList=JSON.parseArray(message.getData(), ImageData.class);
						}
						int position= (int) (Math.random()*imageUrlList.size());
						ImageLoaderUtil.getInstance().setImagebyurl(imageUrlList.get(position).getPic(), iv_gg);
					}

				},null));
	}
//	class MyAdapter extends  BaseAdapter{
//		String name[]=new String[]{getResources().getString(R.string.video),getResources().getString(R.string.music),getResources().getString(R.string.picture),getResources().getString(R.string.yuepu)};
//		int pic[]=new int[]{R.drawable.icon_send_video,R.drawable.icon_music,R.drawable.icon_pic,R.drawable.icon_pic};
//		@Override
//		public int getCount() {
//			return name.length>pic.length?pic.length:name.length;
//		}
//
//		@Override
//		public Object getItem(int position) {
//			return null;
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return 0;
//		}
//
//		@SuppressLint("ViewHolder")
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			convertView=LayoutInflater.from(context).inflate(R.layout.adapter_post_item, parent,false);
//			TextView textView=(TextView) convertView.findViewById(R.id.tv_name);
//			ImageView imageview=(ImageView) convertView.findViewById(R.id.iv_pic);
//			textView.setText(name[position]);
//			imageview.setImageResource(pic[position]);
//			return convertView;
//		}
//	}
}
