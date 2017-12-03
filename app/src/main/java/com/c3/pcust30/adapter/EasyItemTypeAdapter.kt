package com.c3.pcust30.adapter

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.c3.library.view.adapter.SmallVerticalAdapter
import com.c3.pcust30.R
import com.c3.pcust30.data.bean.EasyModel

/**
 * 简单的栏目的数据绑定适配器
 * Created by Lyan on 17/12/3.
 */
class EasyItemTypeAdapter(data: List<EasyModel>) : SmallVerticalAdapter<EasyModel>(data) {
    override fun layoutResID(): Int = R.layout.item_signle_type

    override fun setItemChildView(itemView: View, position: Int, entity: EasyModel) {
        val itemName = itemView.findViewById<TextView>(R.id.itemLabel)
        itemName.text = entity.name
        val itemIcon = itemView.findViewById<ImageView>(R.id.itemIcon)
        (itemIcon.background as GradientDrawable).setColor(entity.bgColor)
        itemIcon.setImageBitmap(entity.img)
    }
}