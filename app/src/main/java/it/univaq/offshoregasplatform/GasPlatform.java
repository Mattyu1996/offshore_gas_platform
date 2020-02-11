package it.univaq.offshoregasplatform;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trivelle")
public class GasPlatform implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String denominazione, stato, tipo, minerale, operatore, titoloMinerario, centraleCollegata, zona, foglio,
            sezioneUnimig, capitaneriaDiPorto, dimensioni;
    private int codice, annoCostruzione, pozziAllacciati, pozziProduttiviNonEroganti,
            pozziInProduzione, pozziInMonitoraggio, distanzaCosta, altezza, profonditaFondale;
    private Double latitudine, longitudine;


    public GasPlatform(){}

    public GasPlatform(Parcel in){
        this.denominazione = in.readString();
        this. stato = in.readString();
        this.tipo = in.readString();
        this.minerale = in.readString();
        this.operatore = in.readString();
        this.titoloMinerario = in.readString();
        this.centraleCollegata = in.readString();
        this.zona = in.readString();
        this.foglio = in.readString();
        this.sezioneUnimig = in.readString();
        this.capitaneriaDiPorto = in.readString();
        this.capitaneriaDiPorto = in.readString();
        this.codice = in.readInt();
        this.annoCostruzione = in.readInt();
        this.pozziAllacciati = in.readInt();
        this.pozziProduttiviNonEroganti = in.readInt();
        this.pozziInProduzione = in.readInt();
        this.pozziInMonitoraggio = in.readInt();
        this.distanzaCosta = in.readInt();
        this.distanzaCosta = in.readInt();
        this.profonditaFondale = in.readInt();
        this.latitudine= in.readDouble();
        this.longitudine = in.readDouble();
    }

    public static Parcelable.Creator<GasPlatform> CREATOR = new Creator<GasPlatform>() {
        @Override
        public GasPlatform createFromParcel(Parcel source) {
            return new GasPlatform(source);
        }

        @Override
        public GasPlatform[] newArray(int size) {
            return new GasPlatform[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMinerale() {
        return minerale;
    }

    public void setMinerale(String minerale) {
        this.minerale = minerale;
    }

    public String getOperatore() {
        return operatore;
    }

    public void setOperatore(String operatore) {
        this.operatore = operatore;
    }

    public String getTitoloMinerario() {
        return titoloMinerario;
    }

    public void setTitoloMinerario(String titoloMinerario) {
        this.titoloMinerario = titoloMinerario;
    }

    public String getCentraleCollegata() {
        return centraleCollegata;
    }

    public void setCentraleCollegata(String centraleCollegata) {
        this.centraleCollegata = centraleCollegata;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getFoglio() {
        return foglio;
    }

    public void setFoglio(String foglio) {
        this.foglio = foglio;
    }

    public String getSezioneUnimig() {
        return sezioneUnimig;
    }

    public void setSezioneUnimig(String sezioneUnimig) {
        this.sezioneUnimig = sezioneUnimig;
    }

    public String getCapitaneriaDiPorto() {
        return capitaneriaDiPorto;
    }

    public void setCapitaneriaDiPorto(String capitaneriaDiPorto) {
        this.capitaneriaDiPorto = capitaneriaDiPorto;
    }

    public String getDimensioni() {
        return dimensioni;
    }

    public void setDimensioni(String dimensioni) {
        this.dimensioni = dimensioni;
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public int getAnnoCostruzione() {
        return annoCostruzione;
    }

    public void setAnnoCostruzione(int annoCostruzione) {
        this.annoCostruzione = annoCostruzione;
    }

    public int getPozziAllacciati() {
        return pozziAllacciati;
    }

    public void setPozziAllacciati(int pozziAllacciati) {
        this.pozziAllacciati = pozziAllacciati;
    }

    public int getPozziProduttiviNonEroganti() {
        return pozziProduttiviNonEroganti;
    }

    public void setPozziProduttiviNonEroganti(int pozziProduttiviNonEroganti) {
        this.pozziProduttiviNonEroganti = pozziProduttiviNonEroganti;
    }

    public int getPozziInProduzione() {
        return pozziInProduzione;
    }

    public void setPozziInProduzione(int pozziInProduzione) {
        this.pozziInProduzione = pozziInProduzione;
    }

    public int getPozziInMonitoraggio() {
        return pozziInMonitoraggio;
    }

    public void setPozziInMonitoraggio(int pozziInMonitoraggio) {
        this.pozziInMonitoraggio = pozziInMonitoraggio;
    }

    public int getDistanzaCosta() {
        return distanzaCosta;
    }

    public void setDistanzaCosta(int distanzaCosta) {
        this.distanzaCosta = distanzaCosta;
    }

    public int getAltezza() {
        return altezza;
    }

    public void setAltezza(int altezza) {
        this.altezza = altezza;
    }

    public int getProfonditaFondale() {
        return profonditaFondale;
    }

    public void setProfonditaFondale(int profonditaFondale) {
        this.profonditaFondale = profonditaFondale;
    }

    public Double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(Double latitudine) {
        this.latitudine = latitudine;
    }

    public Double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(Double longitudine) {
        this.longitudine = longitudine;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(denominazione);
        dest.writeString(stato);
        dest.writeString(tipo);
        dest.writeString(minerale);
        dest.writeString(operatore);
        dest.writeString(titoloMinerario);
        dest.writeString(centraleCollegata);
        dest.writeString(zona);
        dest.writeString(foglio);
        dest.writeString(sezioneUnimig);
        dest.writeString(capitaneriaDiPorto);
        dest.writeString(dimensioni);
        dest.writeInt(codice);
        dest.writeInt(annoCostruzione);
        dest.writeInt(pozziAllacciati);
        dest.writeInt(pozziProduttiviNonEroganti);
        dest.writeInt(pozziInProduzione);
        dest.writeInt(pozziInMonitoraggio);
        dest.writeInt(distanzaCosta);
        dest.writeInt(altezza);
        dest.writeInt(profonditaFondale);
        dest.writeDouble(latitudine);
        dest.writeDouble(longitudine);
    }
}
