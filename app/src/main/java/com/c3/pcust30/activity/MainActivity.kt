package com.c3.pcust30.activity

import android.graphics.Color
import android.os.Bundle
import com.c3.pcust30.R
import com.c3.pcust30.base.BaseActivity

/**
 * 作者： LYJ
 * 功能： 主界面
 * 创建日期： 2017/11/7
 */
class MainActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBodyView(R.layout.activity_main)
        mTitleView.self.setBackgroundColor(Color.GRAY)
    }

//    /**
//     * 点击事件
//     */
//    override fun onChildClick(parentTag: Int, childTag: Int) {
//        when(childTag){
//            TitleChildTag.BACK_BTN -> Toast.makeText(this,"返回", Toast.LENGTH_SHORT).show()
//            TitleChildTag.RIGHT_BTN -> Toast.makeText(this,"右侧", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setBodyView(R.layout.activity_main)
//        val loadHelper = LoadDialogHelper()
//        loadHelper.setCustomLoadDialog(DefaultLoadingDialog(this))
//        test.setOnClickListener { loadHelper.showDialog() }
//        //左侧
//        mTitleView.addChildView(CustomTitleLeft(this)
//                .setChildButtonImage(com.c3.library.R.drawable.default_back_icon, 0)
//                .addOnChildClickListener(this)
//                , IsTitleView.LEFT)
//        //中间
//        mTitleView.addChildView(CustomTitleCenter(this).setChildTextColor(Color.WHITE).setChildTextSize(18)
//                .setChildText("标题", 0).toChangeAllText(), IsTitleView.CENTER)
//        //右侧
//        mTitleView.addChildView(rightView(), IsTitleView.RIGHT)
//        //设置背景色
//        mTitleView.self.setBackgroundColor(Color.GRAY)
//        Logger.i("46dp = ${resources.getDimensionPixelSize(R.dimen.dp_46)}像素")
//    }
//
//    private fun rightView(): IsTitleChildView? {
//        return CustomTitleRight(this).setChildTextColorList(com.c3.library.R.color.defualt_text_btn_color_list)
//                .addOnChildClickListener(this).setChildText("测试", 0).toChangeAllText()
//    }

}
