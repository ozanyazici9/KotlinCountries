package com.ozanyazici.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ozanyazici.kotlincountries.R
import com.ozanyazici.kotlincountries.databinding.FragmentCountryBinding
import com.ozanyazici.kotlincountries.util.downloadFromURL
import com.ozanyazici.kotlincountries.util.placeholderProgressBar
import com.ozanyazici.kotlincountries.viewmodel.CountryViewModel


class CountryFragment : Fragment() {

    private var countryUuid = 0
    private lateinit var viewModel: CountryViewModel
    private lateinit var binding: FragmentCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country.let {
                binding.countryName.text = it.countryName
                binding.countryCapital.text = it.countryCapital
                binding.countryCurrency.text = it.countryCurrency
                binding.countryRegion.text = it.countryRegion
                binding.countryLanguage.text = it.countryLanguage
                context?.let {
                    binding.countryImage.downloadFromURL(country.imageUrl, placeholderProgressBar(it))
                }
            }
        })
    }
}