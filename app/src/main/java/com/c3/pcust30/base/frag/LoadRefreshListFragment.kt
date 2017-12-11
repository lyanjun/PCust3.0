package com.c3.pcust30.base.frag

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.c3.pcust30.R
import com.c3.pcust30.data.net.DEFAULT_PAGE_NUMBER
import com.c3.pcust30.data.net.DEFAULT_PAGE_SIZE
import com.c3.pcust30.top.LOAD_MORE
import com.c3.pcust30.top.LOAD_NONE
import com.c3.pcust30.top.LOAD_REFRESH
import com.chad.library.adapter.base.BaseQuickAdapter
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
     * 设置空布局
     */
    @LayoutRes
    open protected fun emptyViewLayoutIdRes(): Int = R.layout.view_data_empty

    /**
     * 绑定数据和设置列表显示效果
     */
    protected fun bindDataWithSetShowType(listView: RecyclerView, layoutManager: RecyclerView.LayoutManager, listAdapter: BaseQuickAdapter<*,*>) {
        listAdapter.setEmptyView(emptyViewLayoutIdRes(),listView.parent as ViewGroup)
        listView.layoutManager = layoutManager
        listView.setHasFixedSize(true)
        listView.adapter = listAdapter
    }
}