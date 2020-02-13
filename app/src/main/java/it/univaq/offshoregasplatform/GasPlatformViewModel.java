package it.univaq.offshoregasplatform;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class GasPlatformViewModel extends ViewModel {

    private MutableLiveData<ArrayList<GasPlatform>> platforms = new MutableLiveData<>();

    private MutableLiveData<ArrayList<GasPlatform>> nearPlatforms = new MutableLiveData<>();

    public MutableLiveData<GasPlatform> getCurrentPlatform() {
        return currentPlatform;
    }

    public void setCurrentPlatform(MutableLiveData<GasPlatform> currentPlatform) {
        this.currentPlatform = currentPlatform;
    }

    private MutableLiveData<GasPlatform> currentPlatform = new MutableLiveData<>();

    public void setNearPlatforms(ArrayList<GasPlatform> piattaformeVicine){
        this.nearPlatforms.setValue(piattaformeVicine);
    }

    public void setPlatforms(ArrayList<GasPlatform> piattaforme) {
        this.platforms.setValue(piattaforme);
    }

    public MutableLiveData<ArrayList<GasPlatform>> getPlatforms() {
        return this.platforms;
    }

    public MutableLiveData<ArrayList<GasPlatform>> getNearPlatforms(){
        return this.nearPlatforms;
    }
}
