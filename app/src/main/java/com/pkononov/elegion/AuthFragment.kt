package com.pkononov.elegion

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.pkononov.elegion.RegistrationFragment.Companion.JSON
import okhttp3.*
import okio.IOException


class AuthFragment : Fragment() {

    private lateinit var buttonEnter: Button
    private lateinit var buttonRegister: Button

    private lateinit var etLogin: EditText
    private lateinit var etPassword: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_auth, container, false)


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
        if (isEmailValid(etLogin.text) && isPasswordValid(etPassword.text)) {

            var request = Request.Builder()
                .url(BuildConfig.SERVER_URL + "user/")
                .build()

            var client = ApiUtils.getBasicAuthClient(
                etLogin.text.toString(),
                etPassword.text.toString(),
                true
            )

            client.newCall(request).enqueue(object : Callback {

                var handler = Handler(activity!!.mainLooper)

                override fun onFailure(call: Call, e: IOException) {
                    handler.post(object : Runnable {
                        override fun run() {
                            showMessage(R.string.request_error)
                        }

                    })
                }

                override fun onResponse(call: Call, response: Response) {
                    handler.post(object : Runnable {
                        override fun run() {
                            if (!response.isSuccessful) {
                                showMessage(R.string.login_error)
                            } else {
                                try {
                                    var gson = Gson()
                                    var json: JsonObject = gson.fromJson(
                                        response.body.toString(),
                                        JsonObject::class.java
                                    )
                                    var user: User =
                                        gson.fromJson(json.get("data"), User::class.java)

                                    val startProfileIntent =
                                        Intent(activity, ProfileActivity::class.java)
                                    startProfileIntent.putExtra(ProfileActivity.USER_KEY, user)
                                    startActivity(startProfileIntent)
                                    activity?.finish()
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }
                            }
                        }

                    })
                }
            })

        } else {
            showMessage(R.string.login_input_error)
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
