package com.c3.pcust30.fragment

import com.c3.library.view.title.IsTitleChildView
import com.c3.pcust30.R
import com.c3.pcust30.base.frag.BaseFragment

/**
 * 作者： LYJ
 * 功能： 测试视图
 * 创建日期： 2017/11/15
 */

class TestFragment : BaseFragment() {

    override fun setFragmentView(): Int {
        return R.layout.fragment_test
    }

    override fun setTitleLeftChildView(): IsTitleChildView? = null
}
