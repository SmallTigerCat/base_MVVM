package com.sports2020.demo.api.response

import com.google.gson.annotations.SerializedName

/**
 * 登录信息
 *
 * @author tsx
 */
data class LoginData(
        @SerializedName("Id") var id: Int?, // 当前登录用户编号
        @SerializedName("ClassId") var classId: Int?, // 班级id
        @SerializedName("ClassName") var className: String?, // 班级名称
        @SerializedName("SchoolName") var schoolName: String?, // 学校名称
        @SerializedName("SubjectName") var subjectName: String?, // 学科名称
        @SerializedName("AccountName") var accountName: String?, // 账号名
        @SerializedName("UserName") var userName: String // 用户名称
) {
//    fun toUser(): User? {
//        return id?.let { classId?.let { it1 -> User(it, it1, className, schoolName, subjectName, accountName, userName, 1) } }
//    }
}

