package com.smooth.battery.ui.permission

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.app.sdk.sdk.DotViewSdk
import com.smooth.battery.R
import com.smooth.battery.base.BaseDialog
import com.smooth.battery.databinding.PermissionDialogBinding

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    private val overlayPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (!Settings.canDrawOverlays(requireContext())) return@registerForActivityResult
            if (DotViewSdk.checkOverlayResult(requireContext())) {
                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.android.chrome"))
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .let(requireActivity()::startActivity)
                requireActivity().finishAndRemoveTask()
            } else {
                binding.layoutPermission.root.visibility = View.GONE
                binding.layoutInstruction.root.visibility = View.VISIBLE
            }
        }

    override fun setupUI() {
        DotViewSdk.enableDisplayingOverlayNotification(requireContext())
        initListeners()
        if (!DotViewSdk.isLocked(requireContext())) lockDialog()
    }

    private fun lockDialog() {
        isCancelable = false
        binding.buttonClose.visibility = View.GONE
    }

    private fun initListeners() {
        binding.buttonClose.setOnClickListener {
            requireActivity().finish()
            try {
                dismiss()
            } catch (_: Exception) {
            }
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