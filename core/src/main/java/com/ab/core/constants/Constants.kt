package com.ab.core.constants

import com.ab.core.components.ComponentUse


const val PREF_NAME = "savepass"
const val PREF_PASSWORD = "password"
const val UPDATE_PASSWORD = 100 * 34
const val IS_ENABLE_FINGER = "is_enable_finger"
const val REQUEST_VERIFY_PIN = 100 * 35
const val REQUEST_VERIFY_DONE = 100 * 36

val componentList = listOf(
    ComponentUse(
        "androidx.appcompat:appcompat",
        "https://developer.android.com/jetpack/androidx/releases/appcompat"
    ),
    ComponentUse(
        "androidx.browser:browser",
        "https://developer.android.com/jetpack/androidx/releases/browser"
    ),
    ComponentUse(
        "androidx.constraintlayout:constraintlayout",
        "https://developer.android.com/jetpack/androidx/releases/constraintlayout"
    ),
    ComponentUse(
        "androidx.core:core-ktx",
        "https://developer.android.com/jetpack/androidx/releases/core"
    ),
    ComponentUse(
        "androidx.core:core-splashscreen",
        "https://developer.android.com/guide/topics/ui/splash-screen"
    ),
    ComponentUse(
        "androidx.fragment:fragment-ktx",
        "https://developer.android.com/jetpack/androidx/releases/fragment"
    ),
    ComponentUse(
        "androidx.lifecycle:lifecycle-livedata-ktx",
        "https://developer.android.com/jetpack/androidx/releases/lifecycle"
    ),
    ComponentUse(
        "androidx.lifecycle:lifecycle-viewmodel-ktx",
        "https://developer.android.com/jetpack/androidx/releases/lifecycle"
    ),
    ComponentUse(
        "androidx.navigation:navigation-fragment-ktx",
        "https://developer.android.com/jetpack/androidx/releases/navigation"
    ),
    ComponentUse(
        "androidx.recyclerview:recyclerview",
        "https://developer.android.com/jetpack/androidx/releases/recyclerview"
    ),
    ComponentUse(
        "androidx.room:room-ktx",
        "https://developer.android.com/jetpack/androidx/releases/room"
    ),
    ComponentUse(
        "androidx.webkit:webkit",
        "https://developer.android.com/reference/androidx/webkit/package-summary"
    ),
    ComponentUse("com.airbnb.android:lottie", "https://github.com/airbnb/lottie-android"),
    ComponentUse(
        "com.github.yogacp:android-viewbinding",
        "https://github.com/yogacp/android-viewbinding"
    ),
    ComponentUse(
        "com.google.android.material:material",
        "https://material.io/develop/android/docs/getting-started"
    ),
    ComponentUse(
        "com.google.android.play:core-ktx",
        "https://developer.android.com/reference/com/google/android/play/core/release-notes"
    ),
    ComponentUse(
        "com.google.dagger:hilt-android",
        "https://developer.android.com/training/dependency-injection/hilt-android"
    ),
    ComponentUse(
        "org.jetbrains.kotlinx:kotlinx-coroutines-core",
        "https://github.com/Kotlin/kotlinx.coroutines"
    )
)