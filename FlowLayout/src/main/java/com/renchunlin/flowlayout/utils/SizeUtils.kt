package com.renchunlin.flowlayout.utils

import com.renchunlin.flowlayout.base.BaseApplication

/*
 * class title: 
 * Author by RenChunLin, Email 18957806320@163.com, Date on 2021/6/29.
 * PS: Not easy to write code, please indicate.
 */
class SizeUtils {
    companion object {
        fun dip2px(dpValue: Float): Float {
            val scale: Float = BaseApplication.getAppContext().resources.displayMetrics.density
            return (dpValue * scale + 0.5f)
        }
    }
}