package com.example.jakirespons.mvvm.main

import android.Manifest
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.IBinder
import android.provider.MediaStore
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.jakirespons.MyApplication
import com.example.jakirespons.R
import com.example.jakirespons.base.BaseViewModel
import com.example.jakirespons.databinding.FragmentOptionAturBinding
import com.example.jakirespons.databinding.MainFragmentBinding
import com.example.jakirespons.service.ForegroundOnlyLocationService
import com.example.jakirespons.utils.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.IOException

class MainFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding : MainFragmentBinding
    private val viewModel: MainViewModel by viewModel()
    private val resultLauncherCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK){
            Lapor.photoPath = viewModel.currentPhotoPath
            val direction = MainFragmentDirections.actionMainFragmentToCategoryFragment()
            binding.root.findNavController().navigate(direction)
        }
        GlobalScope.launch {
            delay(5000)
            setLocationForegroundServiceOff()
        }
    }
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

    private lateinit var sheetBehavior: BottomSheetBehavior<FrameLayout>
    private lateinit var sheetDialog: BottomSheetDialog
    private val adapter = MainAdapter{
        val direction = MainFragmentDirections.actionMainFragmentToDetailFragment(it.id ?: "")
        requireView().findNavController().navigate(direction)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        requireContext().showToast(R.string.request_permissions, Toast.LENGTH_LONG)

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            reqPermissions()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val serviceIntent = Intent(requireContext(), ForegroundOnlyLocationService::class.java)
        requireContext().bindService(serviceIntent, foregroundOnlyServiceConnection, Context.BIND_AUTO_CREATE)
        locationManager = requireContext().getLocationManager()
        binding.apply {
            val sortView = FragmentOptionAturBinding.inflate(layoutInflater)

            sortView.apply {
                btnClose.setOnClickListener {
                    sheetDialog.dismiss()
                }

                btnChoose.setOnClickListener {
                    when (rgOptions.checkedRadioButtonId) {
                        R.id.rb_baru -> {
                            viewModel.sort = MainViewModel.SORT_LATEST
                        }
                        R.id.rb_mou -> {
                            viewModel.sort = MainViewModel.SORT_OLDEST
                        }
                        R.id.rb_lvg -> {
                            viewModel.sort = MainViewModel.SORT_COMMENT
                        }
                        R.id.rb_moyes -> {
                            viewModel.sort = MainViewModel.SORT_SUPPORT
                        }
                    }

                    sheetDialog.dismiss()
                }
            }

            sheetBehavior = BottomSheetBehavior.from(bottomSheet)
            sheetDialog = BottomSheetDialog(requireContext())
            sheetDialog.setContentView(sortView.root)
            sheetDialog.setOnShowListener {
                when (viewModel.sort) {
                    MainViewModel.SORT_LATEST -> {
                        sortView.rgOptions.check(R.id.rb_baru)
                    }
                    MainViewModel.SORT_OLDEST -> {
                        sortView.rgOptions.check(R.id.rb_mou)
                    }
                    MainViewModel.SORT_COMMENT -> {
                        sortView.rgOptions.check(R.id.rb_lvg)
                    }
                    MainViewModel.SORT_SUPPORT -> {
                        sortView.rgOptions.check(R.id.rb_moyes)
                    }
                }
            }
            sheetDialog.setOnDismissListener {
                viewModel.getData()
            }
            sheetDialog.window?.addFlags(FLAG_TRANSLUCENT_STATUS)

            toolbar.inflateMenu(R.menu.main_menu)
            toolbar.setOnMenuItemClickListener{
                when (it.itemId) {
                    R.id.report -> {
                        onReportMenuClicked()
                        return@setOnMenuItemClickListener true
                    }
                    else -> return@setOnMenuItemClickListener false
                }
            }
            buttonFilter.setOnClickListener {
                buttonFilterOnClick()
            }
            imgFilter.setOnClickListener {
                buttonFilterOnClick()
            }
            tvSort.setOnClickListener {
                buttonFilterOnClick()
            }

            buttonSort.setOnClickListener {
                buttonSortOnClick()
            }
            imgSort.setOnClickListener {
                buttonSortOnClick()
            }
            tvSort.setOnClickListener {
                buttonSortOnClick()
            }

            swipeRefresh.setOnRefreshListener {
                viewModel.getData()
            }
            rvLaporan.adapter = adapter

            searchQuery.setOnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    viewModel.search = searchQuery.text.toString()
                    viewModel.getData()
                    return@setOnKeyListener true
                }
                false
            }

            btnSearch.setOnClickListener {
                viewModel.search = searchQuery.text.toString()
                viewModel.getData()
            }
        }
        viewModel.apply {
            storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

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

            list.observe(viewLifecycleOwner, {
                if (it.isNotEmpty()){
                    adapter.setData(it)
                }
                else {
                    binding.root.showSnackbar(R.string.no_data)
                }
            })

            isConnected = requireContext().isConnected()
        }

        viewModel.getData()
    }

    private fun buttonFilterOnClick() {
        requireView().showSnackbar(R.string.under_development)
    }

    private fun buttonSortOnClick() {
        sheetDialog.show()

    }

    private fun onReportMenuClicked() {
        if (requireContext().isLocationEnabled()){
            reqPermissions()
        }
        else {
            requireContext().showAlertReqGPSOn {
                onReportMenuClicked()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (MyApplication.foregroundOnlyLocationServiceBound) {
            requireContext().unbindService(foregroundOnlyServiceConnection)
            MyApplication.foregroundOnlyLocationServiceBound = false
        }
    }

    private fun setLocationForegroundServiceOn() {
        foregroundOnlyLocationService?.subscribeToLocationUpdates()
    }

    private fun setLocationForegroundServiceOff() {
        foregroundOnlyLocationService?.unsubscribeToLocationUpdates()
    }

    @AfterPermissionGranted(RC_CAMERA_AND_STORAGE)
    private fun reqPermissions() {
        val permissions = arrayListOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (EasyPermissions.hasPermissions(requireContext(), *permissions.toTypedArray())){
            setLocationForegroundServiceOn()
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        viewModel.createImageFile()
                    } catch (ex: IOException) {
                        ex.printStackTrace()
                        requireContext().showToast(R.string.error_file_creation, Toast.LENGTH_SHORT)
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            requireContext(),
                            "com.example.jakirespons.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    }
                }
                GlobalScope.launch {
                    delay(500)
                    resultLauncherCamera.launch(takePictureIntent)
                }
            }

        }
        else {
            EasyPermissions.requestPermissions(this, getString(R.string.request_permissions),
                RC_CAMERA_AND_STORAGE, *permissions.toTypedArray())
        }
    }

    companion object {
        const val RC_CAMERA_AND_STORAGE = 2
    }

}