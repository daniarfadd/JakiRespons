package com.example.jakirespons.mvvm.category.description.summary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.jakirespons.databinding.SummaryFragmentBinding
import com.example.jakirespons.utils.Lapor
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class SummaryFragment : Fragment() {
    private val summaryViewModel: SummaryViewModel by viewModel()
    private lateinit var binding: SummaryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  SummaryFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Glide.with(requireContext())
            .load(Lapor.photoPath)
            .into(binding.ivReport)

        binding.description.text = Lapor.description
        binding.category.text = Lapor.category
    }

}