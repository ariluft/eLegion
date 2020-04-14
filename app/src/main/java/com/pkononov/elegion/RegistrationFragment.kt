package com.pkononov.elegion

import android.os.Bundle
import android.os.Handler
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
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okio.IOException

class RegistrationFragment : Fragment() {
    private lateinit var mLogin: EditText
    private lateinit var mName: EditText
    private lateinit var mPassword: EditText
    private lateinit var mPasswordAgain: EditText
    private lateinit var mRegistration: Button

    private lateinit var mSharedPreferences: SharedPreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.registration_fragment, container, false)

        mSharedPreferences = SharedPreferencesHelper(view.context)

        mLogin = view.findViewById(R.id.etLogin)
        mName = view.findViewById(R.id.etName)
        mPassword = view.findViewById(R.id.etPassword)
        mPasswordAgain = view.findViewById(R.id.etPasswordAgain)
        mRegistration = view.findViewById(R.id.btnRegistration)

        mRegistration.setOnClickListener(mOnRegestrationClickListener)
        return view
    }

    companion object {
        fun newInstance() = RegistrationFragment()
        val JSON : MediaType? = "application/json; charset=utf-8".toMediaTypeOrNull()
    }

    private val mOnRegestrationClickListener = View.OnClickListener {
        if (isInputValid()) {
            var user = User(mLogin.text.toString(), mName.text.toString(), mPassword.text.toString())

            var request = Request.Builder()
                .url(BuildConfig.SERVER_URL + "registration/")
                .post(RequestBody.create(JSON, Gson().toJson(user)))
                .build()

            var client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback{

                var handler = Handler(activity!!.mainLooper)

                override fun onFailure(call: Call, e: IOException) {
                    handler.post(object:Runnable{
                        override fun run() {
                            showMessage(R.string.request_error)
                        }

                    })
                }

                override fun onResponse(call: Call, response: Response) {
                    handler.post(object:Runnable{
                        override fun run() {
                            if (response.isSuccessful){
                                //showMessage(R.string.registration_success)
                                fragmentManager?.popBackStack()
                            }
                            else{
                                //todo детальная обработка ошибок
                                showMessage(R.string.request_error)
                            }
                        }

                    })
                }
            })

            /*
            if (isAdded) {
                showMessage(R.string.registration_input_success)
                fragmentManager?.popBackStack()
            } else {
                showMessage(R.string.registration_input_error)
            }*/
        } else {
            showMessage(R.string.login_input_error)
        }
    }

    private fun isInputValid() = isEmailValid(email = mLogin.text.toString()) && isPasswordValid() && isNameValid()

    private fun isEmailValid(email: String) =
        !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isPasswordValid() = mPassword.text.toString()
        .equals(mPasswordAgain.text.toString()) && !TextUtils.isEmpty(mPassword.text.toString())

    private fun isNameValid() =
        !TextUtils.isEmpty(mName.text.toString())

    private fun showMessage(@StringRes string: Int) {
        Toast.makeText(activity, string, Toast.LENGTH_LONG).show()
    }

}