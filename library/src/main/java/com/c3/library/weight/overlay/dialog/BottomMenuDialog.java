package com.c3.library.weight.overlay.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.ScreenUtils;
import com.c3.library.R;
import com.c3.library.weight.overlay.adapter.BottomMenuAdapter;
import com.c3.library.weight.overlay.listener.OnMenuSelectedListener;
import com.c3.library.weight.overlay.model.MenuModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 作者： LYJ
 * 功能： 菜单弹窗
 * 创建日期： 2017/12/20
 */

public class BottomMenuDialog extends Dialog implements OnItemClickListener, View.OnTouchListener {

    private BottomMenuAdapter bottomMenuAdapter;
    private List<MenuModel> menuModels;
    private OnMenuSelectedListener onMenuSelectedListener;

    public BottomMenuDialog(@NonNull Context context, List<MenuModel> menuModels) {
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
        setCancelable(false);
    }

    /**
     * 设置功能
     */

    @SuppressLint("ClickableViewAccessibility")
    private void setFunction() {
        ConstraintLayout menuGroup = findViewById(R.id.menu_group);
        menuGroup.setOnTouchListener(this);
        RecyclerView menuListView = findViewById(R.id.menu_list);
        menuListView.setOnTouchListener(this);
        bottomMenuAdapter = new BottomMenuAdapter(menuModels);
        bottomMenuAdapter.setOnItemClickListener(this);
        menuListView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        menuListView.setAdapter(bottomMenuAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Observable.timer(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> bottomMenuAdapter.startAnimation());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        dismissMineDialog(menuModels.get(position).getMenuType());
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        dismissMineDialog(null);
        return false;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        dismissMineDialog(null);
        return super.onTouchEvent(event);
    }

    private void dismissMineDialog(String callBackType) {
        bottomMenuAdapter.dismissAnimation();
        Observable.timer(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    dismiss();
                    if (null != onMenuSelectedListener && null != callBackType) {
                        onMenuSelectedListener.onMenuSelected(callBackType);
                    }
                });
    }

    public void setOnMenuSelectedListener(OnMenuSelectedListener onMenuSelectedListener) {
        this.onMenuSelectedListener = onMenuSelectedListener;
    }
}
