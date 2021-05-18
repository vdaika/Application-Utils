package com.vdaika.apputils.guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.rd.PageIndicatorView
import com.rd.animation.type.AnimationType
import com.vdaika.apputils.R

class GuideFragment : Fragment(), OnPageChangeListener, View.OnClickListener {
    private lateinit var mViewPager: ViewPager
    private lateinit var mIndicator: PageIndicatorView
    private lateinit var mBtClose: View
    private lateinit var mAdapter: PagerAdapter
    private var mFragments: ArrayList<GuidePageFragment> = ArrayList()
    private var mGuideListener: OnGuideListener? = null
    private lateinit var mView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_guide, container, false)
        initView(mView)
        return mView
    }

    private fun initView(viewGroup: View) {
        val fragmentIds = arguments!!.getIntArray(KEY_FRAGMENTS)
        for (i in fragmentIds!!.indices) {
            mFragments.add(GuidePageFragment.newInstance(fragmentIds[i]))
        }
        mAdapter = object : FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int): Fragment {
                return mFragments[position]
            }

            override fun getCount(): Int {
                return mFragments.size
            }
        }
        mViewPager = viewGroup.findViewById(R.id.guide_view_pager)
        mViewPager.adapter = mAdapter
        mViewPager.addOnPageChangeListener(this)
        mIndicator = viewGroup.findViewById(R.id.guide_page_indicator)
        mIndicator.count = fragmentIds.size
        mIndicator.setAnimationType(AnimationType.WORM)
        mBtClose = viewGroup.findViewById(R.id.guide_button_close)
        mBtClose.setOnClickListener(this)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onPageSelected(position: Int) {
        mIndicator.selection = position
    }

    override fun onPageScrollStateChanged(state: Int) {}
    override fun onClick(v: View) {
        parentFragmentManager.popBackStack()
        if (mGuideListener != null) {
            mGuideListener!!.onGuideCloseListener()
        }
    }

    interface OnGuideListener {
        fun onGuideCloseListener()
    }

    fun setGuideListener(guideListener: OnGuideListener) {
        mGuideListener = guideListener
    }

    override fun onDestroy() {
        mGuideListener = null
        super.onDestroy()
    }

    companion object {
        private const val KEY_FRAGMENTS = "fragments_list"
        fun newInstance(fragmentIds: IntArray?): GuideFragment {
            val guideFragment = GuideFragment()
            val bundle = Bundle()
            bundle.putIntArray(KEY_FRAGMENTS, fragmentIds)
            guideFragment.arguments = bundle
            return guideFragment
        }
    }
}