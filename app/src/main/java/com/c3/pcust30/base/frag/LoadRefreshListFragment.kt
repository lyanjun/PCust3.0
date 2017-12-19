package com.c3.pcust30.base.frag

import com.c3.pcust30.data.net.DEFAULT_PAGE_NUMBER
import com.c3.pcust30.data.net.DEFAULT_PAGE_SIZE
import com.c3.pcust30.top.LOAD_MORE
import com.c3.pcust30.top.LOAD_NONE
import com.c3.pcust30.top.LOAD_REFRESH
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

/**
 * 刷新加载列表碎片
 * Created by Lyan on 17/12/11.
 */
abstract class LoadRefreshListFragment : BaseFragment(), OnRefreshListener, OnLoadmoreListener {
    protected val pageDataCount = DEFAULT_PAGE_SIZE//每页请求的数据数量
    protected var page = 0//请求的页数

    /**
     * 下拉刷新
     */
    override fun onRefresh(mRefreshLayout: RefreshLayout?) {
        getDataFromServer(LOAD_REFRESH)
    }

    /**
     * 上拉加载
     */
    override fun onLoadmore(mRefreshLayout: RefreshLayout?) {
        getDataFromServer(LOAD_MORE, ++page)
    }

    /**
     * 获取网络请求数据
     */
    open protected fun getDataFromServer(loadType: Int = LOAD_NONE, loadPage: Int = DEFAULT_PAGE_NUMBER) {
        page = loadPage//记录页数
    }

    /**
     * 绑定刷新加载监听
     */
    protected fun bindRefreshWithLoadMoreListener(mRefreshLayout: RefreshLayout) {
        mRefreshLayout.setOnRefreshListener(this)
        mRefreshLayout.setOnLoadmoreListener(this)
    }

    /**
     * 刷新页面数据
     */
    override fun onRefreshView() = getDataFromServer(LOAD_REFRESH)
}