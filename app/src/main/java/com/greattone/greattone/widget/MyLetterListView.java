package com.greattone.greattone.widget;

import com.greattone.greattone.util.DisplayUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyLetterListView extends View {

	OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	String[] b = { "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
			"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
			"Y", "Z" };
	int choose = -1;
	Paint paint = new Paint();
	public boolean showBkg = false;
	float textSize = DisplayUtil.sp2px(getContext(), 15);

	public MyLetterListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyLetterListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyLetterListView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// if(showBkg){
		// canvas.drawColor(Color.parseColor("#40000000"));
		// }

		int height = getHeight();
		int width = getWidth();
		int singleHeight = height / b.length;
		for (int i = 0; i < b.length; i++) {
			paint.setColor(Color.parseColor("#3f5b78"));
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setTextSize(textSize);
			paint.setAntiAlias(true);
			if (i == choose) {
				paint.setColor(Color.parseColor("#77bbff"));
				paint.setFakeBoldText(true);
			}
			float xPos = width / 2 - paint.measureText(b[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(b[i], xPos, yPos, paint);
			paint.reset();
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();

		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * b.length);

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			showBkg = true;
			if (oldChoose != c && listener != null) {
				if (c >= 0 && c < b.length) {
					listener.onTouchingLetterChanged(b[c]);
					choose = c;
					invalidate();
				}
			}

			break;
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != c && listener != null) {
				if (c >= 0 && c < b.length) {
					listener.onTouchingLetterChanged(b[c]);
					choose = c;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			showBkg = false;
			final float x = event.getX();
			if (x > 0 && x < getWidth()) {
			listener.onClick(b[c]);
			}
			listener.onTouchUp();
			choose = -1;
			invalidate();
			break;
		}
		return true;
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	public interface OnTouchingLetterChangedListener {
		/**
		 * 改变选择的字母时
		 * @param s  字母
		 */
		public void onTouchingLetterChanged(String s);
		/**当手指抬起时*/
		public void onTouchUp();
		/**当选中某个字母时*/
		/**
		 * 当选中某个字母时
		 * 		是指抬起时，手指在某个字母上面
		 * @param s 字母
		 */
		public void onClick(String s);
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}
}
