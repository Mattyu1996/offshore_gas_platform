package it.univaq.offshoregasplatform;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class GasPlatformViewModel extends ViewModel {

    private MutableLiveData<ArrayList<GasPlatform>> platforms = new MutableLiveData<>();

    public void setPlatforms(ArrayList<GasPlatform> piattaforme) {
        this.platforms.setValue(piattaforme);
    }

    public MutableLiveData<ArrayList<GasPlatform>> getPlatforms() {
        return this.platforms;
    }
}
