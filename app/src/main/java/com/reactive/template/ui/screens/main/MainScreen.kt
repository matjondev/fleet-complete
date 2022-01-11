package com.reactive.template.ui.screens.main

import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.reactive.template.R
import com.reactive.template.base.BaseFragment
import com.reactive.template.base.Result
import com.reactive.template.databinding.ScreenMainBinding
import com.reactive.template.ui.adapters.VehiclesAdapter
import com.reactive.template.utils.extensions.gone
import com.reactive.template.utils.extensions.loge
import com.reactive.template.utils.extensions.toast
import com.reactive.template.utils.extensions.visible
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainScreen : BaseFragment<ScreenMainBinding>() {

    override fun getBinding(inflater: LayoutInflater) = ScreenMainBinding.inflate(inflater)

    private lateinit var adapter: VehiclesAdapter

    override fun initialize() {
        fetchData()
        initRecycler()
    }

    override fun observe() {
        viewModel.lastData.observe(viewLifecycleOwner){
            when (it){
                is Result.Loading -> {
                    showShimmerEffect()
                }
                is Result.Error -> {
                    showResultContent()
                    toast(requireContext(),it.error.toString())
                }

                is Result.Success ->{
                    showResultContent()
                    adapter.setData(it.data.response)
                }
            }
        }
    }

    private fun initRecycler() {
        adapter = VehiclesAdapter().apply {
            listener = {
                addFragment(MapScreen.newInstance(it))
            }
        }

        binding.recycler.adapter = adapter
    }


    private fun fetchData(){
        viewModel.getLastData()
    }

    override fun initClicks() {
        binding{
            reflesh.setOnClickListener { fetchData() }
            apiKey.setOnClickListener { openDialog() }
        }
    }

    private fun showShimmerEffect(){
        binding{
            shimmerContent.root.visible()
            recycler.gone()
        }
    }
    private fun showResultContent(){
        lifecycleScope.launch {
            delay(400)
            binding{
                shimmerContent.root.gone()
                recycler.visible()
            }
        }

    }

    private fun openDialog() {
        val editTextContainer = layoutInflater.inflate(R.layout.dialog_edit_text,null)
        val editTextView = editTextContainer.findViewById<EditText>(R.id.edit_text)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Enter Api Key")
            .setView(editTextContainer)
            .setPositiveButton("Ok"){dialogInterface,var2->
                val key = editTextView.text.toString()
                loge(key)
            }
        alertDialog.show()
    }
}