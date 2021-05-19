package com.example.favoritepager.ui.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.favoritepager.ui.fragment.step.CheckFragment
import com.example.favoritepager.ui.fragment.step.NumFragment
import com.example.favoritepager.ui.fragment.step.TextFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private const val TAG = "PageAdapter"
class PageAdapter(fr: Fragment, private val countSteps: Int) : FragmentStateAdapter(fr) {
    private var _mPosition = MutableStateFlow(0)
    val mPosition: StateFlow<Int>
    get() = _mPosition


    override fun getItemCount(): Int = countSteps

    override fun createFragment(position: Int): Fragment {
        _mPosition.value = position
        Log.i(TAG, "--> createFragment: ${mPosition.value}")

        return when (position) {
            0 -> TextFragment()
            1 -> NumFragment()
            else -> CheckFragment()
        }

    }
}