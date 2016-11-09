package com.greattone.greattone.widget;

import java.util.ArrayList;
import java.util.List;

import com.greattone.greattone.R;
import com.greattone.greattone.entity.Label;
import com.greattone.greattone.util.DisplayUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;
/**
 * 多选框列表
 * @author makaifeng
 *
 */
public class CheckBoxListView extends FlowLayout {
	List<Label> labelList;
	int num=0;
	int max=0;
    public CheckBoxListView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
        init();
    }  


	public CheckBoxListView(Context context, AttributeSet attrs) {  
	        super(context, attrs);  
	        init();
	}  
	
	public CheckBoxListView(Context context) {  
	        super(context);  
	        init();
	}  
	@SuppressWarnings("deprecation")
	private void init() {
		if (labelList!=null) {
			for (final Label label : labelList) {
				if (label.isIscheck()) {
					num++;
				}
				final CheckBox checkBox =new CheckBox(getContext());
				checkBox.setLayoutParams(new LayoutParams((DisplayUtil.getScreenWidth(getContext())-DisplayUtil.dip2px(getContext(), 10*2+10*2+10*2))/4, LayoutParams.WRAP_CONTENT));
				checkBox.setTextAppearance(getContext(),R.style.RegisterCheckboxTheme);
				checkBox.setText(label.getTitle());
				checkBox.setChecked(label.isIscheck());
				checkBox
						.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
							public void onCheckedChanged(CompoundButton buttonView,
									boolean isChecked) {
								if (isChecked) {
									num++;
									if (num==max) {
//										((BaseActivity) context).toast("最多选择4种");
										num--;
										buttonView.setChecked(false);
										return;
									}
									label.setIscheck(true);
									return;
								}
									num--;
									label.setIscheck(false);
							}
						});
				addView(checkBox);
			}
		}
	}
	
	/**
	 * 加载列表
	 * @param labelList
	 */
	public void setList(List<Label> labelList){
		this.labelList=labelList;
		max=labelList.size();
		num=0;
		init();
	}
	/**
	 * 获取全部数据
	 * @return
	 */
	public List<Label> getList(){
		return labelList;
	}
	/**
	 * 获取选中的数据
	 * @return
	 */
	public List<String> getCheckList(){
		List<String> mlist=new ArrayList<String>();
		for (Label label : labelList) {
			if (label.isIscheck()) {
				mlist.add(label.getTitle());
			}
		}
		return mlist;
	}
	/**
	 * 获取选中的数据
	 * @param split 已该符号分割
	 * @return 带符号分割符号的字符串
	 */
	public String getCheckList(String split){
		StringBuffer string = new StringBuffer();
		string.append(split);
		for (Label label : labelList) {
			if (label.isIscheck()) {
				string.append(label.getTitle());
				string.append(split);
			}
		}
		return string.toString();
	}
}
