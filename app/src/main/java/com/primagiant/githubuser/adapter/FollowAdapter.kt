package com.primagiant.githubuser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.primagiant.githubuser.databinding.ItemUserBinding
import com.primagiant.githubuser.response.ItemsItem

class FollowAdapter(private val data: List<ItemsItem>, private val context: Context) :
    RecyclerView.Adapter<FollowAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvUsername.text = data[position].login
        Glide.with(context)
            .load(data[position].avatarUrl)
            .into(holder.binding.avatarImg)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(
        val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root)
}