package com.c3.pcust30.fragment.child.test

import android.graphics.Color
import android.os.Bundle
import com.baidu.location.BDLocation
import com.baidu.mapapi.map.BitmapDescriptorFactory
import com.baidu.mapapi.map.MyLocationConfiguration
import com.baidu.mapapi.map.MyLocationData
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.route.PlanNode
import com.baidu.mapapi.search.route.RoutePlanSearch
import com.baidu.mapapi.search.route.WalkingRoutePlanOption
import com.c3.pcust30.R
import com.c3.pcust30.base.frag.BaseFragment
import com.c3.pcust30.tools.map.*
import com.c3.pcust30.tools.map.overlay.MyWalkPlanOverlay
import kotlinx.android.synthetic.main.fragment_map_test.*


/**
 * 作者： LYJ
 * 功能： 地图测试界面(测试用)
 * 创建日期： 2017/12/22
 */
class MapTestFragment : BaseFragment() {
    private var startLatLng: LatLng? = null
    //125.330629,43.915435火车站、125.295505,43.851499金苑大厦
    private var stopLatLng: LatLng? = LatLng(43.915435, 125.330629)
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
        zoomScaleFalse(mapView, LocationTool.nowCityLaLng!!)
        walkPathBtn.setOnClickListener {
            mapView.map.clear()
            //获取路线规划数据
            val planSearch = RoutePlanSearch.newInstance()
            planSearch.setOnGetRoutePlanResultListener(OnRoutePlanResultImpl(Walk = { walkingRouteResult ->
                //步行路线规划
                val routeLines = walkingRouteResult.routeLines
                val walkingRouteOverlay = MyWalkPlanOverlay(mapView.map)
                walkingRouteOverlay.setData(routeLines[0])
                walkingRouteOverlay.setPlanLineColor(Color.RED)
                walkingRouteOverlay.addToMap()
//                walkingRouteOverlay.zoomToSpan()
                planSearch.destroy()
            }))
            val startLocation = PlanNode.withLocation(startLatLng)
            val stopLocation = PlanNode.withLocation(stopLatLng)
            planSearch.walkingSearch(object : WalkingRoutePlanOption() {}
                    .from(startLocation)
                    .to(stopLocation))
        }
    }

    /**
     * 入场动画结束
     */
    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        showLoading()
        LocationTool.toLocation(LocationTool.LocationType.START)
        LocationTool.setLocationResultListener { location -> setFunctionWithLocation(location) }
    }

    /**
     * 获取定位数据
     */
    private fun setFunctionWithLocation(location: BDLocation) {
        startLatLng = LatLng(location.latitude, location.longitude)
        moveToLocation(mapView, startLatLng!!)
        val locData = MyLocationData.Builder().latitude(location.latitude).longitude(location.longitude).build()
        mapView.map.setMyLocationData(locData)
        mapView.map.setMyLocationConfiguration(MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,
                true, BitmapDescriptorFactory.fromResource(R.drawable.location_is_mine_icon)))
        if (17 != mapView.map.mapStatus.zoom.toInt()) {
            scaleTransitionAnimation(mapView.map, mapView.map.mapStatus.zoom.toInt()..17, { hideLoading() })
        } else {
            hideLoading()
        }

    }

    /**
     * 地图生命周期的绑定
     */
    override fun onResume() {
        super.onResume()
        mapView.map.isMyLocationEnabled = true
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    /**
     * 释放资源
     */
    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
        mapView.map.isMyLocationEnabled = false
        LocationTool.toLocation(LocationTool.LocationType.STOP)
    }
}