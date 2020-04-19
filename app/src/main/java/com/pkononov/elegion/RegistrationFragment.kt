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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.registration_fragment.*
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
            val user = OldUser(
                mLogin.text.toString(),
                mName.text.toString(),
                mPassword.text.toString()
            )

            ApiUtils.getApi()
                .registration(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    showMessage(R.string.registration_success)
                    fragmentManager?.popBackStack()
                }, {
                    showMessage(R.string.request_error)
                })

        } else {
            showMessage(R.string.login_input_error)
        }
    }

    private fun isInputValid() =
        isEmailValid(email = mLogin.text.toString()) && isPasswordValid() && isNameValid()

    private fun isEmailValid(email: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            etLogin.setError("Пустое поле")
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etLogin.setError("Email некорректный")
            return false
        }

        return true
    }

    private fun isPasswordValid(): Boolean {
        if (TextUtils.isEmpty(mPassword.text.toString())) {
            mPassword.setError("Пустое поле")
            return false
        }

        if (TextUtils.isEmpty(mPasswordAgain.text.toString())) {
            mPasswordAgain.setError("Пустое поле")
            return false
        }

        if (mPassword.text.toString() != mPasswordAgain.text.toString()) {
            mPassword.setError("Поле должно соответствовать следуюещму")
            mPasswordAgain.setError("Поле должно соответствовать предыдущему")
            return false
        }

        return true
    }

    private fun isNameValid(): Boolean {
        if (TextUtils.isEmpty(mName.text.toString())) {
            etName.setError("Пустое поле")
        }
        return true
    }

    private fun showMessage(@StringRes string: Int) {
        Toast.makeText(activity, string, Toast.LENGTH_LONG).show()
    }

    private fun showMessage(string: String) {
        Toast.makeText(activity, string, Toast.LENGTH_LONG).show()
    }
}