package com.reza.base.presentation.favorite

import androidx.recyclerview.widget.LinearLayoutManager
import com.reza.base.core.BaseView

interface FavoriteView: BaseView {
    var layoutManager: LinearLayoutManager
}