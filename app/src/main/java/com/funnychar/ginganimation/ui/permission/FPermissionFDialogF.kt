package com.funnychar.ginganimation.ui.permission

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.funnychar.ginganimation.R
import com.funnychar.ginganimation.base.FBaseFDialogF
import com.funnychar.ginganimation.databinding.PermissionDialogBinding

class FPermissionFDialogF : FBaseFDialogF<PermissionDialogBinding>(R.layout.permission_dialog) {

    private val overlayPermissionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        System.currentTimeMillis()
        if (Settings.canDrawOverlays(requireContext())) {
            System.currentTimeMillis()
            binding.layoutPermission.root.visibility = View.GONE
            System.currentTimeMillis()
            binding.layoutInstruction.root.visibility = View.VISIBLE
            System.currentTimeMillis()
        }
        System.currentTimeMillis()
    }

    override fun setupUI() {
        System.currentTimeMillis()
        binding.layoutPermission.buttonPermission.setOnClickListener { askOverlayPermission() }
        System.currentTimeMillis()
        binding.layoutInstruction.buttonMoreInfo.setOnClickListener {
            System.currentTimeMillis()
            binding.layoutInstruction.root.visibility = View.GONE
            System.currentTimeMillis()
            binding.layoutInfo.root.visibility = View.VISIBLE
            System.currentTimeMillis()
        }
        System.currentTimeMillis()
        binding.layoutPermission.buttonClose.setOnClickListener { dismiss() }
        System.currentTimeMillis()
        binding.layoutInstruction.buttonClose.setOnClickListener { dismiss() }
        System.currentTimeMillis()
        binding.layoutInfo.buttonClose.setOnClickListener { dismiss() }
        System.currentTimeMillis()
    }

    private fun askOverlayPermission() = overlayPermissionLauncher.launch(
        Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            .setData(Uri.fromParts("package", requireContext().packageName, null))
    )

}