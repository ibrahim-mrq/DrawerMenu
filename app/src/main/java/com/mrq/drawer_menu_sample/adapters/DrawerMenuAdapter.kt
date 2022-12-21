package com.mrq.drawer_menu_sample.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mrq.drawer_menu_sample.databinding.CustomDrawerMenuBinding
import com.mrq.drawer_menu_sample.model.DrawerMenu

@SuppressLint("NotifyDataSetChanged")
class DrawerMenuAdapter(val context: Context) :
    RecyclerView.Adapter<DrawerMenuAdapter.DrawerMenuViewHolder>() {

    var list = emptyList<DrawerMenu>()

    class DrawerMenuViewHolder(val binding: CustomDrawerMenuBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawerMenuViewHolder {
        return DrawerMenuViewHolder(
            CustomDrawerMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DrawerMenuViewHolder, position: Int) {
        val model = list[position]
        holder.binding.name.text = model.title
        holder.binding.photo.setImageResource(model.icon)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(newList: List<DrawerMenu>) {
        list = newList
        notifyDataSetChanged()
    }


}