package com.vdaika.apputils.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.vdaika.apputils.R

class AboutFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        val content = arguments!!.getString(KEY_CONTENT)
        val email = view.findViewById<View>(R.id.email)
        email.setOnClickListener(this)
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { parentFragmentManager.popBackStack() }
    }

    override fun onClick(v: View) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                getString(R.string.string_common_mail_to), getString(R.string.string_common_email), null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.string_common_my_feedback)
        emailIntent.putExtra(Intent.EXTRA_TEXT, R.string.string_common_say_hi)
        startActivity(Intent.createChooser(emailIntent, getString(R.string.string_common_send_email)))
    }

    companion object {
        private const val KEY_CONTENT = "content_id"
        fun newInstance(content: String?): AboutFragment {
            val fragment = AboutFragment()
            val bundle = Bundle()
            bundle.putString(KEY_CONTENT, content)
            fragment.arguments = bundle
            return fragment
        }
    }
}