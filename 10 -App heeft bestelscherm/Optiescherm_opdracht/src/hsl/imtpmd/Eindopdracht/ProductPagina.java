package hsl.imtpmd.Eindopdracht;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import hsl.imtpmd.optiescherm.R;

/**
 * Created by Wiebe on 5/6/14.
 */
public class ProductPagina extends Activity{

    // Buttons die op de product pagina staan
    Button bestelButton;
    Button annuleerButton;

    private int prijs = 0;
    private int hotelinfo = 1;
    private int sterren = 2;

    public int aantalBesteld = 0;

public void onCreate(Bundle savedInstanceState)
{
    super.onCreate(savedInstanceState);
    setContentView(R.layout.productpagina);
    // het aanroepen van de listeners methode
    buttonListeners();

    // Arraylist aanmaken welke gevuld wordt met de hotelinfo vanaf de MAIN
    ArrayList<String> hotelInfoLijst = MainActivity.getInfoLijst();

    // ophalen van de textvelden
    TextView productplaats = (TextView) findViewById(R.id.productplaats);
    TextView productnaam = (TextView) findViewById(R.id.productnaam);
    TextView productprijs = (TextView) findViewById(R.id.productprijs);
    TextView productinfo = (TextView) findViewById(R.id.productinfo);
    TextView productsterren = (TextView) findViewById(R.id.productsterren);

    // Het vullen van de textvelden

    productplaats.setText(MainActivity.soortenBestemmingenLijst.get(MainActivity.soortBestemming));
    productnaam.setText(MainActivity.gekozenBestemming);
    productprijs.setText(hotelInfoLijst.get(prijs));
    productinfo.setText("'"+hotelInfoLijst.get(hotelinfo)+"'");
    productsterren.setText( hotelInfoLijst.get(sterren));

}

    // methode welke de buttons,listeners en gelijk methodes geeft. Dit gebeurd in een aparte
    // methode om conflicten met threads te voorkomen.
 public void buttonListeners(){
       bestelButton     = (Button) findViewById(R.id.bestelbutton);


    // functionaliteiten van de bestelbutton
       bestelButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


               /// Bij druk op de bestel knop gaat die terug naar de main pagina

               Intent tmpIntent;
                 tmpIntent = new Intent(getApplicationContext(), BoekingsPagina.class);
              //tmpIntent.putExtra(SHOWITEMINTENT_EXTRA_FETCHROWID, position);
                startActivity(tmpIntent);

               EditText aantal = (EditText) findViewById(R.id.aantalinvoer);
        //       aantalBesteld =  Integer.parseInt(aantal.getText().toString());

               Log.d("aantaltest", String.valueOf(aantalBesteld));


           }
       });


        // functionaliteiten van de annuleer button
        annuleerButton   = (Button) findViewById(R.id.annuleerbutton);
        annuleerButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

            // Bij druk op de annuleer knop gaat die terug naar de main pagina

               Intent tmpIntent;
               tmpIntent = new Intent(getApplicationContext(), MainActivity.class);
               // tmpIntent.putExtra(SHOWITEMINTENT_EXTRA_FETCHROWID, position);
               startActivity(tmpIntent);
           }
       });
   }

}


