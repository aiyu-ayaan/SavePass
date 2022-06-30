package com.ab.core.components

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class ComponentUse(
    val name: String,
    val link: String
) : Parcelable
