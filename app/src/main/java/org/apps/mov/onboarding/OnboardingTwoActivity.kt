package org.apps.mov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.apps.mov.databinding.ActivityOnboardingTwoBinding
import org.apps.mov.sign.signin.SignInActivity

class OnboardingTwoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingTwoBinding.inflate(layoutInflater)

        binding.btnHome.setOnClickListener{
            var intent = Intent(this@OnboardingTwoActivity, OnboardingThreeActivity::class.java)
            startActivity(intent)
        }
        binding.btnDaftar.setOnClickListener{
            finishAffinity()
            var intent = Intent(this@OnboardingTwoActivity, SignInActivity::class.java)
            startActivity(intent)
        }
        setContentView(binding.root)
    }
}