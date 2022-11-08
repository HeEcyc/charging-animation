package com.darkky.ca.ui.permission

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.darkky.ca.R
import com.darkky.ca.base.BaseDialog
import com.darkky.ca.databinding.PermissionDialogBinding

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    private val overlayPermissionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (Settings.canDrawOverlays(requireContext())) {
            binding.layoutPermission.root.visibility = View.GONE
            binding.layoutInstruction.root.visibility = View.VISIBLE
        }
    }

    override fun setupUI() {
        binding.layoutPermission.buttonPermission.setOnClickListener { askOverlayPermission() }
        binding.layoutInstruction.buttonMoreInfo.setOnClickListener {
            binding.layoutInstruction.root.visibility = View.GONE
            binding.layoutInfo.root.visibility = View.VISIBLE
        }
        binding.layoutInstruction.buttonClose.setOnClickListener { dismiss() }
        binding.layoutInfo.buttonClose.setOnClickListener { dismiss() }
    }

    private fun askOverlayPermission() = overlayPermissionLauncher.launch(
        Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            .setData(Uri.fromParts("package", requireContext().packageName, null))
    )

}