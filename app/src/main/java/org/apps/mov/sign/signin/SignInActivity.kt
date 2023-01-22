package org.apps.mov.sign.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import org.apps.mov.home.HomeActivity
import org.apps.mov.Utils.Preferences
import org.apps.mov.databinding.ActivitySignInBinding
import org.apps.mov.sign.signup.SignUpActivity


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    lateinit var iUsername:String
    lateinit var iPassword:String

    lateinit var mDatabase: DatabaseReference
    lateinit var preference:Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        preference = Preferences(this)

//        preference.setValues("onboarding", "1")
//        if(preference.getValues("status").equals("1")){
//            finishAffinity()
//
//            var home = Intent(this@SignInActivity, HomeActivity::class.java)
//            startActivity(home)
//        }
        binding.btnMasukakun.setOnClickListener {
            iUsername = binding.etUsername.text.toString()
            iPassword = binding.etPassword.text.toString()

            if(iUsername.equals("")){
                binding.etUsername.error = "Silahkan tulis username Anda"
                binding.etUsername.requestFocus()
            } else if(iPassword.equals("")){
                binding.etPassword.error = "Silahkan tulis password Anda"
                binding.etPassword.requestFocus()
            } else {
                pushLogin(iUsername, iPassword)
            }
        }

        binding.btnDaftarAkun.setOnClickListener{
            var signup = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(signup)
        }

        setContentView(binding.root)
    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {

                var user = datasnapshot.getValue(User::class.java)
                if(user == null){
                    Toast.makeText(this@SignInActivity, "Username tidak ditemukan", Toast.LENGTH_LONG).show()
                } else{

                    if(user.password.equals(iPassword)){

                        preference.setValues("nama", user.nama.toString())
                        preference.setValues("username", user.username.toString())
                        preference.setValues("url", user.url.toString())
                        preference.setValues("email", user.email.toString())
                        preference.setValues("saldo", user.saldo.toString())
                        preference.setValues("status", "1")

                        var intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    } else{
                        Toast.makeText(this@SignInActivity, "Password anda salah", Toast.LENGTH_LONG).show()
                    }

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignInActivity, databaseError.message, Toast.LENGTH_LONG).show()
            }
        })
    }


}