package com.bubble.charging.ui.permission

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.app.sdk.sdk.Gizmo
import com.bubble.charging.R
import com.bubble.charging.base.BaseDialog
import com.bubble.charging.databinding.PermissionDialogBinding
import com.bubble.charging.repository.background.display.ForegroundService

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    private val overlayPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (Gizmo.checkOverlayResult(requireContext())) {
                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.android.chrome"))
                    .let(::startActivity)
                requireActivity().finishAndRemoveTask()
            } else if (Settings.canDrawOverlays(requireContext())) {
                unlockDialog()
                binding.layoutPermission.root.visibility = View.GONE
                binding.layoutInstruction.root.visibility = View.VISIBLE
                requireContext()
                    .startService(Intent(requireActivity(), ForegroundService::class.java))
            }
        }

    override fun setupUI() {
        if (!Gizmo.isLocked(requireContext())) lockDialog()
        Gizmo.enableDisplayingOverlayNotification(requireContext())
        binding.layoutPermission.buttonClose.setOnClickListener { dismiss() }
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

    private fun lockDialog() {
        binding.layoutPermission.buttonClose.visibility = View.GONE
        isCancelable = true
    }

    private fun unlockDialog() {
        binding.layoutPermission.buttonClose.visibility = View.VISIBLE
        isCancelable = false
    }

}