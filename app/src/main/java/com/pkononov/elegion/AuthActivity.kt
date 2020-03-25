package com.pkononov.elegion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AuthActivity : AppCompatActivity() {

    private lateinit var buttonEnter: Button
    private lateinit var buttonRegister: Button

    private lateinit var etLogin:EditText
    private lateinit var etPassword:EditText


    private val clickListener: View.OnClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.buttonEnter-> {
                Toast.makeText(this, "Clicked 1", Toast.LENGTH_SHORT).show()
            }
            R.id.buttonRegister-> {
                Toast.makeText(this, "Clicked 2", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonEnter = findViewById(R.id.buttonEnter)
        buttonRegister = findViewById(R.id.buttonRegister)
        etLogin = findViewById(R.id.etLogin)
        etPassword = findViewById(R.id.etPassword)

        buttonEnter.setOnClickListener(clickListener)
        buttonRegister.setOnClickListener(clickListener)

    }
}
