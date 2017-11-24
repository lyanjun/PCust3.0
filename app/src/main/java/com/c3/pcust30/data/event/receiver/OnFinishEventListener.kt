package com.c3.pcust30.data.event.receiver

import com.c3.pcust30.data.event.MineEvents

/**
 * 作者： LYJ
 * 功能： 关闭界面的指令
 * 创建日期： 2017/11/24
 */
interface OnFinishEventListener {
    //接收关闭指令
    fun finishAtPresentView(event: MineEvents.FinishActivityEvent)
}