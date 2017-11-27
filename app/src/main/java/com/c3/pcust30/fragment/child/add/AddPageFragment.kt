package com.c3.pcust30.fragment.child.add

import com.c3.library.view.title.IsTitleChildView
import com.c3.pcust30.R
import com.c3.pcust30.base.frag.TopFragment

/**
 * 作者： LYJ
 * 功能： 新增界面
 * 创建日期： 2017/11/27
 */

class AddPageFragment : TopFragment() {
    /**
     * 设置布局
     */
    override fun setFragmentView(): Int = R.layout.fragment_add_page

    override fun setTitleLeftChildView(): IsTitleChildView? = null

    override fun setTitleText(): CharSequence = getString(R.string.frag_title_add)
}