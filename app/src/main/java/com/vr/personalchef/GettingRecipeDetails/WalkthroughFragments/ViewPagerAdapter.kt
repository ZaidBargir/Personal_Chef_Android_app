package com.vr.personalchef.GettingRecipeDetails.WalkthroughFragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    val fragments = listOf(
        WalkthroughFragment1(),
        WalkthroughFragment2(),
    )

    override fun getCount(): Int {
       return fragments
           .size
    }

    override fun getItem(position: Int): Fragment {
      return fragments[position]
    }
}