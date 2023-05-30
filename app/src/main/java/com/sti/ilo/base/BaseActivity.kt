package com.sti.ilo.base

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.StrictMode
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.sti.ilo.BR

abstract class BaseActivity<TViewModel : BaseViewModel, TBinding : ViewDataBinding> : AppCompatActivity() {

    private var onPermission: ((Boolean) -> Unit)? = null

    lateinit var binding: TBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

}
