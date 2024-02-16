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

package com.aaatech.common.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import com.aaatech.common.CommonSettings
import com.aaatech.common.R
import com.aaatech.common.databinding.ActivityAboutBinding
import com.aaatech.common.utils.AppVersion
import com.aaatech.common.utils.BaseLogConst
import com.aaatech.common.utils.Tracker
import com.aaatech.common.utils.getAppShareUri
import com.aaatech.common.utils.getDrawableByName
import com.aaatech.common.utils.openFeedback
import com.aaatech.common.utils.openPlayStore


/**
 * @author Arun A
 */
class AboutActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binder: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binder.root)

        setSupportActionBar(binder.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null

        initControls()
    }

    private fun initControls() {
        with(binder) {
            val appVersion = AppVersion(this@AboutActivity, CommonSettings.APP_NAME)
            logoTextview.text = appVersion.getVersionText()
            logoTextview.setCompoundDrawablesWithIntrinsicBounds(
                null,
                getDrawableByName("logo_splash"),
                null,
                null
            )
            actionRateUs.setOnClickListener(this@AboutActivity)
            actionOtherApps.setOnClickListener(this@AboutActivity)
            actionShare.setOnClickListener(this@AboutActivity)
            actionGiveFeedback.setOnClickListener(this@AboutActivity)
            actionCheckUpdate.setOnClickListener(this@AboutActivity)
            actionPrivacyPolicy.setOnClickListener(this@AboutActivity)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.action_give_feedback -> {
                openFeedback(this)
                Tracker.track(BaseLogConst.FEEDBACK)
            }
            R.id.action_rate_us -> {
                openPlayStore(this)
                Tracker.track(BaseLogConst.RATING)
            }
            R.id.action_check_update -> {
                openPlayStore(this)
                Tracker.track(BaseLogConst.CHECK_FOR_UPDATE)
            }
            R.id.action_share -> {
                val shareText =
                    ("I found this app is very useful. Give it a try. ${getAppShareUri()}")
                ShareCompat.IntentBuilder
                    .from(this)
                    .setText(shareText)
                    .setType("text/plain")
                    .setChooserTitle("Share ${CommonSettings.APP_NAME}")
                    .startChooser()
                Tracker.track(BaseLogConst.SHARE)
            }
        }
    }
}
