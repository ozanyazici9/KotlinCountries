package com.ozanyazici.kotlincountries.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ozanyazici.kotlincountries.model.Country
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.ozanyazici.kotlincountries.R
import com.ozanyazici.kotlincountries.databinding.ItemCountryBinding
import com.ozanyazici.kotlincountries.view.FeedFragmentDirections


class CountryAdapter(val countryList: ArrayList<Country>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), CountryClickListener {

    class CountryViewHolder(var binding: ItemCountryBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemCountryBinding>(inflater,R.layout.item_country,parent,false)
        return CountryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        //DataBinding ullandığımızda çok daha az kod yazıyoruz.
        //Databinding aynı zamanda viewbinding gibi görünüm bağlama işleminide yapıyor.
        holder.binding.country = countryList[position]
        //this i listener a atayarak CountryAdapterin örneğini bu değişkenin içine atmış oluyoruz.
        //Böylelikle item_countryde Linearlayout a tıklanıldığında buradaki onCountryClicked çalışacak diyorum.
        //Yani şunun gibi oluyor = countryAdapter.onCountryClicked .
        //listener değişkeninin türünün CountryClickListener olmasının sebebi
        //onCountryCicked metodunun o Interface e ait olmasından kaynaklanıyor.
        holder.binding.listener = this

        /*
        holder.binding.name.text = countryList[position].countryName
        holder.binding.region.text = countryList[position].countryRegion

        holder.itemView.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.binding.imageView.downloadFromURL(countryList[position].imageUrl,
            placeholderProgressBar(holder.itemView.context)
        )
         */
    }

    //SwipeRefresfhlayout güncellendiğinde yeni bir veri varsa bu metod çalışacak.
    @SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(v: View, uuid: Int) {
        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }
}