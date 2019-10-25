package com.reza.base.core

interface ComponentOwner<T> {

    var binding: T

//    @Suppress("UNCHECKED_CAST")
//    fun <C : T> componentAs(classOfT: Class<C>): C = binding as C

//    fun buildComponent(): T
}
