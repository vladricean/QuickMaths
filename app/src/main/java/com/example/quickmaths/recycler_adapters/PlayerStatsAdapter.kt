package com.example.quickmaths.recycler_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quickmaths.database.Player
import com.example.quickmaths.databinding.ListItemPlayerStatsBinding

class PlayerStatsAdapter(val clickListener: PlayerListener) :
    ListAdapter<Player, PlayerStatsAdapter.ViewHolder>(PlayerDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemPlayerStatsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Player, clickListener: PlayerListener) {
            binding.playerStats = item
            binding.executePendingBindings()
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPlayerStatsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class PlayerDiffCallback : DiffUtil.ItemCallback<Player>() {
    override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem.playerId == newItem.playerId
    }

    override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem == newItem
    }
}

class PlayerListener(val clickListener: (playerId: Long) -> Unit) {
    fun onClick(player: Player) = clickListener(player.playerId)
}