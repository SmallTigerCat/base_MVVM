package com.sports2020.demo.api.response

import com.google.gson.annotations.SerializedName

/**
 * 下课排班信息

 * @author tsx
 */
data class LineUpData(
        @SerializedName("ClassId") var classId: Int?, // 班级ID
        @SerializedName("ClassName") var className: String?, // 班级名称
        @SerializedName("LineUpStartTime") var lineUpStartTime: String?, // 排队开始时间
        @SerializedName("LineUpTime") var lineUpTime: String?, // 排队时间
        @SerializedName("SortCode") var sortCode: Int?, // 排队顺序
        @SerializedName("ExitId") var exitId: Int?, // 进出口Id
        @SerializedName("ExitName") var exitName: String?, // 进出口名称
        @SerializedName("ExitType") var exitType: Int?, // 进出口类别（1入口，2出口)
        @SerializedName("LineUpIntervalTime") var lineUpIntervalTime: Int?, // 下课排队间隔时间（秒）
        @SerializedName("LineUpCount") var lineUpCount: Int? // 下课排队数量
)