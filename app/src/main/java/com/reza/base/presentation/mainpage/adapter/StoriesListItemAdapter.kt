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

            data.kids.let {
                var totalComment: Int? = it?.size
                if (totalComment == null) {
                    totalComment = 0
                }
                val commentTotal = context.getString(R.string.news_comment, totalComment.toString())
                viewModel?.bTextComments?.set(commentTotal)
            }
        }

        override fun onClick(view: View) {

            DetailPageActivity.startThisActivity(this.context)


            /*data?.let {
                if (viewModel?.bIsFavorite?.get()!!) {
                    removeFromFavorite(it)
                } else {
                    addToFavorite(it)
                }
            }*/
        }

        /*private fun checkIsFavorite(idNews: Int) {
            try {
                context.database.use {
                    val result = select(NewsItem.TABLE_NEWS_FAVORITE)
                        .whereArgs(
                            "(NEWS_ID = {id})",
                            "id" to idNews
                        )
                    val favorite = result.parseList(classParser<NewsItem>())
                    viewModel?.bIsFavorite?.set(!favorite.isEmpty())
                }
            } catch (e: SQLiteConstraintException) {
                Toast.makeText(
                    context,
                    context.getString(R.string.favorite_error, e.localizedMessage),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        private fun addToFavorite(data: NewsItem) {
            try {
                context.database.use {
                    insert(
                        NewsItem.TABLE_NEWS_FAVORITE,
                        NewsItem.NEWS_ID to data.id,
                        NewsItem.NEWS_TITLE to data.title,
                        NewsItem.NEWS_AUTHOR to data.by,
                        NewsItem.NEWS_DATE to data.time
                    )
                }
                viewModel?.bIsFavorite?.set(true)
                Toast.makeText(
                    context,
                    context.getString(R.string.favorite_msg_add),
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: SQLiteConstraintException) {
                Toast.makeText(
                    context,
                    context.getString(R.string.favorite_error, e.localizedMessage),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        private fun removeFromFavorite(data: NewsItem) {
            try {
                context.database.use {
                    delete(
                        NewsItem.TABLE_NEWS_FAVORITE, "(NEWS_ID = {id})",
                        "id" to data.id
                    )
                }
                Toast.makeText(
                    context,
                    context.getString(R.string.favorite_msg_remove),
                    Toast.LENGTH_SHORT
                ).show()
                viewModel?.bIsFavorite?.set(false)
            } catch (e: SQLiteConstraintException) {
                Toast.makeText(
                    context,
                    context.getString(R.string.favorite_error, e.localizedMessage),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }*/
    }
}