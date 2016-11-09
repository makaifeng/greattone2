/*
 *  Copyright 2011 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.greattone.greattone.wheel;

import java.util.List;

import android.content.Context;

/**
 * Wheel items adapter interface
 */
public class ArrayWheelViewAdapter extends AbstractWheelTextAdapter {
	List<String> list;
int currentPosition;
	public ArrayWheelViewAdapter(Context context, List<String> list) {
		super(context);
		this.list = list;
	}

	@Override
	public CharSequence getItemText(int index) {
		if (index >= 0 && index < list.size()) {
			return list.get(index);
		}
		return null;
	}

	@Override
	public int getItemsCount() {
		return list.size();
	}


//	@Override
//	public View getItem(int index, View convertView, ViewGroup parent) {
//		if (index >= 0 && index < getItemsCount()) {
//			if (convertView == null) {
//				convertView = new TextView(context);
//			}
//			TextView textView = (TextView) convertView;
//			if (textView != null) {
//				CharSequence text = list.get(index).getName();
//				if (text == null) {
//					text = "";
//				}
//				textView.setText(text);
//				textView.setTextColor(0xFF101010);
//				textView.setGravity(Gravity.CENTER);
//					textView.setTextSize(15);
//				textView.setLines(1);
//				textView.setEllipsize(TruncateAt.END);
//				textView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
//			}
//			return convertView;
//		}
//		return null;
//	}

}
