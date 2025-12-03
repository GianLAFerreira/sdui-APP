package com.example.guiaturistico.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.guiaturistico.data.dto.PlaceDto

class PlacesAdapter(
    private val onClick: (PlaceDto) -> Unit
) : RecyclerView.Adapter<PlacesAdapter.VH>() {

    private val items = mutableListOf<PlaceDto>()

    fun submit(list: List<PlaceDto>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class VH(val b: ItemPlaceBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.b.tvName.text = item.name
        holder.b.tvAddress.text = item.address ?: ""
        holder.b.root.setOnClickListener { onClick(item) }
    }

    override fun getItemCount() = items.size
}
