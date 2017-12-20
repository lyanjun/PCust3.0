package com.c3.library.weight.overlay.adapter;

import android.support.annotation.Nullable;

import com.c3.library.R;
import com.c3.library.weight.overlay.model.MenuModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 作者： LYJ
 * 功能： 底部菜单项弹窗的数据适配器
 * 创建日期： 2017/12/20
 */

public class BottomMenuAdapter extends BaseQuickAdapter<MenuModel, BaseViewHolder> {

    public BottomMenuAdapter(@Nullable List<MenuModel> data) {
        super(R.layout.item_bottom_menu, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuModel item) {
        helper.setText(R.id.menu_title, item.getMenuName());
        helper.setImageResource(R.id.menu_icon, item.getDrawableRes());
    }
}
