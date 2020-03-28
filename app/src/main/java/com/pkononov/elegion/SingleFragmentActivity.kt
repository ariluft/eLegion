package com.pkononov.elegion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

abstract class SingleFragmentActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_fragment)
        if (savedInstanceState == null){
            var fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, getFragment())
                .commit()
        }
    }

    override fun onBackPressed() {
        var fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount == 1){
            finish()
        }
        else {
            fragmentManager.popBackStack()
        }
    }

    protected abstract fun getFragment(): Fragment
}