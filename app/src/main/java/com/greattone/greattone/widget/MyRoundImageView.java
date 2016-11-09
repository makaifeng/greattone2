package com.greattone.greattone.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.greattone.greattone.R;


public class MyRoundImageView extends ImageView {

	private int mWidth;
	private int mRadius;
	private int radius;
	private BitmapShader mBitmapShader;
	private Paint mPaint = new Paint();
	private Paint mPaint2 = new Paint();
	private Matrix mMatrix = new Matrix();
	private RectF mRect = new RectF();
	private RectF mRect2 = new RectF();
	private int mDifferRadius = 5;
	private int differRadius = 0;
int frame=0;
int color=0;
	public MyRoundImageView(Context context) {
		this(context, null);
	}

	public MyRoundImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyRoundImageView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.MyRoundImageView, defStyleAttr, 0);
		differRadius = a.getInt(R.styleable.MyRoundImageView_differ_radius, 0);
		a.recycle();
	}

	/**
	 * 设置BitmapShader
	 */
	private void setBitmapShader() {
		Drawable drawable = getDrawable();
		if (null == drawable) {
			return;
		}
		Bitmap bitmap = drawableToBitmap(drawable);
		if (bitmap==null) {
			return;
		}
		// 将bitmap作为着色器来创建一个BitmapShader
		mBitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
		float scale = 1.0f;
		// // if (mType == TYPE_CIRCLE) {
		// // 拿到bitmap宽或高的小值
		// int bSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
		// scale = mWidth * 1.0f / bSize;

		// } else if (mType == TYPE_ROUND || mType == TYPE_OVAL) {
		// //
		// 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
		scale = Math.max((getWidth() )* 1.0f / bitmap.getWidth(), (getHeight() )
				* 1.0f / bitmap.getHeight());
		// }
		// shader的变换矩阵，我们这里主要用于放大或者缩小
		mMatrix.setScale(scale, scale);
		// 设置变换矩阵
		mBitmapShader.setLocalMatrix(mMatrix);
		mPaint.setShader(mBitmapShader);

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onDraw(Canvas canvas) {

		if (null == getDrawable()) {
			return;
		}
		setBitmapShader();
		// if (mType == TYPE_CIRCLE) {
		// canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
		// } else if (mType == TYPE_ROUND) {
		// mPaint.setColor(Color.RED);
		if (color==0) {
			mPaint2.setColor(getResources().getColor(android.R.color.white));
		}else {
			mPaint2.setColor(getResources().getColor(color));
		}
		canvas.drawRoundRect(mRect, mRadius - mDifferRadius, mRadius
				- mDifferRadius, mPaint2);
		canvas.drawRoundRect(mRect2, mRadius - mDifferRadius, mRadius
				- mDifferRadius, mPaint);
		// }else if(mType == TYPE_OVAL){
		// canvas.drawOval(mRect, mPaint);
		// }
	}

	@Override
	protected boolean setFrame(int l, int t, int r, int b) {
		mWidth = Math.min(r - l, b - t);
		if (radius == 0) {
			mRadius = mWidth / 2;
		} else {
			mRadius = radius ;
		}
		if (differRadius == 0) {
		} else {
			mDifferRadius = differRadius;
		}
		mRect.set(0, 0, mWidth, mWidth);
		mRect2.set(frame, frame, mWidth-frame, mWidth-frame);
		return super.setFrame(l, t, r, b);
	}

	private Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		} else if (drawable instanceof NinePatchDrawable) {
			Bitmap bitmap = Bitmap
					.createBitmap(
							drawable.getIntrinsicWidth(),
							drawable.getIntrinsicHeight(),
							drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
									: Bitmap.Config.RGB_565);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			drawable.draw(canvas);
			return bitmap;
		} else {
			return null;
		}
	}
/**
 * 
 * @param differRadius
 */
	public void setDifferRoundRadius(int differRadius) {
		this.differRadius = differRadius;
	}
/**
 * 设置圆角大小  px值
 * @param mRadius
 */
	public void setRadius(int mRadius) {
		this.radius = mRadius;
	}
	/**
	 * 设置边框大小
	 * @param frame
	 */
	public void setFrame(int frame) {
		this.frame = frame;
	}
	/**
	 * 设置边框颜色
	 * @param color
	 */
	public void setFrameColor(int color) {
		this.color = color;
	}
//	private int dip2Px(float dip) {
//		return (int) (dip
//				* getContext().getResources().getDisplayMetrics().density + 0.5f);
//	}

}
