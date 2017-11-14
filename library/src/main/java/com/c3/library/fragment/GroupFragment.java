package com.c3.library.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c3.library.R;
import com.c3.library.weight.toast.ShowHint;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * 作者： LYJ
 * 功能： 容器级别的子布局（用来当主界面的模块分级）
 * 创建日期： 2017/11/14
 */

public class GroupFragment extends SwipeBackFragment {

    // 再点一次退出程序时间设置
    private long TOUCH_TIME = 0;
    private View rootView;//根布局
    private Class childFragClass;
    private ISupportFragment childFragObject;

    /**
     * 设置容器中的第一个子界面
     * @param fragObject
     * @param fragClass
     */
    public GroupFragment setFirstChildView(ISupportFragment fragObject, Class fragClass) {
        this.childFragClass = fragClass;
        this.childFragObject = fragObject;
        return this;
    }

    /**
     * 初始化视图
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //仅初始化视图一次
        return null == rootView ? rootView = inflater.inflate(R.layout.fragment_default_group, container, false) : rootView;
    }

    /**
     * 处理回退事件
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        } else if (System.currentTimeMillis() - TOUCH_TIME < 2000) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            ShowHint.hint(_mActivity, "再按一次退出程序");
        }
        return true;
    }

    /**
     * 加载要显示的界面
     *
     * @param savedInstanceState
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (null == findChildFragment(childFragClass)) {
            loadRootFragment(R.id.default_fragment_group, childFragObject);
        }
    }
}
