package com.gosemathraj.fliploot.ui.home

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.gosemathraj.fliploot.data.models.productlist.ProductList
import com.gosemathraj.fliploot.data.remote.api.config.Resource
import com.gosemathraj.fliploot.data.repo.FlipLootRepo
import com.gosemathraj.fliploot.ui.base.BaseViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

class HomeViewModel @ViewModelInject constructor(
    private val flipLootRepo: FlipLootRepo,
    @ApplicationContext private val applicationContext: Context) : BaseViewModel() {

    var productListLiveData = MutableLiveData<Resource<ProductList>>()

    init {
        getProductsList()
    }

    private fun getProductsList() {
        launchOnViewModelScope {
            productListLiveData.value = Resource.loading()
            apiCall<ProductList>(applicationContext) {
                onEnqueue = {
                    flipLootRepo.getAllProductsList()
                }
                onSuccess = {
                    it?.let {
                        productListLiveData.value = Resource.success(it)
                    }
                }
                onError = {
                    productListLiveData.value = Resource.error(it)
                }
            }
        }
    }
}