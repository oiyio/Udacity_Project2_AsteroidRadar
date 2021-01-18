package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.entity.asDomainModel
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AsteroidAdapter(AsteroidClickListener {
            view.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        })

        binding.recyclerViewAsteroidList.adapter = adapter

        viewModel.asteroidList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.asDomainModel())
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.show_week_menu -> {
                viewModel.getAsteroidList()
                return true
            }
            R.id.show_today_menu -> {
                viewModel.getAsteroidsOfToday()
                return true
            }
            R.id.show_saved_menu -> {
                viewModel.getAllSavedAsteroids()
                return true
            }
        }
        return true
    }
}