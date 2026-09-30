package com.example.marcosgonzalez

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

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

        if(numRandom == 0) {
            numRandom = Random.nextInt(1,100)
        }

        val b = findViewById<Button>(R.id.boton1)
        val textLog = findViewById<TextView>(R.id.texto1)
        val inputUser = findViewById<EditText>(R.id.numUser)
        val miScrollView = findViewById<ScrollView>(R.id.myScrollView)

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder

            .setTitle("¡¡ENHORABUENA!!")
            .setPositiveButton("Retry") { dialog, which ->
                numRandom = Random.nextInt(1,100)
                numTries = 0
                textLog.setText("")
            }
            .setNegativeButton("Finish") { dialog, which ->
                this.finish()
            }

        val dialog: AlertDialog = builder.create()

        b.setOnClickListener {
            val numUser = inputUser.text.toString().toIntOrNull()
            if (numUser != null) {
                if (numUser == numRandom) {
                    numTries++
                    dialog.setMessage("Era el numero " + numRandom + "\nHas usado " + numTries + " intentos.")
                    dialog.show()
                } else if (numUser > numRandom) {
                    numTries++
                    textLog.setText(textLog.text.toString() + "El número es más pequeño que " + numUser + "\n")
                } else {
                    textLog.setText(textLog.text.toString() + "El número es más grande que " + numUser + "\n")
                    numTries++
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

    companion object {

        var numRandom = 0
        var numTries = 0

    }

}
