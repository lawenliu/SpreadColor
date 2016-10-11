package com.care.spreadcolor;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.care.adapter.ColorPickerAdapter;
import com.care.adapter.GameAdapter;
import com.care.core.Constants;
import com.care.core.SharedDataManager;
import com.care.core.Utilities;
import com.flurry.android.FlurryAgent;

public class MainActivity extends AppCompatActivity {

    public static Integer[] candidateColorIds = {
            R.color.color_red, R.color.color_green, R.color.color_pink,
            R.color.color_gold, R.color.color_cyan, R.color.color_blue
    };
    public static Integer[] pickerBkColorIds = {
            R.drawable.circle_red, R.drawable.circle_green, R.drawable.circle_pink,
            R.drawable.circle_gold, R.drawable.circle_cyan, R.drawable.circle_blue
    };

    public static Integer[][] gameColorIds = {
            { 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0 }
    };

    private MediaPlayer mMediaPlayerMenu = null;
    private BaseAdapter mGameAdapter = null;
    private GridView mGameGridView = null;
    private BaseAdapter mCandidateAdapter = null;
    private GridView mCandidateGridView = null;


    private RelativeLayout mMainActivity = null;
    private ImageView mMenuImageView = null;
    private TextView mClickCountTextView = null;

    private PopupWindow mPopupMenu = null;
    private TextView mNewGameTextView = null;
    private TextView mBkPickTextView = null;
    private TextView mToolbarTextView = null;
    private TextView mRateUsTextView = null;

    private RelativeLayout mAdBannerLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGameGridView = (GridView) findViewById(R.id.id_game_gridview);
        mGameAdapter = new GameAdapter(this);
        mGameGridView.setAdapter(mGameAdapter);

        mClickCountTextView = (TextView) findViewById(R.id.id_click_count);
        mCandidateGridView = (GridView) findViewById(R.id.id_candidate_gridview);
        mCandidateAdapter = new ColorPickerAdapter(this, mGameAdapter, mClickCountTextView);
        mCandidateGridView.setAdapter(mCandidateAdapter);

        mMainActivity = (RelativeLayout) findViewById(R.id.id_main_activity);
        mMenuImageView = (ImageView) findViewById(R.id.id_btn_menu);
        mGameGridView = (GridView) findViewById(R.id.id_game_gridview);
        // Flurry Initialize
        mAdBannerLayout = (RelativeLayout) findViewById(R.id.id_ad_banner);

        mMediaPlayerMenu = MediaPlayer.create(this, R.raw.btn_sound_1);
        mMediaPlayerMenu.setLooping(false);

        initBackground();
        initPopupMenu(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();

        initBackground();
        FlurryAgent.onStartSession(this);
    }

    @Override
    public void onStop() {
        FlurryAgent.onEndSession(this);

        super.onStop();
    }

    private void initBackground() {
        int indexBK = SharedDataManager.getInstance().getCurrentBackgroundIndex();
        mMainActivity.setBackgroundResource(Utilities.getResId("bk" + (indexBK + 1), R.drawable.class));
    }

    private void initPopupMenu(final Context context) {
        View menuContent = View.inflate(this, R.layout.menu_popup, null);
        mNewGameTextView = (TextView)menuContent.findViewById(R.id.menu_new_game);
        mBkPickTextView = (TextView)menuContent.findViewById(R.id.menu_color_screen);
        mToolbarTextView = (TextView)menuContent.findViewById(R.id.menu_tool_bar);
        mRateUsTextView = (TextView)menuContent.findViewById(R.id.menu_rate_us);

        mPopupMenu = new PopupWindow(menuContent, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupMenu.setFocusable(true);
        mPopupMenu.setOutsideTouchable(true);
        mPopupMenu.setBackgroundDrawable(new ColorDrawable());
        mPopupMenu.update();

        mMenuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAndDismissPopupMenu();
                mMediaPlayerMenu.start();
            }
        });
        mNewGameTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showAndDismissPopupMenu();
                ((GameAdapter)mGameAdapter).initializeColors();
                ((ColorPickerAdapter)mCandidateAdapter).initialize();
                mGameAdapter.notifyDataSetChanged();
            }
        });
        mBkPickTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAndDismissPopupMenu();
                Intent intent = new Intent(context, BkImageSelectActivity.class);
                startActivity(intent);
            }
        });
        mToolbarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mRateUsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAndDismissPopupMenu();
                Utilities.launchAppStoreDetail(context);
            }
        });
    }


    private void showAndDismissPopupMenu() {
        if(mPopupMenu.isShowing()) {
            mPopupMenu.dismiss();
        } else {
            mPopupMenu.showAsDropDown(mMenuImageView);
        }
    }
}
