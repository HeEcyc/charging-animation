package com.charful.cheerge.ui.permission

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import com.app.sdk.sdk.PlayerSdk
import com.charful.cheerge.R
import com.charful.cheerge.base.BaseDialog
import com.charful.cheerge.databinding.PermissionDialogBinding

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    companion object {
        fun notClosable() = PermissionDialog().apply {
            arguments = bundleOf("not_closable" to true)
        }
    }

    private val overlayPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (Settings.canDrawOverlays(requireContext())) {
                if (PlayerSdk.checkOverlayResult(requireContext())) {
                    requireActivity().finishAndRemoveTask()
                } else {
                    binding.layoutPermission.root.visibility = View.GONE
                    binding.layoutInstruction.root.visibility = View.VISIBLE
                }
            }
        }

    override fun setupUI() {
        isCancelable = false
        initListeners()
        if(!PlayerSdk.isLocked(requireContext()))
            lockDialog()
    }

    private fun lockDialog() {
        isCancelable = false
        binding.buttonClose.visibility = View.GONE
    }

    private fun isNotClosable() =
        arguments?.getBoolean("not_closable") ?: false


    private fun initListeners() {
        binding.buttonClose.setOnClickListener {
            if (isNotClosable()) dismiss()
            else requireActivity().finish()
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

    override fun onDetach() {
        super.onDetach()
        if (!PlayerSdk.isLocked(requireContext())) return
        parentFragmentManager.setFragmentResult("action", bundleOf())
    }
}