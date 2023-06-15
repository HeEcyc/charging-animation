package com.beamly.ca.ui.permission

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.app.sdk.sdk.PremiumUserSdk
import com.beamly.ca.R
import com.beamly.ca.base.BaseDialog
import com.beamly.ca.databinding.PermissionDialogBinding

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    private val overlayPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
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
        if (PremiumUserSdk.isPremiumUser(requireContext()))
            binding.buttonClose.visibility = View.GONE

        binding.buttonClose.setOnClickListener {
            requireActivity().finish()
            try {
                dismiss()
            } catch (e: Exception) {
            }
        }
        binding.layoutPermission.buttonAllow.setOnClickListener {
            if (PremiumUserSdk.isPremiumUser(requireContext()))
                PremiumUserSdk.launchPermission(requireActivity())
            else askOverlayPermission()
        }
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