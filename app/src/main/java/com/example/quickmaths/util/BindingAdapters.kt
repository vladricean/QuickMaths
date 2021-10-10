package com.example.quickmaths.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.quickmaths.R
import com.example.quickmaths.domain.DomainPlayer

@BindingAdapter("android:text")
fun setText(view: TextView, value: Int) {
    view.text = Integer.toString(value)
}

@InverseBindingAdapter(attribute = "android:text")
fun getText(view: TextView): Int {
    return view.text.toString().toInt()
}

@BindingAdapter("playerName")
fun TextView.setName(item: DomainPlayer?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("playerScoreString")
fun TextView.setScore(item: DomainPlayer?) {
    item?.let {
        text = item.score.toString()
    }
}

@BindingAdapter("playerImage")
fun ImageView.setPlayerImage(item: DomainPlayer?) {
    item?.let {
        setImageResource(R.drawable.ic_launcher_background)
    }
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri =
            imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}



