package com.example.quickmaths.util

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.quickmaths.database.Player

fun formatPlayer(player: Player): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append("Player: ")
        append(player.name)
        append(player.score)
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}