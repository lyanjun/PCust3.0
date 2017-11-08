package com.c3.library.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * 作者： LYJ
 * 功能： 尝试以最小层级结构创建标题栏
 * 创建日期： 2017/11/8
 */

public class CustomTitleView extends ConstraintLayout {
    private Context mContext;
    private TextView titleTxt;//显示标题

    public CustomTitleView(Context context) {
        super(context , null);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    /**
     * 初始化
     */
    private void initView(Context context){
        titleTxt = new TextView(context);//初始化标题栏
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.bottomToBottom = LayoutParams.PARENT_ID;
        layoutParams.topToTop = LayoutParams.PARENT_ID;
        layoutParams.leftToLeft = LayoutParams.PARENT_ID;
        layoutParams.rightToRight = LayoutParams.PARENT_ID;
        titleTxt.setText("标题");
        titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);
        titleTxt.setTextColor(0xffff0000);
        addView(titleTxt,layoutParams);


        TextView titleTxt = new TextView(context);//初始化标题栏
        LayoutParams layoutParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams1.bottomToBottom = LayoutParams.PARENT_ID;
        layoutParams1.topToTop = LayoutParams.PARENT_ID;
        titleTxt.setText("左侧");
        titleTxt.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);
        titleTxt.setTextColor(0xffff0000);
        addView(titleTxt,layoutParams1);

        TextView titleTxt2 = new TextView(context);//初始化标题栏
        LayoutParams layoutParams2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams2.bottomToBottom = LayoutParams.PARENT_ID;
        layoutParams2.topToTop = LayoutParams.PARENT_ID;
        layoutParams2.rightToRight = LayoutParams.PARENT_ID;
        titleTxt2.setText("右侧");
        titleTxt2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);
        titleTxt2.setTextColor(0xffff0000);
        addView(titleTxt2,layoutParams2);
    }
}
