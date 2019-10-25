package com.reza.base.presentation.mainpage.adapter

import androidx.databinding.ObservableField
import com.reza.base.core.BaseViewModel

class StoriesListItemViewModel : BaseViewModel() {
    var bTextId = ObservableField<String>()
    var bTextTitle = ObservableField<String>()
    var bTextAuthor = ObservableField<String>()
    var bTextDate = ObservableField<String>()
    var bTextScores = ObservableField<String>()
    var bTextComments = ObservableField<String>()
}