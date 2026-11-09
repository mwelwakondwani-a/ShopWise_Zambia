package com.example.shopwise.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopwise.data.Product
import com.example.shopwise.databinding.ItemProductBinding

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.VH>() {
    private var list = emptyList<Product>()
    fun setData(newList: List<Product>) { list = newList; notifyDataSetChanged() }
    class VH(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = list[position]
        holder.binding.tvName.text = item.name
        holder.binding.tvDetails.text = "Stock: ${item.quantity} | Price: K${item.sellingPrice}"
    }
    override fun getItemCount() = list.size
}