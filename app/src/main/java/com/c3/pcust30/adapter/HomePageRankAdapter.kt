package com.c3.pcust30.adapter

/**
 * 作者： LYJ
 * 功能： 首页排行数据适配器
 * 创建日期： 2017/11/28
 */
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.c3.library.view.adapter.SmallVerticalAdapter
import com.c3.pcust30.R
import com.c3.pcust30.data.net.rsp.body.UserWorkInfoRsp

class HomePageRankAdapter(rankList: List<UserWorkInfoRsp.RangeRecView>, private val itemWidth: Int,
                          private val itemHeight: Int) : SmallVerticalAdapter<UserWorkInfoRsp.RangeRecView>(rankList) {
    /**
     * 设置Item的布局
     */
    override fun layoutResID(): Int = R.layout.item_home_page_rank

    /**
     * 设置itemView
     */
    override fun setItemView(itemView: View) {
        itemView.layoutParams.width = itemWidth//指定宽度
        itemView.layoutParams.height = itemHeight//指定高度
    }

    /**
     * 设置显示的数据
     */
    override fun setItemChildView(itemView: View, position: Int, entity: UserWorkInfoRsp.RangeRecView) {
        val name: TextView = itemView.findViewById(R.id.nameTv)//姓名
        val workNum: TextView = itemView.findViewById(R.id.workTv)//业务量
        val rankTv: TextView = itemView.findViewById(R.id.rankTv)//排名文字
        val rankImg: ImageView = itemView.findViewById(R.id.rankImg)//排名图标

        val rank = entity.range//获取排名名次
        val unTopColor = ContextCompat.getColor(itemView.context, R.color.black)
        val topColor = ContextCompat.getColor(itemView.context, R.color.rank_top_text_color)
        rankTv.setTextColor(unTopColor)
        when (rank) {
            1, 2, 3 -> {
                val drawableRes = itemView.context.resources
                        .getIdentifier("home_rank$rank", "drawable", itemView.context.packageName)
                rankImg.setImageResource(drawableRes)
                setItemShow(topColor, name, workNum, rankTv, rankImg, View.VISIBLE)
            }
            else -> {
                rankTv.text = if (rank == -1) "未上榜" else rank.toString()
                setItemShow(unTopColor, name, workNum, rankTv, rankImg, View.INVISIBLE)
            }
        }
        if (entity.isSelf.isNullOrBlank()) {
            name.text = entity.username//显示用户名
        } else {
            name.text = "我"//显示用户名
        }
        workNum.text = entity.custcount.toString()//显示任务量
    }

    /**
     * 设置Item显示的效果
     */
    private fun setItemShow(textColor: Int, name: TextView, workNum: TextView, rankTv: TextView, rankImg: ImageView, visible: Int) {
        rankImg.visibility = if (visible == View.VISIBLE) View.VISIBLE else View.INVISIBLE
        rankTv.visibility = if (visible == View.INVISIBLE) View.VISIBLE else View.INVISIBLE
        name.setTextColor(textColor)
        workNum.setTextColor(textColor)
    }
}
