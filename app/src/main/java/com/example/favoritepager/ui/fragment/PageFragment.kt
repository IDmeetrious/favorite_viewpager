package com.example.favoritepager.ui.fragment

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.favoritepager.R
import com.example.favoritepager.ui.adapter.PageAdapter
import com.example.favoritepager.ui.fragment.step.CheckFragment
import com.example.favoritepager.util.Constants.STEP_TITLE
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private const val TAG = "PageFragment"
class PageFragment: Fragment() {
    private val countSteps = 3
    private var currentStep = 0

    private var pager: ViewPager2? = null
    private lateinit var adapter: PageAdapter
    private lateinit var pageTitle: TextView
    private lateinit var nextStep: Button
    private lateinit var prevStep: Button
    private lateinit var prevStepNum: Button
    private lateinit var currentStepNum: Button
    private lateinit var nextStepNum: Button
    private lateinit var dividerRight: View
    private lateinit var dividerLeft: View

    private var pageTitleFlow = MutableStateFlow("Complete each step of this task")

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
            pageTitle = it.findViewById(R.id.page_current_step_title)
            nextStep = it.findViewById(R.id.page_next_btn)
            prevStep = it.findViewById(R.id.page_prev_btn)
            prevStepNum = it.findViewById(R.id.page_prev_step_num)
            currentStepNum = it.findViewById(R.id.page_current_step_num)
            nextStepNum = it.findViewById(R.id.page_next_step_num)
            dividerRight = it.findViewById(R.id.page_divider_right)
            dividerLeft = it.findViewById(R.id.page_divider_left)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)

        adapter = PageAdapter(this, countSteps)

        pager?.let {
            it.isUserInputEnabled = false
            it.adapter = adapter
        }

        startEmitFlow()

        updateViews()

        nextStep.setOnClickListener {

            if (!nextStep.text.equals("Done") && currentStep < countSteps){
                currentStep++
                pager?.setCurrentItem(currentStep, true)
                startEmitFlow()
                // Update any changes with views
                updateViews()
            } else {
                Snackbar.make(view, "You successfully complete this task!", Snackbar.LENGTH_LONG)
                    .setTextColor(Color.WHITE)
                    .setBackgroundTint(getColor(requireContext(), R.color.step_done_btn))
                    .show()
            }
        }
        prevStep.setOnClickListener {
            if (currentStep > 0) {
                currentStep--
                pager?.setCurrentItem(currentStep, true)
                startEmitFlow()
                // Update any changes with views
                updateViews()
            }
        }

    }

    private fun startEmitFlow() {

        /** Created by ID
         * date: 20-May-21, 11:53 AM
         * TODO: getFirst step from Current bundle
         */

        val fr: Fragment? = childFragmentManager.findFragmentByTag("f${currentStep}")
        Log.i(TAG, "--> updateTitle: [$fr]")
        fr?.arguments?.let {
            val title = it.getString(STEP_TITLE, STEP_TITLE)
            CoroutineScope(Dispatchers.IO).launch {
                pageTitleFlow.emit(title)
            }
        }
    }

    private fun updateViews() {

        if (currentStep >= 1) prevStepNum.text = "${currentStep - 1}"
        currentStepNum.text = "${pager?.currentItem}"
        if (currentStep < countSteps) nextStepNum.text = "${currentStep + 1}"

        if (currentStep == 0) {
            prevStep.visibility = View.GONE
            prevStepNum.visibility = View.GONE
            dividerLeft.visibility = View.GONE
        } else {
            prevStep.visibility = View.VISIBLE
            prevStepNum.visibility = View.VISIBLE
            dividerLeft.visibility = View.VISIBLE
        }
        if (currentStep == countSteps - 1){
            nextStep.text = "Done"
            nextStep.setBackgroundColor(getColor(requireContext(), R.color.step_done_btn))
            nextStepNum.visibility = View.GONE
            dividerRight.visibility = View.GONE
        } else {
            nextStep.text = "Next"
            nextStep.setBackgroundColor(getColor(requireContext(), R.color.step_num_blue))
            nextStepNum.visibility = View.VISIBLE
            dividerRight.visibility = View.VISIBLE
        }
        updateTitle()
    }

    private fun updateTitle() {
//        val fr: Fragment? = childFragmentManager.findFragmentByTag("f${currentStep}")
        CoroutineScope(Dispatchers.Main).launch {
            pageTitleFlow.collect {
                Log.i(TAG, "--> updateTitle: withFlow[$it]")
                pageTitle.text = it
            }
        }
        Log.i(TAG, "--> updateTitle: $")
        Log.i(TAG, "--> updateTitle:${arguments?.getString(STEP_TITLE)}")
//        fr?.arguments?.let {
//            pageTitle.text = it.getString(STEP_TITLE, STEP_TITLE)
//        }

    }
}