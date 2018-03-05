package com.jiainfo.demos.custom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jiainfo.demos.R;

/**
 *
 * @author LG
 * @date 2018/3/3
 */

public class CustomeView extends View {
    private Paint mPaint;
    private Bitmap mBitmap;
    private Rect mRect;
    private Path mPath;
    private String str="ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public CustomeView(Context context) {
        this(context,null);
    }

    public CustomeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.img);
        mRect=new Rect(50,50,250,250);
        mPath = new Path();
        mPath.addRect(new RectF(mRect), Path.Direction.CW);
        mPath.addCircle(300,300,150, Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.clipOutRect(mRect);
        //将内容裁剪成一个矩形
//        canvas.clipRect(mRect);
        //将内容裁剪成一个Path的形状
//        canvas.clipPath(mPath);
        //绘制圆弧
//        canvas.drawArc(new RectF(mRect),30,270,true,mPaint);
        //绘制位图
        canvas.drawBitmap(mBitmap,0,0,mPaint);
        //绘制圆
//        canvas.drawCircle(100,100,50,mPaint);
        //填充颜色
//        canvas.drawColor(Color.RED);
        //绘制线
//        canvas.drawLine(100,0,100,100,mPaint);
        //绘制椭圆
//        canvas.drawOval(100,100,200,250,mPaint);
        //绘制路径
//        canvas.drawPath(mPath,mPaint);
        //绘制点
//        canvas.drawPoint(400,400,mPaint);
        //绘制文字并指定每个文字的位置
//        canvas.drawPosText("我能",new float[]{100,100,200,200},mPaint);
        //绘制矩形
//        canvas.drawRect(mRect,mPaint);
        //绘制圆角矩形
//        canvas.drawRoundRect(new RectF(mRect),10,10,mPaint);
        //绘制文字
//        canvas.drawText("ABC",100,100,mPaint);
        //在路径上绘制文字
//        canvas.drawTextOnPath(str,mPath,0,0,mPaint);
    }
}
