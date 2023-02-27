package com.jkutkut.proyectob_pmdm_t2_jorge_re;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.jkutkut.proyectob_pmdm_t2_jorge_re.api.result.Museum;

public class MuseumDetailActivity extends AppCompatActivity {

    public static final String ARG = "museum";

    private Museum museum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum_detail);

        museum = (Museum) getIntent().getSerializableExtra(ARG);

        TextView txtvName = findViewById(R.id.txtvName);
        TextView txtvAddress = findViewById(R.id.txtvAddress);
        TextView txtvDescription = findViewById(R.id.txtvDescription);
        TextView txtvSchedule = findViewById(R.id.txtvSchedule);

        txtvName.setText(museum.getTitle());
        txtvAddress.setText(String.format(
            getString(R.string.address_template),
            museum.getAddress().getStreetAddress(),
            museum.getAddress().getPostalCode(),
            museum.getAddress().getLocality()
        ));
        txtvDescription.setText(
            museum.getOrganization().getOrganizationDesc()
        );
        txtvSchedule.setText(
            museum.getOrganization().getSchedule()
        );
    }
}