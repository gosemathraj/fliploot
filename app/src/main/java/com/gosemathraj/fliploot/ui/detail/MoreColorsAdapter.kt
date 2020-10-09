package com.gosemathraj.fliploot.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gosemathraj.fliploot.R
import com.gosemathraj.fliploot.data.models.productdetails.MoreColors
import com.gosemathraj.fliploot.databinding.ItemMoreColorsBinding
import com.squareup.picasso.Picasso

class MoreColorsAdapter(val context : Context, var moreColorsList : List<MoreColors>) :
    RecyclerView.Adapter<MoreColorsAdapter.MoreColorsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreColorsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MoreColorsViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_more_colors, parent, false))
    }

    override fun getItemCount(): Int {
        return moreColorsList.size
    }

    override fun onBindViewHolder(holder: MoreColorsViewHolder, position: Int) {
        val moreColors = moreColorsList[position]
        holder.binding.morecolors = moreColors

        Picasso.get().load(moreColorsList[position].displayImage).into(holder.binding.imgMoreColors)
        holder.binding.tvMoreColorPrice.text = context.getString(R.string.rupee_symbol) + " " + moreColorsList[position].price.toString()
        holder.binding.executePendingBindings()
    }

    fun refreshList(mColorsList: List<MoreColors>){
        this.moreColorsList = mColorsList
    }

    inner class MoreColorsViewHolder(val binding : ItemMoreColorsBinding)
        : RecyclerView.ViewHolder(binding.root)
}