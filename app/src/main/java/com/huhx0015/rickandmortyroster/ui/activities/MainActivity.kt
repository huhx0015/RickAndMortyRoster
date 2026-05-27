package com.huhx0015.rickandmortyroster.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.huhx0015.rickandmortyroster.ui.screens.main.MainScreen
import com.huhx0015.rickandmortyroster.ui.theme.RickMortyRosterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
          RickMortyRosterTheme {
            MainScreen(navController = rememberNavController())
          }
        }
    }
}