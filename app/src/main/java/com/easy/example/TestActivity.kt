package com.easy.example

import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.*
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import androidx.transition.*
import com.easy.example.databinding.ActivityTest2Binding
import com.easy.example.fragment.HomeViewPagerFragment
import com.easy.example.fragment.PlantDetailFragment
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

        val binding = ActivityTest2Binding.inflate(layoutInflater)
        setContentView(R.layout.activity_test)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {


            val e = Slide(Gravity.BOTTOM)
            window.enterTransition = e
            
            val explode = ChangeScroll()
            //window.enterTransition = explode
            //val scene = Scene(binding.root as ViewGroup)
            //TransitionManager.go(scene, explode)

        }else{

        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container_view) as NavHostFragment

        navHostFragment.navController.apply {
            graph = createGraph(nav_graph.id, nav_graph.dest.home) {

                fragment<HomeViewPagerFragment>(nav_graph.dest.home, "HomeViewPagerFragment") {
                    action(nav_graph.action.to_plant_detail, nav_graph.dest.plant_detail) {
                        anim {

                        }
                    }
                }


                fragment<PlantDetailFragment>(
                    nav_graph.dest.plant_detail,
                    "PlantDetailFragment"
                ) {
                    argument(nav_graph.args.plant_id) {
                        type = NavType.StringType
                    }
                }


            }
        }
    }

    private inline fun <reified F : Fragment> NavGraphBuilder.fragment(
        destinationId: Int,
        label: String,
        builder: FragmentNavigatorDestinationBuilder.() -> Unit = {}
    ) {
        fragment<F>(destinationId, builder)
        this.label = label
    }

    private fun FragmentNavigatorDestinationBuilder.action(
        actionId: Int,
        destinationId: Int,
        optionsBuilder: NavOptionsBuilder.() -> Unit = {}
    ) {
        action(actionId) {
            this.destinationId = destinationId
            navOptions {
                optionsBuilder.invoke(this)
            }
        }
    }

    private fun navigateToPlant(plantId: String) {
        val args = bundleOf(nav_graph.args.plant_id to plantId)
        findNavController(fragment_container_view.id).navigate(
            nav_graph.action.to_plant_detail,
            args
        )
    }

    private fun NavOptionsBuilder.options() {
        navOptions {
            anim {
                enter = R.anim.nav_default_enter_anim
                exit = R.anim.nav_default_exit_anim
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
            popUpTo(nav_graph.dest.home) {
                inclusive = true // default false
            }
            // if popping exclusively, you can specify popUpTo as
            // a property. e.g. popUpTo = nav_graph.dest.start_dest
            launchSingleTop = true // default false
        }
    }


    object nav_graph {

//        nav_graph.id                     // graph id
//        nav_graph.dest.home              // home destination id
//        nav_graph.action.to_plant_detail // action home -> plant_detail id
//        nav_graph.args.plant_id          // destination argument name

        const val id = 1 // graph id

        object dest {
            const val home = 2
            const val plant_detail = 3
        }

        object action {
            const val to_plant_detail = 4
        }

        object args {
            const val plant_id = "plantId"
        }

    }

}
