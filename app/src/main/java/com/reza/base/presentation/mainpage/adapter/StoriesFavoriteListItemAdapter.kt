package com.reza.base.presentation.mainpage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.reza.base.R
import com.reza.base.core.BaseRecycleViewAdapter
import com.reza.base.core.BaseViewHolder
import com.reza.base.core.ViewDataBindingOwner
import com.reza.base.databinding.ItemStoriesBinding
import com.reza.base.databinding.ItemStoriesFavoriteBinding
import com.reza.base.entity.NewsItem
import com.reza.base.presentation.detailpage.DetailPageActivity
import com.reza.base.util.DateHelper

class StoriesFavoriteListItemAdapter : BaseRecycleViewAdapter<NewsItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<NewsItem> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stories_favorite, parent, false)
        return ViewHolder(parent.context, view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<NewsItem>, position: Int) {
        (holder as ViewHolder).bindData(getItem(position), position)
    }

    class ViewHolder(context: Context, view: View) :
        BaseViewHolder<NewsItem>(context, view),
        StoriesFavoriteListItemView,
        ViewDataBindingOwner<ItemStoriesFavoriteBinding> {

        override lateinit var binding: ItemStoriesFavoriteBinding
        private var viewModel: StoriesFavoriteListItemViewModel? = null
        private var data: NewsItem? = null
        private var newsId: Int = 0

        init {
            binding.vm = StoriesFavoriteListItemViewModel()
            binding.view = this
            viewModel = binding.vm
        }

        override fun bindData(data: NewsItem) {
            // ignore
        }

        fun bindData(data: NewsItem, position: Int) {
            this.data = data

            data.id.let {
                newsId = it
                viewModel?.bTextId?.set(it.toString())
            }

            data.title.let {
                viewModel?.bTextTitle?.set(it)
            }
        }

        override fun onClick(view: View) {

            DetailPageActivity.startThisActivity(this.context, newsId)

        }
    }
}