package com.c3.pcust30.base.frag

import android.os.Bundle
import me.yokeyword.fragmentation.anim.DefaultNoAnimator

/**
 * 作者： LYJ
 * 功能： 一级界面
 * 创建日期： 2017/11/27
 */
abstract class TopFragment : BaseFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentAnimator = DefaultNoAnimator()
    }
    /**
     * 初始化(默认不可滑动退出)
     */
    override fun onViewCreatedInitMember(savedInstanceState: Bundle?) {
        super.onViewCreatedInitMember(savedInstanceState)
        setSwipeBackEnable(false)//关闭滑动
    }
}