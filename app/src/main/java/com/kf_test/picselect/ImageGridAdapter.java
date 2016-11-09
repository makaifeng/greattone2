package com.kf_test.picselect;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.greattone.greattone.R;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.ImageLoader;
import com.greattone.greattone.util.ImageLoaderUtil;

public class ImageGridAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<ImageBean> mDatas = null;
	private ChoseImageListener mChoseImageListener = null;
	boolean canSelectImage;
	int type;
	int selectedCount;
	private int maxSelect;
	private int mWidth;
	public ImageGridAdapter(Context c, boolean canSelectImage, int type,int selectedCount) {
		this.canSelectImage = canSelectImage;
		this.type = type;
		inflater = LayoutInflater.from(c);
		this.	selectedCount=selectedCount;
        int screenWidth = DisplayUtil.getScreenWidth(c);
        mWidth = (screenWidth - DisplayUtil.dip2px(c, 4))/3;
	}

	@Override
	public int getCount() {
		if (mDatas == null) {
			return 0;
		} else {
			return mDatas.size();
		}
	}

	@Override
	public ImageBean getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	public int getSelectedCount() {
		return selectedCount;
	}

	public void swapDatas(ArrayList<ImageBean> images) {

		if (this.mDatas != null) {
			this.mDatas.clear();
		}
		this.mDatas = images;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	        if(position==0) {
	    		convertView = inflater.inflate(R.layout.item_camera_layout, parent,false);
	            convertView.setTag(null);
//	            //设置高度等于宽度
	            GridView.LayoutParams lp = new GridView.LayoutParams(mWidth, mWidth);
	            convertView.setLayoutParams(lp);
	        }else{
	            ViewHolder holder = null;
				if (convertView == null) {
					convertView = inflater.inflate(R.layout.image_grid_item, parent,false);
					holder = new ViewHolder();
					holder.imgQueue = (ImageView) convertView
							.findViewById(R.id.imgQueue);
					holder.iv_play = (ImageView) convertView
							.findViewById(R.id.iv_play);
					holder.imgQueueMultiSelected = (ImageView) convertView
							.findViewById(R.id.cb_select_tag);
//					holder.layerView = convertView.findViewById(R.id.v_selected_frame);
					convertView.setTag(holder);
		
				} else {
					holder = (ViewHolder) convertView.getTag();
					if (holder==null) {
						convertView = inflater.inflate(R.layout.image_grid_item, parent,false);
						holder = new ViewHolder();
						holder.imgQueue = (ImageView) convertView
								.findViewById(R.id.imgQueue);
						holder.iv_play = (ImageView) convertView
								.findViewById(R.id.iv_play);
						holder.imgQueueMultiSelected = (ImageView) convertView
								.findViewById(R.id.cb_select_tag);
//						holder.layerView = convertView.findViewById(R.id.v_selected_frame);
						convertView.setTag(holder);
					}
					if (holder.imgQueueMultiSelected!=null) {
						holder.imgQueueMultiSelected.setOnClickListener(null);
					}
				}
				holder.setPosition(position-1);
	    }
		return convertView;
	}

	public class ViewHolder {
		ImageView iv_play;
		ImageView imgQueue;
		ImageView imgQueueMultiSelected;
//		View layerView;

		void setPosition(int position) {
			if (!canSelectImage) {
				imgQueueMultiSelected.setVisibility(View.GONE);
			}
			try {
			if (type==GalleryActivity.TYPE_VIDEO) {
				iv_play.setVisibility(View.VISIBLE);
				ImageLoaderUtil.getInstance().setImagebyurl(
						"file://" + mDatas.get(position).getPath(), imgQueue);
			}else {
				ImageLoader.getInstance().display( mDatas.get(position).getPath(), imgQueue,
						mWidth, mWidth);
				iv_play.setVisibility(View.GONE);
			}
				if (mDatas.get(position).isSeleted()) {
					imgQueueMultiSelected
					.setSelected(true);
//					layerView
//							.setBackgroundResource(R.color.image_selected_color);
//					if (mChoseImageListener!=null) {
//						selectedCount++;
//						mChoseImageListener.onSelected( mDatas.get(position), selectedCount);
//					}
				} else {
					imgQueueMultiSelected
							.setSelected(false);
//					layerView.setBackgroundResource(android.R.color.transparent);
				}

				imgQueueMultiSelected
						.setOnClickListener(new MultiSelectListener(position));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void clear() {
		mDatas.clear();
		notifyDataSetChanged();
	}

	private class MultiSelectListener implements OnClickListener {

		private int position = -1;

		public MultiSelectListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			if (mChoseImageListener == null) {
				return;
			}
			ImageBean image = getItem(position);
			if (image.isSeleted()) {
				v.setSelected(false);
//				((ImageView) v).setImageResource(R.drawable.image_check_off);
				image.setSeleted(false);
				selectedCount--;
				mChoseImageListener.onCancelSelect(image, selectedCount);
			} else {
				if (selectedCount >= maxSelect) {
					return;
				}
//				((ImageView) v).setImageResource(R.drawable.image_check_on);
				v.setSelected(true);
				image.setSeleted(true);
				selectedCount++;
				mChoseImageListener.onSelected(image, selectedCount);
			}
		}

	}

	public void setMaxSelect(int maxSelect) {
		this.maxSelect = maxSelect;
	}

	public void setChoseImageListener(ChoseImageListener listener) {
		this.mChoseImageListener = listener;
	}

}
