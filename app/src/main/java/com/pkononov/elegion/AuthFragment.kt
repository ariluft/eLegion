package com.pkononov.elegion

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import java.util.*


class AuthFragment : Fragment() {

    private lateinit var buttonEnter: Button
    private lateinit var buttonRegister: Button

    private lateinit var etLogin: EditText
    private lateinit var etPassword: EditText

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_auth, container, false)

        sharedPreferencesHelper = SharedPreferencesHelper(view.context)

        buttonEnter = view.findViewById(R.id.buttonEnter)
        buttonRegister = view.findViewById(R.id.buttonRegister)
        etLogin = view.findViewById(R.id.etLogin)
        etPassword = view.findViewById(R.id.etPassword)

        buttonEnter.setOnClickListener(onButtonClickListener)
        buttonRegister.setOnClickListener(onButtonClickListener)
        return view
    }

    companion object {
        fun newInstance(): AuthFragment = AuthFragment().apply {
            arguments = Bundle()
        }
    }


    private val onButtonClickListener: View.OnClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.buttonEnter -> {
                login()
            }
            R.id.buttonRegister -> {
                register()
            }
        }
    }

    private fun login() {
        var isLoginSuccess = false

        sharedPreferencesHelper.getUsers().forEach { user ->
            if (user.login.toLowerCase() == etLogin.text.toString().toLowerCase() &&
                user.password == etPassword.text.toString()
            ) {

                isLoginSuccess = true

                if (isEmailValid(etLogin.text) && isPasswordValid(etPassword.text)) {
                    val startProfileIntent =
                        Intent(activity, ProfileActivity::class.java)
                    startProfileIntent.putExtra(
                        ProfileActivity.USER_KEY,
                        User(
                            etLogin.text.toString(),
                            etPassword.text.toString()
                        )
                    )
                    startActivity(startProfileIntent)
                    activity?.finish()
                } else {
                    showMessage(R.string.login_input_error)
                }
            }
        }

        if (!isLoginSuccess){
            showMessage(R.string.login_error)
        }
    }

    private fun register() {
        fragmentManager!!
            .beginTransaction()
            .replace(R.id.fragmentContainer, RegistrationFragment.newInstance())
            .addToBackStack(RegistrationFragment.javaClass.name)
            .commit();
    }


    private fun showMessage(@StringRes string: Int) {
        Toast.makeText(activity, string, Toast.LENGTH_LONG).show()
    }

    private fun isEmailValid(loginText: Editable): Boolean {
        return !TextUtils.isEmpty(loginText) &&
                Patterns.EMAIL_ADDRESS.matcher(loginText).matches()
    }

    private fun isPasswordValid(passwordText: Editable): Boolean {
        return !TextUtils.isEmpty(passwordText)
    }
}
