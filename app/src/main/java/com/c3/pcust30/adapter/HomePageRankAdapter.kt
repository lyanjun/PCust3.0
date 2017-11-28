package com.c3.pcust30.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.c3.pcust30.R
import com.c3.pcust30.data.net.rsp.body.UserWorkInfoRsp

/**
 * 作者： LYJ
 * 功能： 首页排行数据适配器
 * 创建日期： 2017/11/28
 */

class HomePageRankAdapter(private val rankList: List<UserWorkInfoRsp.RangeRecView>) : BaseAdapter() {

    override fun getCount(): Int = rankList.size

    override fun getItem(position: Int): Any = rankList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val holder: ViewHolder?
        val view: View?
        if (null == convertView) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_page_rank, parent, false)
            holder = ViewHolder(view.findViewById(R.id.nameTv), view.findViewById(R.id.workTv),
                    view.findViewById(R.id.rankTv), view.findViewById(R.id.rankImg))
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }
        //展示数据
        val rankEntity = rankList[position]
        val rank = rankEntity.range//获取排名名次
        val unTopColor = ContextCompat.getColor(parent.context, R.color.black)
        val topColor = ContextCompat.getColor(parent.context, R.color.rank_top_text_color)
        holder.rankTv.setTextColor(unTopColor)
        when (rank) {
            1, 2, 3 -> {
                val drawableRes = parent.context.resources
                        .getIdentifier("home_rank$rank", "drawable", parent.context.packageName)
                holder.rankImg.setImageResource(drawableRes)
                setItemShow(topColor, holder, View.VISIBLE)
            }
            else -> {
                holder.rankTv.text = if (rank == -1) "未上榜" else rank.toString()
                setItemShow(unTopColor, holder, View.INVISIBLE)
            }
        }
        if (rankEntity.isSelf.isNullOrBlank()) {
            holder.name.text = rankEntity.username//显示用户名
        } else {
            holder.name.text = "我"//显示用户名
        }
        holder.workNum.text = rankEntity.custcount.toString()//显示任务量
        return view
    }

    /**
     * 设置Item显示的效果
     */
    private fun setItemShow(textColor: Int, holder: ViewHolder, visible: Int) {
        holder.rankImg.visibility = if (visible == View.VISIBLE) View.VISIBLE else View.INVISIBLE
        holder.rankTv.visibility = if (visible == View.INVISIBLE) View.VISIBLE else View.INVISIBLE
        holder.name.setTextColor(textColor)
        holder.workNum.setTextColor(textColor)
    }

    /**
     * 复用类
     */
    private class ViewHolder(val name: TextView, val workNum: TextView,
                             val rankTv: TextView, val rankImg: ImageView)
}
