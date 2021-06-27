package net.smartgekko.gotamovies.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.smartgekko.gotamovies.R
import net.smartgekko.gotamovies.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding:ActivityHomeBinding

    override fun onBackPressed() {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frgmCont, HomeFragment())
                .commitAllowingStateLoss()
        }

        val topAppBar = binding.topAppBar
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.close -> {
                    finishAndRemoveTask()

                    true
                }
                else -> false
            }
        }
    }
}