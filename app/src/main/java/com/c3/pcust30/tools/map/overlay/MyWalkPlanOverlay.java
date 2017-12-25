package com.c3.pcust30.tools.map.overlay;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;

/**
 * 作者： LYJ
 * 功能： 自定义路径规划覆盖物
 * 创建日期： 2017/12/25
 */

public class MyWalkPlanOverlay extends WalkingRouteOverlay {

    private int planLineColor = 0;//路径规划线的颜色

    public MyWalkPlanOverlay(BaiduMap baiduMap) {
        super(baiduMap);
    }

    @Override
    public int getLineColor() {
        return planLineColor;
    }

    public void setPlanLineColor(int planLineColor) {
        this.planLineColor = planLineColor;
    }

    @Override
    public BitmapDescriptor getStartMarker() {
        return null;
    }
}
