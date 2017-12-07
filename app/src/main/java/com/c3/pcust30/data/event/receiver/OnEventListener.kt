package com.c3.pcust30.data.event.receiver

import com.c3.pcust30.data.event.MineEvents

/**
 * 作者： LYJ
 * 功能： 接收设置加载弹窗显示状态的信息
 * 创建日期： 2017/11/27
 */
interface OnEventListener {
    fun onEventListener(event: MineEvents)
}