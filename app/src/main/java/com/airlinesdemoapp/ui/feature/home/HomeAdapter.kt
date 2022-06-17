package com.airlinesdemoapp.ui.feature.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.airlinesdemoapp.R
import com.airlinesdemoapp.domain.entity.UserInfo
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_layout.view.*

class HomeAdapter(
    _activity: AppCompatActivity, val onClickInterface: OnClickInterface,
    var list: ArrayList<UserInfo>) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    val activity: AppCompatActivity = _activity

    interface OnClickInterface {
        fun onClickRow(current: UserInfo)
        fun onDeleteItem(current: UserInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val current = list.get(position)
        holder.bind(current)
        Glide.with(activity)
            .load(current?.avatar)
            .into(holder.itemView.imageViewAvatar)
        holder.itemView.setOnClickListener {
            onClickInterface.onClickRow(current)
        }
        holder.itemView.imageViewInfo.setOnClickListener {
            onClickInterface.onDeleteItem(current)
        }
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: UserInfo?) {
            itemView.textViewUserName.text = buildString {
                append(user?.first_name)
                append(" ")
                append(user?.last_name)
            }
        }

        companion object {
            fun create(parent: ViewGroup): HomeViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_layout, parent, false)
                return HomeViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun addData(listItems: ArrayList<UserInfo>) {
        val size = this.list.size
        this.list.addAll(listItems)
        val sizeNew = this.list.size
        notifyItemRangeChanged(size, sizeNew)
    }
}