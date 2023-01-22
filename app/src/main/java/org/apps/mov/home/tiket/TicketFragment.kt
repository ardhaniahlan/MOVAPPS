package org.apps.mov.home.tiket

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import org.apps.mov.Utils.Preferences
import org.apps.mov.databinding.FragmentTicketBinding
import org.apps.mov.home.dashboard.ComingSoonAdapter
import org.apps.mov.model.Film

class TicketFragment : Fragment() {

    private lateinit var binding : FragmentTicketBinding
    private lateinit var preference : Preferences
    private lateinit var mDatabase : DatabaseReference
    private var dataList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTicketBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preference = Preferences(requireContext())
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        binding.rvTiket.layoutManager = LinearLayoutManager(context)
        getData()


    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(datasnapshot: DataSnapshot) {
                dataList.clear()

                for(getDataSnapshot in datasnapshot.children){
                    val film = getDataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                binding.rvTiket.adapter = ComingSoonAdapter(dataList){
                    var intent = Intent(context,TicketActivity::class.java).putExtra("data", it)
                    startActivity(intent)

                }

                binding.tvTotal.setText("${dataList.size} Movies")

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context," " + databaseError.message,Toast.LENGTH_LONG).show()
            }

        })
    }


}