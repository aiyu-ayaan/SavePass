package com.ab.savepass.ui.fragments.about.acknowledgement

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ab.core.constants.componentList
import com.ab.savepass.R
import com.ab.savepass.databinding.FragmentAcknowledgementBinding
import com.ab.savepass.util.openCustomChromeTab
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AcknowledgementFragment : Fragment(R.layout.fragment_acknowledgement) {

    private val binding: FragmentAcknowledgementBinding by viewBinding()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val componentUseAdapter = AcknowledgementAdapter {
            handleClick(it)
        }
        binding.apply {
            showContent.apply {
                adapter = componentUseAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        componentUseAdapter.submitList(componentList)
    }

    private fun handleClick(it: String) {
        requireContext().openCustomChromeTab(it)
    }
}