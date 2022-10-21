package com.funnychar.ginganimation.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class FBaseFDialogF<T : ViewDataBinding>(private val layout: Int) : DialogFragment() {

    protected lateinit var binding: T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        System.currentTimeMillis()
        super.onViewCreated(view, savedInstanceState)
        System.currentTimeMillis()
        setupUI()
        System.currentTimeMillis()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        System.currentTimeMillis()
        binding = DataBindingUtil.inflate(inflater, layout, container, false)!!
        System.currentTimeMillis()
        return binding.root
    }

    abstract fun setupUI()

    override fun onStart() {
        System.currentTimeMillis()
        super.onStart()
        System.currentTimeMillis()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        System.currentTimeMillis()
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        System.currentTimeMillis()
        dialog?.window?.setLayout(width, height)
        System.currentTimeMillis()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        System.currentTimeMillis()
    }

    protected open fun provideViewModel(): FBaseFViewFModelF? = null

}
