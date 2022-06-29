package com.ab.savepass.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ab.core.room.PasswordModel
import com.ab.savepass.databinding.RowHomeItemBinding
import com.ab.savepass.util.PasswordDiffUtilCallback

class HomeAdapter(
    private val clickListener: (PasswordModel) -> Unit
) :
    ListAdapter<PasswordModel, HomeAdapter.HomeViewHolder>(PasswordDiffUtilCallback()) {

    inner class HomeViewHolder(
        private val binding: RowHomeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        init {
            binding.root.setOnClickListener {
                absoluteAdapterPosition.apply {
                    if (this != RecyclerView.NO_POSITION) {
                        clickListener.invoke(getItem(this))
                    }
                }
            }
        }

        fun bind(passwordModel: PasswordModel) = binding.apply {
            textViewUser.text = passwordModel.username
            textViewWebsite.text = passwordModel.website
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder =
        HomeViewHolder(
            RowHomeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


