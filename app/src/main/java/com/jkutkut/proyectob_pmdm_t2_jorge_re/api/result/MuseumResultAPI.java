package com.jkutkut.proyectob_pmdm_t2_jorge_re.api.result;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MuseumResultAPI implements Serializable {

    @SerializedName("@context")
    @Expose
    private Context context;
    @SerializedName("@graph")
    @Expose
    private List<Museum> museums;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Museum> getMuseums() {
        return museums;
    }

    public void setMuseums(List<Museum> museums) {
        this.museums = museums;
    }

}