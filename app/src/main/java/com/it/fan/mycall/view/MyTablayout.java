package com.it.fan.mycall.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.it.fan.mycall.R;
import com.it.fan.mycall.gloable.TableLayoutCallbck;

public class MyTablayout extends View {

    private String[] contentArr;
    private int radius = 0;
    private int selectColor;
    private int unselectColor;
    private int selectTextColor;
    private int unselectTextColor;
    private int textSize;
    private Paint mSelectTextPaint,mUnSelectTextPaint;
    private Paint selectPaint,unselectPaint;
    private Paint.FontMetrics mFontMetrics;
    private int mWidth;
    private int mHeight;
    private boolean isCheckedLeft = true;
    private boolean isLeftArrow = false;//true 向上 false向下
    private boolean isRightArrow = false;
    private TableLayoutCallbck mCallbck;

    public MyTablayout(Context context) {
        this(context,null);
    }

    public MyTablayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTablayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTablayout);
        String content = typedArray.getString(R.styleable.MyTablayout_tb_content_arr);
        if(!TextUtils.isEmpty(content)){
            contentArr = content.split(",");
        }
        radius = (int) typedArray.getDimension(R.styleable.MyTablayout_tb_CornerRadius,5);
        selectColor = typedArray.getColor(R.styleable.MyTablayout_tb_select_color, 0);
        unselectColor = typedArray.getColor(R.styleable.MyTablayout_tb_unselect_color, 0);
        selectTextColor = typedArray.getColor(R.styleable.MyTablayout_tb_select_text_color, 0);
        unselectTextColor = typedArray.getColor(R.styleable.MyTablayout_tb_unselect_text_color, 0);
        textSize = (int) typedArray.getDimension(R.styleable.MyTablayout_tb_text_size, 15);
        typedArray.recycle();

        mSelectTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mSelectTextPaint.setColor(selectTextColor);
        mSelectTextPaint.setTextSize(textSize);
        mUnSelectTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mUnSelectTextPaint.setTextSize(textSize);
        mUnSelectTextPaint.setColor(unselectTextColor);

        selectPaint = new Paint();
        selectPaint.setColor(selectColor);
        selectPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        selectPaint.setAntiAlias(true);
        unselectPaint = new Paint();
        unselectPaint.setColor(unselectColor);
        unselectPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        unselectPaint.setAntiAlias(true);
        mFontMetrics = mSelectTextPaint.getFontMetrics();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defaultWidth = getDefaultWidth();
        int defaultHeight = getDefaultHeight();
        setMeasuredDimension(getExpectSize(defaultWidth,widthMeasureSpec),getExpectSize(defaultHeight,heightMeasureSpec));
    }

    /**
     * get default height when android:layout_height="wrap_content"
     */
    private int getDefaultHeight() {
        float perHight = mFontMetrics.bottom - mFontMetrics.top ;
        return (int) (perHight) + getPaddingTop() + getPaddingBottom();
    }

    /**
     * get default width when android:layout_width="wrap_content"
     */
    private int getDefaultWidth() {
        int totalPadding = getPaddingRight() + getPaddingLeft();
        if(contentArr != null){
            return (int) (mSelectTextPaint.measureText(contentArr[0]) + totalPadding);
        }
        return (int) totalPadding;
    }

    private int getExpectSize(int size, int measureSpec){
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(size, specSize);
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawLeft(canvas);
        drawRight(canvas);
    }

    private void drawLeft(Canvas canvas) {
        Path path = new Path();
        path.moveTo(mWidth/2,0);
        path.lineTo(mWidth/2,mHeight);
        path.lineTo(radius,mHeight);
        path.arcTo(new RectF(0f,mHeight-2*radius,2*radius,mHeight),90,90,false);
        path.lineTo(0,radius);
        path.arcTo(new RectF(0,0,radius*2,radius*2),180,90,false);
        path.close();
        if(isCheckedLeft){
            canvas.drawPath(path,selectPaint);
            if(contentArr != null){
                Bitmap arrow = BitmapFactory.decodeResource(getResources(), isCheckedLeft ? R.mipmap.icon_white_arrow:R.mipmap.icon_blue_arrow);
                float offset = -(mSelectTextPaint.ascent() + mSelectTextPaint.descent()) * 0.5f;
                canvas.drawText(contentArr[0],mWidth/4 - mSelectTextPaint.measureText(contentArr[0])/2,mHeight/2+offset,mSelectTextPaint);
                float l = mWidth/4+mSelectTextPaint.measureText(contentArr[0])/2+10;
                int t = mHeight/2-arrow.getHeight()/2;
                if(isLeftArrow){
                    canvas.save();
                    canvas.rotate(180,l+arrow.getWidth()/2,t+arrow.getHeight()/2);
                    canvas.drawBitmap(arrow,l,t,selectPaint);
                    canvas.restore();
                } else {
                    canvas.drawBitmap(arrow,l,t,selectPaint);
                }
            }
        } else {
            canvas.drawPath(path,unselectPaint);
            if(contentArr != null){
                Bitmap arrow = BitmapFactory.decodeResource(getResources(), !isCheckedLeft? R.mipmap.icon_blue_arrow:R.mipmap.icon_white_arrow);
                float offset = -(mUnSelectTextPaint.ascent() + mUnSelectTextPaint.descent()) * 0.5f;
                canvas.drawText(contentArr[0],mWidth/4 - mUnSelectTextPaint.measureText(contentArr[0])/2,mHeight/2+offset,mUnSelectTextPaint);
                canvas.drawBitmap(arrow,mWidth/4+mUnSelectTextPaint.measureText(contentArr[0])/2+10,mHeight/2-arrow.getHeight()/2,unselectPaint);
            }
        }

    }

    private void drawRight(Canvas canvas){
        Path path = new Path();
        path.moveTo(mWidth/2,0);
        path.lineTo(mWidth/2,mHeight);
        path.lineTo(mWidth-radius,mHeight);
        path.arcTo(new RectF(mWidth-2*radius,mHeight-2*radius,mWidth,mHeight),90,-90,false);
        path.lineTo(mWidth,radius);
        path.arcTo(new RectF(mWidth-2*radius,0,mWidth,2*radius),0,-90,false);
        path.close();
        if(isCheckedLeft){
            canvas.drawPath(path,unselectPaint);
            if(contentArr != null){
                Bitmap arrow = BitmapFactory.decodeResource(getResources(), isCheckedLeft? R.mipmap.icon_blue_arrow : R.mipmap.icon_white_arrow);
                float offset = -(mUnSelectTextPaint.ascent() + mUnSelectTextPaint.descent()) * 0.5f;
                canvas.drawText(contentArr[1],mWidth*3/4 - mUnSelectTextPaint.measureText(contentArr[1])/2,mHeight/2+offset,mUnSelectTextPaint);
                canvas.drawBitmap(arrow,mWidth*3/4 + mUnSelectTextPaint.measureText(contentArr[1])/2 + 10,mHeight/2-arrow.getHeight()/2,unselectPaint);
            }
        } else {
            canvas.drawPath(path,selectPaint);
            if(contentArr != null){
                Bitmap arrow = BitmapFactory.decodeResource(getResources(), !isCheckedLeft ? R.mipmap.icon_white_arrow:R.mipmap.icon_blue_arrow);
                float offset = -(mSelectTextPaint.ascent() + mSelectTextPaint.descent()) * 0.5f;
                float l = mWidth*3/4 + mSelectTextPaint.measureText(contentArr[1])/2 + 10;
                float t = mHeight/2-arrow.getHeight()/2;
                canvas.drawText(contentArr[1],mWidth*3/4 - mSelectTextPaint.measureText(contentArr[1])/2,mHeight/2+offset,mSelectTextPaint);
                if(isRightArrow){
                    canvas.save();
                    canvas.rotate(180,l+arrow.getWidth()/2,t+arrow.getHeight()/2);
                    canvas.drawBitmap(arrow,l,t,selectPaint);
                    canvas.restore();
                } else {
                    canvas.drawBitmap(arrow,l,t,selectPaint);
                }


            }
        }

    }

    public void restoreArrow(){
        isLeftArrow = false;
        isRightArrow = false;
        invalidate();
    }

    public void setmCallbck(TableLayoutCallbck mCallbck) {
        this.mCallbck = mCallbck;
    }

    public void setCheckedLeft(boolean checked){
        isCheckedLeft = checked;
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getX() < mWidth/2){
                    if(isCheckedLeft){
                        isLeftArrow = true;
                    } else {
                        isLeftArrow = false;
                    }
                    isCheckedLeft = true;
                } else {
                    if(!isCheckedLeft){
                        isRightArrow = true;
                    } else {
                        isRightArrow = false;
                    }
                    isCheckedLeft = false;
                }
                invalidate();
                if(mCallbck != null){
                    mCallbck.checked(isCheckedLeft);
                    mCallbck.arrow(isCheckedLeft,isLeftArrow,isRightArrow);
                }
                break;
        }
        return true;
    }

}
