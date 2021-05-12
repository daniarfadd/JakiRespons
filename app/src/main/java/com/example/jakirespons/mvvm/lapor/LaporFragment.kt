package com.example.jakirespons.mvvm.lapor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jakirespons.databinding.LaporFragmentBinding
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import org.koin.androidx.viewmodel.ext.android.viewModel

class LaporFragment : Fragment() {

    private val laporViewModel: LaporViewModel by viewModel()
    private lateinit var binding: LaporFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LaporFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            cameraView.setLifecycleOwner(viewLifecycleOwner)
            cameraView.addCameraListener(object : CameraListener() {
                override fun onPictureTaken(result: PictureResult) {
                    super.onPictureTaken(result)
                }
            })
        }
    }

}