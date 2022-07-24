package com.example.homework12

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homework12.databinding.FragmentHomeBinding
import java.util.*

class HomeFragment : Fragment(), OnItemClick {

    private var binding: FragmentHomeBinding? = null

    private val list = mutableListOf<ItemData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.recycleView.layoutManager = GridLayoutManager(context, 2)
        binding!!.recycleView.adapter = ItemAdapter(list, this)
        items()
        binding!!.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    refreshItems()
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                list.clear()
                val searchText = query?.lowercase(Locale.getDefault())
                if (searchText!!.isNotEmpty()) {
                    list.forEach {
                        if (it.description.lowercase(Locale.getDefault()).contains(searchText)) {
                            list.add(it)
                        }
                    }
                }
                refreshItems()
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun items() {
        list.add(ItemData(getString(R.string.caucasian), R.drawable.caucass))
        list.add(ItemData(getString(R.string.caucasian), R.drawable.caucass_two))
        list.add(ItemData(getString(R.string.rottweiler), R.drawable.rot))
        list.add(ItemData(getString(R.string.rottweiler), R.drawable.rot_two))
        list.add(ItemData(getString(R.string.german), R.drawable.german))
        list.add(ItemData(getString(R.string.german), R.drawable.german_two))
        list.add(ItemData(getString(R.string.jack), R.drawable.jack_russel))
        list.add(ItemData(getString(R.string.jack), R.drawable.jack_russel_two))
        list.add(ItemData(getString(R.string.jack), R.drawable.jack_russel_tree))
    }

    override fun onItemClicked(position: Int) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUserFragment(title = list[position].description,
            description = description(position),
            list[position].customItem))
    }

    private fun description(position: Int): String {
        return (when (list[position].description) {
            getString(R.string.rottweiler) -> {
                getString(R.string.rottweilerDesc)
            }
            getString(R.string.caucasian) -> {
                getString(R.string.caucasianDesc)
            }
            getString(R.string.jack) -> {
                getString(R.string.jackDesc)
            }
            getString(R.string.doberman) -> {
                getString(R.string.dobermanDesc)
            }
            getString(R.string.german) -> {
                getString(R.string.germanDesc)
            }
            else -> {
                return ""
            }
        }
                ).toString()
    }

    private fun refreshItems() {
        ItemAdapter(list, this).setItems(list)
    }
}