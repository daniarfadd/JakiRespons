package com.example.jakirespons.mvvm.lapor.category

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.jakirespons.R
import com.example.jakirespons.databinding.ActivityMainBinding
import com.example.jakirespons.databinding.CategoryFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryFragment : Fragment() {
    private val categoryViewModel: CategoryViewModel by viewModel()
    private lateinit var binding: CategoryFragmentBinding
    private val adapter = CategoryAdapter {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CategoryFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            recyclerView.adapter = adapter
            categoryFilter.addTextChangedListener {
                categoryViewModel.filter(it.toString())
            }
        }
        categoryViewModel.apply {
            categories.observe(viewLifecycleOwner, {
                adapter.setData(it)
            })
        }
    }

}