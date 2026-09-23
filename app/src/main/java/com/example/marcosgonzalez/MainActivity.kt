package com.example.marcosgonzalez

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var numRandom = (1..100).random()

        val b = findViewById<Button>(R.id.boton1)
        val textLog = findViewById<TextView>(R.id.texto1)
        val inputUser = findViewById<EditText>(R.id.numUser)
        val miScrollView = findViewById<ScrollView>(R.id.myScrollView)

        b.setOnClickListener {
            val numUser = inputUser.text.toString().toIntOrNull()
            if (numUser != null) {
                if (numUser == numRandom) {
                    textLog.setText(textLog.text.toString() + "¡¡ENHORABUENA!! Era el numero: " + numRandom + "\nCAMBIAMOS DE NÚMERO")
                    numRandom = (1..100).random()
                } else if (numUser > numRandom) {
                    textLog.setText(textLog.text.toString() + "El número es más pequeño que " + numUser + "\n")
                } else {
                    textLog.setText(textLog.text.toString() + "El número es más grande que " + numUser + "\n")
                }

                miScrollView.post {
                    miScrollView.fullScroll(android.view.View.FOCUS_DOWN)
                }

                inputUser.text.clear()
            } else {
                inputUser.error = "Introduce numero válido"
            }

        }


    }
}