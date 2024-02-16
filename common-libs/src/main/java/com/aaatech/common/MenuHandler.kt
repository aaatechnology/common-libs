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
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.aaatech.common.activities.AboutActivity
import com.aaatech.common.utils.BaseLogConst
import com.aaatech.common.utils.Tracker
import com.aaatech.common.utils.openPlayStore
import kotlin.system.exitProcess

/**
 * @author Arun A
 */
internal class MenuHandler {
    companion object {
        private const val STORE_URL =
            "https://play.google.com/store/apps/developer?id=AAA+Technology"
    }

    fun createOptionsMenu(@NonNull activity: Activity, @Nullable menu: Menu?) {
        activity.menuInflater.inflate(R.menu.common_menu, menu)
    }

    fun optionsItemSelected(@NonNull activity: Activity, @NonNull item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.rate_us -> {
                openPlayStore(activity)
                Tracker.track(BaseLogConst.RATING)
                true
            }

            R.id.other_apps -> {
                activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(STORE_URL)))
                Tracker.track(BaseLogConst.MORE_APPS)
                true
            }

            R.id.exit -> {
                Tracker.track(BaseLogConst.EXIT)
                exitProcess(0)
            }

            R.id.about -> {
                activity.startActivity(Intent(activity, AboutActivity::class.java))
                Tracker.track(BaseLogConst.ABOUT)
                true
            }

            else -> false
        }
    }
}
