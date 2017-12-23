package com.c3.pcust30.fragment.child.test

import android.os.Bundle
import com.c3.pcust30.R
import com.c3.pcust30.base.frag.BaseFragment
import com.c3.pcust30.tools.LocationTool
import com.c3.pcust30.tools.zoomScaleFalse
import kotlinx.android.synthetic.main.fragment_map_test.*


/**
 * 作者： LYJ
 * 功能： 地图测试界面(测试用)
 * 创建日期： 2017/12/22
 */
class MapTestFragment : BaseFragment() {
    /**
     * 设置布局
     */
    override fun setFragmentView(): Int = R.layout.fragment_map_test

    /**
     * 设置标题
     */
    override fun setTitleText(): CharSequence = "地图测试"

    /**
     * 初始化
     */
    override fun onViewCreatedInitMember(savedInstanceState: Bundle?) {
        super.onViewCreatedInitMember(savedInstanceState)
        zoomScaleFalse(mapView)
        LocationTool.toLocation(LocationTool.LocationType.START)
    }

    /**
     * 入场动画结束
     */
    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)

    }
    /**
     * 地图生命周期的绑定
     */
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
        LocationTool.toLocation(LocationTool.LocationType.STOP)
    }
}