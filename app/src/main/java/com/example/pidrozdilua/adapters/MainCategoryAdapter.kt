package com.example.pidrozdilua.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pidrozdilua.R
import com.example.pidrozdilua.models.MainCategory

class MainCategoryAdapter(
    private val categories: List<MainCategory>,
    private val clickListener: (MainCategory) -> Unit
) : RecyclerView.Adapter<MainCategoryAdapter.MainCategoryViewHolder>() {

    inner class MainCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryIcon: ImageView = itemView.findViewById(R.id.category_icon)
        val categoryName: TextView = itemView.findViewById(R.id.category_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_category, parent, false)
        return MainCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainCategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.categoryIcon.setImageResource(category.iconResId)
        holder.categoryName.text = category.name
        holder.itemView.setOnClickListener { clickListener(category) }
    }

    override fun getItemCount() = categories.size
}
