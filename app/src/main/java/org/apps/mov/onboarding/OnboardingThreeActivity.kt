package org.apps.mov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.apps.mov.databinding.ActivityOnboardingThreeBinding
import org.apps.mov.sign.signin.SignInActivity

class OnboardingThreeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingThreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingThreeBinding.inflate(layoutInflater)

        binding.btnDaftar.setOnClickListener{
            finishAffinity()
            var intent = Intent(this@OnboardingThreeActivity, SignInActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)
    }
}