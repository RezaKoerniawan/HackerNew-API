package com.reza.base.presentation.mainpage.adapter

import androidx.databinding.ObservableField
import com.reza.base.core.BaseViewModel

class StoriesFavoriteListItemViewModel : BaseViewModel() {
    var bTextId = ObservableField<String>()
    var bTextTitle = ObservableField<String>()
}