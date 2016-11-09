package com.greattone.greattone.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseFragment;

public class MusicFragment extends BaseFragment {
	/**
	 * fragment 主布局
	 */
	private View rootView;
//	/**
//	 * 屏幕宽度
//	 */
//	private int screenWidth;
	
	String text;
//	public MusicFragment(String text) {
//		this.text=text;
//	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			rootView = inflater.inflate(R.layout.fragment_music, container,
					false);// 关联布局文件
//			screenWidth = ((BaseActivity) getActivity()).screenWidth;
			initView();
			text=getArguments().getString("text");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rootView;
	}

	/**
	 * 加载视图
	 */
	private void initView() {
		// 标题栏
		TextView textView = (TextView) rootView.findViewById(R.id.text);
		if (text!=null) {
			textView.setText(text);
		}
	}
}
