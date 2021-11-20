package com.example.quickmaths.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quickmaths.databinding.ListItemPlayerStatsBinding
import com.example.quickmaths.domain.DomainPlayer

class PlayerStatsAdapter(val clickListener: PlayerListener) : ListAdapter<DomainPlayer, PlayerStatsAdapter.ViewHolder>(PlayerDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemPlayerStatsBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: DomainPlayer, clickListener: PlayerListener) {
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

class PlayerDiffCallback : DiffUtil.ItemCallback<DomainPlayer>() {
    override fun areItemsTheSame(oldItem: DomainPlayer, newItem: DomainPlayer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DomainPlayer, newItem: DomainPlayer): Boolean {
        return oldItem == newItem
    }
}

class PlayerListener(val clickListener: (playerId: String) -> Unit) {
    fun onClick(player: DomainPlayer) = clickListener(player.id)
}