package com.reza.base.presentation.detailpage

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.reza.base.R
import com.reza.base.core.BaseActivity
import com.reza.base.core.ViewDataBindingOwner
import com.reza.base.databinding.ActivityDetailPageBinding
import com.reza.base.entity.NewsItem
import com.reza.base.util.DateHelper
import com.reza.base.util.database
import org.jetbrains.anko.db.*

class DetailPageActivity : BaseActivity(),
    DetailPageView,
    ViewDataBindingOwner<ActivityDetailPageBinding> {

    private var data: NewsItem? = null
    private var newsId: Int = 0

    override fun getViewLayoutResId(): Int {
        return R.layout.activity_detail_page
    }

    companion object {
        const val NEWS_ID = "NEWS_ID"
        fun startThisActivity(context: Context, newsId: Int) {
            val intent = Intent(context, DetailPageActivity::class.java)
            intent.putExtra(NEWS_ID, newsId)
            context.startActivity(intent)
        }
    }

    private lateinit var viewModel: DetailPageViewModel
    override lateinit var binding: ActivityDetailPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = DetailPageViewModel()
        viewModel = binding.vm!!
        setUI()
    }

    private fun setUI() {
        title = getString(R.string.detail_title)

        observeError()
        observeResult()
    }

    private fun observeError() {
        observeData(viewModel.getError()) { error ->
            error?.let {
                viewModel.bTextError.set(it.message.toString())
                viewModel.bShowErrorView.set(true)
            }
        }
    }

    private fun observeResult() {
        observeData(viewModel.getNewsList()) { data ->
            data?.let {
                setData(it)
                viewModel.bShowLoadingView.set(false)
            }
        }
    }

    private fun setData(data: NewsItem) {
        this.data = data
        checkIsFavorite(data.id)

        viewModel.bTextId.set(data.id.toString())
        viewModel.bTextTitle.set(data.title)
        viewModel.bTextAuthor.set(data.by)
        viewModel.bTextDate.set(DateHelper.dateMessageFormat(data.time, DateHelper.DDMMMMYYYY))
    }

    override fun onResume() {
        super.onResume()
        val extras = intent.extras
        if (extras != null) {
            newsId = extras.getInt(NEWS_ID)
            viewModel.getNewsItemFromApi(newsId.toString())
        }
    }

    override fun onClick(view: View) {
        data?.let {
                if (viewModel.bIsFavorite.get()!!) {
                    removeFromFavorite(it)
                } else {
                    addToFavorite(it)
                }
            }
    }

    private fun checkIsFavorite(idNews: Int) {
        try {
            this.database.use {
                val result = select(NewsItem.TABLE_NEWS_FAVORITE)
                    .whereArgs(
                        "(NEWS_ID = {id})",
                        "id" to idNews
                    )
                val favorite = result.parseList(classParser<NewsItem>())
                viewModel.bIsFavorite.set(!favorite.isEmpty())
            }
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(
                this,
                this.getString(R.string.favorite_error, e.localizedMessage),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun addToFavorite(data: NewsItem) {
        try {
            this.database.use {
                insert(
                    NewsItem.TABLE_NEWS_FAVORITE,
                    NewsItem.NEWS_ID to data.id,
                    NewsItem.NEWS_TITLE to data.title,
                    NewsItem.NEWS_AUTHOR to data.by,
                    NewsItem.NEWS_DATE to data.time,
                    NewsItem.NEWS_SCORE to data.score,
                    NewsItem.NEWS_TOTAL_COMMENT to data.descendants
                )
            }
            viewModel.bIsFavorite.set(true)
            Toast.makeText(
                this,
                this.getString(R.string.favorite_msg_add),
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(
                this,
                this.getString(R.string.favorite_error, e.localizedMessage),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun removeFromFavorite(data: NewsItem) {
        try {
            this.database.use {
                delete(
                    NewsItem.TABLE_NEWS_FAVORITE, "(NEWS_ID = {id})",
                    "id" to data.id
                )
            }
            Toast.makeText(
                this,
                this.getString(R.string.favorite_msg_remove),
                Toast.LENGTH_SHORT
            ).show()
            viewModel.bIsFavorite.set(false)
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(
                this,
                this.getString(R.string.favorite_error, e.localizedMessage),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}