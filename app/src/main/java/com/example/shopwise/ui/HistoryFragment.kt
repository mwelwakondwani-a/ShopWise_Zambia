package com.example.shopwise.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopwise.data.Sale
import com.example.shopwise.databinding.FragmentHistoryBinding
import com.example.shopwise.databinding.ItemSaleBinding
import com.example.shopwise.viewmodel.ShopViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShopViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = SaleAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        viewModel.allSales.asLiveData().observe(viewLifecycleOwner) { sales ->
            binding.tvEmpty.visibility = if (sales.isEmpty()) View.VISIBLE else View.GONE
            binding.recyclerView.visibility = if (sales.isEmpty()) View.GONE else View.VISIBLE
            adapter.setData(sales)
        }
    }

    inner class SaleAdapter : RecyclerView.Adapter<SaleAdapter.ViewHolder>() {
        private var list = emptyList<Sale>()
        fun setData(newList: List<Sale>) { list = newList; notifyDataSetChanged() }
        inner class ViewHolder(val binding: ItemSaleBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(ItemSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val sale = list[position]
            val dateString = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(sale.saleDate))
            holder.binding.tvSaleName.text = "Sale: $dateString"
            holder.binding.tvSaleDetails.text = "Total: K${sale.totalAmount} | Ref: ${sale.paymentReference}"
        }
        override fun getItemCount() = list.size
    }
    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}