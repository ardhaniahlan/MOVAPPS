package org.apps.mov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.apps.mov.Utils.Preferences
import org.apps.mov.databinding.ActivityOnboardingOneBinding
import org.apps.mov.sign.signin.SignInActivity

class OnboardingOneActivity : AppCompatActivity() {

    lateinit var preference: Preferences

    private lateinit var binding: ActivityOnboardingOneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingOneBinding.inflate(layoutInflater)
        preference = Preferences(this)

        if(preference.getValues("onboarding").equals("1")){
            finishAffinity()

            var intent = Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.btnHome.setOnClickListener{
            var intent = Intent(this@OnboardingOneActivity, OnboardingTwoActivity::class.java)
            startActivity(intent)
        }

        binding.btnDaftar.setOnClickListener{
            preference.setValues("onboarding", "1")
            finishAffinity()

            var intent = Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)

    }
}