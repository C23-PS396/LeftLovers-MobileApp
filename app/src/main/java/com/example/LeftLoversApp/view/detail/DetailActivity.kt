package com.example.LeftLoversApp.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.LeftLoversApp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    companion object {
        const val NAME = "name"
        const val DESC = "desc"
        const val IMG = "img"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val name = intent.getStringExtra(NAME)
        val desc = intent.getStringExtra(DESC)
        val img = intent.getStringExtra(IMG)

        binding.tvName.text = name
        binding.tvDesc.text = desc
        Glide.with(this).load(img).into(binding.imgStory)
    }
}