package org.apps.mov.home.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.apps.mov.R
import org.apps.mov.Utils.Preferences
import org.apps.mov.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding:FragmentProfileBinding

    private lateinit var preference: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preference = Preferences(requireContext())

        binding.tvNama.text = preference.getValues("nama")
        binding.tvEmail.text = preference.getValues("email")

        Glide.with(this)
            .load(preference.getValues("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(binding.icProfile)

    }


}