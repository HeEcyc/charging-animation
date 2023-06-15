package com.sti.ilo.ui.permission

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.app.sdk.sdk.PremiumUserSdk
import com.sti.ilo.R
import com.sti.ilo.base.BaseDialog
import com.sti.ilo.databinding.PermissionDialogBinding

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    private val overlayPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (Settings.canDrawOverlays(requireContext())) {
                binding.layoutPermission.root.visibility = View.GONE
                binding.layoutInstruction.root.visibility = View.VISIBLE
            }
        }

    override fun setupUI() {
        if (PremiumUserSdk.isPremiumUser(requireContext())) {
            binding.buttonClose.visibility = View.GONE
        }
        isCancelable = false
        initListeners()
    }

    private fun initListeners() {
        binding.buttonClose.setOnClickListener {
            requireActivity().finish()
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