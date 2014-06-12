package hsl.imtpmd.Eindopdracht;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hsl.imtpmd.optiescherm.R;

/**
 * Created by Wiebe on 5/9/14.
 */
public class BestelPagina extends Activity{

    Button bestelButtonBestelPagina;
    Button annuleerButtonBestelPagina;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bestelpagina);

        buttonListeners();

        TextView productNaam = (TextView) findViewById(R.id.PruductNaamBestel);

        productNaam.setText(MainActivity.getProductNaam(MainActivity.positieListviewKlik));
    }

    // methode welke de buttons,listeners en gelijk methodes geeft. Dit gebeurd in een aparte
    // methode om conflicten met threads te voorkomen.
    public void buttonListeners(){
        bestelButtonBestelPagina     = (Button) findViewById(R.id.BestelBestel);


        // functionaliteiten van de bestelbutton
        bestelButtonBestelPagina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               bestellingVerzenden();

            }
        });

        annuleerButtonBestelPagina     = (Button) findViewById(R.id.AnnuleerBestel);


        // functionaliteiten van de bestelbutton
        annuleerButtonBestelPagina.setOnClickListener(new View.OnClickListener() {
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

    private void bestellingVerzenden(){



    }

}
