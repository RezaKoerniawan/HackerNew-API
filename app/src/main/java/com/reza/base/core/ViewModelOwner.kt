package com.reza.base.core

interface ViewModelOwner<T : BaseViewModel> {
    val viewModel: T
}
