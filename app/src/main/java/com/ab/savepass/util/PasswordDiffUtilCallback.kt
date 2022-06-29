package com.ab.savepass.util

import androidx.recyclerview.widget.DiffUtil
import com.ab.core.room.PasswordModel

class PasswordDiffUtilCallback : DiffUtil.ItemCallback<PasswordModel>() {
    override fun areItemsTheSame(oldItem: PasswordModel, newItem: PasswordModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PasswordModel, newItem: PasswordModel): Boolean {
        return oldItem == newItem
    }
}