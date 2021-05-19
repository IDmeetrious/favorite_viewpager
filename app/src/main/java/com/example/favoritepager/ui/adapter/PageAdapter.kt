package com.example.favoritepager.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.favoritepager.ui.fragment.step.CheckFragment
import com.example.favoritepager.ui.fragment.step.NumFragment
import com.example.favoritepager.ui.fragment.step.TextFragment


class PageAdapter(fr: Fragment, private val countSteps: Int) : FragmentStateAdapter(fr) {
    override fun getItemCount(): Int = countSteps

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TextFragment()
            1 -> NumFragment()
            else -> CheckFragment()
        }

    }
}