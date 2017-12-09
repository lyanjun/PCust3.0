package com.c3.pcust30.fragment.child.manage

import com.c3.pcust30.R
import com.c3.pcust30.base.frag.BaseFragment

/**
 * 作者： LYJ
 * 功能： 客户管理界面
 * 创建日期： 2017/12/9
 */
class ClientManagementFragment : BaseFragment() {

    override fun setFragmentView(): Int = R.layout.fragment_client_management

    override fun setTitleText(): CharSequence = resources.getString(R.string.frag_title_client)
}
