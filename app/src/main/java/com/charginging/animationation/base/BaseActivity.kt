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
import com.charginging.animationation.BR
import com.charginging.animationation.utils.APP_LINK
import com.charginging.animationation.utils.IRON_SOURCE_APP_KEY
import com.ironsource.mediationsdk.IronSource

abstract class BaseActivity<TViewModel : BaseViewModel, TBinding : ViewDataBinding>(
) : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        IronSource.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        IronSource.onPause(this)
    }

    private var onPermission: ((Boolean) -> Unit)? = null

    lateinit var binding: TBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IronSource.setMetaData("is_child_directed","false")
        IronSource.init(this, IRON_SOURCE_APP_KEY)
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().build())
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.setContentView(this, provideLayoutId())
        binding.setVariable(BR.viewModel, provideViewModel())
        binding.lifecycleOwner = this
        initActivitySettings()
        setupUI()
    }

    abstract fun provideLayoutId(): Int

    open fun initActivitySettings() {}

    abstract fun provideViewModel(): TViewModel

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
