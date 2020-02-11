package it.univaq.offshoregasplatform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Test
        super.onCreate(savedInstanceState);
        getGasPlatformsFromHttp();
        setContentView(R.layout.activity_main);

        ArrayList g = new ArrayList<GasPlatform>();
        GasPlatform gs = new GasPlatform();
        gs.setAltezza(180);
        gs.setAnnoCostruzione(2020);
        gs.setCapitaneriaDiPorto("capitaneria");
        gs.setCentraleCollegata("pes");
        gs.setCodice(5050);
        gs.setDenominazione("pasqualone");
        gs.setDimensioni("tanto");
        gs.setDistanzaCosta(10);
        gs.setFoglio("un tot");
        gs.setLatitudine(55.99);
        gs.setLongitudine(56.99);
        gs.setMinerale("quarzo");
        gs.setOperatore("giggi");
        gs.setPozziAllacciati(4);
        gs.setPozziInMonitoraggio(2);
        gs.setPozziInProduzione(6);
        gs.setPozziProduttiviNonEroganti(0);
        gs.setProfonditaFondale(8);
        gs.setSezioneUnimig("non so di che parli");
        gs.setStato("italia");
        gs.setTipo("forza pescara");
        gs.setTitoloMinerario("tosto");
        gs.setZona("colliwood");

        DataBase.getInstance(this).gasPlatform_dao().save(g);

    }

    private void getGasPlatformsFromHttp(){
        //Ottengo le Preferenze "preferenze"
        SharedPreferences pref = getSharedPreferences("preferenze", Context.MODE_PRIVATE);
        //Se è la prima volta che apro l'applicazione dopo averla installata effettuo la richiesta http
        if(pref.getBoolean("firstTime", true)){
            StringRequest richiesta = new StringRequest("http://www.bitesrl.it/clienti/univaq/corso/piattaforme.json",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // ANDATO TUTTO BENE
                            //System.out.println(response);
                            final ArrayList<GasPlatform> platforms = new ArrayList<>();
                            try {
                                JSONArray array = new JSONArray(response);
                                for(int i = 0; i < array.length(); i++ ){

                                    JSONObject json = array.optJSONObject(i);
                                    if(json == null) continue;

                                    GasPlatform plt = new GasPlatform();

                                    //Parsing del JSON
                                    plt.setDenominazione(json.optString("cdenominazione__"));
                                    plt.setCodice(json.optInt("ccodice"));
                                    plt.setStato(json.optString("cstato"));
                                    String[] str = json.optString("canno_costruzione").split("\\|");
                                    plt.setAnnoCostruzione(Integer.parseInt(str[0]));
                                    str = json.optString("ctipo").split("\\|");
                                    plt.setTipo(str[0]);
                                    plt.setMinerale(json.optString("cminerale"));
                                    str = json.optString("coperatore").split("\\|");
                                    plt.setOperatore(str[0]);
                                    if(json.getString("cnumero_pozzi_allacciati__")!= ""){
                                        plt.setPozziAllacciati(json.optInt("cnumero_pozzi_allacciati__"));
                                    }
                                    if(json.getString("cpozzi_produttivi_non_eroganti")!= ""){
                                        plt.setPozziProduttiviNonEroganti(json.optInt("cpozzi_produttivi_non_eroganti"));
                                    }
                                    if(json.getString("cpozzi_in_produzione")!= ""){
                                        plt.setPozziInProduzione(json.optInt("cpozzi_in_produzione"));
                                    }
                                    if(json.optString("cpozzi_in_monitoraggio")!= ""){
                                        plt.setPozziInMonitoraggio(json.optInt("cpozzi_in_monitoraggio"));
                                    }

                                    str = json.optString("ctitolo_minerario").split("\\|");
                                    plt.setTitoloMinerario(str[0]);
                                    if(json.optString("ccollegata_alla_centrale") != ""){
                                        str = json.optString("ccollegata_alla_centrale").split("\\|");
                                        plt.setCentraleCollegata(str[0]);
                                    }
                                    str = json.optString("czona").split("\\|");
                                    plt.setZona(str[0]);
                                    plt.setFoglio(json.optString("cfoglio"));
                                    str = json.optString("csezione_unmig").split("\\|");
                                    plt.setSezioneUnimig(str[0]);
                                    str = json.optString("ccapitaneria_di_porto").split("\\|");
                                    plt.setCapitaneriaDiPorto(str[0]);
                                    plt.setDistanzaCosta(json.optInt("cdistanza_costa___km_"));
                                    plt.setAltezza(json.optInt("caltezza_slm__m_"));
                                    plt.setProfonditaFondale(json.optInt("cprofondit__fondale__m_"));
                                    plt.setDimensioni(json.optString("cdimensioni"));
                                    plt.setLatitudine(json.getDouble("clatitudine__wgs84__"));
                                    plt.setLongitudine(json.getDouble("clongitudine__wgs_84__"));

                                    //Aggiungo la piattaforma alla lista
                                    platforms.add(plt);
                                }

                            } catch (JSONException e){
                                e.printStackTrace();
                            }

                            System.out.println("La richiesta http ha ottenuto: " + platforms.size()+" piattaforme");
                            //Inserire nel DB

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Richiesta Http fallita");
                    System.out.println(error.toString());
                }
            });
            //Aggiungo la richiesta alla coda di Volley
            MyVolley.getInstance(this).getQueue().add(richiesta);
            //Imposto il valore di firstTime a falso per non dover più effettuare la richiesta http per ottenere le piattaforme
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("firstTime", false);
            editor.apply();
        }
    }
}
