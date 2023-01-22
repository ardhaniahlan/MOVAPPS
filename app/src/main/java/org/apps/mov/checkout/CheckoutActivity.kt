package org.apps.mov.checkout


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import org.apps.mov.Utils.Preferences
import org.apps.mov.databinding.ActivityCheckoutBinding
import org.apps.mov.home.HomeActivity
import org.apps.mov.model.Checkout

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private var dataList = ArrayList<Checkout>()
    private var total:Int = 0
    private lateinit var preferece : Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)

        preferece = Preferences(this)
        dataList = intent.getSerializableExtra("data") as ArrayList<Checkout>

        for (a in dataList.indices){
            total = total + dataList[a].harga!!.toInt()
        }

        dataList.add(Checkout("Total Harus Dibayar", total.toString()))

        binding.rvCheckout.layoutManager = LinearLayoutManager(this)
        binding.rvCheckout.adapter = CheckoutAdapter(dataList){}

        binding.btnBayar.setOnClickListener {
            var intent = Intent(this,CheckoutSuccessActivity::class.java)
            startActivity(intent)
        }

        binding.btnBatal.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this,HomeActivity::class.java))
        }

        setContentView(binding.root)
    }


}