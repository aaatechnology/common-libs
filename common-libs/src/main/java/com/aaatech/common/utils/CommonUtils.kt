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

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ShareCompat
import androidx.core.text.TextUtilsCompat
import androidx.core.view.ViewCompat
import com.aaatech.common.CommonSettings.APPLICATION_ID
import com.aaatech.common.CommonSettings.APP_NAME
import java.util.*

/**
 * @author Arun
 */
fun isRTL(): Boolean = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == ViewCompat.LAYOUT_DIRECTION_RTL

fun openFeedback(activity: Activity?) {
    ShareCompat.IntentBuilder
            .from(activity!!)
            .setEmailTo(arrayOf("aaatech2014@gmail.com"))
            .setSubject("$APP_NAME Feedback")
            .setType("text/email")
            .setChooserTitle("Send Feedback")
            .startChooser()
}

fun openPlayStore(context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, getAppUri())
    if (isIntentAvailable(context, intent)) {
        context.startActivity(intent)
    }
}

fun getAppUri(): Uri? {
    return Uri.parse("market://details?id=$APPLICATION_ID")
}

fun getAppStoreUri(): Uri? {
    return Uri.parse("https://play.google.com/store/apps/developer?id=AAA+Technology")
}

fun isIntentAvailable(context: Context, intent: Intent): Boolean {
    val packageManager = context.packageManager
    val list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    return list.size > 0
}

fun getAppShareUri(): Uri {
    return Uri.parse("https://play.google.com/store/apps/details?id=$APPLICATION_ID")
}
