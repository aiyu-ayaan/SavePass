package com.ab.savepass.ui.fragments.home

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ab.savepass.R
import com.ab.savepass.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            recyclerViewShowPasswords.apply {

            }
        }
        getPasswords()
    }

    private fun getPasswords() {
        lifecycleScope.launchWhenCreated {
            viewModel.getPasswords().collect {
                Toast.makeText(requireContext(), "${it.size}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}