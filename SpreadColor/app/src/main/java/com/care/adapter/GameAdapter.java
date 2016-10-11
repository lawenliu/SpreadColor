package com.care.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.care.spreadcolor.GameItemView;
import com.care.spreadcolor.MainActivity;

import java.util.Date;
import java.util.Random;

/**
 * Created by wliu37 on 10/6/2016.
 */

public class GameAdapter extends BaseAdapter {

    private Context mContext;

    public GameAdapter(Context context) {
        super();

        mContext = context;
        initializeColors();
    }

    public void initializeColors() {
        Random r = new Random(new Date().getTime());
        for (int i = 0; i < MainActivity.candidateColorIds.length; i++) {
            for (int j = 0; j < MainActivity.candidateColorIds.length; j++) {
                int index = r.nextInt(MainActivity.candidateColorIds.length);
                MainActivity.gameColorIds[i][j] = MainActivity.candidateColorIds[index];
            }
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
        GameItemView itemView = (GameItemView)convertView;

        if (itemView == null || itemView.getCurrentIndex() != position) {
            itemView = new GameItemView(mContext);
        }

        itemView.setArguments(position, MainActivity.gameColorIds[position / MainActivity.candidateColorIds.length][position % MainActivity.candidateColorIds.length]);

        return itemView;
    }

    @Override
    public int getCount() {
        return MainActivity.candidateColorIds.length * MainActivity.candidateColorIds.length;
    }
}
