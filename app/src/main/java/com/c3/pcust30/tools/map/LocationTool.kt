package com.c3.pcust30.tools.map

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.geocode.*
import com.orhanobut.hawk.Hawk

/**
 * 作者： LYJ
 * 功能： 定位工具
 * 创建日期： 2017/12/23
 */
@SuppressLint("StaticFieldLeak")
object LocationTool : BDAbstractLocationListener(), OnGetGeoCoderResultListener {

    private var context: Context? = null
    private var locationClient: LocationClient? = null
    private var scanSpan = 0//默认定位一次
    private var resultListener: ((location: BDLocation) -> Unit)? = null
    var nowCityLaLng: LatLng? = Hawk.get<LatLng>("now_city_loc_info") //当前城市位置坐标
    var nowCityName: String? = Hawk.get<String>("now_city_name")//当前城市名称
    private var geoCoder: GeoCoder? = null//地理编码实例

    /**
     * 初始化
     */
    fun initContext(applicationContext: Context) {
        context = applicationContext
        locationClient = LocationClient(context)
        initLocationConfig()//设置定位的配置
    }

    /**
     * 配置定位参数
     */
    private fun initLocationConfig() {
        locationClient?.registerLocationListener(this)
        val option = LocationClientOption()
        option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
        option.setCoorType("bd09ll")
        option.setScanSpan(scanSpan)
        option.isOpenGps = true
        option.setIsNeedAddress(true)
        option.isLocationNotify = true
        option.SetIgnoreCacheException(false)
        option.setWifiCacheTimeOut(5 * 60 * 1000)
        option.setEnableSimulateGps(false)
        locationClient?.locOption = option
    }

    /**
     * 定位回调
     */
    override fun onReceiveLocation(locationInfo: BDLocation) {
        if (scanSpan == 0) locationClient!!.stop()
        resultListener?.invoke(locationInfo)
        /**
         * 获取当前城市的位置信息
         */
        if (locationResultType(locationInfo.locType) == LocationResultType.SUCCESS && !TextUtils.equals(locationInfo.city, nowCityName)) {
            nowCityName = locationInfo.city
            geoCodeOfCityName()
            return
        }
        if (locationResultType(locationInfo.locType) == LocationResultType.SUCCESS && nowCityLaLng == null) {
            nowCityName = locationInfo.city
            geoCodeOfCityName()
        }
    }

    /**
     * 通过地理编码获取当前城市中西坐标
     */
    private fun geoCodeOfCityName() {
        //进行地理编码获取当前城市经纬度坐标
        geoCoder = GeoCoder.newInstance()
        geoCoder?.setOnGetGeoCodeResultListener(this)
        geoCoder?.geocode(GeoCodeOption().city(nowCityName).address(nowCityName))
    }

    /**
     * 判断返回结果
     */
    @Suppress("MemberVisibilityCanPrivate")
    fun locationResultType(locCode: Int): LocationResultType {
        return if (locCode == 61 || locCode == 161) {
            LocationResultType.SUCCESS
        } else {
            LocationResultType.FAILURE
        }
    }

    /**
     * 获取定位结果
     */
    fun setLocationResultListener(resultListener: (location: BDLocation) -> Unit) {
        LocationTool.resultListener = resultListener
    }

    /**
     * 定位操作
     */
    fun toLocation(locationType: LocationType, interval: Int = 0) {
        when (locationType) {
            LocationType.START -> if (!locationClient!!.isStarted) locationClient?.start()//开启定位
            LocationType.RESTART -> if (!locationClient!!.isStarted) locationClient?.restart()//再次开启定位
            LocationType.STOP -> if (locationClient!!.isStarted) locationClient?.stop()//停止定位
        }
        scanSpan = interval
    }

    /**
     * 定位类型
     */
    enum class LocationType {
        START, RESTART, STOP//开始、重新开始、结束
    }

    /**
     * 定位结果类型
     */
    enum class LocationResultType {
        SUCCESS, FAILURE//定位成功、定位失败
    }

    /**
     * 地理编码
     */
    override fun onGetGeoCodeResult(result: GeoCodeResult?) {
        //获取地理编码成功
        if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
            nowCityLaLng = result.location//获取当前城市中心点经纬度坐标
            Hawk.put("now_city_loc_info", nowCityLaLng)
            Hawk.put("now_city_name", nowCityName)
        }
        geoCoder?.destroy()
    }

    /**
     * 反地理编码
     */
    override fun onGetReverseGeoCodeResult(result: ReverseGeoCodeResult?) {

    }
}
