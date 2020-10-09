package com.gosemathraj.fliploot.ui.home

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gosemathraj.fliploot.data.local.LocalDataSource
import com.gosemathraj.fliploot.data.local.entity.ProductEntity
import com.gosemathraj.fliploot.data.models.productlist.ProductList
import com.gosemathraj.fliploot.data.models.productlist.Products
import com.gosemathraj.fliploot.data.remote.api.config.Resource
import com.gosemathraj.fliploot.data.repo.FlipLootRepo
import com.gosemathraj.fliploot.ui.base.BaseViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel @ViewModelInject constructor(
    private val flipLootRepo: FlipLootRepo,
    @ApplicationContext private val applicationContext: Context,
    private val localDataSource: LocalDataSource) : BaseViewModel() {

    var productListLiveData = MutableLiveData<Resource<ProductList>>()
    var productEntityList = ArrayList<ProductEntity>()

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
                        viewModelScope.launch {
                            productEntityList =
                                withContext(Dispatchers.Default) { getAllFavourites() } as ArrayList<ProductEntity>
                            for (product in it.products) {
                                product.isFavourite = checkIfFavourite(product)
                            }
                            productListLiveData.value = Resource.success(it)
                        }
                    }
                }
                onError = {
                    productListLiveData.value = Resource.error(it)
                }
            }
        }
    }

    fun onFavItemClicked(product: Products) {

    }

    fun removeProduct(product: Products) {
        launchOnViewModelScope {
            val productEntity = ProductEntity()
            productEntity.productId = product.productId
            localDataSource.deleteQuote(productEntity)
        }
    }

    fun insertProduct(product: Products) {
        launchOnViewModelScope {
            val productEntity = ProductEntity()
            productEntity.productId = product.productId
            localDataSource.insert(productEntity)
        }
    }

    suspend fun getAllFavourites() : List<ProductEntity> {
        return localDataSource.getAll()
    }

    private fun checkIfFavourite(product: Products) : Boolean {
        if (productEntityList.isEmpty()) {
            return false
        }
        for (productEntity in productEntityList) {
            if (productEntity.productId == product.productId) {
                return true
                break
            }
        }
        return false
    }
 }