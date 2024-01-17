package com.example.devoir2androide

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreference : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreference = this.getSharedPreferences("auth", MODE_PRIVATE)
        val isAuthenticate = sharedPreference.getBoolean("isAuthenticate", false)
        val nom = sharedPreference.getString("nom", null)
        val prenom = sharedPreference.getString("prenom", null)
        val genre = sharedPreference.getString("genre", null)
        val pays = sharedPreference.getString("pays", null)
         if(isAuthenticate)
         {
             Intent(this, profil::class.java).also {
                 val user = User(nom.toString(), prenom.toString(), genre.toString(), pays.toString())
                 it.putExtra("user", user)
                 startActivity(it)
             }
         }else
         {
             Toast.makeText(this, "Connectez vous Svp", Toast.LENGTH_SHORT).show()
         }

        val tvNom = findViewById<EditText>(R.id.editTextNom)
        val tvprenom = findViewById<EditText>(R.id.editTextPrenom)
        val tvGenre = findViewById<RadioGroup>(R.id.radioGroupGenre)
        val tvPays = findViewById<Spinner>(R.id.spinnerPays)
        val error = findViewById<TextView>(R.id.errors)
        val inscription = findViewById<Button>(R.id.buttonInscription)
        inscription.setOnClickListener {
              val textNom = tvNom.text.toString().trim()
              val textprenom = tvprenom.text.toString().trim()

              val selectedGenreId = tvGenre.checkedRadioButtonId
              val selectedGenre = findViewById<RadioButton>(selectedGenreId)?.text.toString().trim()
              // Récupérer le pays sélectionné
              val selectedPays = tvPays.selectedItem.toString().trim()
              if(textNom.isEmpty() || textprenom.isEmpty() || selectedPays.isEmpty() || selectedGenre.isEmpty())
              {
                  error.setText("Tout les champs sont obligatoire")
                  error.visibility = View.VISIBLE
              }else
              {
                  val user = User(textNom, textprenom, selectedGenre, selectedPays)
                  var edit = sharedPreference.edit()
                  edit.putBoolean("isAuthenticate", true)
                  edit.putString("nom", user.nom.toString())
                  edit.putString("prenom", user.prenom.toString())
                  edit.putString("genre", user.genre.toString())
                  edit.putString("pays", user.pays.toString())
                  edit.commit() // ou apply()

                  Intent(applicationContext, profil::class.java).also {
                      it.putExtra("user", user)
                      startActivity(it)
                  }
              }

          }

    }
}