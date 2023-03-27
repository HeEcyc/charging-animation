package com.juno.ca.ui.catalog

import android.view.View
import androidx.activity.viewModels
import com.juno.ca.R
import com.juno.ca.base.BaseActivity
import com.juno.ca.databinding.CatalogActivityBinding
import com.juno.ca.ui.custom.ItemDecorationWithEnds
import com.juno.ca.ui.preview.PreviewActivity

class CatalogActivity : BaseActivity<CatalogViewModel, CatalogActivityBinding>() {

    private val viewModel: CatalogViewModel by viewModels()

    override fun provideLayoutId() = R.layout.catalog_activity

    override fun provideViewModel() = viewModel

    override fun setupUI() {
        binding.rv.post {
            val baseLengthUnit = binding.rv.width / 360
            var itemDecoration = ItemDecorationWithEnds(
                topFirst = baseLengthUnit * 30,
                top = 0,
                topLast = 0,
                bottomFirst = baseLengthUnit * 16,
                bottom = baseLengthUnit * 16,
                bottomLast = baseLengthUnit * 16,
                firstPredicate = { i -> i in 0..1 },
                lastPredicate = { i, c -> if (c % 2 == 0) i in (c - 2) until c else i == c - 1 }
            )
            binding.rv.addItemDecoration(itemDecoration)
            val isLTR = binding.root.layoutDirection == View.LAYOUT_DIRECTION_LTR
            val outerSpace = baseLengthUnit * 16
            val innerSpace = baseLengthUnit * 8
            itemDecoration = ItemDecorationWithEnds(
                leftFirst = if (isLTR) outerSpace else innerSpace,
                leftLast = if (isLTR) innerSpace else outerSpace,
                rightFirst = if (isLTR) innerSpace else outerSpace,
                rightLast = if (isLTR) outerSpace else innerSpace,
                firstPredicate = { i -> i % 2 == 0 },
                lastPredicate = { i, _ -> i % 2 == 1 }
            )
            binding.rv.addItemDecoration(itemDecoration)
        }
        viewModel.showPreview.observe(this) {
            startActivity(PreviewActivity.getIntent(this, it))
        }
        binding.buttonBack.setOnClickListener { finish() }
    }

}