package com.c3.pcust30.top

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import com.c3.pcust30.R
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * 作者： LYJ
 * 功能： 一些常用的方法
 * 创建日期： 2017/12/9
 */
/**
 * 获取图片的资源ID
 */
fun getDrawableResId(resName: String, context: Context): Int {
    val applicationContext = context.applicationContext
    return applicationContext.resources.getIdentifier(resName, "drawable", applicationContext.packageName)
}

/**
 * 绑定数据和设置列表显示效果
 */
fun bindDataWithSetShowType(listView: RecyclerView, layoutManager: RecyclerView.LayoutManager,
                            listAdapter: BaseQuickAdapter<*, *>,
                            @LayoutRes emptyLayoutResID: Int = R.layout.view_data_empty) {
    listAdapter.setEmptyView(emptyLayoutResID, listView.parent as ViewGroup)
    listView.layoutManager = layoutManager
    listView.setHasFixedSize(true)
    listView.adapter = listAdapter
}

/**
 * 设置跟进状态图标
 */
fun setItemStatusIcon(stateIcon: ImageView, followStatus: String) {
    when (followStatus) {
        "1" -> stateIcon.setImageResource(R.drawable.follow_green)
        "2" -> stateIcon.setImageResource(R.drawable.follow_red)
        "3" -> stateIcon.setImageResource(R.drawable.follow_blue)
        else -> stateIcon.setImageResource(0)
    }
}