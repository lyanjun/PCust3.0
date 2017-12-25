package com.c3.pcust30.tools.map

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.widget.ImageView
import android.widget.ZoomControls
import com.baidu.mapapi.map.BaiduMap
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.map.TextureMapView
import com.baidu.mapapi.model.LatLng
import com.orhanobut.logger.Logger


/**
 * 作者： LYJ
 * 功能： 地图设置
 * 创建日期： 2017/12/22
 */
/**
 * 隐藏百度Logo
 */
fun goneMapViewLogo(mapView: TextureMapView) {
    val child = mapView.getChildAt(1)
    if (child != null && (child is ImageView || child is ZoomControls)) {
        child.visibility = View.INVISIBLE
    }
}

/**
 * 取消比例尺和缩放提示
 */
fun zoomScaleFalse(mapView: TextureMapView, latLng: LatLng? = null) {
    mapView.showScaleControl(false)
    mapView.showZoomControls(false)
    if (latLng != null) {
        moveToLocation(mapView, latLng)
    }
    goneMapViewLogo(mapView)
}

/**
 * 移动到指定地理位置
 */
fun moveToLocation(mapView: TextureMapView, latLng: LatLng) {
    try {
        val mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng)
        mapView.map.setMapStatus(mapStatusUpdate)
    } catch (e: NullPointerException) {
        Logger.t("移动地图").i(e.message)
    }
}

/**
 * 缩放
 */
fun scaleTransitionAnimation(map: BaiduMap, zoomRange: IntRange, End: (() -> Unit)? = null) {
    val valueAnimator = ValueAnimator.ofFloat(zoomRange.first.toFloat(), zoomRange.last.toFloat())
    valueAnimator.addUpdateListener { animation ->
        map.setMapStatus(MapStatusUpdateFactory.zoomTo(animation.animatedValue as Float))
    }
    valueAnimator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationEnd(animation: Animator?) {
            End?.invoke()//缩放动画结束
        }
        override fun onAnimationCancel(animation: Animator?) {}
        override fun onAnimationStart(animation: Animator?) {}
        override fun onAnimationRepeat(animation: Animator?) {}
    })
    valueAnimator.duration = 2000
    valueAnimator.start()
}
