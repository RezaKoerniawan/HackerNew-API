package com.reza.base.presentation.detailpage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.reza.base.R
import com.reza.base.core.BaseActivity
import com.reza.base.core.ViewDataBindingOwner
import com.reza.base.databinding.ActivityDetailPageBinding

class DetailPageActivity : BaseActivity(),
    DetailPageView,
    ViewDataBindingOwner<ActivityDetailPageBinding> {

    override fun getViewLayoutResId(): Int {
        return R.layout.activity_detail_page
    }

    companion object {
        fun startThisActivity(context: Context) {
            val intent = Intent(context, DetailPageActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var viewModel: DetailPageViewModel
    override lateinit var binding: ActivityDetailPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = DetailPageViewModel()
        viewModel = binding.vm!!

        title = getString(R.string.detail_title)
    }
}