package com.c3.library.activity;

import android.content.Intent;

import com.c3.library.R;
import com.c3.library.constant.SceneType;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 作者： LYJ
 * 功能： Activity不滑动的基类
 * 创建日期： 2017/11/7
 */

public class NonSkidBaseActivity extends SupportActivity {
    //设置默认的进出场动画类型
    private int theSceneType = SceneType.DEFAULT_TYPE;
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
    protected final void startActivity(Intent intent,int sceneType) {
        startActivity(intent);
        setSceneType(sceneType);
    }
    /**
     * 设置入场动画
     * @param intent
     */
    protected final void startActivityForResult(Intent intent, int requestCode,int sceneType) {
        startActivityForResult(intent,requestCode);
        setSceneType(sceneType);
    }

    /**
     * 修改进出场动画
     * @param sceneType
     */
    protected final void changeSceneType(int sceneType) {
        this.theSceneType = sceneType;
    }

    /**
     * 设置出场动画
     */
    @Override
    public void finish() {
        super.finish();
        setSceneType(theSceneType);
    }


    /**
     * 设置进出场动画
     * @param sceneType
     */
    private void setSceneType(int sceneType){
        if (sceneType == SceneType.CUSTOM_TYPE){
            overridePendingTransition(R.anim.window_in, R.anim.window_out);
        }else {
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
    }
}
