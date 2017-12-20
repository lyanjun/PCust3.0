package com.c3.library.weight.overlay.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.ScreenUtils;
import com.c3.library.R;
import com.c3.library.weight.overlay.adapter.BottomMenuAdapter;
import com.c3.library.weight.overlay.model.MenuModel;

import java.util.List;

/**
 * 作者： LYJ
 * 功能： 菜单弹窗
 * 创建日期： 2017/12/20
 */

public class BottomMenuDialog extends Dialog {

    private RecyclerView menuListView;
    private BottomMenuAdapter bottomMenuAdapter;
    private List<MenuModel> menuModels;
    public BottomMenuDialog(@NonNull Context context ,  List<MenuModel> menuModels) {
        this(context, R.style.Custom_Dialog_Theme_Background_DimEnabled_True);
        this.menuModels = menuModels;
    }

    private BottomMenuDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_menu_layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = ScreenUtils.getScreenHeight() >> 1;
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(R.style.PopFadeAnim);
        setFunction();//设置功能
    }

    /**
     * 设置功能
     */

    private void setFunction() {
        menuListView = findViewById(R.id.menu_list);
        bottomMenuAdapter = new BottomMenuAdapter(menuModels);
        menuListView.setLayoutManager(new GridLayoutManager(getContext(),3));
        menuListView.setAdapter(bottomMenuAdapter);
//        SpringChain springChain = SpringChain.create();
//        int childCount = bottomMenuAdapter.getItemCount();
//        Logger.t("数量").;
//        for (int i = 0; i < childCount; i++) {
//            final View view = menuListView.getChildAt(i);
//            springChain.addSpring(new SimpleSpringListener() {
//                @Override
//                public void onSpringUpdate(Spring spring) {
//                    view.setTranslationY((float) spring.getCurrentValue());
//                }
//            });
//        }
//        List<Spring> springs = springChain.getAllSprings();
//        for (int i = 0; i < springs.size(); i++) {
//            springs.get(i).setCurrentValue(1000);
//        }
//        springChain.setControlSpringIndex(0).getControlSpring().setEndValue(50);
    }

}
