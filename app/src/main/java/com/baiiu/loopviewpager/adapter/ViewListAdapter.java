package com.baiiu.loopviewpager.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.baiiu.loopviewpager.base.BasePagerAdapter;
import java.util.List;

/**
 * author: baiiu
 * date: on 16/3/25 15:00
 * description:
 */
public class ViewListAdapter extends BasePagerAdapter<ImageView> {

    public ViewListAdapter(Context context, List<ImageView> list) {
        super(context, list);
    }

    @Override public Object instantiateItem(ViewGroup container, int position) {
        View view = onCreateView(position);
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }

        container.addView(view);
        return view;
    }

    @Override public View onCreateView(int position) {
        return list.get(position);
    }

    /**
     * 必须要复写
     */
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
