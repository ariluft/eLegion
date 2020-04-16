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
import com.pkononov.elegion.model.OldUser
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Callback

class RegistrationFragment : Fragment() {
    private lateinit var mLogin: EditText
    private lateinit var mName: EditText
    private lateinit var mPassword: EditText
    private lateinit var mPasswordAgain: EditText
    private lateinit var mRegistration: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.registration_fragment, container, false)


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
        val JSON: MediaType? = "application/json; charset=utf-8".toMediaTypeOrNull()
    }

    private val mOnRegestrationClickListener = View.OnClickListener {

        if (isInputValid()) {
            var user = OldUser(
                mLogin.text.toString(),
                mName.text.toString(),
                mPassword.text.toString()
            )

            var handler = Handler(activity!!.mainLooper)


            ApiUtils.getApi().registration(user).enqueue(object : Callback<Void> {

                override fun onFailure(call: retrofit2.Call<Void>, t: Throwable) {
                    handler.post { showMessage(R.string.request_error) }
                }

                override fun onResponse(
                    call: retrofit2.Call<Void>,
                    response: retrofit2.Response<Void>
                ) {
                    handler.post {
                        if (response.isSuccessful) {
                            showMessage(R.string.registration_success)
                            fragmentManager?.popBackStack()
                        } else {
                            //todo детальная обработка ошибок
                            showMessage(R.string.request_error)
                        }
                    }
                }
            })

        } else {
            showMessage(R.string.login_input_error)
        }
    }

    private fun isInputValid() =
        isEmailValid(email = mLogin.text.toString()) && isPasswordValid() && isNameValid()

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