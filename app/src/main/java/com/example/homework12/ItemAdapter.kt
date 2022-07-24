package com.example.homework12

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.homework12.databinding.CustomItemBinding

class ItemAdapter(
    val list: MutableList<ItemData>,
    private val onItemClick: OnItemClick,
) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(CustomItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = list.size

    inner class ItemViewHolder(private val binding: CustomItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var itemData: ItemData

        fun bind() {
            itemData = list[adapterPosition]

            binding.apply {
                ivCustomItem.setImageResource(itemData.customItem)
                tvBreed.text = itemData.description
            }

            binding.root.setOnClickListener {
                onItemClick.onItemClicked(adapterPosition)
            }
        }
    }

    fun setItems(newItems: List<ItemData>) {
        val diff = DiffUtils(newList = newItems, oldList = list)
        val result = DiffUtil.calculateDiff(diff)
        this.list.clear()
        this.list.addAll(newItems)
        result.dispatchUpdatesTo(this)
    }

}