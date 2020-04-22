package com.easy.lib.adapter

import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.PagerAdapter

/**
 * @author too young
 * @date  2019/7/3 12:17
 */
class FragmentAdapter(
        private val adapterFragmentManager: FragmentManager,
        private val fragmentList: List<Fragment>,
        private val titleList: List<String>? = null
) : FragmentStatePagerAdapter(adapterFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var currentPrimaryItem: Fragment? = null
    private var transaction: FragmentTransaction? = null

    override fun notifyDataSetChanged() {
        currentPrimaryItem = null
        super.notifyDataSetChanged()
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        val fragment = `object` as Fragment
        if (fragment !== currentPrimaryItem) {
            currentPrimaryItem?.let { currentPrimaryItem ->
                currentPrimaryItem.setMenuVisibility(false)
                if (transaction == null) {
                    transaction = adapterFragmentManager.beginTransaction()
                }
                if (fragmentList.indexOf(currentPrimaryItem) >= 0) {
                    transaction?.setMaxLifecycle(currentPrimaryItem, Lifecycle.State.STARTED)
                }
            }
            fragment.setMenuVisibility(true)
            if (transaction == null) {
                transaction = adapterFragmentManager.beginTransaction()
            }
            transaction?.setMaxLifecycle(fragment, Lifecycle.State.RESUMED)

            currentPrimaryItem = fragment
        }
    }

    override fun finishUpdate(container: ViewGroup) {
        super.finishUpdate(container)
        if (transaction != null) {
            transaction?.commitNowAllowingStateLoss()
            transaction = null
        }
    }

    override fun getItem(position: Int) = fragmentList[position]

    override fun getPageTitle(position: Int) = titleList?.get(position) ?: position.toString()

    override fun getCount() = titleList?.size ?: fragmentList.size

    override fun getItemPosition(`object`: Any) = PagerAdapter.POSITION_NONE

    constructor(
            fragment: Fragment,
            fragmentList: List<Fragment>,
            titleList: List<String>? = null
    ) : this(fragment.childFragmentManager, fragmentList, titleList)

    constructor(
            activity: FragmentActivity,
            fragmentList: List<Fragment>,
            titleList: List<String>? = null
    ) : this(activity.supportFragmentManager, fragmentList, titleList)

}