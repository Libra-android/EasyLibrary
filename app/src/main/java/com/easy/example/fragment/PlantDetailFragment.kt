package com.easy.example.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.easy.example.R
import com.easy.example.TestActivity
import kotlinx.android.synthetic.main.layout_2.view.*

class PlantDetailFragment : Fragment(R.layout.layout_2) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.text.text = paintId
    }
    private lateinit var paintId : String
    override fun onAttach(context: Context) {
        super.onAttach(context)
        paintId = requireArguments().getString(TestActivity.nav_graph.args.plant_id)?:"每日数据"

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}