package com.pkononov.elegion

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextUtils
import android.util.Base64
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
import com.pkononov.elegion.model.User
import com.pkononov.elegion.model.Users
import retrofit2.Call
import retrofit2.Response
import java.io.UnsupportedEncodingException


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
        val view = inflater.inflate(R.layout.fragment_auth, container, false)

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



            val login = etLogin.text.toString()
            val password =  etPassword.text.toString()
            var token = getAuthToken(login, password)



            ApiUtils.getApi().authorization(token!!).enqueue(object : retrofit2.Callback<Users> {

                val handler = Handler(activity!!.mainLooper)

                override fun onFailure(call: Call<Users>, t: Throwable) {
                    handler.post { showMessage(R.string.request_error) }
                }

                override fun onResponse(call: Call<Users>, response: Response<Users>) {
                   handler.post{
                       if (!response.isSuccessful) {
                           showMessage(R.string.login_error)
                       }
                       else{
                           try {
                               val user = OldUser(
                                   response.body()!!.data.email,
                                   response.body()!!.data.name,
                                   ""
                               )
                               val intent =
                                   Intent(activity, ProfileActivity::class.java)
                               intent.putExtra(ProfileActivity.USER_KEY, user)
                               startActivity(intent)
                               activity!!.finish()
                           } catch (e: Exception) {
                               e.printStackTrace()
                           }
                       }
                   }
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
            .addToBackStack(RegistrationFragment::class.java.name)
            .commit();
    }


    private fun showMessage(@StringRes string: Int) {
        Toast.makeText(activity, string, Toast.LENGTH_LONG).show()
    }

    private fun showMessage(string: String) {
        Toast.makeText(activity, string, Toast.LENGTH_LONG).show()
    }

    private fun isEmailValid(loginText: Editable): Boolean {
        return !TextUtils.isEmpty(loginText) &&
                Patterns.EMAIL_ADDRESS.matcher(loginText).matches()
    }

    private fun isPasswordValid(passwordText: Editable): Boolean {
        return !TextUtils.isEmpty(passwordText)
    }

    fun getAuthToken(email: String, password: String): String? {
        var data = ByteArray(0)
        try {
            data = "$email:$password".toByteArray(charset("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return "Basic " + Base64.encodeToString(data, Base64.NO_WRAP)
    }
}
