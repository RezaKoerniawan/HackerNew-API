package com.reza.base.presentation.mainpage.adapter

import androidx.databinding.ObservableField
import com.reza.base.core.BaseViewModel

class NewsListItemViewModel : BaseViewModel() {
    var bTextId = ObservableField<String>()
    var bTextTitle = ObservableField<String>()
    var bTextAuthor = ObservableField<String>()
    var bTextDate = ObservableField<String>()
    var bIsFavorite = ObservableField<Boolean>(false)
}