package com.c3.pcust30.data.net.rsp.body

import com.c3.pcust30.data.net.rsp.TradingResponseBody

/**
 * 客户列表返回信息
 * Created by Lyan on 17/12/11.
 */
class ClientDataListRsp : TradingResponseBody {

    /**
     * 客户详情
     */
    class ClientInfo {
        var followstatus: String? = null//客户跟进状态: 1:当日, 2:逾期, 3:近期, 4:其他
        var status: String? = null//客户状态
        var accessway: String? = null//客户来源方式
        var tel: String? = null//联系方式
        var gid: Int = 0//客户编码
        var gender: String? = null//先生/女士
        var custname: String? = null//客户姓名
    }

    /**
     * 对象
     */
    class ClientEntity : TradingResponseBody {
        var PcustindiinfoView: ClientInfo? = null
    }

    /**
     * 数组
     */
    class ClientArray : TradingResponseBody {
        var PcustindiinfoView: List<ClientInfo>? = null
    }
}