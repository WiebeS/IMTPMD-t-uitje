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


    private String naam;
    private String adres;
    private String telefoon;
    private String email;

    public static final String MyPREFERENCES = "MyPrefs" ;

    SharedPreferences sharedPreferences;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boekings_pagina);

        buttonListeners();


        Naam        = (TextView) findViewById(R.id.naaminvoer);
        Adres       = (TextView) findViewById(R.id.adresinvoer);
        Telefoon    = (TextView) findViewById(R.id.telefooninvoer);

        Email       = (TextView) findViewById(R.id.emailinvoerr);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        Naam.setText(sharedPreferences.getString(naam, ""));


        if (sharedPreferences.contains(telefoon))
        {
            Telefoon.setText(sharedPreferences.getString(telefoon, ""));

        }
//        if (sharedPreferences.contains(Email))
//        {
//            email.setText(sharedPreferences.getString(Email, ""));
//
//        }
//        if (sharedPreferences.contains(Street))
//        {
//            street.setText(sharedPreferences.getString(Street, ""));
//
//        }
    }

    public void savenEnLaden(){
        String n   = Naam.getText().toString();
        String tel = Telefoon.getText().toString();
        String e   = Email.getText().toString();
        String a   = Adres.getText().toString();


        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(naam, n);
        editor.putString(telefoon, tel);
        editor.putString(email, e);
        editor.putString(adres, a);
     //   editor.putString(Place, p);

        editor.commit();

    }

    public void buttonListeners(){
        boekButton     = (Button) findViewById(R.id.boekButton);


        // functionaliteiten van de bestelbutton
        boekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                /// Bij druk op de bestel knop gaat die terug naar de main pagina

                Intent tmpIntent;
                tmpIntent = new Intent(getApplicationContext(), BoekingsPagina.class);
                //tmpIntent.putExtra(SHOWITEMINTENT_EXTRA_FETCHROWID, position);
                startActivity(tmpIntent);


            }
        });


        // functionaliteiten van de annuleer button
        annuleerButton   = (Button) findViewById(R.id.AnnuleerButtonBoek);
        annuleerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Bij druk op de annuleer knop gaat die terug naar de main pagina


                savenEnLaden();

                Intent tmpIntent;
                tmpIntent = new Intent(getApplicationContext(), ProductPagina.class);
                // tmpIntent.putExtra(SHOWITEMINTENT_EXTRA_FETCHROWID, position);
                startActivity(tmpIntent);
            }
        });
    }

}

