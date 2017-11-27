package com.c3.pcust30.fragment.child.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import com.c3.library.view.title.IsTitleChildView
import com.c3.library.weight.toast.ShowHint
import com.c3.pcust30.R
import com.c3.pcust30.base.frag.TopFragment
import com.c3.pcust30.data.info.ORG_CODE
import com.c3.pcust30.data.info.USER_CODE
import com.c3.pcust30.data.info.UserInfo
import com.c3.pcust30.data.net.*
import com.c3.pcust30.data.net.rep.TradingRequest
import com.c3.pcust30.data.net.rsp.TradingResponse
import com.c3.pcust30.data.net.rsp.body.LoginRsp
import com.c3.pcust30.data.net.rsp.body.UserWorkInfoRsp
import com.c3.pcust30.http.config.CUSTOM_DATA_STATISTICS_TRADING_CODE
import com.c3.pcust30.http.config.CUSTOM_INFO_TRADING_CODE
import com.c3.pcust30.http.tool.TradingTool
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.Logger
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_home_page.*
import kotlinx.android.synthetic.main.fragment_home_page_top.*
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
    @SuppressLint("SetTextI18n")
    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        //进行网络请求请求(折线统计图和用户统计信息的数据请求)
        val customTradingRequest = TradingTool.commitTradingNewThread(requestJsonWithUserOrg(CUSTOM_INFO_TRADING_CODE))
        val lineChartTradingRequest = TradingTool.commitTradingNewThread(requestJsonWithUserOrg(CUSTOM_DATA_STATISTICS_TRADING_CODE))
        showLoading()//展示等待弹窗
        Observable.zip(customTradingRequest, lineChartTradingRequest,
                BiFunction<String, String, Array<String>> { json1, json2 -> arrayOf(json1, json2) })
                .doFinally { hideLoading() }
                .bindToLifecycle(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ jsonStrArray -> setJsonRspArray(jsonStrArray) },
                        { error -> ShowHint.failure(mContext, error!!.message!!) })
        //显示用户名称 和 提示文字
        topUserName.text = UserInfo.userName
        topCustomHint.text = "${UserInfo.userName},您好"
    }

    /**
     * 请求json数据
     */
    private fun requestJsonWithUserOrg(tradingCode: String) = getJson(TradingRequest().addHeader(SERVICE_CODE, tradingCode)
            .addBody(LOGIN_USER_CODE, UserInfo.userCode)
            .addBody(LOGIN_ORG_CODE, UserInfo.orgCode))


    /**
     * 处理返回结果
     */
    override fun getResponse(result: String, tag: Int) {
        super.getResponse(result, tag)
        when (tag) {
            0 -> {//用户基本统计信息
                val objType = object : TypeToken<TradingResponse<UserWorkInfoRsp>>() {}.type//解析类型
                val workResponse = Gson().fromJson<TradingResponse<UserWorkInfoRsp>>(result, objType)//解析结果
                if (TextUtils.equals(TRADING_SUCCESS, workResponse.header!!.rspCode)) {
                    //显示用户基本统计信息
                    showUserWorkInfo(workResponse.body?.dataInfo ?: UserWorkInfoRsp.DataInfo())
                    //用户统计排行
                } else {
                    ShowHint.failure(mContext, workResponse.header!!.rspMsg!!)
                }
            }
            1 -> {//用户折线统计信息

            }
        }
    }

    /**
     * 展示用户信息数据
     */
    @Suppress("DEPRECATION")
    @SuppressLint("SetTextI18n")
    private fun showUserWorkInfo(userWorkInfo: UserWorkInfoRsp.DataInfo) {
        topRankMonth.text = "本月排名:　　${isHasData(userWorkInfo.monRange, "未上榜")}"
        topRankYear.text = "本年度排名:　　${isHasData(userWorkInfo.yearRange, "未上榜")}"
        topMakeCount.text = "本月营销客户数量:　　${isHasData(userWorkInfo.custCount, "无记录")}"
        topInvestCount.text = "本月完成投资客户数量:　　${isHasData(userWorkInfo.investCount, "无记录")}"
        topLastTime.text = "统计截止时间:　　${userWorkInfo.stadate ?: "暂无"}"
        topTaskHintTxt.text = "您今天有${userWorkInfo.taskCount}条任务"
        val hintCount = userWorkInfo.monRange
        when (hintCount) {
            -1 -> { topRankHintMsg.text = "本月新增客户排名:未上榜" }
            1 -> { topRankHintMsg.text = "本月新增客户排名($hintCount/${userWorkInfo.totalAcctCount}),您已经无敌了" }
            else -> {
                topRankHintMsg.text = Html.fromHtml("本月新增客户排名($hintCount/${userWorkInfo.
                        totalAcctCount})<font color='#ff0000'>（距离上一名营销客户数量还差${userWorkInfo.
                        differencial}个客户）</font>")
            }
        }
    }

    /**
     * 根据结果显示信息
     */
    private fun isHasData(value: Int, hint: String): String = if (value != -1) value.toString() else hint
}
