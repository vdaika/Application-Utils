package com.vdaika.apputils.guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vdaika.apputils.Utils

class GuidePageFragment : Fragment() {
    var mView: ViewGroup? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutID = arguments!!.getInt(KEY_LAYOUT_ID)
        mView = inflater.inflate(layoutID, null) as ViewGroup
        return mView
    }

    override fun onDestroy() {
        super.onDestroy()
        Utils.freeView(mView)
        mView = null
    }

    companion object {
        private const val KEY_LAYOUT_ID = "layout_guide"
        fun newInstance(layoutID: Int): GuidePageFragment {
            val guideFragment = GuidePageFragment()
            val bundle = Bundle()
            bundle.putInt(KEY_LAYOUT_ID, layoutID)
            guideFragment.arguments = bundle
            return guideFragment
        }
    }
}