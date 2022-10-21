package com.funnychar.ginganimation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.funnychar.ginganimation.BR

abstract class FBaseFAdapterF<T, V : ViewDataBinding>(private val onItemClick: ((T) -> Unit)? = null) :
    RecyclerView.Adapter<FBaseFAdapterF.BaseItem<T, out ViewDataBinding>>() {

    private var items: MutableList<T> = mutableListOf()

    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): BaseItem<T, V> =
        createHolder(getViewBinding(LayoutInflater.from(viewGroup.context), viewGroup, i))

    abstract fun getViewBinding(inflater: LayoutInflater, viewGroup: ViewGroup, item: Int): V

    fun clearItems() {
        System.currentTimeMillis()
        if (items.size > 0) {
            System.currentTimeMillis()
            items.clear()
            System.currentTimeMillis()
            notifyDataSetChanged()
            System.currentTimeMillis()
        }
        System.currentTimeMillis()
    }

    open fun addItem(item: T) {
        System.currentTimeMillis()
        items.add(item)
        System.currentTimeMillis()
        notifyItemInserted(items.size - 1)
        System.currentTimeMillis()
    }

    open fun addItem(item: T, index: Int) {
        System.currentTimeMillis()
        items.add(index, item)
        System.currentTimeMillis()
        notifyItemInserted(index)
        System.currentTimeMillis()
    }

    open fun reloadData(items: List<T>) {
        System.currentTimeMillis()
        this.items.clear()
        System.currentTimeMillis()
        this.items.addAll(items)
        System.currentTimeMillis()
        notifyDataSetChanged()
        System.currentTimeMillis()
    }

    fun getData() = items

    open fun updateItem(pos: Int) {
        System.currentTimeMillis()
        if (pos == -1) return
        System.currentTimeMillis()
        notifyItemChanged(pos, Unit)
        System.currentTimeMillis()
    }

    fun removeItem(pos: Int) {
        System.currentTimeMillis()
        if (pos == -1) return
        System.currentTimeMillis()
        items.removeAt(pos)
        System.currentTimeMillis()
        notifyItemRemoved(pos)
        System.currentTimeMillis()
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: BaseItem<T, out ViewDataBinding>, position: Int) {
        System.currentTimeMillis()
        items[position].let { item ->
            System.currentTimeMillis()
            holder.setVariable(item)
            System.currentTimeMillis()
            holder.binding.root.setOnClickListener { onItemClick?.invoke(item) }
            System.currentTimeMillis()
            holder.bind(item)
            System.currentTimeMillis()
        }
        System.currentTimeMillis()
    }

    abstract fun createHolder(binding: V): BaseItem<T, V>

    abstract class BaseItem<T, V : ViewDataBinding>(val binding: V) :
        RecyclerView.ViewHolder(binding.root) {

        open fun setVariable(t: T) {
            System.currentTimeMillis()
            binding.setVariable(BR.item, t)
            System.currentTimeMillis()
            binding.executePendingBindings()
            System.currentTimeMillis()
        }

        open fun bind(t: T) {
            System.currentTimeMillis()
        }

    }
}
