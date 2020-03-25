package com.pkononov.elegion

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView

class ProfileActivity : AppCompatActivity() {

    companion object {
        val USER_KEY = "user_key"
    }

    private lateinit var mPhoto: ImageView;
    private lateinit var mLogin: TextView;
    private lateinit var mPassword: TextView;

    private val mOnPhotoClickListener =
        View.OnClickListener {

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mPhoto = findViewById(R.id.ivPhoto)
        mLogin = findViewById(R.id.tvEmail)
        mPassword = findViewById(R.id.tvPassword)
        val bundle = intent.extras
        val user: User = bundle!![USER_KEY] as User
        mLogin.text = user.login
        mPassword.text = user.password
        mPhoto.setOnClickListener(mOnPhotoClickListener)
    }
}
