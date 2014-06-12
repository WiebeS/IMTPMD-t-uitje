package hsl.imtpmd.Eindopdracht;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import hsl.imtpmd.optiescherm.R;

public class MainActivity extends ListActivity
{
    // Alle variabels

    // kleuren van de bar en de achtergrond
    public static int colorBar = Color.parseColor("#fc6610");
    public static int colorBK = Color.parseColor("#fc9b10");

    // string welke hard coded is, waarin het IP adres moet staan van de local host
    // LET OP: Als deze niet klopt dan crasht de app!
    public static String ip = "192.168.178.15";


    private ListView hoofdListView ;
    public ServerCommunicator serverCommunicator;

    public static String gekozenBestemming;

    private static MainActivity activity;
    // array met de soorten taarten
    static ArrayList<String> soortenBestemmingenLijst;
    static ArrayList<HashMap<String, String>> productData;


    // Int welke het type taart is, dus 0,1,2,3,4
    public static int soortBestemming;
    // Int welke de positie van de Klik op de lisview rij heeft
    public static int positieListviewKlik;

    public ArrayList<HashMap<String, String>> lijst;

    //private int gekozenSoortTaart;

    private SharedPreferences sharedPreferences;

    //deze methode wordt elke keer aangeroepen als de applicatie opstart
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        // userinterface inladen
        setupUserinterface();
        // de reis catagorie laden
        sharedPreferences = this.getSharedPreferences("Bestemming",soortBestemming);
        soortBestemming = sharedPreferences.getInt("Bestemming",soortBestemming);

        hoofdListView = getListView();

        soortenBestemmingenLijst = getSoortenBestemmingenLijst();
        // de lijst vullen met de juiste data
        lijst  = getProductData(soortBestemming);

      ListAdapter adapter = new SimpleAdapter(this, lijst , R.layout.listview_lijsten,
               new String[] { "hotelnaam" },
               new int[] { R.id.naam });

        // Koppel de adapter met de bestemmingen aan de listview
          hoofdListView.setAdapter(adapter);

        // ListView Item Click Listener opzetten
        setMyListListener();

    }

// methode welke de listeners opzet
    private void setMyListListener(){
        getListView().setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id){
                Intent tmpIntent;
                tmpIntent = new Intent(getApplicationContext(), ProductPagina.class);
                startActivity(tmpIntent);

                // het zetten van de positieListviewKlik int zodat de product pagina deze kan gebruiken
                positieListviewKlik = position;

                // de gekozen bestemming wordt gezocht, dit gebeurd aan de hand van de geklikte listview ROW en
                // natuurlijk per bestemming
                gekozenBestemming = lijst.get(positieListviewKlik).get("hotelnaam");
            }
        });
    }

    // Methode welke de user interface opzet
    public void setupUserinterface()
    {
        //setup de userinterface met de waardes uit preferences
        // Achtergrond kleur van de actionbar instellen
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(colorBar));

        //achtergrond kleur
        LinearLayout layout = (LinearLayout) findViewById( R.id.mainlayout );
        layout.setBackgroundColor( colorBK );
    }

    // methode welke de soorten hotels in de listview gaat zetten, hij krijgt als parameter de
    // gekozen bestemming mee, welke bepaalt wordt door de knoppen
    // Ook zorgt deze methode ervoor dat het gekozen soort hotels wordt opgeslagen voor hergebruik.
    public void soortBestemmingInladen(int soortBestemming){

        //de gekozen plaats opslaan sharedpreferences
        Editor editor = sharedPreferences.edit();
        this.soortBestemming = soortBestemming;

        editor.putInt("Bestemming",soortBestemming);
        editor.commit();

        lijst = getProductData(soortBestemming);

        // De adapter vullen met de lijst en aangeven met welke onderdelen van het JSON object
        // De String namen moeten overeen komen met de stringnamen van het jsonobject wat ontvangen is
        ListAdapter adapter = new SimpleAdapter(this, lijst , R.layout.listview_lijsten,
                new String[] { "hotelnaam", },
                new int[] { R.id.naam });

        hoofdListView.setAdapter(adapter);

    }


// knop van Parijs, deze zet het soort bestemming op Parijs
    public void parijs(View view) throws ExecutionException, InterruptedException {

        // zet de INT soortaart goed zodat deze opgeslagen kan worden om, zodat de keuze
       this.soortBestemming = 0;
       soortBestemmingInladen(0);
    }

// Knop van Reykjavik
    public void reykjavik(View view){

        soortBestemming = 1;
        soortBestemmingInladen(1);
        }

    // knop van de Berlijn

    public void berlijn(View view){

        soortBestemming = 2;
        soortBestemmingInladen(2);

    }

    // knop van Amsterdam

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

        String reactie = null;
        try
        {
            try
            {
                //servercommunicator proberen te verbinden met de server
                reactie = new ServerCommunicator( activity, ip, 4444, categorieJObject.toString()).execute().get();
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


        JSONArray soortenBestemmingLijstJA = null;
        try
        {
            soortenBestemmingLijstJA = new JSONArray(reactie);
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
            //Het verzenden van het object
            productdataJObject.put("hotellijst", soortenBestemmingenLijst.get(soortBestemming) );

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        String reactie = null;
        try
        {
            try
            {
                //Proberen verbinding te maken
                reactie = new ServerCommunicator( activity, ip, 4444, productdataJObject.toString()).execute().get();
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


        JSONArray productJArray = null;
        try
        {
            productJArray = new JSONArray(reactie);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        JSONObject productobject = null;

        //Het aanmaken van de productenlijst
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
                productData.add(producten);
                productenlijst.add(producten);

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        return productenlijst;
    }


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

        String reactie = null;
        try
        {
            try
            {
                //servercommunicator proberen te verbinden met de server
                reactie = new ServerCommunicator( activity, ip, 4444, categorieJObject.toString()).execute().get();
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


        JSONArray soortenBestemmingLijstJA = null;
        try
        {
            soortenBestemmingLijstJA = new JSONArray(reactie);
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

        String reactie = null;
        try
        {
            try
            {
                //Proberen verbinding te maken
                reactie = new ServerCommunicator( activity, ip, 4444, productinfoJObject.toString()).execute().get();
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


        JSONArray productinfoJArray = null;
        try
        {
            productinfoJArray = new JSONArray(reactie);
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
        // Geef de informatie terug

        return hotelSterren;
    }

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

        String reactie = null;
        try
        {
            try
            {
                //server communicator voor de verbinding
                reactie = new ServerCommunicator( activity, ip, 4444, productnaamJObject.toString()).execute().get();
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


        JSONArray productnaamJSN = null;
        try
        {
            productnaamJSN = new JSONArray(reactie);
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

