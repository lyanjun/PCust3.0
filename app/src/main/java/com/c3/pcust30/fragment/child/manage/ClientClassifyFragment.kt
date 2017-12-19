package com.c3.pcust30.fragment.child.manage

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.c3.library.weight.toast.ShowHint
import com.c3.pcust30.R
import com.c3.pcust30.adapter.ClientClassLevelAdapter
import com.c3.pcust30.base.frag.BaseFragment
import com.c3.pcust30.data.bean.ClientTypeModel
import com.c3.pcust30.data.net.getResultBodyWithHeader
import com.c3.pcust30.data.net.listIsNotNull
import com.c3.pcust30.data.net.parseResult
import com.c3.pcust30.data.net.rsp.TradingResponse
import com.c3.pcust30.data.net.rsp.body.ClientClassLevelRsp
import com.c3.pcust30.data.net.rsp.body.ClientClassRateRsp
import com.c3.pcust30.data.net.rsp.body.ClientClassTypeRsp
import com.c3.pcust30.http.config.CLIENT_LABEL_CODE
import com.c3.pcust30.http.config.CLIENT_RATE_CODE
import com.c3.pcust30.http.config.CLIENT_TYPE_CODE
import com.c3.pcust30.http.tool.RequestJsonUtils
import com.c3.pcust30.http.tool.TradingTool
import com.c3.pcust30.top.bindDataWithSetShowType
import com.c3.pcust30.top.getDrawableResId
import com.google.gson.reflect.TypeToken
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import kotlinx.android.synthetic.main.fragment_client_classifty.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

/**
 * 作者： LYJ
 * 功能： 客户分类界面
 * 创建日期： 2017/12/8
 */
class ClientClassifyFragment : BaseFragment() {
    private val clientTypes: MutableList<ClientTypeModel> by lazy { mutableListOf<ClientTypeModel>() }
    private val clientAdapter: ClientClassLevelAdapter by lazy { ClientClassLevelAdapter(clientTypes) }
    /**
     * 设置布局
     */
    override fun setFragmentView(): Int = R.layout.fragment_client_classifty

    override fun setTitleText(): CharSequence = resources.getString(R.string.frag_title_manager_client_class)

    override fun onViewCreatedInitMember(savedInstanceState: Bundle?) {
        super.onViewCreatedInitMember(savedInstanceState)
        bindDataWithSetShowType(clientClassList,LinearLayoutManager(mContext),clientAdapter)
        clientAdapter.setOnItemChildClickListener { _, _, position -> setConditionsSelectClientDataList(clientTypes[position].t.type, clientTypes[position].t.flag) }
        OverScrollDecoratorHelper.setUpOverScroll(clientClassList, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
    }

    /**
     * 根据查询条件查询客户列表
     */
    private fun setConditionsSelectClientDataList(key: String, value: String) {
        val bundle = Bundle()
        bundle.putString(key, value)
        val clientManagementFragment = ClientManagementFragment()
        clientManagementFragment.arguments = bundle
        start(clientManagementFragment)
    }

    /**
     * 入场动画结束后
     */
    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        downLoadClientClassifyData()//下载客户分类数据
    }

    /**
     * 下载分类数据
     */
    private fun downLoadClientClassifyData() {
        showLoading()//展示等待弹窗
        Observable.zip(TradingTool.commitTradingNewThread(RequestJsonUtils.requestJsonWithUserOrg(CLIENT_TYPE_CODE)),
                TradingTool.commitTradingNewThread(RequestJsonUtils.requestJsonWithUserOrg(CLIENT_RATE_CODE)),
                TradingTool.commitTradingNewThread(RequestJsonUtils.requestJsonWithUserOrg(CLIENT_LABEL_CODE, "num")),
                Function3<String, String, String, Array<String>> { t1, t2, t3 -> arrayOf(t1, t2, t3) })
                .bindToLifecycle(this)
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { hideLoading() }
                .doOnNext { clientTypes.clear() }
                .subscribe({ jsonStrArray -> setJsonRspArray(jsonStrArray) },
                        { error -> ShowHint.failure(mContext, error.message!!) },
                        { clientAdapter.notifyDataSetChanged() })
    }

    /**
     * 返回结果
     */
    override fun getResponse(result: String, tag: Int) {
        super.getResponse(result, tag)
        when (tag) {
            0 -> {//客户分类
                val typeResponse = getResultBodyWithHeader(result, object : TypeToken<TradingResponse<ClientClassTypeRsp>>() {})//解析结果
                getResultBody(typeResponse, { bodyEntity ->
                    val typeArray = bodyEntity.countView
                    listIsNotNull(typeArray!!, {
                        clientTypes.add(ClientTypeModel(true, "客户分类"))
                        clientTypes.add(addTypeToDataList(typeArray[2], "继续跟进"))
                        clientTypes.add(addTypeToDataList(typeArray[4], "无需跟进"))
                        clientTypes.add(addTypeToDataList(typeArray[0], "成功跟进"))
                        clientTypes.add(addTypeToDataList(typeArray[1], "一个月后再联系"))
                        clientTypes.add(addTypeToDataList(typeArray[3], "无联系"))
                    })
                })
            }
            1 -> {//客户评级
                val rateResponse = getResultBodyWithHeader(result, object : TypeToken<TradingResponse<ClientClassRateRsp>>() {})//解析结果
                getResultBody(rateResponse, { bodyEntity ->
                    val rateArray = bodyEntity.effView
                    listIsNotNull(rateArray!!, {
                        clientTypes.add(ClientTypeModel(true, "客户评级"))
                        clientTypes.add(ClientTypeModel(ClientTypeModel.ClientType(rateArray[0].yorndesc!!,
                                rateArray[0].customnum!!, "level", rateArray[0].yorn!!, R.drawable.ok_list_icon)))
                        clientTypes.add(ClientTypeModel(ClientTypeModel.ClientType(rateArray[1].yorndesc!!,
                                rateArray[1].customnum!!, "level", rateArray[1].yorn!!, R.drawable.ng_list_icon)))
                    })
                })
            }
            2 -> {//客户标签
                val levelResponse = getResultBodyWithHeader(result, object : TypeToken<TradingResponse<ClientClassLevelRsp>>() {})//解析结果
                getResultBody(levelResponse, { bodyEntity ->
                    val labelCount = bodyEntity.viewsCount?.viewsCount//标签个数
                    val labelList: MutableList<ClientClassLevelRsp.LevelInfo> = mutableListOf()
                    parseResult(labelCount!!, result, object : TypeToken<TradingResponse<ClientClassLevelRsp.LevelInfoEntity>>() {},
                            object : TypeToken<TradingResponse<ClientClassLevelRsp.LevelInfoArray>>() {},
                            { entityBody -> labelList.add(entityBody.pculabel!!) },
                            { arrayBody -> labelList.addAll(arrayBody.pculabel!!) })
                    listIsNotNull(labelList, {
                        clientTypes.add(ClientTypeModel(true, "标签分类"))
                        labelList.forEach {
                            clientTypes.add(ClientTypeModel(ClientTypeModel.ClientType(
                                    it.labelname!!, it.customnum!!, "label", it.gid!!, R.drawable.label_icon
                            )))
                        }
                    })
                })
            }
        }
    }

    /**
     * 设置客户分类列表内容
     */
    private fun addTypeToDataList(typeInfo: ClientClassTypeRsp.TypeInfo, title: String): ClientTypeModel =
            ClientTypeModel(ClientTypeModel.ClientType(title, typeInfo.count ?: "0", "type",
                    typeInfo.status!!, getDrawableResId("filter_${typeInfo.status}", mContext)))

    /**
     * 刷新界面
     */
    override fun onRefreshView() = downLoadClientClassifyData()
}