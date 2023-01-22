package org.apps.mov.sign.signup

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import org.apps.mov.home.HomeActivity
import org.apps.mov.R
import org.apps.mov.Utils.Preferences
import org.apps.mov.databinding.ActivitySignUpPhotoscreenBinding
import java.util.*

class SignUpPhotoScreenActivity : AppCompatActivity(), PermissionListener{

    private lateinit var binding: ActivitySignUpPhotoscreenBinding

    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd:Boolean = false
    lateinit var filePath: Uri

    lateinit var storage : FirebaseStorage
    lateinit var storageReference : StorageReference
    lateinit var preferences: Preferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpPhotoscreenBinding.inflate(layoutInflater)
        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()

        binding.icBack.setOnClickListener {
            var intent = Intent(this@SignUpPhotoScreenActivity,SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.tvHello.text = "Selamat Datang\n" + intent.getStringExtra("nama")
        binding.icAdd.setOnClickListener {
            if (statusAdd) {
                statusAdd = false
                binding.btnSave.visibility = View.VISIBLE
                binding.icAdd.setImageResource(R.drawable.ic_add)
                binding.icProfile.setImageResource(R.drawable.user_pic)
            } else {
                Dexter.withActivity(this)
                    .withPermission(android.Manifest.permission.CAMERA)
                    .withListener(this)
                    .check()
            }
        }

        binding.btnHome.setOnClickListener {
            finishAffinity()

            var home = Intent(this@SignUpPhotoScreenActivity, HomeActivity::class.java)
            startActivity(home)
        }

        binding.btnSave.setOnClickListener {
            if(filePath != null){
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading....")
                progressDialog.show()

                var ref = storageReference.child("image/" + UUID.randomUUID().toString())
                ref.putFile(filePath).addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(this,"Uploaded", Toast.LENGTH_LONG).show()

                    ref.downloadUrl.addOnSuccessListener {
                        preferences.setValues("url", it.toString())
                    }

                    finishAffinity()

                    var home = Intent(this@SignUpPhotoScreenActivity, HomeActivity::class.java)
                    startActivity(home)
                }
                .addOnFailureListener{
                    progressDialog.dismiss()
                    Toast.makeText(this,"Failed", Toast.LENGTH_LONG).show()
                }

                .addOnProgressListener {
                    taskSnapshot -> var progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                    progressDialog.setMessage("Upload " + progress.toInt() + " %")
                }

            } else{

            }
        }

        setContentView(binding.root)
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{
            takePictureIntent -> takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this,"Tidak bisa menambahkan foto profile", Toast.LENGTH_LONG).show()

    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
        TODO("Not yet implemented")
    }

    override fun onBackPressed() {
        Toast.makeText(this,"Tergesah? Klik tombol Upload nanti aja", Toast.LENGTH_LONG).show()
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            var bitmap = data?.extras?.get("data") as Bitmap
            statusAdd = true

            filePath = data.getData()!!
            Glide.with(this)
                .load(bitmap)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.icProfile)

            binding.btnSave.visibility = View.VISIBLE
            binding.icAdd.setImageResource(R.drawable.ic_delete)
        }
    }

}