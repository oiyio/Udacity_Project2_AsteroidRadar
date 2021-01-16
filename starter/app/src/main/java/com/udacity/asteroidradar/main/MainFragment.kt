package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*
import org.json.JSONObject

class MainFragment : Fragment() {

    lateinit var binding : FragmentMainBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        https://api.nasa.gov/planetary/apod?api_key=XuPUs0qRudQjLk2Zbr1t21n766Sbl1SmnCP1aZBH
        https://apod.nasa.gov/apod/image/2101/Pluto-Mountains-Plains9-17-15_1024.jpg
        * */

        viewModel.properties.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "test - " + it.url, Toast.LENGTH_SHORT).show()
            Picasso.get().load(it.url).into(binding.imageViewImageOfTheDay)
        })

        viewModel.asteroidList.observe(viewLifecycleOwner, Observer {
            val jsonObject = JSONObject(it)
            var asteroidList : ArrayList<Asteroid> = parseAsteroidsJsonResult(jsonObject)
            val adapter = AsteroidAdapter()
            binding.recyclerViewAsteroidList.adapter = adapter
            adapter.submitList(asteroidList)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
