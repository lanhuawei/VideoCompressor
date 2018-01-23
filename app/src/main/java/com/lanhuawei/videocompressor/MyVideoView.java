package com.lanhuawei.videocompressor;

import android.content.Context;
import android.util.AttributeSet;

import io.vov.vitamio.widget.VideoView;

/**
 * Created by Ivan.L LanHuaWei
 * on 2018/1/17.
 */

public class MyVideoView extends VideoView {

    public MyVideoView(Context context)
    {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
//        Log.d("haha","调用onMeasure");
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if(widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY)
        {
            setMeasuredDimension(widthSize,heightSize);
        }
        else
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }


}
