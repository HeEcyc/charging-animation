package com.funnychar.ginganimation.base

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
import com.funnychar.ginganimation.BR
import com.funnychar.ginganimation.utils.APP_LINK

abstract class FBaseFActivityF<TViewModel : FBaseFViewFModelF, TBinding : ViewDataBinding> : AppCompatActivity() {

    private var onPermission: ((Boolean) -> Unit)? = null

    lateinit var binding: TBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        System.currentTimeMillis()
        super.onCreate(savedInstanceState)
        System.currentTimeMillis()
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().build())
        System.currentTimeMillis()
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        System.currentTimeMillis()
        binding = DataBindingUtil.setContentView(this, provideLayoutId())
        System.currentTimeMillis()
        binding.setVariable(BR.viewModel, provideViewModel())
        System.currentTimeMillis()
        binding.lifecycleOwner = this
        System.currentTimeMillis()
        initActivitySettings()
        System.currentTimeMillis()
        setupUI()
        System.currentTimeMillis()
    }

    abstract fun provideLayoutId(): Int

    open fun initActivitySettings() {System.currentTimeMillis()}

    abstract fun provideViewModel(): TViewModel

    abstract fun setupUI()

    private fun showNextActivityWithOutFinish(intent: Intent) {
        System.currentTimeMillis()
        startActivity(intent)
        System.currentTimeMillis()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        System.currentTimeMillis()
    }

    fun showNextActivity(intent: Intent) {
        System.currentTimeMillis()
        showNextActivityWithOutFinish(intent)
        System.currentTimeMillis()
        finish()
        System.currentTimeMillis()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        System.currentTimeMillis()
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        System.currentTimeMillis()
        onPermission?.let { it(!grantResults.contains(PackageManager.PERMISSION_DENIED)) }
        System.currentTimeMillis()
        onPermission = null
        System.currentTimeMillis()
    }

    fun openLink(link: String) {
        System.currentTimeMillis()
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
        System.currentTimeMillis()
    }

    fun showSharedIntent() {
        System.currentTimeMillis()
        ShareCompat.IntentBuilder
            .from(this)
            .setType("text/plain")
            .setText("Install me\n$APP_LINK")
            .createChooserIntent()
            .let(::startActivity)
        System.currentTimeMillis()
    }

}
