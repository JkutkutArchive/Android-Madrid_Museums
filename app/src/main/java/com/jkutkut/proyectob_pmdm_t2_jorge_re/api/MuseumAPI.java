package com.jkutkut.proyectob_pmdm_t2_jorge_re.api;

import com.jkutkut.proyectob_pmdm_t2_jorge_re.api.result.MuseumResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MuseumAPI {
    String URL = "https://datos.madrid.es/egob/catalogo/";

    @GET("201132-0-museos.json")
    Call<MuseumResult> getMuseums(
        @Query("distrito_nombre") String district
    );
}
