package com.example.guiaturistico.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import com.example.guiaturistico.R
import com.example.guiaturistico.databinding.FragmentListBinding

class ListFragment : Fragment(R.layout.fragment_list) {
    private val vm: ListViewModel by viewModels()
    private var _b: FragmentListBinding? = null
    private val b get() = _b!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _b = FragmentListBinding.bind(view)
        val adapter = PlacesAdapter { place ->
            findNavController().navigate(
                ListFragmentDirections.actionListToDetail(place.id)
            )
        }
        b.recycler.adapter = adapter

        vm.state.collect(viewLifecycleOwner) { adapter.submit(it) }
        vm.load("Sao Paulo")

        b.btnSearch.setOnClickListener {
            val q = b.etSearch.text?.toString().orEmpty()
            vm.search(q, "Sao Paulo")
        }
    }

    override fun onDestroyView() {
        _b = null
        super.onDestroyView()
    }
}
