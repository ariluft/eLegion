package com.pkononov.elegion

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class AuthActivity :SingleFragmentActivity() {
    override fun getFragment(): Fragment {
        return AuthFragment.newInstance()
    }
}