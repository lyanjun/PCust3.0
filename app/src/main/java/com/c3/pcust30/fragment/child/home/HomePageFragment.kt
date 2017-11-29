package com.c3.pcust30.fragment.child.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Html
import android.text.TextUtils
import android.widget.LinearLayout
import com.c3.library.utils.DataTools
import com.c3.library.view.title.IsTitleChildView
import com.c3.library.weight.toast.ShowHint
import com.c3.pcust30.R
import com.c3.pcust30.adapter.HomePageRankAdapter
import com.c3.pcust30.base.frag.TopFragment
import com.c3.pcust30.data.info.UserInfo
import com.c3.pcust30.data.net.*
import com.c3.pcust30.data.net.rep.TradingRequest
import com.c3.pcust30.data.net.rsp.TradingResponse
import com.c3.pcust30.data.net.rsp.body.UserWorkChartRsp
import com.c3.pcust30.data.net.rsp.body.UserWorkInfoRsp
import com.c3.pcust30.http.config.CUSTOM_DATA_STATISTICS_TRADING_CODE
import com.c3.pcust30.http.config.CUSTOM_INFO_TRADING_CODE
import com.c3.pcust30.http.tool.TradingTool
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_home_page.*
import kotlinx.android.synthetic.main.fragment_home_page_top.*
import kotlinx.android.synthetic.main.item_home_page_rank.view.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import org.joda.time.DateTime
import java.util.*
import kotlin.collections.ArrayList


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
        showLoading()//展示等待弹窗
        Observable.zip(TradingTool.commitTradingNewThread(requestJsonWithUserOrg(CUSTOM_INFO_TRADING_CODE)),
                TradingTool.commitTradingNewThread(requestJsonWithUserOrg(CUSTOM_DATA_STATISTICS_TRADING_CODE)),
                BiFunction<String, String, Array<String>> { json1, json2 -> arrayOf(json1, json2) })
                .doFinally { hideLoading() }
                .bindToLifecycle(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ jsonStrArray -> setJsonRspArray(jsonStrArray) },
                        { error ->
                            ShowHint.failure(mContext, error.message!!)
                            Logger.i(error.message!!)
                        })
        //显示用户名称 和 提示文字
        topUserName.text = UserInfo.userName
        topCustomHint.text = "${UserInfo.userName},您好"
        //设置折线图表的显示效果
        val legend = middleLineChart.legend
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        middleLineChart.setTouchEnabled(false)
        middleLineChart.description.isEnabled = false
        middleLineChart.axisRight.isEnabled = false
    }

    /**
     * 请求json数据
     */
    private fun requestJsonWithUserOrg(tradingCode: String) = getJson(TradingRequest()
            .addHeader(SERVICE_CODE, tradingCode).addHeader(LOGIN_USER_NAME, UserInfo.userCode)
            .addBody(LOGIN_USER_CODE, UserInfo.userCode).addBody(LOGIN_ORG_CODE, UserInfo.orgCode))


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
                    showUserWorkInfo(workResponse.body!!.dataInfo ?: UserWorkInfoRsp.DataInfo())
                    //用户统计排行
                    showUserRankList(workResponse.body!!.rangeRecView ?: mutableListOf())
                } else {
                    ShowHint.failure(mContext, workResponse.header!!.rspMsg!!)
                }
            }
            1 -> {//用户折线统计信息
                //分三步解析（数据很不规范，理论和实践差距总是很大）
                var dataType = object : TypeToken<TradingResponse<UserWorkChartRsp>>() {}.type//解析类型
                val chartRsp = Gson().fromJson<TradingResponse<UserWorkChartRsp>>(result, dataType)
                if (TextUtils.equals(TRADING_SUCCESS, chartRsp.header!!.rspCode)) {
                    val dataCount = chartRsp.body!!.stacount?.stacount
                    val lineChartY: MutableList<Int> = ArrayList()//数量
                    val lineChartX: MutableList<String> = ArrayList()//横坐标
                    when (dataCount) {
                        -1, 0, 1, null -> {
                            if (dataCount == 1) {
                                //数据只有一条
                                dataType = object : TypeToken<TradingResponse<UserWorkChartRsp.ChartEntityRsp>>() {}.type//解析类型
                                val chartEntityRsp = Gson().fromJson<TradingResponse<UserWorkChartRsp.ChartEntityRsp>>(result, dataType)
                                lineChartX.add(chartEntityRsp.body!!.pCustAcctCustCount!!.sdate!!)
                                lineChartY.add(chartEntityRsp.body!!.pCustAcctCustCount!!.custcount)
                            } else {//无数据情况
                                lineChartY.add(0)
                                lineChartX.add(DateTime.now().toString(DataTools.YEAR_MONTH, Locale.CHINESE))
                            }
                            lineChartX.addAll(0, DataTools.getPreviousMonths(6, lineChartX[0]))
                            for (i in 0 until 6) lineChartY.add(0, 0)
                        }
                        else -> {//此处需要显示7条数据（不足七条补齐七条）
                            dataType = object : TypeToken<TradingResponse<UserWorkChartRsp.ChartArrayRsp>>() {}.type//解析类型
                            val chartArrayRsp = Gson().fromJson<TradingResponse<UserWorkChartRsp.ChartArrayRsp>>(result, dataType)
                            if (dataCount < 7) {
                                chartArrayRsp.body!!.pCustAcctCustCount!!.forEach { lineChartX.add(it.sdate!!) }
                                chartArrayRsp.body!!.pCustAcctCustCount!!.forEach { lineChartY.add(it.custcount) }
                                lineChartX.addAll(0, DataTools.getPreviousMonths(7 - dataCount, lineChartX[0]))
                                for (i in 0 until (7 - dataCount)) lineChartY.add(0, 0)
                            } else {//大于等于7条就截取最后7条数据
                                val downLoadSize = chartArrayRsp.body!!.pCustAcctCustCount!!.size
                                chartArrayRsp.body!!.pCustAcctCustCount!!.subList(downLoadSize - 7, downLoadSize).forEach { lineChartX.add(it.sdate!!) }
                                chartArrayRsp.body!!.pCustAcctCustCount!!.subList(downLoadSize - 7, downLoadSize).forEach { lineChartY.add(it.custcount) }
                            }
                        }
                    }
                    showLineChartData(lineChartY, lineChartX)//展示图表数据
                } else {
                    ShowHint.failure(mContext, chartRsp.header!!.rspMsg!!)
                }
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
            -1 -> {
                topRankHintMsg.text = "本月新增客户排名:未上榜"
            }
            1 -> {
                topRankHintMsg.text = "本月新增客户排名($hintCount/${userWorkInfo.totalAcctCount}),您已经无敌了"
            }
            else -> {
                topRankHintMsg.text = Html.fromHtml("本月新增客户排名($hintCount/${userWorkInfo.
                        totalAcctCount})<font color='#ff0000'>（距离上一名营销客户数量还差${userWorkInfo.
                        differencial}个客户）</font>")
            }
        }
    }

    /**
     * 显示排名数据列表
     */
    private fun showUserRankList(rankList: List<UserWorkInfoRsp.RangeRecView>) {
        val rankData: MutableList<UserWorkInfoRsp.RangeRecView> = mutableListOf()//容器
        //列表头部控件
        val header = layoutInflater.inflate(R.layout.item_home_page_rank, bottomRankList, false)
        header.rankTv.text = "排名"
        header.nameTv.text = "姓名"
        header.workTv.text = "业务量"

        //使用最外层容器的高度减去除去列表项所有控件的高度的
        val rankItemHeight = resources.getDimensionPixelOffset(R.dimen.dp_25)
        val surplusHeight = scrollGroup.height - (top.height + middleLineChart.height + topRankHintMsg.height +
                (topRankHintMsg.layoutParams as LinearLayout.LayoutParams).topMargin + rankItemHeight)//剩余高度
        //当前列表项的高度(按照布局文件中设置的高度来估算)
        val showRankCount = surplusHeight / rankItemHeight
        val setItemHeight = surplusHeight / showRankCount//item高度
        val setItemWidth = bottomRankList.width//item宽度
        bottomRankList.addHeaderView(header)//添加头部
        //设置数据
        if (!rankList.isEmpty()) {
            val index = (0 until rankList.size).firstOrNull { !rankList[it].isSelf.isNullOrBlank() } ?: -1//计数器
            if (index in 0 until showRankCount) {
                rankData.addAll(rankList.subList(0, showRankCount))
            } else {
                val mineRank = if (index == -1) {//不包括使用者
                    UserWorkInfoRsp.RangeRecView(-1, "1")
                } else {//包括使用者
                    rankList[index]
                }
                rankData.addAll(rankList.subList(0, showRankCount - 1))
                rankData.add(mineRank)
            }
        }
        bottomRankList.adapter = HomePageRankAdapter(rankData, setItemWidth, setItemHeight)
    }


    /**
     * 根据结果显示信息
     */
    private fun isHasData(value: Int, hint: String): String = if (value != -1) value.toString() else hint

    /**
     * 展示图表数据
     */
    private fun showLineChartData(valuesY: List<Int>, dataX: List<String>) {
        //运行到此处每个数据必然有值 设置显示数据
        middleLineChart.clear()
        val lineData = LineData()
        val dataList: MutableList<Entry> = ArrayList()
        (0 until valuesY.size).mapTo(dataList) { Entry(it.toFloat() + 0.5f, valuesY[it].toFloat()) }
        val lineDataSet = LineDataSet(dataList, "每月客户营销数量")
        lineDataSet.axisDependency = YAxis.AxisDependency.LEFT
        lineDataSet.setCircleColor(ContextCompat.getColor(mContext, R.color.home_line_chart_color))
        lineDataSet.color = ContextCompat.getColor(mContext, R.color.home_line_chart_color)
        lineDataSet.circleRadius = 4f
        lineDataSet.lineWidth = 2f
        lineData.addDataSet(lineDataSet)
        //设置x轴
        val bottomAxis = middleLineChart.xAxis
        bottomAxis.setCenterAxisLabels(true)
        bottomAxis.position = XAxis.XAxisPosition.BOTTOM
        bottomAxis.valueFormatter = IndexAxisValueFormatter(dataX)
        bottomAxis.axisMinimum = lineData.xMin - 0.5f
        bottomAxis.axisMaximum = lineData.xMax + 0.5f
        //设置Y轴
        middleLineChart.axisLeft.axisMinimum = 0f
        middleLineChart.data = lineData
        middleLineChart.animateX(500)
    }
}



