package com.easy.example.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.easy.example.R
import com.easy.example.TestActivity
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.layout_1.view.*

class HomeViewPagerFragment : Fragment(R.layout.layout_1){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.text_view.setOnClickListener {
            navigateToPlant("adasdadsas")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun navigateToPlant(plantId: String) {
        val args = bundleOf(TestActivity.nav_graph.args.plant_id to plantId)
        findNavController().navigate(
            TestActivity.nav_graph.action.to_plant_detail,
            args
        )
    }


}