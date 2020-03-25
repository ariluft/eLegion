package com.pkononov.elegion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes

class AuthActivity : AppCompatActivity() {

    private lateinit var buttonEnter: Button
    private lateinit var buttonRegister: Button

    private lateinit var etLogin:EditText
    private lateinit var etPassword:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_auth)

        buttonEnter = findViewById(R.id.buttonEnter)
        buttonRegister = findViewById(R.id.buttonRegister)
        etLogin = findViewById(R.id.etLogin)
        etPassword = findViewById(R.id.etPassword)

        buttonEnter.setOnClickListener(onButtonClickListener)
        buttonRegister.setOnClickListener(onButtonClickListener)

    }

    private val onButtonClickListener: View.OnClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.buttonEnter-> {
                if (isEmailValid(etLogin.text) && isPasswordValid(etPassword.text)) {
                    //вход
                }
                else{
                    showMessage(R.string.login_input_error)
                }
            }
            R.id.buttonRegister-> {
                //регистрация
            }
        }
    }

    private fun showMessage(@StringRes string: Int){
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }

    private fun isEmailValid(loginText:Editable):Boolean{
        return !TextUtils.isEmpty(loginText) &&
                Patterns.EMAIL_ADDRESS.matcher(loginText).matches()
    }

    private fun isPasswordValid(passwordText:Editable):Boolean{
        return !TextUtils.isEmpty(passwordText)
    }
}
