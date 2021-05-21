package com.example.jakirespons.mvvm.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.jakirespons.R
import com.example.jakirespons.databinding.CategoryFragmentBinding
import com.example.jakirespons.utils.Lapor
import com.example.jakirespons.utils.showSnackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryFragment : Fragment() {
    private val categoryViewModel: CategoryViewModel by viewModel()
    private lateinit var binding: CategoryFragmentBinding
    private val adapter = CategoryAdapter {
        Lapor.category = it
        binding.root.showSnackbar(requireContext().getString(R.string.category_selected_snackbar, it))

        val direction = CategoryFragmentDirections.actionCategoryFragmentToDescriptionFragment()
        binding.root.findNavController().navigate(direction)
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
            Glide.with(requireContext())
                .load(Lapor.photoPath)
                .into(imageView)
            toolbar.apply {
                setNavigationOnClickListener { view ->
                    (activity as AppCompatActivity?)?.supportActionBar?.show()
                    view.findNavController().navigateUp()
                }
            }
        }
        categoryViewModel.apply {
            categories.observe(viewLifecycleOwner, {
                adapter.setData(it)
            })
        }
    }

}