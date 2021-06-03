package com.example.jakirespons.mvvm.detail

import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.jakirespons.R
import com.example.jakirespons.base.BaseViewModel
import com.example.jakirespons.databinding.DetailFragmentBinding
import com.example.jakirespons.utils.isConnected
import com.example.jakirespons.utils.observeInLifecycle
import com.example.jakirespons.utils.showSnackbar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class DetailFragment : Fragment() {

    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var binding: DetailFragmentBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            fabLike.setOnClickListener { view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            toolbar.apply {
                setNavigationOnClickListener { view ->
                    (activity as AppCompatActivity?)?.supportActionBar?.show()
                    view.findNavController().navigateUp()
                }
            }
            swipeRefresh.setOnRefreshListener {
                detailViewModel.getData()
            }
        }

        detailViewModel.apply{
            id = args.id
            isConnected = requireContext().isConnected()

            eventsFlow.onEach {
                when (it) {
                    is BaseViewModel.Event.Refresh -> {
                        binding.swipeRefresh.isRefreshing = it.bool
                    }
                    is BaseViewModel.Event.ShowSnackbar -> {
                        if (BaseViewModel.MESSAGE_NO_INTERNET == it.message) {
                            binding.root.showSnackbar(R.string.error_no_internet)
                        }
                    }
                }
            }.observeInLifecycle(viewLifecycleOwner)

            data.observe(viewLifecycleOwner, {
                binding.apply {
                    Glide.with(root.context)
                        .load(it.photo)
                        .into(gambarLaporan)

                    judulLaporan.text = it.title?.removeSurrounding("\"")
                    nomorLaporan.text = it.id
                    tanggalMasuk.text = it.createdAt
                    kategoriLap.text = it.category?.removeSurrounding("\"")

                    val lat = it.lat?.toDouble() ?: 0.0
                    val long = it.longi?.toDouble() ?: 0.0

                    val geocoder = Geocoder(root.context, Locale.getDefault())
                    try {
                        val addr = geocoder.getFromLocation(lat, long, 1)
                        if (addr.size > 0 ){
                            lokasiLaporan.text = addr[0].locality
                        }
                        else {
                            lokasiLaporan.text = "-"
                        }

                    }
                    catch (e: Exception){
                        lokasiLaporan.text = "-"

                    }

                    llLocation.setOnClickListener {
                        intentMaps(long, lat)
                    }
                    llLocation1.setOnClickListener {
                        intentMaps(long, lat)
                    }
                    llLocation2.setOnClickListener {
                        intentMaps(long, lat)
                    }
                    lokasiLaporan.setOnClickListener {
                        intentMaps(long, lat)
                    }
                    lokasiLaporan1.setOnClickListener {
                        intentMaps(long, lat)
                    }
                    ibLocation.setOnClickListener {
                        intentMaps(long, lat)
                    }

                    if (it.urgent == 1) {
                        urgency.text = root.context.resources.getString(R.string.urgent)
                        urgency.setTextColor(ContextCompat.getColor(root.context, R.color.red))
                    }
                    else {
                        urgency.text = root.context.resources.getString(R.string.not_urgent)
                        urgency.setTextColor(nomorLaporan.textColors)
                    }

//                    statusLap.text = it.currentStatus?.get(0)?.status
//                    tglProses.text = it.currentStatus?.get(0)?.createdAt
//                    prosesLaporan.text = it.currentStatus?.get(0)?.who
                }
            })


        }

        detailViewModel.getData()
    }

    private fun intentMaps(long: Double, lat: Double) {
        // Create a Uri from an intent string. Use the result to create an Intent.
        val gmmIntentUri = Uri.parse("google.navigation:q=${lat},${long}")

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps")

        // Attempt to start an activity that can handle the Intent
        startActivity(mapIntent)
    }

}