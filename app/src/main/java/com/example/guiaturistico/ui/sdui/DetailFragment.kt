package com.example.guiaturistico.ui.sdui

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val vm: DetailViewModel by viewModels()
    private var _b: FragmentDetailBinding? = null
    private val b get() = _b!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _b = FragmentDetailBinding.bind(view)
        val placeId = arguments?.getString("placeId") ?: return
        vm.page.collect(viewLifecycleOwner) { page ->
            page?.let {
                SduiRenderer.render(requireContext(), b.container as LinearLayout, it.components)
            }
        }
        vm.load(placeId)
    }

    override fun onDestroyView() { _b = null; super.onDestroyView() }
}
