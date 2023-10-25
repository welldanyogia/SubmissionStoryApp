package com.example.submissionstoryapp.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.submissionstoryapp.data.response.ListStoryItem
import com.example.submissionstoryapp.databinding.EachItemBinding
import com.squareup.picasso.Picasso

class StoryAdapter : ListAdapter<ListStoryItem, StoryAdapter.StoryViewHolder>(DiffCallback) {

    private var storyClickListener: StoryClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = EachItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val currentStory = getItem(position)
        holder.bind(currentStory)
    }
    fun setStoryClickListener(listener: StoryClickListener) {
        storyClickListener = listener
    }

    inner class StoryViewHolder(private val binding: EachItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    storyClickListener?.onStoryClick(getItem(position))
                }
            }
        }

        fun bind(story: ListStoryItem) {
            binding.titleTv.text = story.name
            binding.descTv.text = story.description
            Picasso.get().load(story.photoUrl).into(binding.logoIv)
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
interface StoryClickListener {
    fun onStoryClick(story: ListStoryItem)
}