package com.primagiant.githubuser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.primagiant.githubuser.databinding.ItemUserBinding
import com.primagiant.githubuser.response.ItemsItem

class UserListAdapter(private val userList: List<ItemsItem>, private val context: Context) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private lateinit var onUserListClickCallback : OnUserListClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvUsername.text = userList[position].login
        Glide.with(context)
            .load(userList[position].avatarUrl)
            .into(holder.binding.avatarImg)

        holder.itemView.setOnClickListener {
            onUserListClickCallback.onItemClicked(
                userList[holder.adapterPosition]
            )
        }
    }

    fun setOnUserListClickCallback(onUserListClickCallback: OnUserListClickCallback) {
        this.onUserListClickCallback = onUserListClickCallback
    }

    class ViewHolder(
        val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root)

    interface OnUserListClickCallback {
        fun onItemClicked(user: ItemsItem)
    }

}