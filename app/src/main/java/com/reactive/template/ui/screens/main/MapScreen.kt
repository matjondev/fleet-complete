package com.reactive.template.ui.screens.main

import android.graphics.Color
import android.view.LayoutInflater
import androidx.core.util.Pair
import androidx.core.util.component1
import androidx.core.util.component2
import com.anyexcuse.anyexcuseconsumer.utils.extensions.param
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.material.datepicker.MaterialDatePicker
import com.reactive.template.R
import com.reactive.template.base.BaseFragment
import com.reactive.template.base.Result
import com.reactive.template.databinding.ScreenMapBinding
import com.reactive.template.network.models.LastData
import com.reactive.template.network.models.RawData
import com.reactive.template.utils.common.formatToRequest
import com.reactive.template.utils.common.getFormatTime
import com.reactive.template.utils.common.today
import com.reactive.template.utils.common.yesterday
import com.reactive.template.utils.extensions.loge
import com.reactive.template.utils.extensions.toast
import java.util.*

class MapScreen : BaseFragment<ScreenMapBinding>() {

    companion object {
        fun newInstance(data: LastData) = MapScreen().apply { this.lastData = data }
    }

    private var lastData by param<LastData>()

    private lateinit var googleMap: GoogleMap
    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap

        val options = GoogleMapOptions().apply {
            compassEnabled(false)
            rotateGesturesEnabled(true)
        }
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
    }

    private var beginDate = Date().yesterday()
    private var endDate = Date().today()

    override fun getBinding(inflater: LayoutInflater) = ScreenMapBinding.inflate(inflater)

    override fun initialize() {
        binding.title.text = getString(R.string.location_history, lastData?.plate)
        binding.map.onCreate(null)
        binding.map.getMapAsync(callback)
        initDate()
        fetch()
    }

    private fun initDate() {
        val rangeOfDate = beginDate.getFormatTime()+ " - " + endDate.getFormatTime()
        binding.date.text = getString(R.string.s_date, rangeOfDate)
    }

    private fun fetch() {
        viewModel.getRawData(
            beginDate.formatToRequest(),
            endDate.formatToRequest(),
            lastData!!.objectId.toString()
        )
    }


    override fun observe() {
        viewModel.rawData.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                    showProgress(true)
                }
                is Result.Error -> {
                    showProgress(false)
                    toast(requireContext(), it.error)
                }
                is Result.Success -> {
                    showProgress(false)
                    drawInMap(it.data.response)
                }
            }
        }
    }

    override fun initClicks() {
        binding {
            back.setOnClickListener { finishFragment() }
            iconDatePicker.setOnClickListener { datePicker() }
        }
    }

    private fun drawInMap(data: List<RawData>) {
        if (data.isEmpty()) {
            toast(requireContext(), "Empty Data")
            return
        }

        googleMap.clear()
        val latLngHole = data.map { rawData ->
            LatLng(rawData.Latitude, rawData.Longitude)
        }

        val polygon = PolygonOptions()
        polygon.strokeColor(Color.BLUE)
        polygon.strokeWidth(8f)
        polygon.addAll(latLngHole)

        val start = latLngHole.first()
        googleMap.addMarker(
            MarkerOptions()
                .position(start)
                .title("Start")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        )?.showInfoWindow()

        val end = latLngHole.last()
        googleMap.addMarker(
            MarkerOptions()
                .position(end)
                .title("End")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        )

        googleMap.addPolygon(polygon)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 18f))
    }

    private fun datePicker() {
        val picker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Select date")
            .setSelection(
                Pair(
                    beginDate.time,
                    endDate.time
                )
            )
            .build()
        picker.addOnPositiveButtonClickListener {
            beginDate.time = it.first
            endDate.time = it.second
            initDate()
            fetch()
        }
        picker.show(parentFragmentManager, "tag")
    }

    override fun onStart() {
        super.onStart()
        binding.map.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.map.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.map.onLowMemory()
    }

}
