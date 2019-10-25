package com.reza.base.presentation.favorite.adapter

import androidx.databinding.ObservableField
import com.reza.base.core.BaseViewModel

class FavoriteListItemViewModel : BaseViewModel() {
    var bTextId = ObservableField<String>()
    var bTextTitle = ObservableField<String>()
    var bTextAuthor = ObservableField<String>()
    var bTextDate = ObservableField<String>()
    var bIsFavorite = ObservableField<Boolean>(true)
}