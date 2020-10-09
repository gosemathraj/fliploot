package com.gosemathraj.fliploot.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.gosemathraj.fliploot.R
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun loadImageUrl(imageView: AppCompatImageView, url : String) {
    url?.let {
        Picasso.get().load(url)
            .error(R.drawable.ic_launcher_background)
            .placeholder(R.drawable.ic_launcher_background)
            .into(imageView)
    }
}