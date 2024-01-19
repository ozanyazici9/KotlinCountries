package com.ozanyazici.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ozanyazici.kotlincountries.adapter.CountryAdapter
import com.ozanyazici.kotlincountries.databinding.FragmentFeedBinding
import com.ozanyazici.kotlincountries.viewmodel.FeedViewModel


class FeedFragment : Fragment() {

    private lateinit var binding : FragmentFeedBinding
    private lateinit var viewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //FeedViewModelımızı bağlıyoruz.
        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        binding.countryList.layoutManager = LinearLayoutManager(context)
        binding.countryList.adapter = countryAdapter

        observeLiveData()

    }

    //Viewmodeldaki LiveDataları gözlemliyorum
    private fun observeLiveData() {
        viewModel.countries.observe(viewLifecycleOwner, Observer {countries ->

            countries?.let {
                binding.countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer {error ->

            error?.let {
                if (it) {
                    binding.countryError.visibility = View.VISIBLE
                    binding.countryList.visibility = View.GONE
                } else {
                    binding.countryError.visibility = View.GONE
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer {loading ->
            loading?.let {
                if (it) {
                    binding.countryError.visibility = View.GONE
                    binding.countryList.visibility = View.GONE
                    binding.countryLoading.visibility = View.VISIBLE
                } else {
                    binding.countryLoading.visibility = View.GONE
                }
            }
        })
    }




}