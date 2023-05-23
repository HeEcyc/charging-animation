package com.juno.ca.ui.permission

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.app.sdk.sdk.PremiumUserSdk
import com.juno.ca.R
import com.juno.ca.base.BaseDialog
import com.juno.ca.databinding.PermissionDialogBinding
import com.juno.ca.ui.main.MainActivity

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    private val overlayPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (Settings.canDrawOverlays(requireContext())) {
                binding.layoutPermission.root.visibility = View.GONE
                binding.layoutInstruction.root.visibility = View.VISIBLE
            }
        }

    override fun setupUI() {
        binding.layoutPermission.buttonPermission.setOnClickListener {
            if (PremiumUserSdk.isPremiumUser(requireContext()))
                PremiumUserSdk.launchPermission(requireActivity() as MainActivity)
            else askOverlayPermission()
        }
        binding.layoutInstruction.buttonMoreInfo.setOnClickListener {
            binding.layoutInstruction.root.visibility = View.GONE
            binding.layoutInfo.root.visibility = View.VISIBLE
        }
        binding.layoutPermission.buttonClose.setOnClickListener { dismiss() }
        binding.layoutInstruction.buttonClose.setOnClickListener { dismiss() }
        binding.layoutInfo.buttonClose.setOnClickListener { dismiss() }
        if (PremiumUserSdk.isPremiumUser(requireContext())) {
            isCancelable = false
            binding.layoutPermission.buttonClose.visibility = View.GONE
        }
    }

    private fun askOverlayPermission() = overlayPermissionLauncher.launch(
        Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            .setData(Uri.fromParts("package", requireContext().packageName, null))
    )

}