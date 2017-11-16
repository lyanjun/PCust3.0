package com.c3.library.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c3.library.activity.MineActivity;
import com.c3.library.view.title.CustomBodyView;
import com.c3.library.view.title.IsTitleView;

/**
 * 作者： LYJ
 * 功能： Fragment基类(可拓展)
 * 创建日期： 2017/11/15
 */

public abstract class ChildFragment extends BaseFragment {
    protected IsTitleView mTitleView;//标题栏
    private MineActivity mineActivity;//activity基类
    protected CustomBodyView mRootView;//根布局
    protected Context mContext;//上下文
    /**
     * 挂载Fragment到Activity上
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mineActivity = (MineActivity) context;
        this.mContext = context;
    }

    /**
     * 初始化视图
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null == mRootView ?
                mRootView = new CustomBodyView(getContext())
                        .initBodyView(setFragmentView())//添加布局
                        .setTitleShowType(setFragmentTitleShowType())//默认是添加标题栏的
                        .initTitleView(mTitleView = setTitleBarView(), mineActivity.getTitleBarHeight())//添加标题栏
                        .combination()//组合
                : mRootView;
    }
    /**
     * 在试图被创建的时候初始化相关操作
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (null != mRootView){
            //凡是对象的创建，和初始化设置都在这里进行
            onViewCreatedInitMember(savedInstanceState);//初始化成员变量
        }
    }
    /**
     * 初始化成员变量
     */
    protected abstract void onViewCreatedInitMember(Bundle savedInstanceState);

    /**
     * 设置标题栏(可覆写)
     * @return
     */
    protected IsTitleView setTitleBarView(){
        return mineActivity.setTitleBarView();
    }

    /**
     * 设置布局
     *
     * @return 布局资源ID
     */
    @LayoutRes
    public abstract int setFragmentView();

    /**
     * 设置标题栏显示效果(默认上下结构)
     *
     * @return
     */
    protected CustomBodyView.TitleShowType setFragmentTitleShowType(){
        return CustomBodyView.TitleShowType.ARRANGE;
    }

    /**
     * 设置入场动画
     *
     * @param intent
     */
    protected final void startActivity(Intent intent, int sceneType) {
        startActivity(intent);
        mineActivity.setSceneType(sceneType);
    }

    /**
     * 设置入场动画
     *
     * @param intent
     */
    protected final void startActivityForResult(Intent intent, int requestCode, int sceneType) {
        startActivityForResult(intent, requestCode);
        mineActivity.setSceneType(sceneType);
    }
}
