package com.pkononov.elegion.albums

import androidx.fragment.app.Fragment
import com.pkononov.elegion.SingleFragmentActivity

class AlbumsActivity : SingleFragmentActivity() {

    companion object {
        val USER_KEY = "user_key"
    }

    override fun getFragment(): Fragment {
        return AlbumsFragment.newInstance()
    }
}