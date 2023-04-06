package com.primagiant.githubuser.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.primagiant.githubuser.data.local.entity.FavoriteUserEntity
import com.primagiant.githubuser.databinding.ItemUserBinding

class FavoriteUserAdapter(
    private val favUserList: List<FavoriteUserEntity>,
    private val context: Context
) :
    RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>() {

    private lateinit var onFavUserListClickCallback: OnFavUserListClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = favUserList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvUsername.text = favUserList[position].username
        Glide.with(context)
            .load(favUserList[position].avatarUrl)
            .into(holder.binding.avatarImg)

        holder.itemView.setOnClickListener {
            @Suppress("DEPRECATION")
            onFavUserListClickCallback.onItemClicked(
                favUserList[holder.adapterPosition]
            )
        }
    }

    fun setOnUserListClickCallback(onFavUserListClickCallback: OnFavUserListClickCallback) {
        this.onFavUserListClickCallback = onFavUserListClickCallback
    }

    class ViewHolder(
        val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root)

    interface OnFavUserListClickCallback {
        fun onItemClicked(user: FavoriteUserEntity)
    }

}