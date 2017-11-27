package com.c3.pcust30.fragment.child.home

import android.os.Bundle
import com.c3.library.view.title.IsTitleChildView
import com.c3.pcust30.R
import com.c3.pcust30.base.frag.TopFragment
import com.c3.pcust30.data.info.ORG_CODE
import com.c3.pcust30.data.info.USER_CODE
import com.c3.pcust30.data.net.LOGIN_ORG_CODE
import com.c3.pcust30.data.net.LOGIN_USER_CODE
import com.c3.pcust30.data.net.SERVICE_CODE
import com.c3.pcust30.data.net.getJson
import com.c3.pcust30.data.net.rep.TradingRequest
import com.c3.pcust30.http.config.CUSTOM_DATA_STATISTICS_TRADING_CODE
import com.c3.pcust30.http.config.CUSTOM_INFO_TRADING_CODE
import com.c3.pcust30.http.tool.TradingTool
import com.orhanobut.hawk.Hawk
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_home_page.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

/**
 * 作者： LYJ
 * 功能： 首页
 * 创建日期： 2017/11/27
 */

class HomePageFragment : TopFragment() {
    /**
     * 设置布局
     */
    override fun setFragmentView(): Int = R.layout.fragment_home_page

    override fun setTitleLeftChildView(): IsTitleChildView? = null
    /**
     * 设置标题
     */
    override fun setTitleText(): CharSequence = getString(R.string.frag_title_home)

    /**
     * 初始化设置
     */
    override fun onViewCreatedInitMember(savedInstanceState: Bundle?) {
        super.onViewCreatedInitMember(savedInstanceState)
        OverScrollDecoratorHelper.setUpOverScroll(scrollGroup)//设置弹性滑动
    }

    /**
     * 入场动画结束后调用
     */
    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        //进行网络请求请求(折线统计图和用户统计信息的数据请求)
        val customTradingRequest = TradingTool.commitTradingNewThread(requestJsonWithUserOrg(CUSTOM_INFO_TRADING_CODE))
        val lineChartTradingRequest = TradingTool.commitTradingNewThread(requestJsonWithUserOrg(CUSTOM_DATA_STATISTICS_TRADING_CODE))
        Observable.zip(customTradingRequest, lineChartTradingRequest, BiFunction<String, String, String> { s, s2 -> s + s2 })
    }

    /**
     * 请求json数据
     */
    private fun requestJsonWithUserOrg(tradingCode: String) = getJson(TradingRequest().addHeader(SERVICE_CODE, tradingCode)
            .addBody(LOGIN_USER_CODE, Hawk.get<String>(USER_CODE))
            .addBody(LOGIN_ORG_CODE, Hawk.get<String>(ORG_CODE)))
}
