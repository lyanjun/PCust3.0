package com.c3.pcust30.base.frag

import android.os.Bundle

/**
 * 作者： LYJ
 * 功能： 一级界面
 * 创建日期： 2017/11/27
 */
abstract class TopFragment : BaseFragment() {
    /**
     * 初始化(默认不可滑动退出)
     */
    override fun onViewCreatedInitMember(savedInstanceState: Bundle?) {
        super.onViewCreatedInitMember(savedInstanceState)
        setSwipeBackEnable(false)//关闭滑动
    }
}