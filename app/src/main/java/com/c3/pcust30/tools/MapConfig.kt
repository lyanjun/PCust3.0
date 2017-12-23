package com.c3.pcust30.tools

import android.view.View
import android.widget.ImageView
import android.widget.ZoomControls
import com.baidu.mapapi.map.TextureMapView


/**
 * 作者： LYJ
 * 功能： 地图设置
 * 创建日期： 2017/12/22
 */
/**
 * 隐藏百度Logo
 */
fun goneMapViewLogo(mapView: TextureMapView){
    val child = mapView.getChildAt(1)
    if (child != null && (child is ImageView || child is ZoomControls)) {
        child.visibility = View.INVISIBLE
    }
}

/**
 * 取消比例尺和缩放提示
 */
fun zoomScaleFalse(mapView: TextureMapView){
    mapView.showScaleControl(false)
    mapView.showZoomControls(false)
    goneMapViewLogo(mapView)
}
