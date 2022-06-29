package com.ab.savepass.ui.fragments.home

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ab.core.room.PasswordModel
import com.ab.savepass.R
import com.ab.savepass.databinding.FragmentHomeBinding
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeAdapter = HomeAdapter {
            navigateToDetail(it)
        }

        val divider = MaterialDividerItemDecoration(context!!, LinearLayoutManager.VERTICAL)
        divider.dividerThickness = resources.getDimensionPixelOffset(R.dimen.div_height)
        divider.dividerColor = ContextCompat.getColor(requireContext(), R.color.divider_color)
        binding.apply {
            recyclerViewShowPasswords.apply {
                adapter = homeAdapter
                addItemDecoration(divider)
                layoutManager = LinearLayoutManager(context)
            }
        }
        getPasswords()
    }

    private fun navigateToDetail(passwordModel: PasswordModel) {
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(passwordModel)
        findNavController().navigate(action)
    }

    private fun getPasswords() {
        lifecycleScope.launchWhenCreated {
            viewModel.getPasswords().collect {
                homeAdapter.submitList(it)
            }
        }
    }
}