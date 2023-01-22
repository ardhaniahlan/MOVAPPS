package org.apps.mov.sign.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import org.apps.mov.databinding.ActivitySignUpBinding
import org.apps.mov.sign.signin.SignInActivity
import org.apps.mov.sign.signin.User

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mFirebaseIntance: FirebaseDatabase
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mDatabase: DatabaseReference

    lateinit var sUsername:String
    lateinit var sPassword:String
    lateinit var sNama:String
    lateinit var sEmail:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        mFirebaseIntance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("User")


        binding.icBack.setOnClickListener {
            var intent = Intent(this@SignUpActivity,SignInActivity::class.java)
            startActivity(intent)
        }

        binding.btnLanjutkan.setOnClickListener {

            sUsername = binding.etUsername.text.toString()
            sPassword = binding.etPassword.text.toString()
            sNama = binding.etNama.text.toString()
            sEmail = binding.etEmail.text.toString()

            if(sUsername.equals("")){
                binding.etUsername.error = "Silahkan isi username Anda"
                binding.etUsername.requestFocus()
            } else if(sPassword.equals("")){
                binding.etPassword.error = "Silahkan isi password Anda"
                binding.etPassword.requestFocus()
            } else if(sNama.equals("")){
                binding.etNama.error = "Silahkan isi nama Anda"
                binding.etNama.requestFocus()
            } else if(sEmail.equals("")){
                binding.etEmail.error = "Silahkan isi email Anda"
                binding.etEmail.requestFocus()
            } else{
                saveUsername(sUsername, sPassword, sNama, sEmail)
            }

        }

        setContentView(binding.root)
    }

    private fun saveUsername(sUsername: String, sPassword: String, sNama: String, sEmail: String) {
        var user = User()
        user.username = sEmail
        user.password = sEmail
        user.nama = sEmail
        user.email = sEmail

        if(sUsername != null){
            checkingUsername(sUsername, user)
        }
    }

    private fun checkingUsername(sUsername: String, data: User) {
        mDatabaseReference.child(sUsername).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(datasnapshot: DataSnapshot) {

                var user = datasnapshot.getValue(User::class.java)
                if (user == null){
                    mDatabaseReference.child(sUsername).setValue(data)

                    var signupPhotoScreen = Intent(this@SignUpActivity,
                        SignUpPhotoScreenActivity::class.java).putExtra("nama", data.nama)
                    startActivity(signupPhotoScreen)

                } else {
                    Toast.makeText(this@SignUpActivity,"User sudah digunakan",Toast.LENGTH_LONG).show()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUpActivity,databaseError.message,Toast.LENGTH_LONG).show()
            }
        })



    }
}