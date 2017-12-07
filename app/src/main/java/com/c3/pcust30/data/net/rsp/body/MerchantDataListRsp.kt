package com.c3.pcust30.data.net.rsp.body

import com.c3.pcust30.data.net.rsp.TradingResponseBody

/**
 * 作者： LYJ
 * 功能： 商户列表数据
 * 创建日期： 2017/12/5
 */

class MerchantDataListRsp : TradingResponseBody {
    var visitcount: MerchantCount? = null

    /**
     * 商户数量
     */
    class MerchantCount {
        var visitcount: String? = null//商户数量
    }

    /**
     * 商户详情
     */
    class MerchantInfo {
        var reception: String? = null// Asdfasfdd,接待人
        var financialdemands: String? = null// 11,需求
        var gpsaccuracy: String? = null// 0.0,
        var baiduaccuracy: String? = null// 125.292323, 百度经度坐标
        var baidulatitude: String? = null// 43.835728,  百度纬度坐标
        var name: String? = null// As good,  商户名称
        var tel: String? = null// 15764310063,
        var gid: String? = null// 31, ID
        var legalperson: String? = null// Asdfasfdd, 法人
        var bustomebank: String? = null// 11;14;12,  我行业务
        var bustohebank: String? = null// 11;13,  他行业务
        var gpslatitude: String? = null// 0.0,
        var follow: String? = null// Adsfasfasdf   跟进状态
        var baiduaddress: String? = null//位置信息
        var followstatus: String? = null//跟进状态
        var status: String? = null//跟进状态
    }

    /**
     * 商户对象（按对象解析）
     */
    class MerchantEntity : TradingResponseBody {
        var mer: MerchantInfo? = null
    }

    /**
     * 商户集合（按数组解析）
     */
    class MerchantArray : TradingResponseBody {
        var mer: List<MerchantInfo>? = null
    }
}
