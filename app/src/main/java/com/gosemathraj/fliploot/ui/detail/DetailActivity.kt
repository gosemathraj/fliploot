package com.gosemathraj.fliploot.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gosemathraj.fliploot.R
import com.gosemathraj.fliploot.data.models.productdetails.*
import com.gosemathraj.fliploot.data.remote.api.config.Error
import com.gosemathraj.fliploot.data.remote.api.config.Resource
import com.gosemathraj.fliploot.databinding.ActivityDetailBinding
import com.gosemathraj.fliploot.ui.base.BaseActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>() {

    private val detailViewModel : DetailViewModel by viewModels()
    private lateinit var moreColorsAdapter: MoreColorsAdapter
    private var moreColorsList = ArrayList<MoreColors>()

    override val layoutResourceId: Int
        get() = R.layout.activity_detail

    override val currentFragment: Fragment?
        get() = null

    override val fragmentContainerId: Int
        get() = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.lifecycleOwner = this
        dataBinding.vm = detailViewModel

        setUp()
        setListeners()
        setObservers()
    }

    private fun setUp() {
        moreColorsAdapter = MoreColorsAdapter(this, moreColorsList)
        rv_more_colors.apply {
            layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = moreColorsAdapter
        }
    }

    private fun setListeners() {
        img_back_arrow.setOnClickListener {
            finish()
        }
    }

    private fun setObservers() {
        detailViewModel.productDetailsLiveData.observe(this, Observer {
            when(it.status) {
                Resource.Status.LOADING -> {
                    initLoader()
                }
                Resource.Status.SUCCESS -> {
                    finishLoader()
                    moreColorsList = it.data?.product?.moreColors as ArrayList<MoreColors>
                    moreColorsAdapter.refreshList(moreColorsList)
                    moreColorsAdapter.notifyDataSetChanged()
                    setProductDetails(it.data)
                    setBackDropImage(it.data.product.images.mainImages[0])
                    setProductSpecification(it.data?.product.details.productAttributes)
                    setProductVariants(it.data?.product.details.variants)
                    setSellerDetails(it.data.product.sellerDetails)
                    setAvailableOffers(it.data.product.availableOffers)
                    setFooterImages(it.data.product.footer)
                  }
                Resource.Status.ERROR -> {
                    finishLoader()
                    it.error?.let { error ->
                        when (error.errorType) {
                            Error.ErrorType.NO_INTERNET_ERROR -> {
                                showToast("No Internet Connection")
                            }
                            Error.ErrorType.GENERAL_ERROR -> {
                                showToast("Something Went Wrong")
                            }
                            Error.ErrorType.NETWORK_ERROR -> {
                                showToast("Something Went Wrong")
                            }
                            else -> error.errorMessage?.let { it1 -> showToast(it1) }
                        }
                    }
                }
            }
        })
    }

    private fun setProductDetails(data: ProductDetails) {
        tv_price.text = getString(R.string.rupee_symbol) + data.product.details.variants[0].priceDetails.listedPrice.toString()
        tv_actual_price.text = data.product.details.variants[0].priceDetails.labelPrice.toString()
        tv_discount.text = data.product.details.variants[0].priceDetails.percentOff.toString() + "%Off"
        tv_title.text = data.product.details.title
    }

    private fun setBackDropImage(s: String) {
        Picasso.get().load(s).into(backdrop)
    }

    private fun setFooterImages(footer: List<String>) {
        Picasso.get().load(footer[0]).into(img_footer1)
        Picasso.get().load(footer[1]).into(img_footer2)
        Picasso.get().load(footer[2]).into(img_footer3)
    }

    private fun setAvailableOffers(availableOffers: List<String>) {
        for (i in availableOffers.indices) {
            val view = LayoutInflater.from(this).inflate(R.layout.item_available_offer, null)
            view.findViewById<TextView>(R.id.tv_available_offers).text = availableOffers[i]
            linear_available_offers.addView(view)
        }
    }

    private fun setSellerDetails(sellerDetails: SellerDetails) {
        Picasso.get().load(sellerDetails.profilePic).into(img_seller_pic)
    }

    private fun setProductVariants(variants: List<Variants>) {
        for (i in variants.indices) {
            val view = LayoutInflater.from(this).inflate(R.layout.item_variant, null)
            val btn = view.findViewById<Button>(R.id.btn_variant)
            btn.text = variants[i].variantName.toString()
            val layoutParams : LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(16, 16, 16, 16)
            linear_variants.addView(btn, layoutParams)

            btn.setOnClickListener {
                for (i in 0 until linear_variants.childCount) {
                    val button : Button = linear_variants.getChildAt(i) as Button
                    button.setBackgroundResource(R.drawable.bg_all_four_borders)
                    button.setTextColor(resources.getColor(R.color.black))
                }
                (it as Button).setBackgroundResource(R.drawable.bg_variant_button)
                (it as Button).setTextColor(resources.getColor(R.color.red))
            }
        }
    }

    private fun setProductSpecification(productAttributes: List<ProductAttributes>) {
        for (i in productAttributes.indices) {
            val view = LayoutInflater.from(this).inflate(R.layout.item_product_specification, null)
            view.findViewById<TextView>(R.id.tv_key).text = productAttributes[i].key
            view.findViewById<TextView>(R.id.tv_value).text = productAttributes[i].value
            rv_specification.addView(view)
        }
    }
}