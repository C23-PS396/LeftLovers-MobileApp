package com.example.LeftLoversApp.view.upload

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.LeftLoversApp.*
import com.example.LeftLoversApp.databinding.ActivityUploadBinding
import com.example.LeftLoversApp.model.UserPreference
import com.example.LeftLoversApp.utils.createCustomTempFile
import com.example.LeftLoversApp.utils.uriToFile
import com.example.LeftLoversApp.view.Result
import com.example.LeftLoversApp.view.login.LoginActivity
import java.io.File
import java.util.*

class UploadActivity : AppCompatActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
    private var getFile: File? = null

    private lateinit var binding: ActivityUploadBinding
    private val uploadViewModel: UploadViewModel by viewModels {
        UploadModelFactory.getInstance(this, UserPreference.getInstance(dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

    }
    private lateinit var currentPhotoPath: String

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
    override fun onResume() {
        super.onResume()
        checkSession()
    }

    private fun checkSession() {
        uploadViewModel.checkToken().observe(this) {
            if(it == "null") {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }else {
                setupButtonCamera()
                setupButtonGaleri()
                setupButtonUpload()
            }
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)

            myFile.let { file ->
                getFile = file
                binding.imgStory.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }
    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@UploadActivity,
                "com.example.storyapp",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }
    private fun setupButtonCamera() {
        binding.buttonCamera.setOnClickListener {
            startTakePhoto()
        }

    }
    private fun setupButtonGaleri() {
        binding.buttonGaleri.setOnClickListener {
            startGallery()
        }
    }
    private fun setupButtonUpload() {
        binding.uploadButton.setOnClickListener {
            uploadViewModel.checkToken().observe(this) {
                postStory("Bearer $it")
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@UploadActivity)
                getFile = myFile
                binding.imgStory.setImageURI(uri)

            }
        }
    }
    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)

    }
    private fun postStory(token: String) {
        println("masuk 3")
        val desc = binding.descText.text.toString()
        if (getFile != null) {
            val temp = uploadViewModel.postStory(token, getFile as File, desc)
            binding.progressBar.visibility = View.VISIBLE
            println("masuk 4")
            temp.observe(this) {
                when(it) {
                    is Result.Loading -> {
                        println("masuk 5")
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        println("masuk 6")
                        val error = it.error
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                    }
                    is Result.Success -> {
                        println("masuk 6")
                        binding.progressBar.visibility = View.INVISIBLE
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }


}