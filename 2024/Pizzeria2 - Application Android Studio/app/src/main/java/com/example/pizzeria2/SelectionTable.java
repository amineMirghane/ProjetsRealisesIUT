package com.example.pizzeria2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Handler;

import com.google.android.material.textfield.TextInputEditText;


public class SelectionTable extends AppCompatActivity implements View.OnClickListener {

    public final static String CLE_DONNEES = "CLE_DONNEES";
    TextInputEditText e;
    Button btnValider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_table);
        e = findViewById(R.id.txtInputNumTable);
        btnValider = findViewById(R.id.btnValider);
        btnValider.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        String valeurTexte = e.getText().toString();
        // Vérifier si le texte n'est pas vide
        if (!valeurTexte.isEmpty()) {
            // Convertir le texte en entier
            int valeur = Integer.parseInt(valeurTexte);
            // Ajouter la valeur dans les extras de l'intent
            intent.putExtra(CLE_DONNEES, valeur);
        }
        startActivity(intent);
    }
}
