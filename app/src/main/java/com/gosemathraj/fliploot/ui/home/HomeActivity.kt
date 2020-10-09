package com.gosemathraj.fliploot.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.gosemathraj.fliploot.R
import com.gosemathraj.fliploot.data.models.productlist.Products
import com.gosemathraj.fliploot.data.remote.api.config.Resource
import com.gosemathraj.fliploot.databinding.ActivityHomeBinding
import com.gosemathraj.fliploot.ui.base.BaseActivity
import com.gosemathraj.fliploot.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private val homeViewModel : HomeViewModel by viewModels()
    private lateinit var productListAdapter: ProductListAdapter
    private var productList = ArrayList<Products>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUp()
        setObservers()
    }

    private fun setUp() {
        productListAdapter = ProductListAdapter(this, productList) { onItemClick() }
        rv_productslist.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, 2)
            adapter = productListAdapter
        }
    }

    private fun onItemClick() {
        openActivity(DetailActivity::class.java)
    }

    private fun setObservers() {
        homeViewModel.productListLiveData.observe(this, Observer {
            when(it.status) {
                Resource.Status.LOADING -> {
                    initLoader()
                }
                Resource.Status.SUCCESS -> {
                    finishLoader()
                    productList = it.data?.products as ArrayList<Products>
                    productListAdapter.refreshList(productList)
                    productListAdapter.notifyDataSetChanged()
                }
                Resource.Status.ERROR -> {
                    finishLoader()
                    showToast(it.message!!)
                }
            }
        })
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_home

    override val currentFragment: Fragment?
        get() = null

    override val fragmentContainerId: Int
        get() = 0
}