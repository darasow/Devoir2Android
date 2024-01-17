package com.example.devoir2androide

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class profil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)
        val user = intent.getParcelableExtra<User>("user") as User
        val valuNom = findViewById<TextView>(R.id.valuNom)
        val valuPrenom = findViewById<TextView>(R.id.valuPrenom)
        val valuGenre = findViewById<TextView>(R.id.valuGenre)
        val valuPays = findViewById<TextView>(R.id.valuPays)
        val deconnexion = findViewById<Button>(R.id.deconnexion)
        valuNom.setText(user.nom.toString())
        valuPrenom.setText(user.prenom.toString())
        valuGenre.setText(user.genre.toString())
        valuPays.setText(user.pays.toString())
        deconnexion.setOnClickListener {
             afficheConfirmeDialogue()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun afficheConfirmeDialogue() {
     val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
        builder.setMessage("Voullez vous quittez ?")
        builder.setPositiveButton("Oui"){dialogInterface, id  ->
            val auth = this.getSharedPreferences("auth", Context.MODE_PRIVATE).edit()
            auth.remove("isAuthenticate")
            auth.apply()
             finish()
        }
        builder.setNegativeButton("Non"){dialogInterface, id  ->
            dialogInterface.dismiss()
        }
     val alerteDialogue : AlertDialog = builder.create()
        alerteDialogue.show()
    }
}