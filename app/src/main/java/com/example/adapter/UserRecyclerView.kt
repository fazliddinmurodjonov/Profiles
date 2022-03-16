package com.example.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlesson5databasetask.databinding.ItemUserBinding
import com.example.model.User

class UserRecyclerView(var userList: ArrayList<User>) :
    RecyclerView.Adapter<UserRecyclerView.UserViewHolder>() {
    lateinit var adapterItem: OnItemClick

    interface OnItemClick {
        fun onClick(user: User)
    }

    fun setOnItemClick(listener: OnItemClick) {
        adapterItem = listener
    }

    inner class UserViewHolder(var binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(user: User) {
            binding.userName.text = user.name
            binding.userPhoneNumber.text = user.phoneNumber
            binding.userProfileImage.setImageURI(Uri.parse(user.imagePath))
            binding.root.setOnClickListener {
                adapterItem.onClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.onBind(user)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}