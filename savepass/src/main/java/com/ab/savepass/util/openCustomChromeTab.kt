package com.ab.savepass.util

import android.content.Context
import android.graphics.Color
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import com.google.android.material.color.MaterialColors

fun Context.openCustomChromeTab(link: String) = this.run {
    val defaultColors = CustomTabColorSchemeParams.Builder()
        .setToolbarColor(
            MaterialColors.getColor(
                this,
                androidx.appcompat.R.attr.colorAccent,
                Color.RED
            )
        )
        .build()
    val customTabIntent =
        CustomTabsIntent.Builder().setDefaultColorSchemeParams(defaultColors).build()
    customTabIntent.intent.`package` = "com.android.chrome"
    customTabIntent.launchUrl(this, Uri.parse(link))
}