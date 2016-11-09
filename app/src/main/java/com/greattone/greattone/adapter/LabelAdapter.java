package com.greattone.greattone.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.greattone.greattone.R;
import com.greattone.greattone.entity.Label;

public class LabelAdapter extends Adapter<Label> {
	int num;
	public LabelAdapter(Context context, List<Label> labelList) {
		super(context, labelList, R.layout.adapter_label);
		for (int i = 0; i < labelList.size(); i++) {
			if (labelList.get(i).isIscheck()) {
				num++;
			}
		}
	}

	public List<Label> getList() {
		List<Label>sList=new ArrayList<Label>();
		for (Label label : mlist) {
			if (label.isIscheck()) {
				sList.add(label);
			}
		}
		return sList;
	}

	public void getview(ViewHolder viewHolder, final int position, Label label) {
		final CheckBox checkBox = (CheckBox) viewHolder
				.getView(R.id.adapter_label_checkbox);
		checkBox.setText(label.getTitle());
		checkBox.setChecked(label.isIscheck());
		checkBox
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							num++;
//							if (num>1) {
////								((BaseActivity) context).toast("最多选择4种");
//								num--;
//								checkBox.setChecked(false);
//								return;
//							}
							((Label) LabelAdapter.this.mlist.get(position))
									.setIscheck(true);
							return;
						}
							num--;
							((Label) LabelAdapter.this.mlist.get(position))
							.setIscheck(false);
					}
				});
	}


}
