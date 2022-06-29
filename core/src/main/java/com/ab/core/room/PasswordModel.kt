package com.ab.core.room

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Keep
@Entity(tableName = "password_table")
@Parcelize
data class PasswordModel(
    var username: String,
    var password: String,
    @PrimaryKey(autoGenerate = false)
    var website: String,
) : Parcelable