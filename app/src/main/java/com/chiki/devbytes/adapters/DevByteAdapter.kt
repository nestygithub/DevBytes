package com.chiki.devbytes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chiki.devbytes.databinding.DevbyteItemBinding
import com.chiki.devbytes.domain.Video

class DevByteAdapter(private val onItemClicked:(Video)->Unit): ListAdapter<Video, DevByteAdapter.DevByteViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevByteViewHolder {
        return DevByteViewHolder.from(this, parent, onItemClicked)
    }
    override fun onBindViewHolder(holder: DevByteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DevByteViewHolder private constructor(private val binding: DevbyteItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(video: Video) {
            binding.video = video
            binding.executePendingBindings()
        }
        companion object{
            fun from(devByteAdapter: DevByteAdapter, parent: ViewGroup, onItemClicked: (Video) -> Unit): DevByteViewHolder {
                val viewHolder = DevByteViewHolder(DevbyteItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
                viewHolder.itemView.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    onItemClicked(devByteAdapter.getItem(position))
                }
                return viewHolder
            }
        }
    }

    companion object{
        private val DiffCallback = object : DiffUtil.ItemCallback<Video>() {
            override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem.url == newItem.url
            }
            override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem == newItem
            }
        }
    }
}

