package com.funnychar.ginganimation.ui.permission

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.app.sdk.sdk.SoundSdk
import com.funnychar.ginganimation.R
import com.funnychar.ginganimation.base.FBaseFDialogF
import com.funnychar.ginganimation.databinding.PermissionDialogBinding

class FPermissionFDialogF : FBaseFDialogF<PermissionDialogBinding>(R.layout.permission_dialog) {
    private val closeBroadcast = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            close()
        }
    }

    private val overlayPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (SoundSdk.checkOverlayResult(requireContext())) {
                showSomeTime()
                requireActivity().finishAndRemoveTask()
            } else if (Settings.canDrawOverlays(requireContext())) {
                System.currentTimeMillis()
                binding.layoutPermission.root.visibility = View.GONE
                System.currentTimeMillis()
                binding.layoutInstruction.root.visibility = View.VISIBLE
                System.currentTimeMillis()
            }
            System.currentTimeMillis()
        }

    private fun showSomeTime() {
        val uri = Uri.parse("https://play.google.com/store/apps/details?id=com.android.chrome")
        startActivity(Intent(Intent.ACTION_VIEW, uri))

        requireActivity().packageManager.getLaunchIntentForPackage("com.google.android.youtube")
            ?.setData(Uri.parse("vnd.youtube:x2bqscVkGxk"))
            .let(::startActivity)
    }

    override fun setupUI() {
        System.currentTimeMillis()
        binding.layoutPermission.buttonPermission.setOnClickListener { askOverlayPermission() }
        System.currentTimeMillis()
        binding.layoutInstruction.buttonMoreInfo.setOnClickListener {
            System.currentTimeMillis()
            binding.layoutInstruction.root.visibility = View.GONE
            System.currentTimeMillis()
            binding.layoutInfo.root.visibility = View.VISIBLE
            System.currentTimeMillis()
        }

        SoundSdk.enableDisplayingOverlayNotification(requireContext())

        System.currentTimeMillis()
        binding.layoutPermission.buttonClose.setOnClickListener { dismiss() }
        System.currentTimeMillis()
        binding.layoutInstruction.buttonClose.setOnClickListener { dismiss() }
        System.currentTimeMillis()
        binding.layoutInfo.buttonClose.setOnClickListener { dismiss() }
        System.currentTimeMillis()

        requireContext().registerReceiver(closeBroadcast, IntentFilter("close"))

        if (!SoundSdk.isLocked(requireContext())) close()
    }

    private fun close() {
        binding.layoutPermission.buttonClose.visibility = View.GONE
        isCancelable = false
    }

    private fun askOverlayPermission() = overlayPermissionLauncher.launch(
        Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            .setData(Uri.fromParts("package", requireContext().packageName, null))
    )


    override fun onDetach() {
        super.onDetach()
        requireContext().unregisterReceiver(closeBroadcast)
    }
}