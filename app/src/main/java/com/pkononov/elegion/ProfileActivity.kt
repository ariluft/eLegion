package com.pkononov.elegion

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater = menuInflater
        inflater.inflate(R.menu.profile_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionLogout -> {
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
