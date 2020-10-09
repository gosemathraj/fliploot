package com.gosemathraj.fliploot.ui.detail

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.gosemathraj.fliploot.data.models.productdetails.ProductDetails
import com.gosemathraj.fliploot.data.remote.api.config.Resource
import com.gosemathraj.fliploot.data.repo.FlipLootRepo
import com.gosemathraj.fliploot.ui.base.BaseViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

class DetailViewModel @ViewModelInject constructor(
    private val flipLootRepo: FlipLootRepo,
    @ApplicationContext private val applicationContext: Context): BaseViewModel() {

    var productDetailsLiveData = MutableLiveData<Resource<ProductDetails>>()
    var prodDetailsLiveData = MutableLiveData<ProductDetails>()

    init {
        getProductDetails()
    }

    private fun getProductDetails() {
        launchOnViewModelScope {
            productDetailsLiveData.value = Resource.loading()
            apiCall<ProductDetails>(applicationContext) {
                onEnqueue = {
                    flipLootRepo.getProductsDetails()
                }
                onSuccess = {
                    it?.let {
                        productDetailsLiveData.value = Resource.success(it)
                        prodDetailsLiveData.value = it
                    }
                }
                onError = {
                    productDetailsLiveData.value = Resource.error(it)
                }
            }
        }
    }
}