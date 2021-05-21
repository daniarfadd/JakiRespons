package com.example.jakirespons.mvvm.category.description.summary

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.jakirespons.MyApplication
import com.example.jakirespons.R
import com.example.jakirespons.databinding.SummaryFragmentBinding
import com.example.jakirespons.service.ForegroundOnlyLocationService
import com.example.jakirespons.utils.*
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class SummaryFragment : Fragment() {
    private val summaryViewModel: SummaryViewModel by viewModel()
    private lateinit var binding: SummaryFragmentBinding
    private lateinit var locationManager : LocationManager

    // Provides location updates for while-in-use feature.
    private var foregroundOnlyLocationService: ForegroundOnlyLocationService? = null

    // Monitors connection to the while-in-use service.
    private val foregroundOnlyServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as ForegroundOnlyLocationService.LocalBinder
            foregroundOnlyLocationService = binder.service
            MyApplication.foregroundOnlyLocationServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            foregroundOnlyLocationService = null
            MyApplication.foregroundOnlyLocationServiceBound = false
        }
    }

    fun setLocationForegroundServiceOn() {
        foregroundOnlyLocationService?.subscribeToLocationUpdates()
    }

    fun setLocationForegroundServiceOff() {
        foregroundOnlyLocationService?.unsubscribeToLocationUpdates()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  SummaryFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val serviceIntent = Intent(requireContext(), ForegroundOnlyLocationService::class.java)
        requireContext().bindService(serviceIntent, foregroundOnlyServiceConnection, Context.BIND_AUTO_CREATE)
        locationManager = requireContext().getLocationManager()
        binding.apply {
            Glide.with(requireContext())
                .load(Lapor.photoPath)
                .into(ivReport)

            description.text = Lapor.description
            category.text = Lapor.category

            toolbar.apply {
                setNavigationOnClickListener { view ->
                    (activity as AppCompatActivity?)?.supportActionBar?.show()
                    view.findNavController().navigateUp()
                }
            }

            send.setOnClickListener {
                send.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
                summaryViewModel.validate()
            }
        }

        summaryViewModel.apply {
            eventsFlow.onEach {
                when(it) {
                    is SummaryViewModel.Event.IsLocValid -> {
                        if (!it.bool) {
                            requireView().showSnackbar(R.string.location_not_valid)
                            if (!requireContext().isLocationEnabled()){
                                requireContext().showAlertReqGPSOn {
                                    summaryViewModel.validate()
                                }
                            }
                            else {
                                setLocationForegroundServiceOn()
                                summaryViewModel.waitLocation()
                            }
                        }
                        else {
                            setLocationForegroundServiceOff()
                        }
                    }

                    is SummaryViewModel.Event.Done -> {
                        if (it.bool) {
                            requireView().showSnackbar(R.string.thank_report)
                            val direction = SummaryFragmentDirections.actionSummaryFragmentToMainFragment()
                            requireView().findNavController().navigate(direction)
                        }
                    }
                }
            }.observeInLifecycle(viewLifecycleOwner)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (MyApplication.foregroundOnlyLocationServiceBound) {
            requireContext().unbindService(foregroundOnlyServiceConnection)
            MyApplication.foregroundOnlyLocationServiceBound = false
        }
    }

}