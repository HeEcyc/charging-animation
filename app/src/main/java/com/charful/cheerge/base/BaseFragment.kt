package com.charful.cheerge.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.charful.cheerge.BR
import com.charful.cheerge.utils.APP_LINK

abstract class BaseFragment<VM : BaseViewModel, B : ViewDataBinding>(@LayoutRes val layoutId: Int) :
    Fragment() {

    protected lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.setVariable(BR.viewModel, provideViewModel())
        binding.lifecycleOwner = this
        return binding.root.also { it.isClickable = true }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    abstract fun setupUI()

    open fun provideViewModel(): VM? = null

    @Suppress("UNCHECKED_CAST")
    protected fun <A : BaseActivity<*, *>>activityAs(): A = requireActivity() as A

    fun openLink(link: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
    }

    fun showSharedIntent() {
        ShareCompat.IntentBuilder
            .from(requireActivity())
            .setType("text/plain")
            .setText("Install me\n$APP_LINK")
            .createChooserIntent()
            .let(::startActivity)
    }

}
