package com.example.quickmaths.recycler_adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quickmaths.R
import com.example.quickmaths.database.Player

class PlayerStatsAdapter : RecyclerView.Adapter<PlayerStatsAdapter.ViewHolder>() {

    var data = listOf<Player>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources
        holder.playerImage.setImageResource(
            if(item.score <= 1){
            R.drawable.ic_launcher_background
        } else{
            R.drawable.ic_launcher_background
        })

        holder.playerName.text = item.name
        holder.playerScore.text = item.score.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_player_stats, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val playerName: TextView = itemView.findViewById(R.id.player_name)
        val playerScore: TextView = itemView.findViewById(R.id.player_score)
        val playerImage: ImageView = itemView.findViewById(R.id.player_image)
    }
}