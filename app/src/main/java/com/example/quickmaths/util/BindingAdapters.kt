package com.example.quickmaths.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quickmaths.R
import com.example.quickmaths.database.Player
import com.example.quickmaths.network.MarsProperty
import com.example.quickmaths.recycler_adapters.PhotoGridAdapter

@BindingAdapter("android:text")
fun setText(view: TextView, value: Int) {
    view.text = Integer.toString(value)
}

@InverseBindingAdapter(attribute = "android:text")
fun getText(view: TextView): Int {
    return view.text.toString().toInt()
}

@BindingAdapter("playerName")
fun TextView.setName(item: Player?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("playerScoreString")
fun TextView.setScore(item: Player?) {
    item?.let {
        text = item.score.toString()
    }
}

@BindingAdapter("playerImage")
fun ImageView.setPlayerImage(item: Player?){
    item?.let {
        setImageResource(R.drawable.ic_launcher_background)
    }
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<MarsProperty>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}


