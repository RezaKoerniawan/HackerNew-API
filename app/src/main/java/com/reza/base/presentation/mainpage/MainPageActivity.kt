package com.reza.base.presentation.mainpage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reza.base.R
import com.reza.base.core.BaseActivity
import com.reza.base.core.ViewDataBindingOwner
import com.reza.base.databinding.ActivityMainPageBinding
import com.reza.base.entity.NewsItem
import com.reza.base.presentation.mainpage.adapter.StoriesFavoriteListItemAdapter
import com.reza.base.presentation.mainpage.adapter.StoriesListItemAdapter
import com.reza.base.util.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class MainPageActivity : BaseActivity(),
    MainPageView,
    ViewDataBindingOwner<ActivityMainPageBinding> {

    override fun getViewLayoutResId(): Int {
        return R.layout.activity_main_page
    }

    companion object {
        fun startThisActivity(context: Context) {
            val intent = Intent(context, MainPageActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var viewModel: MainPageViewModel
    override lateinit var binding: ActivityMainPageBinding

    private lateinit var listAdapterFavorite: StoriesFavoriteListItemAdapter
    override var layoutManagerFavorite: LinearLayoutManager
        get() = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        set(value) {}

    private lateinit var listAdapter: StoriesListItemAdapter
    override var layoutManager: GridLayoutManager
        get() = GridLayoutManager(this, 2)
        set(value) {}

    private var time: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = MainPageViewModel()
        viewModel = binding.vm!!

        setUI()

        observeResult()
        observeError()
    }

    private fun setUI() {
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(false)
            it.setHomeButtonEnabled(false)
        }

        title = getString(R.string.news_title)

        listAdapterFavorite = StoriesFavoriteListItemAdapter()
        binding.rvListFavorite.adapter = listAdapterFavorite

        listAdapter = StoriesListItemAdapter()
        binding.rvList.adapter = listAdapter

        showFavorite()
    }

    private fun showFavorite() {
        database.use {
            val result = select(NewsItem.TABLE_NEWS_FAVORITE)
            val favorite = result.parseList(classParser<NewsItem>())
            listAdapterFavorite.setData(favorite)
        }
    }

    private fun observeError() {
        observeData(viewModel.getError()) { error ->
            error?.let {
                viewModel.bTextError.set(it.message.toString())
                viewModel.bShowErrorView.set(true)
            }
        }
    }

    /**
     * Observe result in viewModel if any data changed
     */
    private fun observeResult() {
        observeData(viewModel.getNewsList()) { data ->
            data?.let {
                listAdapter.setData(it)
                if (it.size >= 30) {
                    viewModel.bShowLoadingView.set(false)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getIdNewsListFromApi()
    }

    override fun onBackPressed() {
        if (time + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            Toast.makeText(this, getString(R.string.app_msg_close), Toast.LENGTH_SHORT).show()
        }
        time = System.currentTimeMillis()
    }
}