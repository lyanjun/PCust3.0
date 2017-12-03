package com.c3.pcust30.fragment.child.manage

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.c3.library.view.title.IsTitleChildView
import com.c3.pcust30.R
import com.c3.pcust30.adapter.EasyItemTypeAdapter
import com.c3.pcust30.base.frag.TopFragment
import com.c3.pcust30.data.bean.EasyModel
import kotlinx.android.synthetic.main.fragment_manage_page.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

/**
 * 作者： LYJ
 * 功能： 新增界面
 * 创建日期： 2017/11/27
 */

class ManagePageFragment : TopFragment() {
    private val items: MutableList<EasyModel> = mutableListOf()
    /**
     * 设置布局
     */
    override fun setFragmentView(): Int = R.layout.fragment_manage_page

    override fun setTitleLeftChildView(): IsTitleChildView? = null

    override fun setTitleText(): CharSequence = getString(R.string.frag_title_manage)
    /**
     * 初始化
     */
    override fun onViewCreatedInitMember(savedInstanceState: Bundle?) {
        super.onViewCreatedInitMember(savedInstanceState)
        OverScrollDecoratorHelper.setUpOverScroll(scrollGroup)
        items.add(EasyModel("个人客户查询", BitmapFactory.decodeResource(resources, R.drawable.item_type_icon_custom),
                ContextCompat.getColor(mContext, R.color.item_icon_bgd_color_c)))
        items.add(EasyModel("商户客户查询", BitmapFactory.decodeResource(resources, R.drawable.item_type_icon_merchant),
                ContextCompat.getColor(mContext, R.color.item_icon_bgd_color_m)))
        singleItem.setAdapter(EasyItemTypeAdapter(items))
    }

}