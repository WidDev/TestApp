package com.example.testapp.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.testapp.interfaces.IIdentifiable

class ItemListViewModel<T : IIdentifiable> : ViewModel() {

    public var items = mutableStateListOf<T>()

    public fun addItem(item:T): T
    {
        items.add(item)
        return item
    }

    public fun removeItem(item:T)
    {
        items.remove(item)
    }


}