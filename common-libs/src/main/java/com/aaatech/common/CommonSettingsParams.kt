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

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.StyleRes

/**
 * @author Arun A
 */
class CommonSettingsParams(settingsBuilder: CommonSettingsBuilder) {
    private var mAppName: String
    private var mAnalyticsLogState = false
    private var mConsoleLogState = false
    private var mSettingsBuilder: CommonSettingsBuilder? = null

    init {
        mSettingsBuilder = settingsBuilder
        mAppName = settingsBuilder.appName
        mAnalyticsLogState = settingsBuilder.analyticsLogState
        mConsoleLogState = settingsBuilder.consoleLogState
    }

    @NonNull
    fun getAppName(): String {
        return mAppName
    }

    fun isAnalyticsEnabled(): Boolean {
        return mAnalyticsLogState
    }

    fun isConsoleLogEnabled(): Boolean {
        return mConsoleLogState
    }

    @DrawableRes
    fun getLogo(): Int {
        return mSettingsBuilder!!.logo
    }

    @Nullable
    fun getPolicyURL(): String? {
        return mSettingsBuilder!!.policyURL
    }

    @StyleRes
    fun getTheme(): Int {
        return mSettingsBuilder!!.theme
    }

    class CommonSettingsBuilder(@NonNull internal val context: Context, @NonNull internal val appName: String) {
        internal var analyticsLogState = false
        internal var consoleLogState = false
        internal var policyURL: String? = null

        @DrawableRes
        internal var logo = 0

        @StyleRes
        internal var theme = 0

        fun setAnalyticsEnabled(analyticsLogState: Boolean): CommonSettingsBuilder {
            this.analyticsLogState = analyticsLogState
            return this
        }

        fun setConsoleLogEnabled(consoleLogState: Boolean): CommonSettingsBuilder {
            this.consoleLogState = consoleLogState
            return this
        }

        fun setLogoDrawableRes(@DrawableRes logo: Int): CommonSettingsBuilder {
            this.logo = logo
            return this
        }

        fun setPrivacyPolicyURL(policyURL: String?): CommonSettingsBuilder {
            this.policyURL = policyURL
            return this
        }

        fun setTheme(@StyleRes theme: Int): CommonSettingsBuilder {
            this.theme = theme
            return this
        }

        fun build(): CommonSettingsParams {
            return CommonSettingsParams(this)
        }
    }
}
