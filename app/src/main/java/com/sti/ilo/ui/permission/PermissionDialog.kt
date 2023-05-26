package com.sti.ilo.ui.permission

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import com.sti.ilo.R
import com.sti.ilo.base.BaseDialog
import com.sti.ilo.databinding.PermissionDialogBinding

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    companion object {
        fun notClosable() = PermissionDialog().apply {
            arguments = bundleOf("not_closable" to true)
        }
    }

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
        parentFragmentManager.setFragmentResult("action", bundleOf())
    }
}