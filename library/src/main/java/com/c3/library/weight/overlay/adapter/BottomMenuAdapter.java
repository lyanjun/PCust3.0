package com.c3.library.weight.overlay.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.c3.library.R;
import com.c3.library.weight.overlay.model.MenuModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringChain;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： LYJ
 * 功能： 底部菜单项弹窗的数据适配器
 * 创建日期： 2017/12/20
 */

public class BottomMenuAdapter extends BaseQuickAdapter<MenuModel, BaseViewHolder> {

    private SpringChain springChain = SpringChain.create();
    private List<View> menuItem = new ArrayList<>();

    public BottomMenuAdapter(@Nullable List<MenuModel> data) {
        super(R.layout.item_bottom_menu, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuModel item) {
        helper.setText(R.id.menu_title, item.getMenuName());
        helper.setImageResource(R.id.menu_icon, item.getDrawableRes());
        menuItem.add(helper.itemView);
        springChain.addSpring(new SimpleSpring(menuItem.get(helper.getLayoutPosition())));
        springChain.getAllSprings().get(helper.getLayoutPosition()).setCurrentValue(ScreenUtils.getScreenHeight() >> 1);

    }

    private static class SimpleSpring extends SimpleSpringListener {
        private View view;

        private SimpleSpring(View view) {
            this.view = view;
        }

        @Override
        public void onSpringUpdate(Spring spring) {
            view.setTranslationY((float) spring.getCurrentValue());
        }
    }

    public void startAnimation() {
        springChain.setControlSpringIndex(0).getControlSpring().setEndValue(SizeUtils.dp2px(20));
    }

    public void dismissAnimation() {
        for (int i = 0; i < menuItem.size(); i++) {
            springChain.getAllSprings().get(i).setCurrentValue(SizeUtils.dp2px(20));
        }
        springChain.setControlSpringIndex(menuItem.size() - 1)
                .getControlSpring().setEndValue(ScreenUtils.getScreenHeight() >> 1);
    }
}
