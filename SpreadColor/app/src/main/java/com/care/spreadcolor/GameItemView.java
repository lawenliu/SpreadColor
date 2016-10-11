package com.care.spreadcolor;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by wliu37 on 10/7/2016.
 */

public class GameItemView extends LinearLayout {
    private int mCurrentIndex = 0;

    private ImageView mGameItemView;

    public GameItemView(Context context) {
        super(context);

        InitView(context);
    }

    public void InitView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_game_item, this);

        mGameItemView = (ImageView) findViewById(R.id.id_game_item);
    }

    public void setArguments(int curIndex, int resID) {
        mCurrentIndex = curIndex;
        mGameItemView.setBackgroundColor(getResources().getColor(resID));
    }

    public int getCurrentIndex() {
        return mCurrentIndex;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // This is the key that will make the height equivalent to its width
    }
}
