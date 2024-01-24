package com.ozanyazici.kotlincountries.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.ozanyazici.kotlincountries.model.Country
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.ozanyazici.kotlincountries.R
import com.ozanyazici.kotlincountries.databinding.ItemCountryBinding
import com.ozanyazici.kotlincountries.util.downloadFromURL
import com.ozanyazici.kotlincountries.util.placeholderProgressBar
import com.ozanyazici.kotlincountries.view.FeedFragmentDirections


class CountryAdapter(val countryList: ArrayList<Country>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(var binding: ItemCountryBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CountryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.binding.name.text = countryList[position].countryName
        holder.binding.region.text = countryList[position].countryRegion

        holder.itemView.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.binding.imageView.downloadFromURL(countryList[position].imageUrl,
            placeholderProgressBar(holder.itemView.context)
        )
    }

    //SwipeRefresfhlayout güncellendiğinde yeni bir veri varsa bu metod çalışacak.
    @SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}