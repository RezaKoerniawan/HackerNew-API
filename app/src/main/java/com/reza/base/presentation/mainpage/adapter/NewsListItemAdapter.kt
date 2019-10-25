package com.reza.base.presentation.mainpage.adapter

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.reza.base.R
import com.reza.base.core.BaseRecycleViewAdapter
import com.reza.base.core.BaseViewHolder
import com.reza.base.core.ViewDataBindingOwner
import com.reza.base.databinding.ItemNewsBinding
import com.reza.base.entity.NewsItem
import com.reza.base.util.DateHelper
import com.reza.base.util.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class NewsListItemAdapter : BaseRecycleViewAdapter<NewsItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<NewsItem> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(parent.context, view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<NewsItem>, position: Int) {
        (holder as ViewHolder).bindData(getItem(position), position)
    }

    class ViewHolder(context: Context, view: View) :
        BaseViewHolder<NewsItem>(context, view),
        NewsListItemView,
        ViewDataBindingOwner<ItemNewsBinding> {

        override lateinit var binding: ItemNewsBinding
        private var viewModel: NewsListItemViewModel? = null
        private var data: NewsItem? = null

        init {
            binding.vm = NewsListItemViewModel()
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
                checkIsFavorite(it)
            }

            data.title.let {
                viewModel?.bTextTitle?.set(it)
            }

            data.by.let {
                viewModel?.bTextAuthor?.set(it)
            }

            data.time.let {
                viewModel?.bTextDate?.set(DateHelper.dateMessageFormat(it, DateHelper.EEDDMMMYYYY_HHMMZZZ))
            }
        }

        override fun onClick(view: View) {
            data?.let {
                if (viewModel?.bIsFavorite?.get()!!) {
                    removeFromFavorite(it)
                } else {
                    addToFavorite(it)
                }
            }
        }

        private fun checkIsFavorite(idNews: Int) {
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
                Toast.makeText(context, context.getString(R.string.favorite_msg_add), Toast.LENGTH_SHORT).show()
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
                Toast.makeText(context, context.getString(R.string.favorite_msg_remove), Toast.LENGTH_SHORT).show()
                viewModel?.bIsFavorite?.set(false)
            } catch (e: SQLiteConstraintException) {
                Toast.makeText(
                    context,
                    context.getString(R.string.favorite_error, e.localizedMessage),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}