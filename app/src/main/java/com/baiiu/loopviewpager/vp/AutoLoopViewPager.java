package com.baiiu.loopviewpager.vp;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import com.baiiu.loopviewpager.R;
import com.baiiu.loopviewpager.vp.autoscroll.AutoScrollViewPager;

/**
 * auther: baiiu
 * time: 16/3/26 26 21:48
 * description: 对LoopingViewPager进行包装
 * <p>
 * 1. 添加自定义属性,可以控制宽高
 * 2. to be continued
 */
public class AutoLoopViewPager extends AutoScrollViewPager {

    /**
     * 默认的宽高比,用于宽高都是wrap_content时
     */
    private static final float DEFAULT_SCALE = 0.5F;

    private float mScale = DEFAULT_SCALE;

    public AutoLoopViewPager(Context context) {
        this(context, null);
    }

    public AutoLoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoopViewPager);
        mScale = typedArray.getFloat(R.styleable.LoopViewPager_scale, DEFAULT_SCALE);
        typedArray.recycle();
    }


    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width_mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int width_size = View.MeasureSpec.getSize(widthMeasureSpec);

        int height_mode = View.MeasureSpec.getMode(heightMeasureSpec);
        int height_size = View.MeasureSpec.getSize(heightMeasureSpec);


        int width_result = width_size;
        int height_result = height_size;

        width_result = width_size;//宽度wrap_content时,size由父控件决定.总是等于parent_size,即屏幕宽度.

        if (height_mode == View.MeasureSpec.EXACTLY) {
            height_result = height_size;
        } else {
            height_result = (int) (width_result * mScale + 0.5);
        }

        int measureSpecWidth = View.MeasureSpec.makeMeasureSpec(width_result, View.MeasureSpec.EXACTLY);
        int measureSpecHeight = View.MeasureSpec.makeMeasureSpec(height_result, View.MeasureSpec.EXACTLY);

        super.onMeasure(measureSpecWidth, measureSpecHeight);
    }

    @Override public void setAdapter(PagerAdapter adapter) {
        if (adapter.getCount() == 2) {
            adapter = new AdapterWrapper(adapter);
        }
        super.setAdapter(adapter);
    }

    public int toRealCurrentItem(int position) {
        if (getAdapter() instanceof AdapterWrapper) {
            return position % 2;
        }
        return position;
    }

    public int getRealCount() {
        PagerAdapter adapter = super.getAdapter();
        if (adapter instanceof AdapterWrapper) {
            return 2;
        }

        return adapter.getCount();
    }


}
