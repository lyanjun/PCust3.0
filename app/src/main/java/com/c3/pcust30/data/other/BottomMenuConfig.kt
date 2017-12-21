@file:Suppress("PropertyName")

package com.c3.pcust30.data.other

import com.c3.library.weight.overlay.model.MenuModel
import com.c3.pcust30.R

/**
 * 作者： LYJ
 * 功能： 菜单配置
 * 创建日期： 2017/12/21
 */

const val MENU_TITLE_INFO = "详情"
const val MENU_TITLE_CHANGE = "修改"
const val MENU_TITLE_CALL = "电话"
const val MENU_TITLE_MEET = "会面"

const val MENU_INFO = "info"
const val MENU_CHANGE = "change"
const val MENU_CALL = "call"
const val MENU_MEET = "meet"

//客户菜单
val CLIENT_MENU by lazy {
    listOf(MenuModel(MENU_TITLE_CHANGE, MENU_CHANGE, R.drawable.menu_change_icon),
            MenuModel(MENU_TITLE_CALL, MENU_CALL, R.drawable.menu_call_icon),
            MenuModel(MENU_TITLE_MEET, MENU_MEET, R.drawable.menu_meet_icon))
}

//商户菜单
val MERCHANT_MENU by lazy {
    listOf(MenuModel(MENU_TITLE_INFO, MENU_INFO, R.drawable.menu_info_icon),
            MenuModel(MENU_TITLE_CHANGE, MENU_CHANGE, R.drawable.menu_change_icon),
            MenuModel(MENU_TITLE_CALL, MENU_CALL, R.drawable.menu_call_icon),
            MenuModel(MENU_TITLE_MEET, MENU_MEET, R.drawable.menu_meet_icon))
}

