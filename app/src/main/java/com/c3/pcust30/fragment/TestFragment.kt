package com.c3.pcust30.fragment

import com.c3.library.view.title.IsTitleChildView
import com.c3.library.weight.toast.ShowHint
import com.c3.pcust30.R
import com.c3.pcust30.base.frag.BaseFragment

/**
 * 作者： LYJ
 * 功能： 测试视图
 * 创建日期： 2017/11/15
 */

class TestFragment : BaseFragment() {

    override fun setFragmentView(): Int = R.layout.fragment_test

    override fun setTitleLeftChildView(): IsTitleChildView? = null

    override fun onBackPressedSupport(): Boolean {
        ShowHint.warn(mContext,"点击了返回键")
        _mActivity.finish()
        System.exit(0)
        return true
    }

}
