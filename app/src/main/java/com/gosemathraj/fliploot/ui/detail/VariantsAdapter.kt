package com.gosemathraj.fliploot.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gosemathraj.fliploot.R
import com.gosemathraj.fliploot.data.models.productdetails.Variants
import com.gosemathraj.fliploot.databinding.ItemVariantsBinding

class VariantsAdapter(private val context: Context, private var variantsList: List<Variants>)
    : RecyclerView.Adapter<VariantsAdapter.VariantsViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariantsViewholder {
        val inflater = LayoutInflater.from(parent.context)
        return VariantsViewholder(DataBindingUtil.inflate(inflater, R.layout.item_variants, parent, false))
    }

    override fun getItemCount(): Int {
        return variantsList.size
    }

    override fun onBindViewHolder(holder: VariantsViewholder, position: Int) {
        val variants = variantsList[position]
        variants.imageUrl = "https://coutloot-cdn.gumlet.com/listingImages/image_2q0pp17np39_1596186841542.jpeg?width=350"
        holder.binding.variant = variants
    }

    inner class VariantsViewholder(val binding : ItemVariantsBinding)
        : RecyclerView.ViewHolder(binding.root)
}