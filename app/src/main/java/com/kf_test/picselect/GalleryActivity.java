package com.kf_test.picselect;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.greattone.greattone.R;
import com.greattone.greattone.Listener.PhotoCallBack;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.Constants;
import com.greattone.greattone.dialog.MyHintPopupWindow;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.FileUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("Override")
public class GalleryActivity extends BaseActivity {
	String FILE_PATH;
	ImageBean video=new ImageBean();
	private ArrayList<ImageBean> mImages = null;
	private LinearLayout numberLayout = null;
	private TextView previewTextView = null;
	private ImageView useButton = null;
	private int selectedCount = 0;
	private int limit = Integer.MAX_VALUE;
	private GridView gridView;
	Context context;
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (position == 0&&type==TYPE_PICTURE) {
				toCamera();
				}
		}
	};
	private ChoseImageListener mViewImageListener = new ChoseImageListener() {

		@Override
		public void onSelected(ImageBean image, int selectedCount) {
			GalleryActivity.this.selectedCount = selectedCount;
			refreshPreviewTextView();
		}

		@Override
		public void onCancelSelect(ImageBean image, int selectedCount) {
			GalleryActivity.this.selectedCount = selectedCount;
			refreshPreviewTextView();
		}
	};
	private TextView tv_select_pic;
	private int type;
	public static final int TYPE_PICTURE = 0;
	public static final int TYPE_MUSIC = 1;
	public static final int TYPE_VIDEO = 2;
	private ArrayList<String> fileList = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pick_images);
		context = this;
		// getSupportActionBar().setIcon(R.drawable.empty_icon);
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// getSupportActionBar().setTitle(R.string.please_choose_pic);
		type = getIntent().getIntExtra("type", 0);
		fileList = getIntent().getStringArrayListExtra(
				Constants.EXTRA_SELECTED_PICTURES);
		if (fileList == null) {
			fileList = new ArrayList<String>();
		}
		selectedCount = fileList.size();
		if (type == TYPE_PICTURE) {
			setHead("选择图片", true, true);
		} else if (type == TYPE_MUSIC) {
			setHead("选择音乐", true, true);
		} else if (type == TYPE_VIDEO) {
			setHead("选择视频", true, true);
		}
		numberLayout = (LinearLayout) findViewById(R.id.ll_picture_count);

		previewTextView = (TextView) findViewById(R.id.tv_preview_image);
		tv_select_pic = (TextView) findViewById(R.id.tv_select_pic);
		useButton = (ImageView) findViewById(R.id.btn_ok);
		useButton.setEnabled(false);
		useButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putStringArrayListExtra(Constants.EXTRA_PHOTO_PATHS,
						getSelectedImagePaths());
				if (type==TYPE_VIDEO) {
					if (video.getSize()>1024*1024*500) {
						toast("该视频大于500M，请选择其他视频");
						return;
					}
					intent.putExtra("size", video.getSize());
				}
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		// if (type == TYPE_PICTURE) {
		// mImages = FileUtil.getImagesList(context);
		// addSelectedPic();
		// } else if (type == TYPE_VIDEO) {
		// mImages = FileUtil.getVideoList(context);
		// }

		gridView = (GridView) findViewById(R.id.gridView);
		int numColumns = (getResources().getDisplayMetrics().widthPixels - DisplayUtil
				.dip2px(context, 6)) / DisplayUtil.dip2px(context, 116);
		gridView.setNumColumns(numColumns);
		gridView.setVerticalScrollBarEnabled(false);
		getImages();

	}
/***
 * 跳转到相机
 */
	protected void toCamera() {
		if (Build.VERSION.SDK_INT >= 23) {
			int checkCallPhonePermission = ContextCompat.checkSelfPermission(
					getApplicationContext(),
					Manifest.permission.CAMERA);
			if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat
						.requestPermissions(
								this,
								new String[] { Manifest.permission.CAMERA },
								Constants. REQUEST_CODE_CAMERA);
				return;
			}
		}
		if (type == TYPE_PICTURE) {
			String imgName = System.currentTimeMillis() + ".png";
			Intent intent = new Intent();
			// 指定开启系统相机的Action
			intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.addCategory(Intent.CATEGORY_DEFAULT);
			FILE_PATH = FileUtil.getLocalImageUrl(context, imgName);
			// 根据文件地址创建文件
			File file = new File(FILE_PATH);
			// 设置系统相机拍摄照片完成后图片文件的存放地址
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			((Activity) context).startActivityForResult(intent, 0);
		} else if (type == TYPE_MUSIC) {
		} else if (type == TYPE_VIDEO) {
			Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			((Activity) context).startActivityForResult(intent, 0);
			// Intent intent=new Intent(context,
			// MediaRecorderDemoActivity.class);
			// ((Activity) context).startActivityForResult(intent,0);
		}
	
		
	}

	/**
	 * 获取图片或者视频数据
	 */
	private void getImages() {
		if (Build.VERSION.SDK_INT >= 23) {
			int checkCallPhonePermission = ContextCompat.checkSelfPermission(
					getApplicationContext(),
					Manifest.permission.WRITE_EXTERNAL_STORAGE);
			if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat
						.requestPermissions(
								this,
								new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
								Constants. REQUEST_CODE_READ_EXTERNAL_STORAGE);
				return;
			}
		}
		new PhotosTask(context, type, back).execute();
	}

	PhotoCallBack back = new PhotoCallBack() {

		@Override
		public void initImageBean(List<ImageBean> mImages) {
			GalleryActivity.this.mImages = (ArrayList<ImageBean>) mImages;
			if (type == TYPE_PICTURE) {
				addSelectedPic();
			}else
			if (type == TYPE_VIDEO) {
				MyHintPopupWindow.build(context, "请选择5分钟以内或500M以下的视频，拍摄视频请尽量选择低品质，以免超出大小", true).show();
			}
			initAdapter();
		}
	};

	private void initAdapter() {
		limit = getIntent().getIntExtra(Constants.EXTRA_PHOTO_LIMIT,
				Integer.MAX_VALUE);
		 ImageGridAdapter mAdapter;
		 ImageGridAdapter2 mAdapter2;
		if (type==TYPE_VIDEO) {
			mAdapter2 = new ImageGridAdapter2(context, true, type, selectedCount);
			mAdapter2.setChoseImageListener(mViewImageListener);
			mAdapter2.swapDatas(mImages);
			  mAdapter2.setMaxSelect(limit);
			gridView.setAdapter(mAdapter2);
		}else{
			  mAdapter = new ImageGridAdapter(context, true, type, selectedCount);
			  mAdapter.setChoseImageListener(mViewImageListener);
			  mAdapter.swapDatas(mImages);
			  mAdapter.setMaxSelect(limit);
			  gridView.setAdapter(mAdapter);
		}
		refreshPreviewTextView();
		PauseOnScrollListener listener = new PauseOnScrollListener(
				ImageLoader.getInstance(), true, true);
		gridView.setOnScrollListener(listener);
		gridView.setOnItemClickListener(itemClickListener);
	}

	private void addSelectedPic() {
		for (ImageBean ib : mImages) {
			if (fileList.contains(ib.getPath())) {
				ib.setSeleted(true);
			}
		}
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		// getSupportLoaderManager().destroyLoader(0);
		// getSupportFragmentManager().removeOnBackStackChangedListener(this);
	}

	// private ArrayList<ImageBean> getSelectedImages() {
	// ArrayList<ImageBean> selectedImages = new ArrayList<ImageBean>();
	// for (ImageBean image : mImages) {
	// if (image.isSeleted()) {
	// selectedImages.add(image);
	// }
	// }
	// return selectedImages;
	// }

	private ArrayList<String> getSelectedImagePaths() {
		ArrayList<String> selectedImages = new ArrayList<String>();
		for (ImageBean image : mImages) {
			if (image.isSeleted()) {
				selectedImages.add(image.getPath());
				if (type==TYPE_VIDEO) {
					video=image;
				}else{
					video=new ImageBean();
				}
			}
		}
		return selectedImages;
	}

	// @Override
	// public boolean onSelected(ImageBean image) {
	// if (selectedCount >= limit) {
	// Toast.makeText(getApplicationContext(), R.string.arrive_limit_count,
	// Toast.LENGTH_SHORT).show();
	// return false;
	// }
	// image.setSeleted(true);
	// selectedCount++;
	// refreshPreviewTextView();
	// return true;
	// }
	//
	// @Override
	// public boolean onCancelSelect(ImageBean image) {
	// image.setSeleted(false);
	// selectedCount--;
	// refreshPreviewTextView();
	// return true;
	// }
	//
	/**
	 * 加载选择数 tv_select_pic
	 */
	private void refreshPreviewTextView() {
		if (selectedCount <= 0) {
			tv_select_pic.setText("已选择");
			numberLayout.setVisibility(View.GONE);
			previewTextView.setText("");
			useButton.setEnabled(false);
		} else {
			if (selectedCount == limit) {
				tv_select_pic.setText("已选满");
			} else {
				tv_select_pic.setText("已选择");
			}
			numberLayout.setVisibility(View.VISIBLE);
			previewTextView.setText(selectedCount + "");
			useButton.setEnabled(true);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 相机拍照完成后，返回图片路径
		if (requestCode == 0) {
			if (resultCode == Activity.RESULT_OK) {
				getImages();
			} else {
			}
		}
	}
	  @Override
	  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
	      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	      if (requestCode == Constants. REQUEST_CODE_READ_EXTERNAL_STORAGE) {
	          if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
	        		getImages();
	          } else {
	            toast("无法打开相册");
	            finish();
	          }
	      }else if (requestCode == Constants. REQUEST_CODE_CAMERA) {
	          if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
	             toCamera();
	          } else {
	        	  toast("无法打开相机");
	          }
		}
	  }
}
