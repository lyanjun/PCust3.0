package com.c3.pcust30.fragment.child.manage

import android.os.Bundle
import android.text.TextUtils
import com.c3.library.weight.toast.ShowHint
import com.c3.pcust30.R
import com.c3.pcust30.base.frag.BaseFragment
import com.c3.pcust30.data.bean.ClientTypeModel
import com.c3.pcust30.data.net.rsp.TradingResponse
import com.c3.pcust30.data.net.rsp.body.ClientClassLevelRsp
import com.c3.pcust30.data.net.rsp.body.ClientClassRateRsp
import com.c3.pcust30.data.net.rsp.body.ClientClassTypeRsp
import com.c3.pcust30.http.config.CLIENT_RATE_CODE
import com.c3.pcust30.http.config.CLIENT_TYPE_CODE
import com.c3.pcust30.http.config.CLIENT_lABEL_CODE
import com.c3.pcust30.http.tool.RequestJsonUtils
import com.c3.pcust30.http.tool.TradingTool
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3

/**
 * 作者： LYJ
 * 功能： 客户分类界面
 * 创建日期： 2017/12/8
 */
class ClientClassifyFragment : BaseFragment() {

    private val clienTypes: MutableList<ClientTypeModel> = mutableListOf()
    /**
     * 设置布局
     */
    override fun setFragmentView(): Int = R.layout.fragment_client_classifty

    override fun setTitleText(): CharSequence = resources.getString(R.string.frag_title_manager_client_class)

//    override fun onViewCreatedInitMember(savedInstanceState: Bundle?) {
//        super.onViewCreatedInitMember(savedInstanceState)
//        OverScrollDecoratorHelper.setUpOverScroll(clientClassList, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
//    }

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
                TradingTool.commitTradingNewThread(RequestJsonUtils.requestJsonWithUserOrg(CLIENT_lABEL_CODE, "num")),
                Function3<String, String, String, Array<String>> { t1, t2, t3 -> arrayOf(t1, t2, t3) })
                .doFinally { hideLoading() }
                .bindToLifecycle(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ jsonStrArray -> setJsonRspArray(jsonStrArray) },
                        { error -> ShowHint.failure(mContext, error.message!!) })
    }

    /**
     * 返回结果
     */
    override fun getResponse(result: String, tag: Int) {
        super.getResponse(result, tag)
        when (tag) {
            0 -> {//客户分类
                val objType = object : TypeToken<TradingResponse<ClientClassTypeRsp>>() {}.type//解析类型
                val typeResponse = Gson().fromJson<TradingResponse<ClientClassTypeRsp>>(result, objType)//解析结果
                getResultBody(typeResponse, { bodyEntity ->
                    val typeArray = bodyEntity.countView
                })
            }
            1 -> {//客户评级
                val objType = object : TypeToken<TradingResponse<ClientClassRateRsp>>() {}.type//解析类型
                val rateResponse = Gson().fromJson<TradingResponse<ClientClassRateRsp>>(result, objType)//解析结果
                getResultBody(rateResponse, { bodyEntity ->
                    val rateArray = bodyEntity.effView
                })
            }
            2 -> {//客户标签
                var objType = object : TypeToken<TradingResponse<ClientClassLevelRsp>>() {}.type//解析类型
                val levelResponse = Gson().fromJson<TradingResponse<ClientClassLevelRsp>>(result, objType)//解析结果
                getResultBody(levelResponse, { bodyEntity ->
                    val labelCount = bodyEntity.viewsCount?.viewsCount//标签个数
                    if (TextUtils.equals(labelCount, "1")) {
                        objType = object : TypeToken<TradingResponse<ClientClassLevelRsp.LevelInfoEntity>>() {}.type//解析类型
                        val levelEntity = Gson().fromJson<TradingResponse<ClientClassLevelRsp.LevelInfoEntity>>(result, objType)//解析结果
                    } else {
                        objType = object : TypeToken<TradingResponse<ClientClassLevelRsp.LevelInfoArray>>() {}.type//解析类型
                        val levelEntity = Gson().fromJson<TradingResponse<ClientClassLevelRsp.LevelInfoArray>>(result, objType)//解析结果
                    }
                })
            }
        }
    }
}