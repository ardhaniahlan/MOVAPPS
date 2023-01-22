package org.apps.mov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.apps.mov.databinding.ActivityCheckoutSuccessBinding
import org.apps.mov.home.HomeActivity
import org.apps.mov.home.tiket.TicketActivity
import org.apps.mov.model.Checkout
import org.apps.mov.model.Film

class CheckoutSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutSuccessBinding.inflate(layoutInflater)

        binding.btnHome.setOnClickListener {
            finishAffinity()

            val intent = Intent(this@CheckoutSuccessActivity,HomeActivity::class.java)
            startActivity(intent)
        }

            binding.btnTiket.setOnClickListener {
                startActivity(Intent(this,TicketActivity::class.java))
            }

        setContentView(binding.root)
    }
}