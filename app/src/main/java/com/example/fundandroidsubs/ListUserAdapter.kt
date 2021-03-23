package com.example.fundandroidsubs

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fundandroidsubs.databinding.ItemUserBinding

class ListUserAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    inner class ListViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = ItemUserBinding.bind(itemView)

        internal fun bind(user: User): User {

            binding.tvUsername.text = user.username
            binding.tvName.text = user.name
            Glide.with(itemView.context)
                .load(user.avatar)
                .into(binding.imgUser)
            val userParcel = User(
                user.username,
                user.name,
                user.location,
                user.repository,
                user.company,
                user.followers,
                user.following,
                user.avatar
            )
            return userParcel
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(user = listUser[position])

        val mContext = holder.itemView.context

        val sendUser = holder.bind(user = listUser[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(mContext, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_USER, sendUser)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
}