package com.care.spreadcolor;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by wliu37 on 10/7/2016.
 */

public class CandidateItemView extends LinearLayout {
    private int mCurrentIndex = 0;

    private ImageView mCandidateItemView;

    public CandidateItemView(Context context) {
        super(context);

        InitView(context);
    }

    public void InitView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_candidate_item, this);

        mCandidateItemView = (ImageView) findViewById(R.id.id_candidate_item);
    }

    public void setArguments(int curIndex, int resID) {
        mCurrentIndex = curIndex;
        mCandidateItemView.setBackground(getResources().getDrawable(resID));
    }

    public int getCurrentIndex() {
        return mCurrentIndex;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // This is the key that will make the height equivalent to its width
    }
}
