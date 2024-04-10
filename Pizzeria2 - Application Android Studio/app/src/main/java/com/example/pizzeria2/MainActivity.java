package com.example.pizzeria2;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import android.content.Context;
import android.util.Log;




public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Créer les instances de bouttons
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btnReinitialiser;
    //Créer l'instance de textView
    TextView pizzaCMD;
    TextView res;
    //Clé de sauvegarde de résultat
    public final static String CLE_SAUVEGARDE_RESULTAT="CLE_SAUVEGARDE_RESULTAT";
    //Nombres de commandes de chaque pizza
    int nbCommandesBTN1 = 0;
    int nbCommandesBTN2 = 0;
    int nbCommandesBTN3 = 0;
    int nbCommandesBTN4 = 0;
    int nbCommandesBTN5 = 0;
    int nbCommandesBTN6 = 0;
    int nbCommandesBTN7 = 0;
    int nbCommandesBTN8 = 0;
    //NumPizza
    int numPizza=0;
    int valeurTable=0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialiser les instances de bouton
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btnReinitialiser=findViewById(R.id.btnReinitialiser);
        //Initialiser l'instance de textView
        pizzaCMD=findViewById(R.id.txtViewNumTable);
        // Mettre un évennement lorsque l'on clique sur le bouton
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btnReinitialiser.setOnClickListener(this);
        // Restaurer l'état sauvegardé
        if (savedInstanceState != null) {
            nbCommandesBTN1 = savedInstanceState.getInt("BTN1_COUNT");
            nbCommandesBTN2 = savedInstanceState.getInt("BTN2_COUNT");
            nbCommandesBTN3 = savedInstanceState.getInt("BTN3_COUNT");
            nbCommandesBTN4 = savedInstanceState.getInt("BTN4_COUNT");
            nbCommandesBTN5 = savedInstanceState.getInt("BTN5_COUNT");
            nbCommandesBTN6 = savedInstanceState.getInt("BTN6_COUNT");
            nbCommandesBTN7 = savedInstanceState.getInt("BTN7_COUNT");
            nbCommandesBTN8 = savedInstanceState.getInt("BTN8_COUNT");
            // Mettre à jour le texte des boutons avec les valeurs restaurées
            btn1.setText("Royale: " + nbCommandesBTN1);
            btn2.setText("Napolitaine: " + nbCommandesBTN2);
            btn3.setText("Quatre Fromages: " + nbCommandesBTN3);
            btn4.setText("Montagnard: " + nbCommandesBTN4);
            btn5.setText("Raclette: " + nbCommandesBTN5);
            btn6.setText("Panna Cotta: " + nbCommandesBTN6);
            btn7.setText("Tiramisu: " + nbCommandesBTN7);
            btn8.setText("Hawaïenne: " + nbCommandesBTN8);
            String pizzaCmdText = savedInstanceState.getString("PIZZA_CMD_TEXT");
            pizzaCMD.setText(pizzaCmdText);
        }
        Intent intent = getIntent();
        // Récupérer la valeur envoyée depuis l'activité SelectionTable
        valeurTable = intent.getIntExtra(SelectionTable.CLE_DONNEES, 1);
        // Afficher la valeur récupérée dans le TextView
        pizzaCMD.setText("Commande de la table n°:" + valeurTable);
        }

        @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
        public void onClick (View v) {
                if (v.getId() == R.id.btn1) {
                    nbCommandesBTN1++;
                    numPizza=5;
                    btn1.setText("Royale: " + nbCommandesBTN1);
                    new CallServerThread(valeurTable, numPizza).start();
                } else if (v.getId() == R.id.btn2) {
                    nbCommandesBTN2++;
                    numPizza=11;
                    btn2.setText("Napolitaine: " + nbCommandesBTN2);
                    new CallServerThread(valeurTable, numPizza).start();
                } else if (v.getId() == R.id.btn3) {
                    nbCommandesBTN3++;
                    numPizza=14;
                    btn3.setText("Quatres Fromages: " + nbCommandesBTN3);
                    new CallServerThread(valeurTable, numPizza).start();
                } else if (v.getId() == R.id.btn4) {
                    nbCommandesBTN4++;
                    numPizza=18;
                    btn4.setText("Montagnard: " + nbCommandesBTN4);
                    new CallServerThread(valeurTable, numPizza).start();
                } else if (v.getId() == R.id.btn5) {
                    nbCommandesBTN5++;
                    numPizza=20;
                    btn5.setText("Raclette: " + nbCommandesBTN5);
                    new CallServerThread(valeurTable, numPizza).start();
                } else if (v.getId() == R.id.btn6) {
                    nbCommandesBTN6++;
                    numPizza=94;
                    btn6.setText("Panna Cotta: " + nbCommandesBTN6);
                    new CallServerThread(valeurTable, numPizza).start();
                } else if (v.getId() == R.id.btn7) {
                    nbCommandesBTN7++;
                    numPizza=91;
                    btn7.setText("Tiramisu: " + nbCommandesBTN7);
                    new CallServerThread(valeurTable, numPizza).start();
                } else if (v.getId() == R.id.btn8) {
                    nbCommandesBTN8++;
                    numPizza=6;
                    btn8.setText("Hawaïenne: " + nbCommandesBTN8);
                    new CallServerThread(valeurTable, numPizza).start();
                } else if(v.getId()==R.id.btnReinitialiser)
                {
                    nbCommandesBTN1 = 0;
                    nbCommandesBTN2 = 0;
                    nbCommandesBTN3 = 0;
                    nbCommandesBTN4 = 0;
                    nbCommandesBTN5 = 0;
                    nbCommandesBTN6 = 0;
                    nbCommandesBTN7 = 0;
                    nbCommandesBTN8 = 0;
                    //Mise à jour du texte
                    btn1.setText("Royale: ");
                    btn2.setText("Napolitaine: ");
                    btn3.setText("Quatre Fromages: ");
                    btn4.setText("Montagnard: ");
                    btn5.setText("Raclette: ");
                    btn6.setText("Panna Cotta: ");
                    btn7.setText("Tiramisu: ");
                    btn8.setText("Hawaïenne: ");
                }
            }

            @Override
            protected void onSaveInstanceState(Bundle outState) {
                super.onSaveInstanceState(outState);
                //Sauvegarde du résultat
                outState.putInt("BTN1_COUNT", nbCommandesBTN1);
                outState.putInt("BTN2_COUNT", nbCommandesBTN2);
                outState.putInt("BTN3_COUNT", nbCommandesBTN3);
                outState.putInt("BTN4_COUNT", nbCommandesBTN4);
                outState.putInt("BTN5_COUNT", nbCommandesBTN5);
                outState.putInt("BTN6_COUNT", nbCommandesBTN6);
                outState.putInt("BTN7_COUNT", nbCommandesBTN7);
                outState.putInt("BTN8_COUNT", nbCommandesBTN8);
                outState.putString("PIZZA_CMD_TEXT", pizzaCMD.getText().toString());
            }

    private void callServer(int numTable, int numCommande, Handler handler) {
        try {
            // Vérifier la connectivité Internet
            if (!isInternetAvailable()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Internet not available", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }

            // Se connecter au serveur
            Socket socket = new Socket("chadok.info", 9874);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Construction du message pour le serveur
            String messagePourLeServeur = "";
            if (numTable < 10) {
                messagePourLeServeur += "0"; // Ajoute le préfixe "0" si numTable est inférieur à 10
            }
            messagePourLeServeur += numTable; // Concatène numTable
            if (numCommande < 10) {
                messagePourLeServeur += "0"; // Ajoute le préfixe "0" si numCommande est inférieur à 10
            }
            messagePourLeServeur += numCommande; // Concatène numCommande

            // Envoi du message au serveur
            writer.println(messagePourLeServeur);

            // Lecture de la première réponse du serveur (commande passée)
            final String firstResponse = reader.readLine();

            // Affichage de la première réponse dans la console
            Log.d("ServerResponse", "Commande passée: " + firstResponse);

            // Lecture de la deuxième réponse du serveur
            final String secondResponse = reader.readLine();

            // Affichage de la deuxième réponse dans la console
            Log.d("ServerResponse", "Réponse du serveur : " + secondResponse);

            // Fermeture de la connexion
            socket.close();
        } catch (IOException e) {
            // Gestion des exceptions d'entrée/sortie
            e.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private class CallServerThread extends Thread {
        private int numTable, numCommande;
        private Handler handler;

        public CallServerThread(int numTable, int numCommande) {
            this.numTable = numTable;
            this.numCommande = numCommande;
            this.handler = new Handler();
        }

        @Override
        public void run() {
            callServer(numTable, numCommande, handler);
        }
    }

    }





