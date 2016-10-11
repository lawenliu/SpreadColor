package com.care.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.care.spreadcolor.CandidateItemView;
import com.care.spreadcolor.MainActivity;
import com.care.spreadcolor.R;

import java.util.ArrayList;

/**
 * Created by wliu37 on 10/6/2016.
 */

public class ColorPickerAdapter extends BaseAdapter {

    private Context mContext;
    private BaseAdapter mGameAdapter;
    private TextView mClickCountTextView;
    private int oldColorId;
    private int clickCount;

    public ColorPickerAdapter(Context context, BaseAdapter gameAdapter, TextView clickCountTextView) {
        super();

        mContext = context;
        mGameAdapter = gameAdapter;
        mClickCountTextView = clickCountTextView;
        initialize();
    }

    public void initialize() {
        oldColorId = MainActivity.gameColorIds[0][0];
        clickCount = 0;
        mClickCountTextView.setText(String.format(" %d", clickCount));
    }

    private void updateColors(int newColorId, int posX, int posY) {
        if (MainActivity.gameColorIds[posX][posY] == newColorId) {
            return;
        }

        MainActivity.gameColorIds[posX][posY] = newColorId;
        if (posX + 1 < MainActivity.candidateColorIds.length &&
                (MainActivity.gameColorIds[posX+1][posY] == oldColorId)) {
            updateColors(newColorId, posX + 1, posY);
        }

        if (posX - 1 >= 0 &&
                (MainActivity.gameColorIds[posX-1][posY] == oldColorId)) {
            updateColors(newColorId, posX - 1, posY);
        }

        if (posY + 1 < MainActivity.candidateColorIds.length &&
                (MainActivity.gameColorIds[posX][posY+1] == oldColorId)) {
            updateColors(newColorId, posX, posY+1);
        }

        if (posY - 1 >= 0 &&
                (MainActivity.gameColorIds[posX][posY-1] == oldColorId)) {
            updateColors(newColorId, posX, posY-1);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CandidateItemView itemView = (CandidateItemView)convertView;

        if (itemView == null || itemView.getCurrentIndex() != position) {
            itemView = new CandidateItemView(mContext);
            itemView.setArguments(position, MainActivity.pickerBkColorIds[position]);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickCount++;
                    mClickCountTextView.setText(String.format(" %d", clickCount));
                    if (MainActivity.candidateColorIds[position] != oldColorId) {
                        updateColors(MainActivity.candidateColorIds[position], 0, 0);
                        oldColorId = MainActivity.candidateColorIds[position];
                        mGameAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        return itemView;
    }

    @Override
    public int getCount() {
        return MainActivity.candidateColorIds.length;
    }
}
