package com.c3.pcust30.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.c3.pcust30.data.net.rsp.body.UserWorkInfoRsp
/**
 * 作者： LYJ
 * 功能： 首页排行数据适配器
 * 创建日期： 2017/11/28
 */

class HomePageRankAdapter(private val rankList: List<UserWorkInfoRsp.RangeRecView>) : BaseAdapter() {

    override fun getCount(): Int {
        return rankList.size
    }

    override fun getItem(position: Int): Any {
        return rankList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View? {
        var holder:ViewHolder?
        if (null == convertView){
            holder = ViewHolder()
            convertView?.tag = holder

        }else{
            holder = convertView.tag as ViewHolder
        }
        return convertView
    }

    /**
     * 复用类
     */
    private class ViewHolder{
        var name: TextView? = null
        var workNum: TextView? = null
        var rankTv: TextView? = null
        var rankImg: ImageView? = null
    }
}
