package com.tikaa.mygithub

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tikaa.mygithub.detail.FollowersFragment
import com.tikaa.mygithub.detail.FollowingFragment

class SectionsPagerAdapter(private val context: Context, fragmentManager: FragmentManager, bundle: Bundle) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmentBundle: Bundle

    init {
        fragmentBundle = bundle
    }

    @StringRes
    private val TAB_TITLE = intArrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2
    )
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return  context.resources.getString(TAB_TITLE[position])
    }

}