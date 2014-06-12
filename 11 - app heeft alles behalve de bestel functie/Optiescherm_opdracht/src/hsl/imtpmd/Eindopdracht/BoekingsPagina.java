package hsl.imtpmd.Eindopdracht;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hsl.imtpmd.optiescherm.R;

/**
 * Created by Wiebe on 5/22/14.
 */
public class BoekingsPagina extends Activity {

    // Buttons die op de product pagina staan
    Button boekButton;
    Button annuleerButton;


    TextView Naam ;
    TextView Adres;
    TextView Telefoon;
    TextView Email;
    TextView Aantal;
    TextView Hotelnaam;

    private String naam = null;
    private String adres;
    private String telefoon;
    private String email;

    public static final String MyPREFERENCES = "MyPrefs" ;

    SharedPreferences sharedPreferences;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boekings_pagina);
        // De buttons instellen
        buttonListeners();
        // Alle invoervelden aanwijzen
        Naam        = (TextView) findViewById(R.id.naaminvoer);
        Adres       = (TextView) findViewById(R.id.adresinvoer);
        Telefoon    = (TextView) findViewById(R.id.telefooninvoer);
        Email       = (TextView) findViewById(R.id.emailinvoerr);
        Aantal       = (TextView) findViewById(R.id.aantalpersonen);
        Hotelnaam       = (TextView) findViewById(R.id.hotelnaaminvoer);

        // De hotelnaam instellen zodat de gebruiker deze nogmaals kan lezen alvorens er geboekt wordt
        Hotelnaam.setText(MainActivity.gekozenBestemming);

        // Het te boeken aantal personen instellen in de textview
        // Dit aantal wordt niet opgeslagen
        String aantal = String.valueOf(ProductPagina.aantalBesteld);
        Aantal.setText(aantal);

        // Opslag instellen
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

         // Als er opgeslagen gegevens worden gevonden dan worden deze geladen en ingevoerd.
        if (sharedPreferences.contains("naam"))
        {
            Naam.setText(sharedPreferences.getString("naam", naam));
        }

        if (sharedPreferences.contains("adres"))
        {
            Adres.setText(sharedPreferences.getString("adres", adres));
        }

        if (sharedPreferences.contains("telefoon"))
        {
            Telefoon.setText(sharedPreferences.getString("telefoon", telefoon));
        }

        if (sharedPreferences.contains("email"))
        {
            Email.setText(sharedPreferences.getString("email", email));
        }

    }

    // Methode welke de gegevens opslaat en welke checkt of de gegevens uberhaupt zijn ingevuld
    // Zijn de gegevens niet ingevuld dan komt er een toast pop up
    public void opslaanGegevens(){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        // naamveld checken
        String sNaam = Naam.getText().toString();
        if (sNaam.matches("")) {
            Toast.makeText(this, "Vul hier uw naam in", Toast.LENGTH_SHORT).show();
            return;
        }

        else {
            this.naam =  Naam.getText().toString();

            editor.putString("naam", naam);
            editor.commit();
        }
        // adresveld checken
        String sAdres = Adres.getText().toString();
        if (sAdres.matches("")) {
            Toast.makeText(this, "Vul hier uw adres in", Toast.LENGTH_SHORT).show();
            return;
        }

        else {
            this.adres =  Adres.getText().toString();

            editor.putString("adres", adres);
            editor.commit();
        }
        // Telefoonveld checken
        String sTel = Telefoon.getText().toString();
        if (sTel.matches("")) {
            Toast.makeText(this, "Vul hier uw adres in", Toast.LENGTH_SHORT).show();
            return;
        }

        else {
            this.telefoon =  Telefoon.getText().toString();

            editor.putString("telefoon", telefoon);
            editor.commit();
        }

        // emailveld checken
        String sEmail = Email.getText().toString();
        if (sEmail.matches("")) {
            Toast.makeText(this, "Vul hier uw adres in", Toast.LENGTH_SHORT).show();
            return;
        }

        else {
            this.email =  Email.getText().toString();

            editor.putString("email", email);
            editor.commit();
        }


        Log.d("Opgeslagen naam" , naam + telefoon + adres+ email);




    }

    public void buttonListeners(){
        boekButton     = (Button) findViewById(R.id.boekButton);


        // functionaliteiten van de bestelbutton
        boekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


             //   /// Bij druk op de bestel knop gaat die terug naar de main pagina

           //     Intent tmpIntent;
             //   tmpIntent = new Intent(getApplicationContext(), BoekingsPagina.class);
                //tmpIntent.putExtra(SHOWITEMINTENT_EXTRA_FETCHROWID, position);
            //    startActivity(tmpIntent);
                opslaanGegevens();

            }
        });


        // functionaliteiten van de annuleer button
        annuleerButton   = (Button) findViewById(R.id.AnnuleerButtonBoek);
        annuleerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Bij druk op de annuleer knop gaat die terug naar de main pagina
                opslaanGegevens();

                Intent tmpIntent;
                tmpIntent = new Intent(getApplicationContext(), ProductPagina.class);
                // tmpIntent.putExtra(SHOWITEMINTENT_EXTRA_FETCHROWID, position);
                startActivity(tmpIntent);
            }
        });
    }

}

