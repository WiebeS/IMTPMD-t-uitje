package hsl.imtpmd.Eindopdracht;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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


public void onCreate(Bundle savedInstanceState)
{
    super.onCreate(savedInstanceState);
    setContentView(R.layout.productpagina);
    // het aanroepen van de listeners methode
    buttonListeners();

    // de textvelden vullen met de gekozen product informatie

     TextView productnaam = (TextView) findViewById(R.id.productnaam);
    // het ophalen productnaam

      String productNaam =  MainActivity.gekozenBestemming;
      productnaam.setText(productNaam);


 //    TextView productInfo = (TextView) findViewById(R.id.productinfo);
  //   productInfo.setText(MainActivity.getProductSterren(productNaam));

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
               tmpIntent = new Intent(getApplicationContext(), BestelPagina.class);
               // tmpIntent.putExtra(SHOWITEMINTENT_EXTRA_FETCHROWID, position);
               startActivity(tmpIntent);



           }
       });


        // functionaliteiten van de annuleer button
         annuleerButton   = (Button) findViewById(R.id.annuleerbutton);
       annuleerButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

      /// Bij druk op de annuleer knop gaat die terug naar de main pagina

               Intent tmpIntent;
               tmpIntent = new Intent(getApplicationContext(), MainActivity.class);
               // tmpIntent.putExtra(SHOWITEMINTENT_EXTRA_FETCHROWID, position);
               startActivity(tmpIntent);
           }
       });
   }

}


