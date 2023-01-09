package com.mrq.drawer_menu_sample.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.mrq.drawer_menu_sample.R
import com.mrq.drawer_menu_sample.databinding.CustomDrawerMenuBinding
import com.mrq.drawer_menu_sample.model.DrawerMenu

@SuppressLint("NotifyDataSetChanged")
class DrawerMenuAdapter : RecyclerView.Adapter<DrawerMenuAdapter.DrawerMenuViewHolder>() {

    private var list = emptyList<DrawerMenu>()
    private var selectedPosition = 0
    var listener: DrawerMenuInterface? = null

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

        if (selectedPosition == position) {
            holder.itemView.isSelected = true
            holder.binding.view.setBackgroundResource(R.color.ora)
            holder.binding.name.setTextViewColor(R.color.ora)
            holder.binding.photo.setTint(R.color.ora)
        } else {
            holder.itemView.isSelected = false
            holder.binding.view.setBackgroundResource(R.color.blue)
            holder.binding.name.setTextViewColor(R.color.white)
            holder.binding.photo.setTint(R.color.white)
        }

        holder.itemView.setOnClickListener {
            if (model.activity == null) {
                if (selectedPosition >= 0) notifyItemChanged(selectedPosition)
                selectedPosition = holder.adapterPosition
                notifyItemChanged(selectedPosition)
            }
            listener!!.onItemSelected(model, position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(newList: ArrayList<DrawerMenu>) {
        list = newList
        notifyDataSetChanged()
    }

    interface DrawerMenuInterface {
        fun onItemSelected(model: DrawerMenu, position: Int)
    }

    private fun TextView.setTextViewColor(color: Int) {
        this.setTextColor(ContextCompat.getColor(context, color))
    }

    private fun ImageView.setTint(@ColorRes colorId: Int) {
        val color = ContextCompat.getColor(this.context, colorId)
        val colorStateList = ColorStateList.valueOf(color)
        ImageViewCompat.setImageTintList(this, colorStateList)
    }

}