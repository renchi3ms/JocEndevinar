package com.example.marcosgonzalez

import android.graphics.Typeface
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

class Record (nT: Int, tP: Long, uN: String){
    val numTries = nT
    val timePlayer = tP
    val nameUser = uN
}

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

        val records = mutableListOf<Record>()

        val user1 = Record(3, 13L, "Bruce")
        records.add(user1)
        val user2 = Record(6, 22L, "Diana")
        records.add(user2)
        val user3 = Record(9, 15L, "Clark")
        records.add(user3)
        val user4 = Record(34, 2L, "Barry")
        records.add(user4)

        var userName = ""

        val dialogView = layoutInflater.inflate(R.layout.new_layout, null)

        val loginDialog = AlertDialog.Builder(this).setTitle("Login").setView(dialogView).setPositiveButton("Accept") {
            dialog, _ -> dialog.dismiss()
        }.setNegativeButton("Cancel") {
            dialog, _ -> dialog.dismiss()
        }.create()

        loginDialog.setOnShowListener {
            loginDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val usernameField = dialogView.findViewById<EditText>(R.id.username)
                val username = usernameField.text.toString().trim()
                if (username.isEmpty()) {
                    usernameField.error = "Nombre INVÁLIDO"
                } else {
                    userName = username
                    loginDialog.dismiss()
                }
            }
        }
        loginDialog.show()


        if(numRandom == 0) {
            numRandom = Random.nextInt(1,100)
            startTime = System.currentTimeMillis()
        }

        val b = findViewById<Button>(R.id.boton1)
        val textLog = findViewById<TextView>(R.id.texto1)
        val inputUser = findViewById<EditText>(R.id.numUser)
        val miScrollView = findViewById<ScrollView>(R.id.myScrollView)

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder

            .setTitle("¡¡ENHORABUENA")

            .setPositiveButton("Retry") { dialog, which ->
                numRandom = Random.nextInt(1,100)
                numTries = 0
                startTime = System.currentTimeMillis()
                textLog.setText("")
            }
            .setNegativeButton("Finish") { dialog, which ->
                this.finish()
            }



        b.setOnClickListener {
            val numUser = inputUser.text.toString().toIntOrNull()
            if (numUser != null) {
                if (numUser == numRandom) {
                    val dialog: AlertDialog = builder.create()
                    numTries++
                    val recordUser = Record(numTries, (System.currentTimeMillis() - startTime) / 1000, userName)
                    val sorted = (records + recordUser).sortedWith(compareBy({ it.numTries }, { it.timePlayer }))
                    val formatRow = "%-10d%-15s%-10s%-10s"
                    val results = StringBuilder()
                    results.append("%-10s%-15s%-10s%-10s".format("POSICIÓN", "NOMBRE", "INTENTOS", "TIEMPO") + "\n")
                    results.append("-".repeat(10+15+10+10) + "\n")
                    sorted.forEachIndexed { index, r ->
                        results.append(formatRow.format(index + 1, r.nameUser, r.numTries, r.timePlayer) + "\n")
                    }
                    val tv = TextView(this).apply {
                        typeface = Typeface.MONOSPACE
                        text = results.toString()
                        setPadding(32, 32, 32, 32)
                        @Suppress("DEPRECATION")
                        setHorizontallyScrolling(true)
                    }
                    dialog.setView(tv)

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
        var startTime = 0L

    }

}
