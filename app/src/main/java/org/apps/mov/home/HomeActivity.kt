package org.apps.mov.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import org.apps.mov.R
import org.apps.mov.databinding.ActivityHomeBinding
import org.apps.mov.home.dashboard.DashboardFragment
import org.apps.mov.home.profile.ProfileFragment
import org.apps.mov.home.tiket.TicketFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        //        NavIcon Active
        val fragmentHome = DashboardFragment()
        val fragmentTicket = TicketFragment()
        val fragmentProfile = ProfileFragment()
        setFragment(fragmentHome)


        binding.icMenu1.setOnClickListener {
            setFragment(fragmentHome)

            changeIcon(binding.icMenu1, R.drawable.ic_home_klik)
            changeIcon(binding.icMenu2, R.drawable.ic_ticket)
            changeIcon(binding.icMenu3, R.drawable.ic_profil)
        }

        binding.icMenu2.setOnClickListener {
            setFragment(fragmentTicket)

            changeIcon(binding.icMenu1, R.drawable.ic_home)
            changeIcon(binding.icMenu2, R.drawable.ic_ticket_klik)
            changeIcon(binding.icMenu3, R.drawable.ic_profil)
        }

        binding.icMenu3.setOnClickListener {
            setFragment(fragmentProfile)

            changeIcon(binding.icMenu1, R.drawable.ic_home)
            changeIcon(binding.icMenu2, R.drawable.ic_ticket)
            changeIcon(binding.icMenu3, R.drawable.ic_profil_klik)
        }

        setContentView(binding.root)
    }

    private fun setFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layoutFrame, fragment)
        fragmentTransaction.commit()
    }

    private fun changeIcon(imageView: ImageView, int: Int){
        imageView.setImageResource(int)
    }

}