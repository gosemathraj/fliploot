package com.gosemathraj.fliploot.ui.home

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gosemathraj.fliploot.R
import com.gosemathraj.fliploot.data.local.LocalDataSource
import com.gosemathraj.fliploot.data.local.entity.ProductEntity
import com.gosemathraj.fliploot.data.models.productlist.ProductList
import com.gosemathraj.fliploot.data.models.productlist.Products
import com.gosemathraj.fliploot.data.remote.api.config.Error
import com.gosemathraj.fliploot.data.remote.api.config.Resource
import com.gosemathraj.fliploot.data.repo.FlipLootRepo
import com.gosemathraj.fliploot.ui.base.BaseViewModel
import com.gosemathraj.fliploot.utils.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel @ViewModelInject constructor(
    private val flipLootRepo: FlipLootRepo,
    @ApplicationContext private val context: Context,
    private val localDataSource: LocalDataSource,
    private val networkUtils: NetworkUtils) : BaseViewModel() {

    var productListLiveData = MutableLiveData<Resource<ProductList>>()
    var productEntityList = ArrayList<ProductEntity>()

    init {
        getProductsList()
    }

    private fun getProductsList() {
        if (networkUtils.isNetworkConnected()) {
            launchOnViewModelScope {
                productListLiveData.value = Resource.loading()
                apiCall<ProductList> {
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
        } else {
            productListLiveData.value = Resource.error(
                Error(Error.ErrorType.NO_INTERNET_ERROR, context.getString(R.string.no_internet_connection))
            )
        }
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