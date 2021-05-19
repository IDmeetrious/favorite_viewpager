package com.example.favoritepager.ui.fragment.step

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.favoritepager.R

private const val TAG = "StepCheckFragment"
class CheckFragment: Fragment(R.layout.fragment_check_step) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "--> onCreate: ")
    }
}