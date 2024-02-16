/*
 * Copyright (c) 2021 AAA Technology
 *
 * All rights reserved.
 *
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY
 * KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
 * PARTICULAR PURPOSE.
 */

package com.aaatech.common.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.aaatech.common.CommonSettings

/**
 * @author Arun
 */
class AppVersion(context: Context, private val appName:String) {
    private var appVersionCode: Int = 0
    private var appVersionName: String

    init {
        try {
            val info: PackageInfo =
                context.packageManager.getPackageInfo(CommonSettings.APPLICATION_ID, 0)
            appVersionName = info.versionName
            appVersionCode = info.versionCode
        } catch (ignore: PackageManager.NameNotFoundException) {
            appVersionName = ""
        }
    }

    fun getVersionText(): String {
        val versionText = if (appVersionName.startsWith('v')) " " else " v"
        return "$appName$versionText$appVersionName"
    }
}
