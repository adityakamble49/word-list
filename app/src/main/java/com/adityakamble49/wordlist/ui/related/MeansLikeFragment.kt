package com.adityakamble49.wordlist.ui.related

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adityakamble49.wordlist.R

/**
 * Means Like Fragment
 *
 * @author Aditya Kamble
 * @since 4/10/2018
 */
class MeansLikeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_means_like, container, false)
    }

    /*
     * Helper Functions
     */

    companion object {
        fun newInstance() = MeansLikeFragment()
    }
}