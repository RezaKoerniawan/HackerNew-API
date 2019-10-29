package com.reza.base.presentation.mainpage

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.reza.base.core.BaseView

interface MainPageView : BaseView {
    var layoutManager: GridLayoutManager
    var layoutManagerFavorite : LinearLayoutManager
}