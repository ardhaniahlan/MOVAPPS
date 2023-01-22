package org.apps.mov.home.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import org.apps.mov.DetailActivity
import org.apps.mov.Utils.Preferences
import org.apps.mov.databinding.FragmentDashboardBinding
import org.apps.mov.model.Film
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment() {

    private lateinit var binding:FragmentDashboardBinding
    private lateinit var preference : Preferences
    private lateinit var mDatabase : DatabaseReference

    private var dataList = ArrayList<Film>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(layoutInflater)




        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        preference = Preferences(requireActivity().applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        binding.tvNama.setText(preference.getValues("nama"))
        if(preference.getValues("saldo").equals("")){
            currency(preference.getValues("saldo")!!.toDouble(), binding.tvSaldo)
        }

        Glide.with(this)
            .load(preference.getValues("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(binding.icProfile)

        binding.rvNowplaying.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        binding.rvComingsoon.layoutManager = LinearLayoutManager(context)

        getData()
    }

    private fun getData(){
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(datasnapshot: DataSnapshot) {
                dataList.clear()
                for(getdataSnapshot in datasnapshot.children){
                    var film = getdataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                binding.rvNowplaying.adapter = NowPlayingAdapter(dataList) {
                    var intent = Intent(context,DetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }

                binding.rvComingsoon.adapter = ComingSoonAdapter(dataList) {
                    var intent = Intent(context,DetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context," " + databaseError.message,Toast.LENGTH_LONG).show()
            }

        })
    }


    private fun currency(harga : Double, textview : TextView){
        val localID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        textview.setText(format.format(harga))
    }

}