package com.mikerzdev.snakegame.presentation.ui.screen.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mikerzdev.snakegame.databinding.ActivityMainBinding
import com.mikerzdev.snakegame.presentation.ui.screen.game.SnakeGameActivity
import com.mikerzdev.snakegame.presentation.ui.screen.score.ScoreActivity
import com.mikerzdev.snakegame.presentation.ui.screen.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.buttonMainMenuStartGame.setOnClickListener {
            val intent = Intent(this, SnakeGameActivity::class.java)
            startActivity(intent)
        }

        binding.buttonMainMenuScore.setOnClickListener {
            val intent = Intent(this, ScoreActivity::class.java)
            startActivity(intent)
        }

        binding.buttonMainMenuSnakeSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

    }
}