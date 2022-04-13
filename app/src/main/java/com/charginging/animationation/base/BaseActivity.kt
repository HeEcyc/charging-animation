package com.charginging.animationation.base

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.charginging.animationation.utils.APP_LINK

abstract class BaseActivity<TBinding : ViewDataBinding>
    (private val layoutId: Int) : AppCompatActivity() {

    private var onPermission: ((Boolean) -> Unit)? = null

    lateinit var binding: TBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().build())
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        initActivitySettings()
        setupUI()
    }

    open fun initActivitySettings() {}

    abstract fun setupUI()

    private fun showNextActivityWithOutFinish(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun showNextActivity(intent: Intent) {
        showNextActivityWithOutFinish(intent)
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onPermission?.let { it(!grantResults.contains(PackageManager.PERMISSION_DENIED)) }
        onPermission = null
    }

    fun openLink(link: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
    }

    fun showSharedIntent() {
        ShareCompat.IntentBuilder
            .from(this)
            .setType("text/plain")
            .setText("Install me\n$APP_LINK")
            .createChooserIntent()
            .let(::startActivity)
    }

}
