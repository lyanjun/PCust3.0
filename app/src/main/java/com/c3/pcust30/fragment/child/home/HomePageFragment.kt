package com.c3.pcust30.fragment.child.home

import com.c3.library.view.title.IsTitleChildView
import com.c3.pcust30.R
import com.c3.pcust30.base.frag.TopFragment

/**
 * 作者： LYJ
 * 功能： 首页
 * 创建日期： 2017/11/27
 */

class HomePageFragment : TopFragment() {
    /**
     * 设置布局
     */
    override fun setFragmentView(): Int = R.layout.fragment_home_page

    override fun setTitleLeftChildView(): IsTitleChildView? = null
    /**
     * 设置标题
     */
    override fun setTitleText(): CharSequence = getString(R.string.frag_title_home)

}
