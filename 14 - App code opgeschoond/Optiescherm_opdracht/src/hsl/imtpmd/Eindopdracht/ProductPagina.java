package hsl.imtpmd.Eindopdracht;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    public static int aantalBesteld = 0;

public void onCreate(Bundle savedInstanceState)
{
    super.onCreate(savedInstanceState);
    setContentView(R.layout.productpagina);
    // het opzetten van de interface
    setupUserinterface();
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

             EditText aantal = (EditText) findViewById(R.id.aantalinvoer);
             aantalBesteld =  Integer.parseInt(aantal.getText().toString());

            if (aantalBesteld <1)
            {
                // berichtje welke in beeld komt als er geen aantal tickets ingevuld wordt
               Toast.makeText(getApplicationContext(), "Vul het aantal te boeken tickets in.", Toast.LENGTH_SHORT).show();

            }
                // worden er wel een aantal tickets ingevuld dan gaat de app verder
                // de gebruiker komt op de bestelpagina terecht
               else
            {
               Intent tmpIntent;
                 tmpIntent = new Intent(getApplicationContext(), BoekingsPagina.class);
                startActivity(tmpIntent);
            }

           }
       });


        // functionaliteiten van de annuleer button
        annuleerButton   = (Button) findViewById(R.id.annuleerbutton);
        annuleerButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

            // Bij druk op de annuleer knop gaat de app terug naar de main pagina

               Intent tmpIntent;
               tmpIntent = new Intent(getApplicationContext(), MainActivity.class);
               startActivity(tmpIntent);
           }
       });
   }



    public void setupUserinterface()
    {
        //setup de userinterface met de waarden uit preferences

        // Achtrgrond kleur van de actionbar instellen
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(MainActivity.colorBar));

        //achtergrond kleur
        LinearLayout layout = (LinearLayout) findViewById( R.id.mainlayout );
        layout.setBackgroundColor( MainActivity.colorBK );
    }
    }