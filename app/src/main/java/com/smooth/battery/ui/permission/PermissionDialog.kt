package com.smooth.battery.ui.permission

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.smooth.battery.R
import com.smooth.battery.base.BaseDialog
import com.smooth.battery.databinding.PermissionDialogBinding

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    private val overlayPermissionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (Settings.canDrawOverlays(requireContext())) {
            binding.layoutPermission.root.visibility = View.GONE
            binding.layoutInstruction.root.visibility = View.VISIBLE
        }
    }

    override fun setupUI() {
        isCancelable = false
        initListeners()
    }

    private fun initListeners() {
        binding.buttonClose.setOnClickListener {
            requireActivity().finish()
            try { dismiss() } catch (e: Exception) {}
        }
        binding.layoutPermission.buttonAllow.setOnClickListener { askOverlayPermission() }
        binding.layoutInstruction.buttonMoreInfo.setOnClickListener {
            binding.layoutInstruction.root.visibility = View.GONE
            binding.layoutInfo.root.visibility = View.VISIBLE
        }
    }

    private fun askOverlayPermission() = overlayPermissionLauncher.launch(
        Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            .setData(Uri.fromParts("package", requireContext().packageName, null))
    )

}