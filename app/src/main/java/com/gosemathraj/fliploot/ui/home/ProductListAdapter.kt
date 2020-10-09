package com.gosemathraj.fliploot.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gosemathraj.fliploot.R
import com.gosemathraj.fliploot.data.models.productlist.Products
import com.gosemathraj.fliploot.databinding.ItemProductlistBinding

class ProductListAdapter(val context: Context ,var productList : List<Products>, val onCLick : (type : Int, product : Products) -> Unit) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProductListViewHolder(DataBindingUtil.inflate(inflater,
            R.layout.item_productlist, parent, false))
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        val product = productList[position]
        holder.binding.product = product

        holder.binding.tvPrice.text = context.getString(R.string.rupee_symbol) + product.details.variants[0].priceDetails.listedPrice.toString()
        holder.binding.tvActualPrice.text = "(${product.details.variants[0].priceDetails.labelPrice.toString()})"
        holder.binding.tvDiscount.text = "${product.details.variants[0].priceDetails.percentOff.toString()} %Off"
        holder.binding.tvTitle.text = product.details.title

        if (product.isFavourite) {
            holder.binding.icFav.setBackgroundResource(R.drawable.ic_heart_fav)
        } else {
            holder.binding.icFav.setBackgroundResource(R.drawable.ic_heart)
        }

        val rating = product.productRating.toInt()

        for (i in 0 until 5) {
            if (i < rating) {
                holder.binding.containerRating[i].setBackgroundResource(R.drawable.ic_star_filled)
            } else {
                holder.binding.containerRating[i].setBackgroundResource(R.drawable.ic_star_empty)
            }
        }

        if (position % 2 == 0) {
            holder.binding.root.setBackgroundResource(R.drawable.bg_position_zero)
        } else {
            holder.binding.root.setBackgroundResource(R.drawable.bg_position_one)
        }

//        holder.binding.imgThumbnail.setOnClickListener {
//            onCLick(1, product)
//        }

        holder.binding.containerFavourite.setOnClickListener {
            onCLick(2, product)
            if (product.isFavourite) {
                holder.binding.icFav.setBackgroundResource(R.drawable.ic_heart_fav)
            } else {
                holder.binding.icFav.setBackgroundResource(R.drawable.ic_heart)
            }
        }

        holder.binding.executePendingBindings()
    }

    fun refreshList(prodList: List<Products>){
        this.productList = prodList
    }

    inner class ProductListViewHolder(val binding : ItemProductlistBinding)
        : RecyclerView.ViewHolder(binding.root)
}