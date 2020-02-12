package it.univaq.offshoregasplatform;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class DatabaseService extends IntentService {
    public static final String FILTER_GET = "it.univaq.offshoregasplatform.GET";
    public static final String EXTRA_ACTION = "extra_action";
    public static final int ACTION_SAVE     = 0;
    public static final int ACTION_GET    = 1;

    public DatabaseService() {
        super("DatabaseService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int action = intent.getIntExtra(EXTRA_ACTION, -1);

        switch (action){
            case ACTION_SAVE:
                this.save(intent);
                break;
            case ACTION_GET:
                this.get();
                break;
        }
    }

    private void save(Intent intent){
        ArrayList<GasPlatform> platforms = intent.getParcelableArrayListExtra("platforms");
        System.out.println("Numero di piattaforme dall'intent: "+ platforms.size());
        DataBase.getInstance(getApplicationContext()).gasPlatform_dao().save(platforms);
    }

    private void get() {
        //Ottengo le Preferenze "preferenze"
        SharedPreferences pref = getSharedPreferences("preferenze", Context.MODE_PRIVATE);
        //Se è la prima volta che apro l'applicazione dopo averla installata effettuo la richiesta http
        if (pref.getBoolean("firstTime", true)) {
            StringRequest richiesta = new StringRequest("http://www.bitesrl.it/clienti/univaq/corso/piattaforme.json",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // ANDATO TUTTO BENE
                            //System.out.println(response);
                            final ArrayList<GasPlatform> platforms = new ArrayList<>();
                            try {
                                JSONArray array = new JSONArray(response);
                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject json = array.optJSONObject(i);
                                    if (json == null) continue;

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
                                    if (json.getString("cnumero_pozzi_allacciati__") != "") {
                                        plt.setPozziAllacciati(json.optInt("cnumero_pozzi_allacciati__"));
                                    }
                                    if (json.getString("cpozzi_produttivi_non_eroganti") != "") {
                                        plt.setPozziProduttiviNonEroganti(json.optInt("cpozzi_produttivi_non_eroganti"));
                                    }
                                    if (json.getString("cpozzi_in_produzione") != "") {
                                        plt.setPozziInProduzione(json.optInt("cpozzi_in_produzione"));
                                    }
                                    if (json.optString("cpozzi_in_monitoraggio") != "") {
                                        plt.setPozziInMonitoraggio(json.optInt("cpozzi_in_monitoraggio"));
                                    }

                                    str = json.optString("ctitolo_minerario").split("\\|");
                                    plt.setTitoloMinerario(str[0]);
                                    if (json.optString("ccollegata_alla_centrale") != "") {
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

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            System.out.println("La richiesta http ha ottenuto: " + platforms.size() + " piattaforme");
                            //Inserire nel DB
                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    List<Long> insert = DataBase.getInstance(getApplicationContext()).gasPlatform_dao().save(platforms);
                                    System.out.println("Inserite nel Database: " + insert.size() + " piattaforme");
                                }
                            });
                            t.start();
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
        else {
            //Altrimenti prendo la lista delle piattaforme dal Database e le restituisco con il LocalBroadcastManager
            List<GasPlatform> platformList = DataBase.getInstance(getApplicationContext()).gasPlatform_dao().getAll();
            ArrayList<GasPlatform> piattaforme = new ArrayList<GasPlatform>(platformList);
            Intent intent = new Intent(FILTER_GET);
            intent.putParcelableArrayListExtra("platforms", piattaforme);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }
}
