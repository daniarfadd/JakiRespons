package com.example.jakirespons.mvvm.category.description

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import com.example.jakirespons.R
import com.example.jakirespons.databinding.DescriptionFragmentBinding
import com.example.jakirespons.utils.observeInLifecycle
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class DescriptionFragment : Fragment() {
    private val descriptionViewModel: DescriptionViewModel by viewModel()
    private lateinit var binding : DescriptionFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DescriptionFragmentBinding.inflate(layoutInflater, container, false)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,   savedInstanceState)
        binding.apply {
            tilDesc.setEndIconOnClickListener {
                descriptionViewModel.validate()
            }
            tvDesc.addTextChangedListener {
                descriptionViewModel.setDesc(it.toString())
            }
            toolbar.apply {
                setNavigationOnClickListener { view ->
                    (activity as AppCompatActivity?)?.supportActionBar?.show()
                    view.findNavController().navigateUp()
                }
            }
        }

        descriptionViewModel.apply {
            eventsFlow.onEach {
                when (it) {
                    is DescriptionViewModel.Event.IsValid -> {
                        if (it.bool) {
                            requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED)
                            val direction = DescriptionFragmentDirections.actionDescriptionFragmentToSummaryFragment()
                            requireView().findNavController().navigate(direction)
                        }
                        else {
                            val alertBuilder = AlertDialog.Builder(requireContext())
                            alertBuilder.apply {
                                setTitle(R.string.error)
                                setMessage(R.string.error_min_10_char)
                                setPositiveButton(R.string.ok) { dialog, _ ->
                                    dialog.dismiss()
                                }
                            }
                            val dialog = alertBuilder.create()
                            dialog.show()
                        }
                    }
                }
            }.observeInLifecycle(viewLifecycleOwner)

            desc.observe(viewLifecycleOwner, {
                if (it.length > 10 ) {
                    binding.tilDesc.setEndIconDrawable(R.drawable.ic_baseline_check_24_active)
                }
                else {
                    binding.tilDesc.setEndIconDrawable(R.drawable.ic_baseline_check_24_inactive)
                }
            })
        }

    }

}