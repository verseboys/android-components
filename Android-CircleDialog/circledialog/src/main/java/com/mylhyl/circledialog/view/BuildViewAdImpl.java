package com.mylhyl.circledialog.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import com.mylhyl.circledialog.internal.CircleParams;

/**
 * 广告
 * Created by hupei on 2019/1/11 11:10.
 */
public final class BuildViewAdImpl extends AbsBuildView {
    private BodyAdView mBodyAdView;

    public BuildViewAdImpl(Context context, CircleParams params) {
        super(context, params);
    }

    @Override
    public void buildRootView() {
        mRoot = createLinearLayout();
    }

    @Override
    public void buildBodyView() {
        buildRootView();
        buildTitleView();
        if (mBodyAdView == null) {
            mBodyAdView = new BodyAdView(mContext, mParams);
            addViewByBody(mBodyAdView);
        }
    }

    @Override
    public BodyAdView getBodyView() {
        return mBodyAdView;
    }

    @Override
    public void refreshContent() {

    }

    public static class SelectorPointDrawable extends StateListDrawable {

        public SelectorPointDrawable(int color, int size) {

            //选中
            GradientDrawable selectedDrawable = new GradientDrawable();
            selectedDrawable.setShape(GradientDrawable.OVAL);
            selectedDrawable.setColor(color);
            selectedDrawable.setSize(size, size);

            //默认
            GradientDrawable defaultDrawable = new GradientDrawable();
            defaultDrawable.setShape(GradientDrawable.OVAL);
            defaultDrawable.setStroke(2, color);
            defaultDrawable.setSize(size, size);

            addState(new int[]{android.R.attr.state_selected}, selectedDrawable);
            addState(new int[]{-android.R.attr.state_selected}, defaultDrawable);
        }
    }
}
