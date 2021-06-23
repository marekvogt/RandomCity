package com.marekvogt.randomcity.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marekvogt.randomcity.R
import com.marekvogt.randomcity.databinding.ListItemRandomCityBinding

class RandomCityListAdapter(
    private val onCityClicked: (city: RandomCityViewEntity) -> Unit
) : ListAdapter<RandomCityViewEntity, RandomCityListAdapter.ViewHolder>(RandomCityViewEntityDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listItemBinding = DataBindingUtil.inflate<ListItemRandomCityBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_random_city, parent, false
        )
        return ViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.viewDataBinding.randomCity = item
        holder.viewDataBinding.root.setOnClickListener {
            onCityClicked.invoke(item)
        }
    }

    class ViewHolder(
        val viewDataBinding: ListItemRandomCityBinding
    ) : RecyclerView.ViewHolder(viewDataBinding.root)
}

private object RandomCityViewEntityDiff : DiffUtil.ItemCallback<RandomCityViewEntity>() {
    override fun areItemsTheSame(oldItem: RandomCityViewEntity, newItem: RandomCityViewEntity) =
        oldItem.emissionDateTime == newItem.emissionDateTime

    override fun areContentsTheSame(oldItem: RandomCityViewEntity, newItem: RandomCityViewEntity) = oldItem == newItem
}