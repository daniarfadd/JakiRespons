package com.example.jakirespons.mvvm.lapor

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.jakirespons.R
import com.example.jakirespons.databinding.LaporFragmentBinding
import com.example.jakirespons.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.IOException


class LaporFragment : Fragment(), EasyPermissions.PermissionCallbacks{

    private lateinit var binding: LaporFragmentBinding
    private val laporViewModel : LaporViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LaporFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        laporViewModel.storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        reqPermissions()
    }

    @AfterPermissionGranted(RC_CAMERA_AND_STORAGE)
    private fun reqPermissions() {
        val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (EasyPermissions.hasPermissions(requireContext(), *permissions)){
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        laporViewModel.createImageFile()
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
                        startActivityForResult(takePictureIntent, REQ_TAKE_PHOTO)
                    }
                }
            }
        }
        else {
            EasyPermissions.requestPermissions(this, getString(R.string.request_permissions), RC_CAMERA_AND_STORAGE, *permissions)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) { }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        requireContext().showToast(R.string.request_permissions, Toast.LENGTH_LONG)

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    companion object {
        const val REQ_TAKE_PHOTO = 1
        const val RC_CAMERA_AND_STORAGE = 2
    }

}