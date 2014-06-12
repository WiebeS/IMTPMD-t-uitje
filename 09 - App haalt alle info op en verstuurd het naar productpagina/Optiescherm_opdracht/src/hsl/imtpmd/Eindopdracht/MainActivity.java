package hsl.imtpmd.Eindopdracht;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import hsl.imtpmd.optiescherm.R;

public class MainActivity extends ListActivity
{


    private int aantalKeerGeopend;
    // string welke hard coded is, waarin het IP adres moet staan van de local host
    // Als deze niet klopt dan crasht de app gelijk!
    public static String ip = "145.101.89.108";
    private static String naam;

   // private SimpleAdapter sAdapter;
    private int kleur;
    private ListView hoofdListView ;
    public ServerCommunicator serverCommunicator;

    public SharedPreferences preferences;

    public static String gekozenBestemming;
    public static String gekozenPlaats;


    private static MainActivity activity;
    // array met de soorten taarten
    static ArrayList<String> soortenBestemmingenLijst;
    // Lijst van het gekozen type taart
    static ArrayList<String> taartLijst;

    static ArrayList<HashMap<String, String>> productData;


    // Int welke het type taart is, dus 0,1,2,3,4
    public static int soortBestemming;
    // Int welke de positie van de Klik op de lisview rij heeft
    public static int positieListviewKlik;

    public ArrayList<HashMap<String, String>> lijst;

    //private int gekozenSoortTaart;

    public SharedPreferences sharedPreferences;

    //deze methode wordt elke keer aangeroepen als de applicatie opstart
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // de taart catagorie laden
        sharedPreferences = this.getSharedPreferences("Bestemming",soortBestemming);
        soortBestemming = sharedPreferences.getInt("Bestemming",soortBestemming);

     //   this.hotelSterren = getProductSterren("Hotel Odinsve");

        // Get ListView object from xml
        hoofdListView = getListView();

     //   ladenEnSaven();
   //    ladenGekozenSoortTaart();

//
         //  String test =  getProductSterren();
   //        Log.d("LAADEN", "INFO:"+ hotelSterren);


        soortenBestemmingenLijst = getSoortenBestemmingenLijst();

    //   ArrayList<HashMap<String, String>> lijst = getProductData(soortBestemming);

       lijst  = getProductData(soortBestemming);


        //   sAdapter = new SimpleAdapter(this, lijst, R.layout.activity_main, new String[] {"naam", "prijs"});

        //   hoofdListView.setAdapter(adapter);
        // ITEM NAAM EN PRIJS VARIABLENE NAMEN VERANDEREN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1

      ListAdapter adapter = new SimpleAdapter(this, lijst , R.layout.my_listview_item,
               new String[] { "hotelnaam", "prijs" },
               new int[] { R.id.itemnaam, R.id.prijs });

        // Koppel de adapter met de taaren aan de listview
          hoofdListView.setAdapter(adapter);

        // ListView Item Click Listener

        setMyListListener();

        /// HIER WAS JEEEEEEEEEEEEEEEEEEEEE BEZUIG

        //stel alle UI interfaces in met opgeslagen preferences (mochten die er zijn)

        //stel de userinterface op met waarden uit de preferences
    //    setupUserinterface();

        // testen of de productsterren info opgehaald kan worden


    }


    private void setMyListListener(){
        getListView().setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id){
            /*same fake code as above for calling another activity using an intent:*/
                Intent tmpIntent;
                tmpIntent = new Intent(getApplicationContext(), ProductPagina.class);
                // tmpIntent.putExtra(SHOWITEMINTENT_EXTRA_FETCHROWID, position);
                startActivity(tmpIntent);

               Log.d("","JAAAAAAAAAAAAAAAAKLIKPO RIJJJJJJJJJJ"+position);
                // het zetten van de positieListviewKlik int zodat de product pagina deze kan gebruiken
                positieListviewKlik = position;

                // de gekozen bestemming wordt gezocht, dit gebeurd aan de hand van de geklikte listview ROW en
                // natuurlijk per bestemming
                gekozenBestemming = lijst.get(positieListviewKlik).get("hotelnaam");


            }
        });
    }

    //we maken een aparte methode voor het invullen van de hoofdscherm interface
    //omdat we deze methode op twee locaties willen aanroepen: in de onCreate en in de sluitApp methoden
    public void setupUserinterface()
    {
        //setup de userinterface met de waarden uit preferences
        //naam
        TextView naamTekst = (TextView) findViewById( R.id.welkomtekst );
        naamTekst.setText( "Welkom terug " + this.naam );

        //aantal keer ingelogd
        TextView ingelogdTekst = (TextView) findViewById( R.id.geopendtekst );
        ingelogdTekst.setText( "U heeft de vorige keer categorie  " + this.aantalKeerGeopend + "  geopend." );

        //achtergrond kleur
        LinearLayout layout = (LinearLayout) findViewById( R.id.mainlayout );
        layout.setBackgroundColor( this.kleur );
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    //deze methoden worden door de knoppen aangeroepen en zijn gekoppeld in de XML via het onClick attribuut
    public void openOpties( View view )
    {
        //toon het product_scherm
        this.setContentView( R.layout.product_scherm);

        //stel de opties in zoals opgehaald uit de preferences
        EditText naamEditTekst = (EditText) this.findViewById( R.id.naamedittext );
        naamEditTekst.setText( this.naam );

        //red radioknop
        RadioButton radioRood = (RadioButton) this.findViewById( R.id.radiorood );

        //green radioknop
        RadioButton radioGroen = (RadioButton) this.findViewById( R.id.radiogroen );

        //blauw radioknop
        RadioButton radioBlauw = (RadioButton) this.findViewById( R.id.radioblauw );

    }


    public void sluitApp( View view )
    {
        this.finish();
    }

    public void naarHoofdscherm( View view )
    {
        //haal gegevens naam en kleur op uit het product_scherm

        EditText naamEditTekst = (EditText) this.findViewById( R.id.naamedittext );
        this.naam = naamEditTekst.getText().toString();

      //  this.kleur = Color.RED;
        RadioButton radioRood = (RadioButton) this.findViewById( R.id.radiorood );

        if( radioRood.isChecked() )
        {
            this.kleur = Color.RED;
        }

        RadioButton radioGroen = (RadioButton) this.findViewById( R.id.radiogroen );
        if( radioGroen.isChecked())
        {
            this.kleur = Color.GREEN;
        }

        RadioButton radioBlauw = (RadioButton) this.findViewById( R.id.radioblauw );
        if(radioBlauw.isChecked())
        {
            this.kleur = Color.BLUE;
        }


        //sla gegevens op in de preferences
        SharedPreferences settings = this.getPreferences(0);
        Editor editor = settings.edit();
        editor.putInt("aantalOpenersPut",aantalKeerGeopend);
        editor.putString(naam, "Naam");
        editor.putInt("KleurINT", kleur);
        editor.commit();


        //stel userinterface opnieuw in met nieuwe waarden
        setContentView( R.layout.activity_main );
        setupUserinterface();
    }

    public void resetAantalKeerGeopend( View view )
    {
        //reset de aantalKeerIngelogd variable
        this.aantalKeerGeopend = 0;
    }

    // methode welke de soorten taart in de listview gaat zetten, hij krijgt als parameter het
    // soort taart mee, welke bepaalt wordt door de taartknoppen
    // Ook zorgt deze methode ervoor dat het gekozen soort taart wordt opgeslagen voor hergebruik.
    public void soortBestemmingInladen(int soortBestemming){

        //de gekozen taartsoort opslaan sharedpreferences
        Editor editor = sharedPreferences.edit();
        this.soortBestemming = soortBestemming;

        editor.putInt("Bestemming",soortBestemming);
        editor.commit();

        Log.d("SAAVEN", "gekozen Bestemming INT:" + soortBestemming);

        lijst = getProductData(soortBestemming);
 

        // getProductData(3);

        //   sAdapter = new SimpleAdapter(this, lijst, R.layout.activity_main, new String[] {"naam", "prijs"});

        //   hoofdListView.setAdapter(adapter);

        // De adapter vullen met de lijst en aangeven met welke onderdelen van het JSON object
        // De String namen moeten overeen komen met de stringnamen van het jsonobject wat ontvangen is
        ListAdapter adapter = new SimpleAdapter(this, lijst , R.layout.my_listview_item,
                new String[] { "hotelnaam", "hotelprijs" },
                new int[] { R.id.itemnaam, R.id.prijs });

        hoofdListView.setAdapter(adapter);



       // this.productNaam = String.valueOf(lijst.get(0));

   //     String test =

      //  Log.d("STRING TAART",test);

       // Log.d("test",taartLijst.get(0));
    }


// knop van Parijs, deze zet het soort bestemming op Parijs
    public void parijs(View view) throws ExecutionException, InterruptedException {


     //   Log.d("TAART", String.valueOf(soortenTaartLijst));

     //  soortenTaartLijst = getSoortenTaartLijst();



      //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
       //         android.R.layout.simple_list_item_1, android.R.id.text1,soortenTaartLijst );


        // Assign adapter to ListView
     //   hoofdListView.setAdapter(adapter);


        Log.d("TAART", "CLICK OP PARIJS");

        // zet de INT soortaart goed zodat deze opgeslagen kan worden om, zodat de keuze
       this.soortBestemming = 0;
       soortBestemmingInladen(0);


    }

    // knop van de cakes, deze zet het soort taart op cakes

    public void reykjavik(View view){

        ArrayList<HashMap<String, String>> lijst = getProductData(3);

       // getProductData(3);

     //   sAdapter = new SimpleAdapter(this, lijst, R.layout.activity_main, new String[] {"naam", "prijs"});

         //   hoofdListView.setAdapter(adapter);

 /*       ListAdapter adapter = new SimpleAdapter(this, lijst , R.layout.my_listview_item,
                new String[] { "naam", "prijs" },
                new int[] { R.id.itemnaam, R.id.prijs });

        hoofdListView.setAdapter(adapter);*/
        soortBestemming = 1;
        soortBestemmingInladen(1);
        }

    // knop van de bruidstaarten, deze zet het soort taart op bruidstaarten

    public void berlijn(View view){

        // Defined Array values to show in ListView
        soortBestemming = 2;
        soortBestemmingInladen(2);

    }

    // knop van de verjaardagstaarten, deze zet het soort taart op verjaardagstaarten

    public void amsterdam(View view){

        soortBestemming = 3;
        soortBestemmingInladen(3);



    }




    //methode om de lijst met categorieën van de server op te halen
    static ArrayList<String> getSoortenBestemmingenLijst()
    {
        soortenBestemmingenLijst = new ArrayList<String>();

        //aanmaken van een nieuw jsonobject
        JSONObject categorieJObject = new JSONObject();
        try
        {
            //verzenden van het jsonobject
            categorieJObject.put("bestemminglijst", "" );
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        String response = null;
        try
        {
            try
            {
                //servercommunicator proberen te verbinden met de server
                response = new ServerCommunicator( activity, ip, 4444, categorieJObject.toString()).execute().get();
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
            }
        }
        catch (InterruptedException e1)
        {
            e1.printStackTrace();
        }
        //om errors te voorkomen
        String jsonFix = response.replace("null", "");

        JSONArray soortenBestemmingLijstJA = null;
        try
        {
            soortenBestemmingLijstJA = new JSONArray(jsonFix);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        JSONObject jObject = null;
        String value = null;
        soortenBestemmingenLijst = new ArrayList<String>();

        for (int i = 0 ; i < soortenBestemmingLijstJA.length(); i++)
        {
            try
            {
                jObject = (JSONObject) soortenBestemmingLijstJA.getJSONObject(i);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            try
            {
                value = jObject.getString("bestemminglijst");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            soortenBestemmingenLijst.add(value);
        }
        //geef de lijst met bestemmingen terug  
        return soortenBestemmingenLijst;
    }


    //methode om de producten van categorieën van de server op te halen
    public static ArrayList<HashMap<String, String>> getProductData(int soortBestemming)
    {
        // hashmap om de producten  op te slaan
        productData = new ArrayList<HashMap<String, String>>();

        JSONObject productdataJObject = new JSONObject();
        try
        {
            //Zenden van het JSON OBJECT

            productdataJObject.put("hotellijst", soortenBestemmingenLijst.get(soortBestemming) );

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        String response = null;
        try
        {
            try
            {
                //Proberen verbinding te maken
                response = new ServerCommunicator( activity, ip, 4444, productdataJObject.toString()).execute().get();
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
            }
        }
        catch (InterruptedException e1)
        {
            e1.printStackTrace();
        };

        String jsonFix = response.replace("null", "");

        JSONArray productJArray = null;
        try
        {
            productJArray = new JSONArray(jsonFix);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        JSONObject productobject = null;

        //aanmaken van de productenlijst
        ArrayList<HashMap<String, String>> productenlijst = new ArrayList<HashMap<String, String>>();

        for (int i = 0 ; i < productJArray.length(); i++)
        {
            try
            {
                productobject = productJArray.getJSONObject(i);

                //Put de producten in de productenlijst.
                HashMap<String, String> producten = new HashMap<String, String>();
                producten = new HashMap<String,String>();
                producten.put("hotelnaam", productobject.getString("hotelnaam"));

             //   producten.put("prijs", prijs);
                productData.add(producten);
                productenlijst.add(producten);

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        Log.d("GETPRODUCT",""+productenlijst);
        return productenlijst;
    }


    // STRING PRODUCTNAAM ZIEN TE MAKEN!! MOET KOMEN UIT DE POSITIE VAN DE LISTVIEW

    //methode om de lijst met categorieën van de server op te halen
    static ArrayList<String> getInfoLijst()
    {
        ArrayList bestemmingInfoLijst = new ArrayList<String>();

        //aanmaken van een nieuw jsonobject
        JSONObject categorieJObject = new JSONObject();
        try
        {
            //verzenden van het jsonobject
            categorieJObject.put("hotelinfo", gekozenBestemming );
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        String response = null;
        try
        {
            try
            {
                //servercommunicator proberen te verbinden met de server
                response = new ServerCommunicator( activity, ip, 4444, categorieJObject.toString()).execute().get();
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
            }
        }
        catch (InterruptedException e1)
        {
            e1.printStackTrace();
        }
        //om errors te voorkomen
        String jsonFix = response.replace("null", "");

        JSONArray soortenBestemmingLijstJA = null;
        try
        {
            soortenBestemmingLijstJA = new JSONArray(jsonFix);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        JSONObject jObject = null;
        String info = null;
        String hotelprijs = null;
        String sterren = null;


        for (int i = 0 ; i < soortenBestemmingLijstJA.length(); i++)
        {
            try
            {
                jObject = (JSONObject) soortenBestemmingLijstJA.getJSONObject(i);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            try
            {

                hotelprijs = jObject.getString("hotelprijs");
                info = jObject.getString("hotelinfo");
                sterren = jObject.getString("hotelsterren");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            bestemmingInfoLijst.add(hotelprijs);

            bestemmingInfoLijst.add(info);

            bestemmingInfoLijst.add(sterren);

        }
        //geef de lijst met bestemmingen terug
        return bestemmingInfoLijst;
    }

    //methode om de productinformatie op te halen voor op de productpagina
    public static String getProductInfo(  )
    {
        JSONObject productinfoJObject = new JSONObject();
        try
        {
            // Proberen te verzenden van de Json string
            productinfoJObject.put("hotelinfo",gekozenBestemming );

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        String response = null;
        try
        {
            try
            {
                //Proberen verbinding te maken
                response = new ServerCommunicator( activity, ip, 4444, productinfoJObject.toString()).execute().get();
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
            }
        }
        catch (InterruptedException  e)
        {
            e.printStackTrace();
        }

        String jsonFix = response.replace("null", "");

        JSONArray productinfoJArray = null;
        try
        {
            productinfoJArray = new JSONArray(jsonFix);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        JSONObject jObject = null;
        try
        {
            jObject = productinfoJArray.getJSONObject(0);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        String hotelSterren = null;

        try
        {

            hotelSterren = jObject.getString("hotelsterren");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        // return de productinformatie

        Log.d("STERREN",""+ hotelSterren);
        return hotelSterren;
    }

    // DEZE V?????????????????????????????????????!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public static String getProductNaam( int pos )
    {
        JSONObject productnaamJObject = new JSONObject();
        try
        {
            // Proberen te verzenden van de Json string
            productnaamJObject.put("productenlijst",soortenBestemmingenLijst.get(soortBestemming) );
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        String response = null;
        try
        {
            try
            {
                //server communicator voor de verbinding
                response = new ServerCommunicator( activity, ip, 4444, productnaamJObject.toString()).execute().get();
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        String jsonFix = response.replace("null", "");

        JSONArray productnaamJSN = null;
        try
        {
            productnaamJSN = new JSONArray(jsonFix);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        JSONObject jSNOBJECT = null;
        ArrayList<String> productNamenLijst= new ArrayList<String>();

        for (int i = 0 ; i < productnaamJSN.length(); i++)
        {
            try
            {
                jSNOBJECT = productnaamJSN.getJSONObject(i);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            try
            {
                productNamenLijst.add(jSNOBJECT.getString("naam"));
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        String productNaam = productNamenLijst.get(pos);
        return productNaam;
    }

    public static String getGekozenBestemming()
    {
        return gekozenBestemming;
    }


}

