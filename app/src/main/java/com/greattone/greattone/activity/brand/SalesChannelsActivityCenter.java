package com.greattone.greattone.activity.brand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.Listener.OnSelectCityListener;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.LabelAdapter;
import com.greattone.greattone.adapter.SalesChannelsListAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.CitySelectDialog;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Label;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.SalesChannels;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;
/**
 * 销售渠道-个人中心
 * @author makaifeng
 *
 */
public class SalesChannelsActivityCenter extends BaseActivity {


	String url;
	String editurl;

	private int page=1;
	private RadioGroup radiogroup;
	private int num=0;
	private int all=2;
	// 渠道列表
	private PullToRefreshView pull_to_refresh;
	private ListView lv_content;
	List<SalesChannels> SalesChannelsList=new ArrayList<SalesChannels>();
	String province;
	String city;
	String district;
	String keyboard;
	// 新增渠道
	private ScrollView scrollview;
	private EditText et_name,et_people,et_contact,et_address;
	private TextView tv_city;
private GridView gv_content;
private LabelAdapter adapter;
List<Label> LabelList;
List<Label> channelList=new ArrayList<Label>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sales_channels2);
		initView();
		gettype();
		getSalesChannels();
	}

	private void initView() {
		setHead("销售渠道", true, true);

		this.radiogroup = ((RadioGroup) findViewById(R.id.radiogroup));
		radiogroup.setOnCheckedChangeListener(onCheckedChangeListener);
		// 渠道列表
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);// 正文
		lv_content.setOnItemClickListener(listener);
		lv_content.setOnScrollListener(ImageLoaderUtil
				.getPauseOnScrollListener());
		addPullRefreshListener();

		// 新增渠道
		this.scrollview = ((ScrollView) findViewById(R.id.scrollview));
		this.et_name = ((EditText) findViewById(R.id.et_name));
		this.et_people = ((EditText) findViewById(R.id.et_people));
		this.et_contact = ((EditText) findViewById(R.id.et_contact));
		findViewById(R.id.ll_area).setOnClickListener(lis);
		this.tv_city = ((TextView) findViewById(R.id.tv_city));
		this.et_address = ((EditText) findViewById(R.id.et_address));
		findViewById(R.id.btn_commit).setOnClickListener(lis);
		this.gv_content = ((GridView) findViewById(R.id.gv_content));

		this.LabelList = new ArrayList<Label>();
		gv_content.setAdapter( new LabelAdapter(context, LabelList));
		radiogroup.check(R.id.radioButton1);
//		initViewData();
	}

//	protected void initViewData() {
//		m_name.setText(useMsg.getUsername());
//		m_company.setText(useMsg.getCompany());
//		m_mobile.setText(useMsg.getTelephone());
//		m_year.setText(useMsg.getChusheng());
//		m_address.setText(useMsg.getAddres());
//		m_descr.setText(useMsg.getSaytext());
//	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_commit:
				commit();

				break;
			case R.id.ll_area:
				CitySelectDialog citySelectDialog = new CitySelectDialog(
						context);
				citySelectDialog
						.setonClickSureListener(new OnSelectCityListener() {
							public void ClickSure(String province, String city,
									String district) {
								tv_city.setText(province + "," + city + ","
										+ district);
							}
						});
				citySelectDialog.show();
				break;
			default:
				break;
			}
		}
	};

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			// Intent intent=new Intent(context, ClassRoomActivity.class);
			// intent.putExtra("id", classRoomList.get(position).getUserid());
			// startActivity(intent);
		}
	};
	OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radioButton1:// 渠道列表
				pull_to_refresh.setVisibility(View.VISIBLE);
				scrollview.setVisibility(View.GONE);
				break;
			case R.id.radioButton2:// 新增渠道
				scrollview.setVisibility(View.VISIBLE);
				pull_to_refresh.setVisibility(View.GONE);
				break;

			default:
				break;
			}
		}
	};

	/** 获取销售渠道的产品类型 */
	private void gettype() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "extend/brandType");
		map.put("field", "product");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						LabelList = new ArrayList<Label>();
						String s[] = message.getData().split("\\|");
						for (String string : s) {
							if (!TextUtils.isEmpty(string)) {
								Label label = new Label();
								label.setTitle(string);
								LabelList.add(label);
							}
						}
							adapter = new LabelAdapter(context, LabelList);
							gv_content.setAdapter(adapter);
							num++;
						MyProgressDialog.Cancel(num,all);
						;
					}
				}, null));
	}

	/**
	 * 提交
	 */
	private void commit() {
		String name = this.et_name.getText().toString();
		String people = this.et_people.getText().toString();
		String contact = this.et_contact.getText().toString();
		String address = this.et_address.getText().toString();
		String city = this.tv_city.getText().toString();
		if (TextUtils.isEmpty(name)) {
			toast("请填写销售渠道名称");
			return;
		}
		List<Label> mlist = adapter.getList();
		if (mlist == null || mlist.size() == 0) {
			toast("请选择渠道经销产品");
			return;
		}
		String type = "|";
		for (Label label : mlist) {
			if (label.isIscheck()) {
				type = type + label.getTitle() + "|";
			}
		}
		if (TextUtils.isEmpty(people)) {
			toast("请填写负责人");
			return;
		}
		
		if (TextUtils.isEmpty(contact)) {
			toast("请填写联系方式");
			return;
		}

		if (TextUtils.isEmpty(city)) {
			toast("请选择渠道城市");
			return;
		}
		if (TextUtils.isEmpty(address)) {
			toast(getResources().getString(R.string.请填写详细地址));
			return;
		}
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "brand/post_channel");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		map.put("title", name);//销售渠道名称
		map.put("product", type);//渠道经销产品
		map.put("charge", people);//负责人
		map.put("phone", contact);//联系方式
		map.put("city", city);//渠道城市
		map.put("address", address);//详细地址
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						toast(message.getInfo());
						radiogroup.check(R.id.radioButton1);
						page = 1;
						SalesChannelsList.clear();
						getSalesChannels();
					}
				}, null));
	}
	/**
	 * 获取销售渠道
	 */
	private void getSalesChannels() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "brand/saleschannelslist");
		map.put("address", province);
		map.put("address1", city);
		map.put("address2", district);
		map.put("keyboard", keyboard);
		map.put("userid", getIntent().getStringExtra("userid"));
		map.put("classid", 116+"");
		map.put("pageSize", 20+"");
		map.put("pageIndex", page+"");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData()!=null&&message.getData().startsWith("[")) {
							List<SalesChannels> mList = JSON.parseArray(
									message.getData(), SalesChannels.class);
							if (mList.size() == 0) {
								toast(getResources().getString(R.string.cannot_load_more));
							}
							SalesChannelsList.addAll(mList);
						} else {
							toast(getResources().getString(R.string.cannot_load_more));
						}
						pull_to_refresh.onHeaderRefreshComplete();
						pull_to_refresh.onFooterRefreshComplete();
							initContentAdapter();
							num++;
						MyProgressDialog.Cancel(num,all);
					}
				}, null));
	}
	protected void initContentAdapter() {
		Parcelable listState = lv_content.onSaveInstanceState();
		SalesChannelsListAdapter adapter = new SalesChannelsListAdapter(context,
				SalesChannelsList);
		lv_content.setAdapter(adapter);
		lv_content.onRestoreInstanceState(listState);
	}
	/**
	 * 添加上下拉刷新功能的监听
	 */
	private void addPullRefreshListener() {
		pull_to_refresh
				.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(PullToRefreshView view) {
						page = 1;
						SalesChannelsList.clear();
						getSalesChannels();
					}
				});
		pull_to_refresh
				.setOnFooterRefreshListener(new OnFooterRefreshListener() {

					@Override
					public void onFooterRefresh(PullToRefreshView view) {
						page++;
						getSalesChannels();
					}
				});
	}
}
