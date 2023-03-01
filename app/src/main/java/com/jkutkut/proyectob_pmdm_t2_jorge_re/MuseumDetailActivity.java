package com.jkutkut.proyectob_pmdm_t2_jorge_re;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jkutkut.proyectob_pmdm_t2_jorge_re.api.MuseumAPI;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.api.RetrofitClient;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.api.result.Museum;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.api.result.MuseumResultAPI;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MuseumDetailActivity extends AppCompatActivity {

    public static final String ARG = "museum";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum_detail);

        Museum museum = (Museum) getIntent().getSerializableExtra(ARG);

        String[] id_split = museum.getAtId().split("/");
        String id = id_split[id_split.length - 1];

        setTitle("");

//        loadData(museum);

        getData(
            id,
            new Callback<MuseumResultAPI>() {
                @Override
                public void onResponse(@NonNull Call<MuseumResultAPI> call, @NonNull Response<MuseumResultAPI> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getBaseContext(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    MuseumResultAPI result = response.body();
                    assert result != null;
                    loadData(result.getMuseums().get(0));
                }

                @Override
                public void onFailure(@NonNull Call<MuseumResultAPI> call, @NonNull Throwable t) {
                    Toast.makeText(getBaseContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            }
        );
    }

    private void getData(String museum_id, Callback<MuseumResultAPI> callback) {
        Retrofit r = RetrofitClient.getClient(MuseumAPI.URL);
        MuseumAPI museumAPI = r.create(MuseumAPI.class);
        Call<MuseumResultAPI> call = museumAPI.getMuseum(museum_id);
        call.enqueue(callback);
    }

    private void loadData(Museum museum) {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView txtvAddress = findViewById(R.id.txtvAddress);
        TextView txtvDistrict = findViewById(R.id.txtvDistrict);
        TextView txtvDescription = findViewById(R.id.txtvDescription);
        TextView txtvScheduleTitle = findViewById(R.id.txtvScheduleTitle);
        TextView txtvSchedule = findViewById(R.id.txtvSchedule);

        progressBar.setVisibility(ProgressBar.GONE);

        setTitle(museum.getTitle());
        txtvAddress.setText(String.format(
                getString(R.string.address_template),
                museum.getAddress().getStreetAddress(),
                museum.getAddress().getPostalCode(),
                museum.getAddress().getLocality()
        ));
        String[] district = museum.getAddress().getDistrict().getId().split("/");
        txtvDistrict.setText(String.format(
                getString(R.string.district_template),
                district[district.length - 1]
        ));
        txtvDescription.setText(
            formatDescription(
                museum.getOrganization().getOrganizationDesc()
            )
        );
        String schedule = formatSchedule(museum.getOrganization().getSchedule());
        if (!schedule.isEmpty()) {
            txtvScheduleTitle.setText(getString(R.string.schedule_label));
            txtvSchedule.setText(schedule);
        }
    }

    private String formatRawAPIData(String data) {
        return data.trim().replaceAll("[ ]{2,}(\\w+?( \\w+?){0,2} ?:) *", "\n\n- $1\n")
                .replaceAll(",\n", ".\n");
    }

    private String formatDescription(String description) {
        String data = formatRawAPIData(description).trim();
        if (!data.isEmpty()) {
            data = "- " + data;
        }
        return data;
    }

    private String formatSchedule(String schedule) {
        return formatRawAPIData(schedule)
            .replaceAll("\\. +", ".\n\n- ")
            .replaceAll("^", "- ");
    }
}