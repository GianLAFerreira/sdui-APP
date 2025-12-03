package com.example.guiaturistico.ui.map


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.exemplo.guiaturistico.R
import com.exemplo.guiaturistico.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(R.layout.fragment_map) {
    private val vm: MapViewModel by viewModels()
    private var _b: FragmentMapBinding? = null
    private val b get() = _b!!
    private var gmap: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _b = FragmentMapBinding.bind(view)
        val mapView = b.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            gmap = map
            val sp = LatLng(-23.55052, -46.63331)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(sp, 12f))
            vm.nearby(sp.latitude, sp.longitude)
        }

        vm.places.collect(viewLifecycleOwner) { places ->
            gmap?.let { map ->
                map.clear()
                places.forEach { p ->
                    val lat = p.coordinates?.lat
                    val lng = p.coordinates?.lng
                    if (lat != null && lng != null) {
                        map.addMarker(
                            MarkerOptions()
                                .position(LatLng(lat, lng))
                                .title(p.name)
                        )
                    }
                }
            }
        }
    }

    // Forward lifecycle to MapView
    override fun onResume() { super.onResume(); b.mapView.onResume() }
    override fun onPause() { b.mapView.onPause(); super.onPause() }
    override fun onLowMemory() { super.onLowMemory(); b.mapView.onLowMemory() }
    override fun onDestroyView() { b.mapView.onDestroy(); _b = null; super.onDestroyView() }
}
