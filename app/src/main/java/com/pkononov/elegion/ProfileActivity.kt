package com.pkononov.elegion

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pkononov.elegion.model.OldUser
import com.pkononov.elegion.model.User

class ProfileActivity : AppCompatActivity() {

    companion object {
        val USER_KEY = "user_key"
    }

    private lateinit var mLogin: TextView;
    private lateinit var mName: TextView;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mLogin = findViewById(R.id.tvEmail)
        mName = findViewById(R.id.tvName)
        val bundle = intent.extras
        val user: OldUser = bundle!![USER_KEY] as OldUser
        mLogin.text = user.email
        mName.text = user.name
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
