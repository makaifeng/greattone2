package com.greattone.greattone.activity.post;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.PostGridAdapter;
import com.greattone.greattone.data.Constants;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.entity.Column;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Picture;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.MyGridView;
import com.kf_test.picselect.GalleryActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostYuePuActivity extends BaseActivity {
	private EditText et_theme;
	private LinearLayout ll_type;
	private TextView tv_type;
	private EditText et_content;
	int type=GalleryActivity.TYPE_PICTURE;
	private MyGridView gv_pic;
	private PostGridAdapter adapter;
	String mid = "36";
	String classid = "38";
	String filepass = System.currentTimeMillis()+"";
	private TextView tv_style;
	private LinearLayout ll_style;
	private EditText et_author;
	List<String> typeList  = new ArrayList<String>();
	List<Column> classList  = new ArrayList<Column>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_yuepu);
		initView();
		getType();
	}
	
	private void initView() {
		setHead("发布乐谱", true, true);
		setOtherText(getResources().getString(R.string.发送), lis);

		et_theme = (EditText) findViewById(R.id.et_theme);
		ll_type = (LinearLayout) findViewById(R.id.ll_type);
		ll_type.setOnClickListener(lis);
		tv_type = (TextView) findViewById(R.id.tv_type);
		ll_style = (LinearLayout) findViewById(R.id.ll_style);
		ll_style.setOnClickListener(lis);
		  tv_style = (TextView) findViewById(R.id.tv_style);
		et_content = (EditText) findViewById(R.id.et_content);
		et_author = (EditText) findViewById(R.id.et_author);
		gv_pic = (MyGridView) findViewById(R.id.gv_pic);
		adapter=new PostGridAdapter(context, type,Constants.MAXPAGE);
		gv_pic.setAdapter(adapter);
		
		ImageView iv_gg = (ImageView) findViewById(R.id.iv_gg);
		iv_gg.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, screenWidth/4));
		ImageLoaderUtil.getInstance().setImagebyurl(getIntent().getStringExtra("ggUrl"), iv_gg);
	}
	private OnClickListener lis=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ll_type://类型
				List<String> mlist=new ArrayList<String>();
			for (Column col : classList) {
					mlist.add(col.getName());
			}
				  NormalPopuWindow		popu1 = new NormalPopuWindow(context, mlist,
						  ll_type);
					popu1.setOnItemClickBack(new NormalPopuWindow.OnItemClickBack() {

						public void OnClick(int position, String text) {
							tv_type.setText(text);
							classid=classList.get(position).getClassid()+"";
						}
					});
					 popu1.show();
				break;
			case R.id.ll_style://风格
				  NormalPopuWindow		popu = new NormalPopuWindow(context, typeList,
						  ll_style);
					popu.setOnItemClickBack(new NormalPopuWindow.OnItemClickBack() {

						public void OnClick(int position, String text) {
							tv_style.setText(text);
						}
					});
					 popu.show();
				break;
			case R.id.tv_head_other://发送
				post();
				break;

			default:
				break;
			}
		}
	};
	private void getType() {
		MyProgressDialog.show(context);
		HttpProxyUtil.getYuepuType(context, new ResponseListener() {

			@Override
			public void setResponseHandle(Message2 message) {
				if (message.getData() != null && message.getData().startsWith("{")) {
					 typeList = JSON.parseArray( JSON.parseObject(message.getData()).getString("type"), String.class);
					 typeList.remove("全部");
					 classList = JSON.parseArray(JSON.parseObject(message.getData()).getString("class"), Column.class);
					 for (int i = 0; i < classList.size(); i++) {
						if (classList.get(i).getName().equals("全部")) {
							classList.remove(i);
						}
					}
				}
				MyProgressDialog.Cancel();
			}}, null);
	}
//	private ArrayList<String> pictureUrlList=new ArrayList<String>();
	private HashMap<String, String> map;
	protected String photo="";
	/**发帖*/
	protected void post() {
		 String title = et_theme.getText().toString().trim();
		 String newstext = et_content.getText().toString().trim();
		 String style = tv_style.getText().toString().trim();
		 String type = tv_type.getText().toString().trim();
		 String author = et_author.getText().toString().trim();
		filepass= System.currentTimeMillis()+"";
		final ArrayList<Picture> pictureFileList=adapter.getList();
		if (pictureFileList.size()==0) {
			toast(getResources().getString(R.string.请选择图片));
			return;
		}
		if (title.isEmpty()) {
			toast("请填写标题");
			return;
		}
//		if (newstext.isEmpty()) {
//			toast(getResources().getString(R.string.请填写内容));
//			return;
//		}
		if (TextUtils.isEmpty(style)) {
			toast("请选择风格");
			return;
		}
		if (TextUtils.isEmpty(type)) {
			toast("请选择类型");
			return;
		}
		 map = new HashMap<String, String>();
		 map.put("zuozhe", author);
		 map.put("fengge", style);
		 map.put("type", classid);
		 map.put("title", title);
		 map.put("smalltext", newstext);
		//发送图片
		MyProgressDialog.show(context);
		for (int i = 0; i < pictureFileList.size(); i++) {
			final int num=i;
			HttpProxyUtil.updatePictureByCompress2(context, filepass, classid, pictureFileList.get(i).getPicUrl(),	new ResponseListener() {

				@Override
				public void setResponseHandle(Message2 message) {
			String picUrl = JSON.parseObject(message.getData()).getString(
							"url");
			photo=photo+picUrl+"::::::";
			if (num+1== pictureFileList.size()) {
				photo.substring(0, photo.length()-6);
				map.put("photo", photo);
				post1();

			}
				}
			}, null);
		}
	}
	protected void post1() {
		map.put("api", "post/ecms");
		map.put("enews", "MAddInfo");
		map.put("mid", mid);
		map.put("classid", classid);
		map.put("open", "1");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {
			
			@Override
			public void setResponseHandle(Message2 message) {
				toast(message.getInfo());
				MyProgressDialog.Cancel();
				finish();
			}
		}, null));
	}
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data){
//		super.onActivityResult( requestCode,  resultCode,  data);
//		if (resultCode==RESULT_OK&&requestCode==1) {//图片
//			 ArrayList<String>  mList=data.getStringArrayListExtra(Constants.EXTRA_PHOTO_PATHS);
//		 pictureFileList.addAll(mList);
//		 adapter.setList(pictureFileList);
//		}else if (resultCode==RESULT_OK&&requestCode==0) {//拍照片
//			ArrayList<String>  mList=new ArrayList<String>();
//			mList.add(FileUtil.getLocalImageUrl(context,  "icon.png"));
//			pictureFileList.addAll(mList);
//			adapter.setList(pictureFileList);
//		}else if (resultCode==RESULT_OK&&requestCode==101) {//地址
//			
//		}
//	}
}
