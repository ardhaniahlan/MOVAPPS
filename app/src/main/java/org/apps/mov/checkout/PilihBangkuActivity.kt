package org.apps.mov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import org.apps.mov.DetailActivity
import org.apps.mov.R
import org.apps.mov.databinding.ActivityPilihBangkuBinding
import org.apps.mov.home.HomeActivity
import org.apps.mov.model.Checkout
import org.apps.mov.model.Film

class PilihBangkuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPilihBangkuBinding
    var statusA3:Boolean = false
    var statusA4:Boolean = false
    var total:Int = 0

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilihBangkuBinding.inflate(layoutInflater)

        val data = intent.getParcelableExtra<Film>("data")

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this@PilihBangkuActivity,DetailActivity::class.java).putExtra("data",data))
        }
        binding.tvKursi.text = data?.judul

        binding.a3.setOnClickListener {
            if(statusA3) {
                binding.a3.setImageResource(R.drawable.ic_empty)
                statusA3 = false
                total = total - 1
                beliTiket(total)
            } else {
                binding.a3.setImageResource(R.drawable.ic_selected)
                statusA3 = true
                total = total + 1
                beliTiket(total)

                val data = Checkout("A3", "50000")
                dataList.add(data)
            }
        }

        binding.a4.setOnClickListener {
            if(statusA4) {
                binding.a4.setImageResource(R.drawable.ic_empty)
                statusA4 = false
                total = total - 1
                beliTiket(total)
            } else {
                binding.a4.setImageResource(R.drawable.ic_selected)
                statusA4 = true
                total = total + 1
                beliTiket(total)

                val data = Checkout("A4", "50000")
                dataList.add(data)
            }
        }

        binding.btnBeliTiket.setOnClickListener {
            var intent = Intent(this@PilihBangkuActivity, CheckoutActivity::class.java).putExtra("data",dataList)
            startActivity(intent)
        }

        setContentView(binding.root)
    }

    private fun beliTiket(total: Int) {
        if (total == 0){
            binding.btnBeliTiket.setText("BELI TIKET")
            binding.btnBeliTiket.visibility = View.INVISIBLE
        } else {
            binding.btnBeliTiket.setText("BELI TIKET (" + total + ")")
            binding.btnBeliTiket.visibility = View.VISIBLE
        }
    }
}