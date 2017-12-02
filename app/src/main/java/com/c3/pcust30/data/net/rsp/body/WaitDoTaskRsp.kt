package com.c3.pcust30.data.net.rsp.body

import com.c3.pcust30.data.net.rsp.TradingResponseBody


/**
 * 返回待办任务详细信息
 * Created by Lyan on 17/12/2.
 */
class WaitDoTaskRsp : TradingResponseBody {

    var pageCount: TaskPageCount? = null
    var taskCount: TaskCount? = null
    var pageNo: TaskPageNo? = null

    /**
     * 返回待办任务总页数
     */
    class TaskPageCount {
        var pageCount: String? = null
    }

    /**
     * 返回代办任务数量
     */
    class TaskCount {
        var taskCount: String? = null
    }

    /**
     * 返回当前请求的页数
     */
    class TaskPageNo {
        var pageNo: String? = null
    }

    /**
     * 待办任务详情
     */
    class TaskInfo {
        var taskId: String? = null//环节ID
        var detailUrl: String? = null//进入流程连接需要手机端拼接成 json 格式
        var createTime: String? = null//申请时间
        var tel: String? = null//联系方式
        var processName: String? = null//流程名称
        var creater: String? = null//申请人
        var taskName: String? = null//环节名称
        var descriptions: String? = null//描述
        var tokenId: String? = null//用来查看流程图
        var source: String? = null//客户来源方式
        var name: String? = null//实例名称
        var gender: String? = null//性别描述 先生/女士
        var due: String? = null//任务是否逾期: 1:逾期: 0:未逾期
        var deadline: String? = null//任务截止日期
        var custname: String? = null//客户姓名
        var custid: Int = 0//客户ID
    }

    /**
     * 返回对象
     */
    class WaitDoTaskEntity : TradingResponseBody {
        var taskInfo: TaskInfo? = null
    }

    /**
     * 返回数组
     */
    class WaitDoTaskArray : TradingResponseBody {
        var taskInfo: List<TaskInfo>? = null//只读类型的集合
    }

}