package net.smartgekko.gotamovies.views.activities

import android.R
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import net.smartgekko.gotamovies.utils.SPLASH_DISPLAY_LENGTH


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(net.smartgekko.gotamovies.R.layout.activity_splash)
        Handler().postDelayed({
            val i = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(i)
            finish()
        }, SPLASH_DISPLAY_LENGTH)
    }
}