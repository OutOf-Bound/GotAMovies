package net.smartgekko.gotamovies.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doFirstRunCheckup()
    }
    private fun doFirstRunCheckup() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}