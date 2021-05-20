package com.example.favoritepager.ui.adapter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.favoritepager.ui.fragment.step.CheckFragment
import com.example.favoritepager.ui.fragment.step.NumFragment
import com.example.favoritepager.ui.fragment.step.TextFragment
import com.example.favoritepager.util.Constants.STEP_CHECKS
import com.example.favoritepager.util.Constants.STEP_NUMBERS
import com.example.favoritepager.util.Constants.STEP_TEXT
import com.example.favoritepager.util.Constants.STEP_TITLE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val TAG = "PageAdapter"

class PageAdapter(fr: Fragment, private val countSteps: Int) :
    FragmentStateAdapter(fr) {

    override fun getItemCount(): Int = countSteps

    override fun createFragment(position: Int): Fragment {
        val args = Bundle()
        var fragment = Fragment()
        Log.i(TAG, "--> createFragment: $position")
        when (position) {
            0 -> {
                val text = "Step Text, here is some information"
                args.putString(STEP_TITLE, text)
                fragment = TextFragment().apply {
                    this.arguments = args
                }
//                fragment = TextFragment()
            }
            1 -> {
                args.putString(STEP_TITLE, "Step Numbers, how much point did you get last time?")
                fragment = NumFragment().apply {
                    this.arguments = args
                }
//                fragment = NumFragment()
            }
            2 -> {
                args.putString(STEP_TITLE, "Step Check, please choose answer below, at least one")
                fragment = CheckFragment().apply {
                    this.arguments = args
                }
//                fragment = CheckFragment()
            }
        }
        return fragment
    }
}