package org.apps.mov.home.tiket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import org.apps.mov.databinding.ActivityTicketBinding
import org.apps.mov.model.Checkout
import org.apps.mov.model.Film

class TicketActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTicketBinding
    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketBinding.inflate(layoutInflater)

        var data = intent.getParcelableExtra<Film>("data")

        binding.tvTitle.text = data?.judul
        binding.tvGenre.text = data?.genre
        binding.tvRate.text = data?.rating

        Glide.with(this)
            .load(data?.poster)
            .into(binding.icPoster)

        binding.rvCheckout.layoutManager = LinearLayoutManager(this)
        dataList.add(Checkout("C1",""))
        dataList.add(Checkout("C2",""))

        binding.rvCheckout.adapter = TicketAdapter(dataList){}

        setContentView(binding.root)
    }
}