package com.pkononov.elegion

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment


class AuthFragment : Fragment() {

    private lateinit var buttonEnter: Button
    private lateinit var buttonRegister: Button

    private lateinit var etLogin: AutoCompleteTextView
    private lateinit var etPassword: EditText

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    private lateinit var mLoginedUsersAdapter: ArrayAdapter<String>

    private val loginFocusChangedListener =
        View.OnFocusChangeListener { view: View, hasFocus: Boolean ->
            if (hasFocus)
                etLogin.showDropDown()
        }

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

        mLoginedUsersAdapter = ArrayAdapter(
            view.context, android.R.layout.simple_dropdown_item_1line,
            sharedPreferencesHelper.getSuccessLogin()
        )

        etLogin.setAdapter(mLoginedUsersAdapter)
        etLogin.onFocusChangeListener = loginFocusChangedListener

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
        sharedPreferencesHelper.getUsers().forEach { user ->
            if (isEmailValid(etLogin.text) && isPasswordValid(etPassword.text)) {
                val user: User? = sharedPreferencesHelper.login(
                    etLogin.text.toString(),
                    etPassword.text.toString()
                )
                if (user != null) {
                    val startProfileIntent =
                        Intent(activity, ProfileActivity::class.java)
                    startProfileIntent.putExtra(ProfileActivity.USER_KEY, user)
                    startActivity(startProfileIntent)
                    activity?.finish()
                } else {
                    showMessage(R.string.login_error)
                }
            } else {
                showMessage(R.string.login_input_error)
            }
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
