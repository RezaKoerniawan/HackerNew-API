package com.reza.base.presentation.detailpage

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.reza.base.core.BaseViewModel
import com.reza.base.entity.NewsItem
import com.reza.base.network.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailPageViewModel : BaseViewModel() {
    private val service = RetrofitFactory.makeRetrofitService()

    var bTextId = ObservableField<String>()
    var bTextTitle = ObservableField<String>()
    var bTextAuthor = ObservableField<String>()
    var bTextDate = ObservableField<String>()
    var bTextDescription = ObservableField<String>()
    var bTextComment = ObservableField<String>()
    var bIsFavorite = ObservableField<Boolean>(false)
    var bTextError = ObservableField<String>("Test")
    val bShowErrorView = ObservableField<Boolean>(false)
    val bShowLoadingView = ObservableField<Boolean>(true)

    private var newsList: MutableLiveData<NewsItem>? = null
    private var error: MutableLiveData<Exception>? = null

    fun getError(): LiveData<Exception> {
        if (error == null)
            error = MutableLiveData()
        return error as LiveData<Exception>
    }

    fun getNewsList(): LiveData<NewsItem> {
        if (newsList == null)
            newsList = MutableLiveData()
        return newsList as LiveData<NewsItem>
    }

    fun getNewsItemFromApi(idNews: String) {
        bShowLoadingView.set(true)
        GlobalScope.launch(Dispatchers.Main)
        {
            val request = service.getNewsItemAsync(idNews)
            try {
                val response = request.await()
                // Do something with the response.body()
                newsList?.value = response.body()
            } catch (e: Exception) {
                error?.value = e
                bShowLoadingView.set(false)
            }
        }
    }
}