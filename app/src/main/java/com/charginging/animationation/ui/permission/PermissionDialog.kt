package com.charginging.animationation.ui.permission

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.charginging.animationation.App
import com.charginging.animationation.R
import com.charginging.animationation.base.BaseDialog
import com.charginging.animationation.databinding.PermissionDialogBinding

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    private val overlayPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (Settings.canDrawOverlays(requireContext())) {
                App.instance.startForegroundService()
                binding.layoutPermission.root.visibility = View.GONE
                binding.layoutInstruction.root.visibility = View.VISIBLE
            }
        }

    override fun setupUI() {
        isCancelable = false
        initListeners()
    }

    private fun initListeners() {
        binding.layoutPermission.buttonAllow.setOnClickListener { askOverlayPermission() }
        binding.layoutInstruction.buttonClose.setOnClickListener { dismiss() }
        binding.layoutInstruction.buttonMoreInfo.setOnClickListener {
            binding.layoutInstruction.root.visibility = View.GONE
            binding.layoutInfo.root.visibility = View.VISIBLE
        }
        binding.layoutInfo.buttonClose.setOnClickListener { dismiss() }
    }

    private fun askOverlayPermission() = overlayPermissionLauncher.launch(
        Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            .setData(Uri.fromParts("package", requireContext().packageName, null))
    )

}