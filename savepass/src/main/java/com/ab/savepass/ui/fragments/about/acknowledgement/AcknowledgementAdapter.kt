/*
 * BIT Lalpur App
 *
 * Created by Ayaan on 9/29/21, 12:32 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 9/29/21, 12:32 AM
 */

package com.ab.savepass.ui.fragments.about.acknowledgement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ab.core.components.ComponentUse
import com.ab.savepass.databinding.RowComponentBinding

class AcknowledgementAdapter(
    private val listener: (String) -> Unit
) :
    RecyclerView.Adapter<AcknowledgementAdapter.ComponentUseViewHolder>() {

    private var componentUseList = listOf<ComponentUse>()

    fun submitList(com: List<ComponentUse>) {
        this.componentUseList = com
    }

    inner class ComponentUseViewHolder constructor(
        private val binding: RowComponentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.invoke(componentUseList[position].link)
                }
            }
        }

        fun bind(com: ComponentUse) = binding.apply {
            textViewName.text = com.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentUseViewHolder =
        ComponentUseViewHolder(
            RowComponentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ComponentUseViewHolder, position: Int) {
        holder.bind(componentUseList[position])
    }

    override fun getItemCount(): Int =
        componentUseList.size
}