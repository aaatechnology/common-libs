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

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.aaatech.common.CommonSettings
import com.aaatech.common.databinding.ActivityWebviewBinding
import com.aaatech.common.utils.BaseLogConst
import com.aaatech.common.utils.Tracker

/**
 * @author Arun A
 */
class PrivacyPolicyActivity : AppCompatActivity() {
    private lateinit var binder: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(CommonSettings.getTheme())
        binder = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binder.root)
        val toolbar = binder.toolbar
        toolbar.title = "Privacy Policy"
        setSupportActionBar(toolbar)

        // getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binder.activityBar.visibility = View.VISIBLE
        with(binder.webView) {
            clearHistory()
            with(settings) {
                cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                //setAppCacheMaxSize((1024 * 1024 * 15).toLong())
                //setAppCachePath(applicationContext.cacheDir.absolutePath)
                allowFileAccess = true
                domStorageEnabled = true
                //setAppCacheEnabled(true)
                javaScriptEnabled = true
                domStorageEnabled = true
                textZoom = 100
                setSupportZoom(true)
                builtInZoomControls = true
                displayZoomControls = false
                defaultZoom = WebSettings.ZoomDensity.MEDIUM
                layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
            }
            if (savedInstanceState != null) {
                restoreState(savedInstanceState)
            }
            webViewClient = CustomWebViewClient(binder.activityBar)
            webChromeClient = CustomWebChromeClient(binder.activityBar)
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CookieManager.getInstance().removeSessionCookies(null)
            } else {
                CookieSyncManager.createInstance(applicationContext)
                CookieManager.getInstance().removeSessionCookie()
            }
        } catch (e: Exception) {
            Tracker.trackException(e)
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        ) // Enable Hardware Acceleration for Kitkat and above devices
        WebView.setWebContentsDebuggingEnabled(CommonSettings.CONSOLE_LOG)

        CommonSettings.getPrivacyPolicyURL()?.let { binder.webView.loadUrl(it) }
    }

    private class CustomWebChromeClient(val progressBar: ProgressBar) : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            //progressBar.setIndeterminate(newProgress > 0);
            progressBar.progress = newProgress

            /*if (newProgress < 100) {
                getSupportActionBar().setSubtitle("Loading...");
            } else {
                getSupportActionBar().setSubtitle("");
            }*/
        }

        init {
            Tracker.print("userwebview", "TestChromeClient()")
        }
    }

    private class CustomWebViewClient(val progressBar: ProgressBar) : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            Tracker.print("Page Finished: $url")
            progressBar.visibility = View.GONE
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
            super.onPageStarted(view, url, favicon)
            progressBar.visibility = View.VISIBLE
            Tracker.print("Page Start: $url")
        }

        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            val sslCertificate = error.certificate
            if (error.hasError(SslError.SSL_UNTRUSTED)) {
                handler.proceed()
            } else {
                Tracker.track(BaseLogConst.ERROR, BaseLogConst.TYPE, error.primaryError)
            }
            Tracker.print("onReceivedSslError $sslCertificate")
            super.onReceivedSslError(view, handler, error)
        }

        init {
            Tracker.print("userwebview", "TestViewClient()")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (binder.webView.canGoBack()) {
            binder.webView.goBack()
            return
        }
        super.onBackPressed()
    }
}
