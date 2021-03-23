package com.example.fundandroidsubs

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fundandroidsubs.databinding.ItemUserBinding

class ListUserAdapter: RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private val mData = ArrayList<User>()

    fun setData(items: ArrayList<User>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = ItemUserBinding.bind(itemView)

        fun bind(user: User): User {

            binding.tvUsername.text = user.username
            Glide.with(itemView.context)
                .load(user.avatar)
                .into(binding.imgUser)
            return return User(
                user.username,
                user.avatar
            )
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(mData[position])
        val mContext = holder.itemView.context
        val sendUser = holder.bind(user = mData[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(mContext, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_USER, sendUser)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}