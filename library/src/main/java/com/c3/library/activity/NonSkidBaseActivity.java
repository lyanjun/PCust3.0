package com.c3.library.activity;

import android.content.Intent;

import com.c3.library.R;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 作者： LYJ
 * 功能： Activity不滑动的基类
 * 创建日期： 2017/11/7
 */

public class NonSkidBaseActivity extends SupportActivity {
    /**
     * 设置Fragment进出场动画
     * @return
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    /**
     * 设置入场动画
     * @param intent
     */
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.window_in, R.anim.window_out);
    }

    /**
     * 设置出场动画
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.window_in, R.anim.window_out);
    }
}
