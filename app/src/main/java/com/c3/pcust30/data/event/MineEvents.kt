package com.c3.pcust30.data.event

/**
 * 作者： LYJ
 * 功能： 事件总线
 * 创建日期： 2017/11/24
 */
sealed class MineEvents {
    class FinishActivityEvent : MineEvents()//关闭界面的通知
    class MainActivityLoadingState(var showLoading: Boolean) : MineEvents()//管理弹窗显示效果消息
    class MainChangeModule(var changeToTask: Boolean) : MineEvents()//切换模块
}


