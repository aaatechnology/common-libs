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
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.aaatech.common.CommonSettings
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import java.util.HashMap

/**
 * @author Arun A
 */
object Tracker {
    // Analytics
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    fun init(context: Context) {
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN) {
            param("Model", Build.MODEL)
        }
    }

    fun track(event: String) {
        if (CommonSettings.ANALYTICS_LOG)
            firebaseAnalytics.logEvent(event, null)
    }

    /*fun track(triple: Triple<String, String, Any>) =
        track(triple.first, triple.second, triple.third)*/

    fun track(event: String, pair: Pair<String, Any>) = track(event, pair.first, pair.second)

    fun track(event: String, key: String, value: Any) {
        if (CommonSettings.ANALYTICS_LOG) {
            firebaseAnalytics.logEvent(event) {
                param(key, value.toString())
            }
        }
    }

    fun track(event: String, bundle: Bundle) {
        if (CommonSettings.ANALYTICS_LOG) {
            firebaseAnalytics.logEvent(event, bundle)
        }
    }

    fun track(event: String, map: HashMap<String, Any>) {
        if (CommonSettings.ANALYTICS_LOG) {
            var bundle: Bundle? = null
            if (map.isNotEmpty()) {
                bundle = Bundle()
                for (entry in map.entries) {
                    when (entry.value) {
                        is Int -> bundle.putInt(entry.key, (entry.value as Int))
                        is Double -> bundle.putDouble(entry.key, (entry.value as Double))
                        is Float -> bundle.putFloat(entry.key, (entry.value as Float))
                        else -> bundle.putString(entry.key, entry.value.toString())
                    }

                }
            }

            firebaseAnalytics.logEvent(event, bundle)
        }
    }


    // Crashlytics
    fun trackException(e: Throwable) {
        try {
            FirebaseCrashlytics.getInstance().recordException(e)
        } catch (ignored: java.lang.Exception) {
        }
    }


    // Debug logger
    /*fun print(log: Any, type: String = "d") {
        if (BuildConfig.PRINT_LOG) {
            when (type) {
                "e" -> Log.e(BuildConfig.APP_NAME, log.toString())
                else -> Log.d(BuildConfig.APP_NAME, log.toString())
            }
        }
    }*/

    fun print(tag: String, log: Any) {
        if (CommonSettings.CONSOLE_LOG) {
            Log.d(tag, log.toString())
        }
    }

    fun print(e: Exception) {
        if (CommonSettings.CONSOLE_LOG) {
            Log.e(CommonSettings.APP_NAME, "${e.message}")
            e.printStackTrace()
        }
    }

    fun print(vararg log: Any) {
        if (CommonSettings.CONSOLE_LOG) {
            Log.d(CommonSettings.APP_NAME, log.contentToString())
        }
    }
}
