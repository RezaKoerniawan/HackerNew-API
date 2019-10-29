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
import com.reza.base.entity.NewsItem
import com.reza.base.presentation.detailpage.DetailPageActivity
import com.reza.base.util.DateHelper

class StoriesListItemAdapter : BaseRecycleViewAdapter<NewsItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<NewsItem> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stories, parent, false)
        return ViewHolder(parent.context, view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<NewsItem>, position: Int) {
        (holder as ViewHolder).bindData(getItem(position), position)
    }

    class ViewHolder(context: Context, view: View) :
        BaseViewHolder<NewsItem>(context, view),
        StoriesListItemView,
        ViewDataBindingOwner<ItemStoriesBinding> {

        override lateinit var binding: ItemStoriesBinding
        private var viewModel: StoriesListItemViewModel? = null
        private var data: NewsItem? = null
        private var newsId: Int = 0

        init {
            binding.vm = StoriesListItemViewModel()
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

            data.by.let {
                viewModel?.bTextAuthor?.set(it)
            }

            data.time.let {
                viewModel?.bTextDate?.set(DateHelper.dateMessageFormat(it, DateHelper.DDMMMMYYYY))
            }

            data.score.let {
                val scoreTotal = context.getString(R.string.news_score, it.toString())
                viewModel?.bTextScores?.set(scoreTotal)
            }

            data.descendants.let {
                val commentTotal = context.getString(R.string.news_comment, it.toString())
                viewModel?.bTextComments?.set(commentTotal)

            }
        }

        override fun onClick(view: View) {

            DetailPageActivity.startThisActivity(this.context, newsId)

        }
    }
}