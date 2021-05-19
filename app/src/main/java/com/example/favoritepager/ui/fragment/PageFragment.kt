package com.example.favoritepager.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.viewpager2.widget.ViewPager2
import com.example.favoritepager.R
import com.example.favoritepager.ui.adapter.PageAdapter
import com.example.favoritepager.ui.fragment.step.CheckFragment

private const val TAG = "PageFragment"
class PageFragment: Fragment() {
    private val countSteps = 3
    private var currentStep = 0

    private var pager: ViewPager2? = null
    private lateinit var adapter: PageAdapter
    private lateinit var nextStep: Button
    private lateinit var prevStep: Button
    private lateinit var prevStepNum: Button
    private lateinit var currentStepNum: Button
    private lateinit var nextStepNum: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "--> onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_page, container, false)

        initViews(mView)

        return mView
    }

    private fun initViews(view: View) {
        view.let {
            pager = it.findViewById(R.id.pager)
            nextStep = it.findViewById(R.id.page_next_btn)
            prevStep = it.findViewById(R.id.page_prev_btn)
            prevStepNum = it.findViewById(R.id.page_prev_step_num)
            currentStepNum = it.findViewById(R.id.page_current_step_num)
            nextStepNum = it.findViewById(R.id.page_next_step_num)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PageAdapter(this, countSteps)
        pager?.adapter = adapter



        nextStep.setOnClickListener {
            if (currentStep <= countSteps){
                currentStep++
                pager?.setCurrentItem(currentStep, true)

                // Update any changes with views
                updateViews()
            }
        }
        prevStep.setOnClickListener {
            if (currentStep > 0) {
                currentStep--
                pager?.setCurrentItem(currentStep, true)
                // Update any changes with views
                updateViews()
            }
        }

    }

    private fun updateViews() {
        currentStep = adapter.mPosition.value

        if (currentStep >= 1) prevStepNum.text = "${currentStep - 1}"
        currentStepNum.text = "${pager?.currentItem}"
        if (currentStep <= countSteps) nextStepNum.text = "${currentStep + 1}"
    }
}