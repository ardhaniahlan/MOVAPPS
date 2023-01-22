package org.apps.mov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import org.apps.mov.checkout.PilihBangkuActivity
import org.apps.mov.databinding.ActivityDetailBinding
import org.apps.mov.home.dashboard.PlaysAdapter
import org.apps.mov.model.Film
import org.apps.mov.model.Plays

class DetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailBinding
    private lateinit var mDatabase : DatabaseReference
    private var dataList = ArrayList<Plays>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        val data = intent.getParcelableExtra<Film>("data")
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")
            .child(data!!.judul.toString())
            .child("play")

        binding.tvKursi.text = data.judul
        binding.tvGenre.text = data.genre
        binding.tvDesc.text = data.desc
        binding.tvRate.text = data.rating

        Glide.with(this)
            .load(data.poster)
            .into(binding.ivPoster)

        binding.rvWhoPlay.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        getData()

        binding.btnPilihbangku.setOnClickListener {
            var intent = Intent(this@DetailActivity, PilihBangkuActivity::class.java).putExtra("data", data)
            startActivity(intent)
        }

        setContentView(binding.root)
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(datasnapshot: DataSnapshot) {
                dataList.clear()

                for(getdataSnapshot in datasnapshot.children){
                    var Film = getdataSnapshot.getValue(Plays::class.java)
                    dataList.add(Film!!)
                }

                binding.rvWhoPlay.adapter = PlaysAdapter(dataList){}

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@DetailActivity," " + databaseError.message,Toast.LENGTH_LONG).show()
            }

        })
    }
}