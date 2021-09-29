package com.example.quickmaths.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.example.quickmaths.R
import com.example.quickmaths.database.Player

@BindingAdapter("android:text")
fun setText(view: TextView, value: Int) {
    view.text = Integer.toString(value)
}

@InverseBindingAdapter(attribute = "android:text")
fun getText(view: TextView): Int {
    return view.text.toString().toInt()
}

@BindingAdapter("playerName")
fun TextView.setName(item: Player) {
    text = item.name
}

@BindingAdapter("playerScoreString")
fun TextView.setScore(item: Player) {
    text = item.score.toString()
}

@BindingAdapter("playerImage")
fun ImageView.setPlayerImage(item: Player){
    setImageResource(R.drawable.ic_launcher_background)
}


