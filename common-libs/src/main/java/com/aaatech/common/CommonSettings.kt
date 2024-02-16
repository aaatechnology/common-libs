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

package com.aaatech.common

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.DrawableRes
import androidx.annotation.Nullable
import androidx.annotation.StyleRes
import com.aaatech.common.utils.Tracker

/**
 * @author Arun A
 */
object CommonSettings {
    internal var APPLICATION_ID: String = ""
    internal lateinit var APP_NAME: String
    internal var CONSOLE_LOG = false
    internal var ANALYTICS_LOG = false

    private var mCommonSettingsParams: CommonSettingsParams? = null
    private var appVersionName: String? = null
    private var appVersionCode = 0
    private lateinit var mMenuHandler: MenuHandler

    fun init(context: Context, commonSettingsParam: CommonSettingsParams) {
        mCommonSettingsParams = commonSettingsParam
        APPLICATION_ID = context.packageName
        APP_NAME = commonSettingsParam.getAppName()
        ANALYTICS_LOG = commonSettingsParam.isAnalyticsEnabled()
        CONSOLE_LOG = commonSettingsParam.isConsoleLogEnabled()
        mMenuHandler = MenuHandler()
        try {
            val info = context.packageManager.getPackageInfo(APPLICATION_ID, 0)
            appVersionName = info.versionName
            appVersionCode = info.versionCode
        } catch (ignore: PackageManager.NameNotFoundException) {
        }

        initFirebaseAnalytics(context)
    }

    private fun initFirebaseAnalytics(context: Context) {
        if (ANALYTICS_LOG) {
            Tracker.init(context)
        }
    }

    fun getAppVersionName(): String {
        return appVersionName!!
    }

    fun getAppVersionCode(): Int {
        return appVersionCode
    }

    fun getVersionName(): String {
        return ""
    }

    fun getVersionCode(): Int {
        return 0
    }

    fun getAppName(): String {
        return APP_NAME!!
    }

    fun getApplicationId(): String {
        return APPLICATION_ID!!
    }

    fun getPackageName(): String {
        return APPLICATION_ID!!
    }

    fun isConsoleLogEnabled(): Boolean {
        return CONSOLE_LOG
    }

    fun isAnalyticsEnabled(): Boolean {
        return ANALYTICS_LOG
    }

    @DrawableRes
    fun getAppIcon(): Int {
        return mCommonSettingsParams!!.getLogo()
    }

    @Nullable
    fun getPrivacyPolicyURL(): String? {
        return mCommonSettingsParams!!.getPolicyURL()
    }

    @StyleRes
    fun getTheme(): Int {
        return mCommonSettingsParams!!.getTheme()
    }

    fun onCreateOptionsMenu(activity: Activity, menu: Menu) {
        mMenuHandler.createOptionsMenu(activity, menu)
    }

    fun onOptionsItemSelected(activity: Activity, item: MenuItem): Boolean {
        return mMenuHandler.optionsItemSelected(activity, item)
    }
}
