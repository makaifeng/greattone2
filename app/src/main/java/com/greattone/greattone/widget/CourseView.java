package com.greattone.greattone.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.greattone.greattone.Enum.EnumTime;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.entity.TimeTable_Day;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
    * Created by Administrator on 2016/10/31.
            */
    public class CourseView extends View {
        private  String TAG="CourseView";
        private String textTime[]=new String[]{"6:00","7:00","8:00","9:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00"};
        EnumSet<EnumTime> currEnumSet = EnumSet.allOf(EnumTime.class);
        private int width;
        private int height;
        private Paint textPaint,linePaint;
        private int textWidth;
        private  int Padding=(int)dip2px(15);//边距
    private  int mheight=(int)dip2px(12);//每格的高度
    private  int linePadding=(int)dip2px(12);//上边距
    private int lineY;
    private List<TimeTable_Day> courseList=new ArrayList();
    private List<RectF> rectFList=new ArrayList();
    private RectF cRect=new   RectF();
    private int courseX,courseY,courseEndX,courseEndY;//课程框的上下左右的位置
    AdapterView.OnItemClickListener OnItemClickListener;
    private boolean isMy=false;
    public CourseView(Context context) {
        this(context, null);
    }


    public CourseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CourseView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }



    /**
     * 添加课程数据
     * @param courseList
     */
    public void setCouseList(List<TimeTable_Day> courseList){
        this.courseList=courseList;
        invalidate();
    }
    public void setIsMy(boolean isMy) {
        this.isMy=isMy;
    }
    private void init() {
        initTextAndLinePaint();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width =w-oldw;
        height =h-oldh;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawTextAndLine(canvas);
        drawCourse(canvas);
        super.onDraw(canvas);
    }

    /**
     * 绘画课程控件
     * @param canvas
     */
    private void drawCourse(Canvas canvas) {
        rectFList.clear();
         courseX=textWidth;
         courseEndX= (int) (width-Padding);
        for (int i=0;i<courseList.size();i++){
           int sPosition = 0;
           int ePosition = 0;

            for (EnumTime enumTime : currEnumSet) {
                 sPosition=enumTime.getTime().equals(courseList.get(i).getStarttime())?enumTime.getPosition():sPosition;
                ePosition=enumTime.getTime().equals(courseList.get(i).getStoptime())?enumTime.getPosition():ePosition;
            }
             courseY=(int)(linePadding+sPosition* mheight);
             courseEndY=(int)(linePadding+ePosition* mheight);

            drawCourseBackground(canvas,courseList.get(i).getState());
            drawCourseText(canvas,courseList.get(i));

        }
    }

    /**
     *绘画课程的文字
     * @param canvas
     */
    private void drawCourseText(Canvas canvas,TimeTable_Day TimeCouse) {
        //课程框中心位置
        int centerX=(courseEndX-courseX)/2+courseX;
        int centerY=(courseEndY-courseY)/2+courseY;
        //课程名字
        Paint textPaint=new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setTextSize(sp2px(14));
        int textWidth1= (int) textPaint.measureText(TimeCouse.getCouname());
        canvas.drawText(TimeCouse.getCouname(),centerX-textWidth1/2,centerY-textPaint.descent(),textPaint);
        //学生名字
        String name="";
        if (isMy){
             name=TimeCouse.getStuname();
        }else{
            if (Data.myinfo.getUsername().equals(TimeCouse.getStuname())){
                name=TimeCouse.getStuname();
            }else { name="有课";}
        }

         textPaint=new Paint();
        textPaint.setColor(Color.DKGRAY);
        textPaint.setTextSize(sp2px(12));
        int textWidth2= (int) textPaint.measureText(name);
        int textHeight2= (int) ((Math.ceil(textPaint.descent() - textPaint.ascent()) + 2));
        canvas.drawText(name,centerX-textWidth2/2,centerY+textHeight2,textPaint);
        //开始时间
         textPaint=new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(sp2px(12));
        canvas.drawText("开始："+TimeCouse.getStarttime(),courseX+dip2px(20),centerY-textPaint.descent(),textPaint);
        //结束时间
        canvas.drawText("结束："+TimeCouse.getStoptime(),courseX+dip2px(20),(centerY+ (int) ((Math.ceil(textPaint.descent() - textPaint.ascent()) + 2))),textPaint);
//        //状态
//        String text="";
//        if (TimeCouse.getState()==0)  text="已完成";
//        else if (TimeCouse.getState()==1)  text="未开始";
//        else if (TimeCouse.getState()==2) text="已取消";
//        Rect Rect=new Rect();
//        textPaint.getTextBounds(text,0,text.length()-1,Rect);
//        canvas.drawText(text,courseEndX-dip2px(20)-textPaint.measureText(text),centerY,textPaint);

    }

    /**
     * 绘画课程的背景
     * @param canvas
     */
    private void drawCourseBackground(Canvas canvas,int state) {

        cRect=new RectF();
        cRect.set(courseX,courseY,courseEndX,courseEndY);
        Paint coursePaint=new Paint();
        if (state==0)   coursePaint.setColor(Color.rgb(255,165,165));
        else if (state==1)    coursePaint  .setColor(Color.rgb(144,247,154));
        else if (state==2)   { coursePaint.setColor(Color.BLACK);   coursePaint.setStrokeWidth(2);coursePaint.setStyle(Paint.Style.STROKE);  }
        rectFList.add(cRect);
        canvas.drawRect(cRect,coursePaint);
    }

    /**
     * 绘画时间和分割线
     */
    private void drawTextAndLine(Canvas canvas) {
         textWidth=(int)(textPaint.measureText(textTime[0])+Padding*2);
       lineY= (int)(Padding-(textPaint.ascent()+ textPaint.descent())/2);
        int textHight= (int) dip2px(48);
        for (int i=0;i<textTime.length;i++){
            canvas.drawText(textTime[i],Padding,(Padding+textHight*i),textPaint);
            canvas.drawLine(textWidth,linePadding+textHight*i, width-Padding,linePadding+textHight*i,linePaint);
        }
    }

    /**
     *加载文字和直线的画笔
     */
    public void initTextAndLinePaint() {
        textPaint=new Paint();
        textPaint.setTextSize(sp2px(15));
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setColor(Color.rgb(165,165,170));
        linePaint=new Paint();
        linePaint.setColor(Color.rgb(211,211,211));
    }

    boolean isDown=false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                isDown=false;
                //获取屏幕上点击的坐标
                float x = event.getX();
                float y = event.getY();
                //如果坐标在我们的文字区域内，则将点击的文字改颜色
                for (int i = 0; i < rectFList.size(); i++){
                    RectF rect=rectFList.get(i);
                    if (rect.contains((int)x,(int)y)) {
                        isDown=true;
                        invalidate();//更新视图
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                //获取屏幕上点击的坐标
                float upx = event.getX();
                float upy = event.getY();
                for (int i = 0; i < rectFList.size(); i++){
                    RectF rect=rectFList.get(i);
                    if (rect.contains((int)upx,(int)upy)&&isDown) {
                        if (OnItemClickListener!=null)
                            OnItemClickListener.onItemClick(null,CourseView.this,i,i);
                        invalidate();//更新视图
                        return true;
                    }
                }

        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, (int) (dip2px(48)*textTime.length));
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener OnItemClickListener){
        this.OnItemClickListener=OnItemClickListener;
    }
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public  float px2dip(float pxValue) {
         float scale = getContext().getResources().getDisplayMetrics().density;
        return  (pxValue / scale + 0.5f);
    }
    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public float dip2px(float dipValue) {
        return (dipValue
                * getContext().getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public  float sp2px(float spValue) {
        return (spValue * getContext().getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }

}
