package com.example.android.annotationtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.nio.file.Paths;
import java.util.ArrayList;

public class PaintView extends View implements View.OnTouchListener {
    private final int maxSize=100;
    private final int minSize=5;
    private final int deftSize=10;
    private final int deftColor=Color.GREEN;
    private int mdotSize;
    private int mPenColor;
    private Path mPath;
    private Path eraserPath;
    private Paint eraserPaint;
    private Paint mPaint;
    private Float mX, mY;
    private Canvas mcanvas;
    private ArrayList<Path> mPaths;
    private ArrayList<Paint> mPaints;

    private boolean isEraserMode;//related to switch
    class PathBean {
        Path path;
        Paint paint;

        PathBean(Path path, Paint paint) {
            this.path = path;
            this.paint = paint;
        }
    }


    public PaintView(Context context) {
        super(context);
        this.init();
    }

    public PaintView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        mcanvas = new Canvas();
        this.init();
    }

    public PaintView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        this.mdotSize=deftSize;
        this.mPenColor=deftColor;


        mPaths=new ArrayList<>();
        mPaints=new ArrayList<>();
        this.mPath=new Path();
        this.eraserPath=new Path();
        this.addPath();
        this.setOnTouchListener(this);
        this.mX=this.mY=(float) 0.0; }

    private void addPath() {
        //setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint=new Paint();
        mPath=new Path();
        eraserPath=new Path();
        if (isEraserMode){
            mPaths.add(eraserPath);
            mPaints.add(eraserPaint);
           // mPaints.add(mPaint);
        }
        else {
            mPaths.add(mPath);
            mPaints.add(mPaint);}



        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mPenColor);
        mPaint.setStrokeWidth(mdotSize);}



    public int getDotSize(){
        return mdotSize;
    }


    public void setPenColor(int penColor) {
        this.mPenColor = penColor;

    }

    public void changeSize(int add){
        this.mdotSize+= add;
        this.mdotSize=Math.max(mdotSize, minSize);
        this.mdotSize=Math.min(mdotSize, maxSize);
    }

    public void reset() {
        this.init();
        this.invalidate();
    }


   public void setErase(boolean isEraserMode)
    { this.isEraserMode = isEraserMode;
   if (isEraserMode) {
       eraserPaint=new Paint();
       eraserPaint.setStrokeWidth(mdotSize);
       eraserPaint.setStyle(Paint.Style.STROKE);
       eraserPaint.setColor(Color.TRANSPARENT);
       eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

     /*  mPaint=new Paint();
       mPaint.setStrokeWidth(mdotSize);
       mPaint.setStyle(Paint.Style.STROKE);
       mPaint.setColor(Color.TRANSPARENT);
       mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));*/


   } else {
       eraserPaint.setXfermode(null);
     // mPaint.setXfermode(null);
 }
 }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (!isEraserMode) {
            commonTouchEvent(event);
            invalidate();
        } else {
            eraserTouchEvent(event);
            invalidate();
        }
        invalidate();
        return true;}

    private void eraserTouchEvent(MotionEvent event) {
        mX=event.getX();
        mY=event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.addPath();
                this.eraserPath.moveTo(mX,mY);

                break;

            case MotionEvent.ACTION_MOVE:

                this.eraserPath.lineTo(mX,mY);

                break;

            case MotionEvent.ACTION_UP:
                mcanvas.drawPath(eraserPath, eraserPaint);
               // eraserPath.reset();

                break;        }
        this.invalidate();

    }

    private void commonTouchEvent(MotionEvent event) {
        mX=event.getX();
        mY=event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                this.addPath();
                this.mPath.moveTo(mX,mY);
                break;


            case MotionEvent.ACTION_MOVE:
                this.mPath.lineTo(mX,mY);
                break;

            case MotionEvent.ACTION_UP:
                mcanvas.drawPath(mPath, mPaint);//将路径绘制在mBitmap上
                Path path = new Path(mPath);//复制出一份mPath
                Paint paint = new Paint(mPaint);
                mPaints.add(paint);

                mPaths.add(path);//将路径对象存入集合
                mPath.reset();
                break;        }
        this.invalidate();

    }


    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

   // if (!isEraserMode) {
    //    if (null != mPath) {
            for (int i=0; i<mPaths.size(); ++i)
               canvas.drawPath(mPaths.get(i),mPaints.get(i));}
              //
               // canvas.drawPath(mPath, mPaint);
        //   }
    //  } else {
     //     if (null != eraserPath) {
         //     for (int i=0; i<mPaths.size(); ++i)
        //         canvas.drawPath(mPaths.get(i),mPaints.get(i));
           //canvas.drawPath(eraserPath, eraserPaint);
   //      }}
  //      }
    }


//}
