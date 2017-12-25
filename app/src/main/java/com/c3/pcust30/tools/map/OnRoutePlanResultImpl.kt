package com.c3.pcust30.tools.map

import com.baidu.mapapi.search.route.*

/**
 * 作者： LYJ
 * 功能： 定图线路规划返回结果
 * 创建日期： 2017/12/25
 */

class OnRoutePlanResultImpl(private var Walk: ((walkingRouteResult: WalkingRouteResult) -> Unit)? = null,
                            private var Driver: ((drivingRouteResult: DrivingRouteResult) -> Unit)? = null) : OnGetRoutePlanResultListener {

    override fun onGetWalkingRouteResult(walkingRouteResult: WalkingRouteResult) {
        Walk?.invoke(walkingRouteResult)
    }

    override fun onGetTransitRouteResult(transitRouteResult: TransitRouteResult) {

    }

    override fun onGetMassTransitRouteResult(massTransitRouteResult: MassTransitRouteResult) {

    }

    override fun onGetDrivingRouteResult(drivingRouteResult: DrivingRouteResult) {
        Driver?.invoke(drivingRouteResult)
    }

    override fun onGetIndoorRouteResult(indoorRouteResult: IndoorRouteResult) {

    }

    override fun onGetBikingRouteResult(bikingRouteResult: BikingRouteResult) {

    }
}
