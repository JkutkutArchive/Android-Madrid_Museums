package com.jkutkut.proyectob_pmdm_t2_jorge_re;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.jkutkut.proyectob_pmdm_t2_jorge_re.api.MuseumAPI;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.api.RetrofitClient;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.api.result.MuseumResult;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.dialog.FilterDialog;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.dialog.FilterDialogListener;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class QueryActivity extends AppCompatActivity implements FilterDialogListener {

    private TextView txtvFilter;

    private String filterDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        setTitle(R.string.query_activity_title);

        filterDistrict = null;

        txtvFilter = findViewById(R.id.txtvFilter);
        AppCompatButton btnFilterQuery = findViewById(R.id.btnFilterQuery);
        AppCompatButton btnQuery = findViewById(R.id.btnQuery);

        btnFilterQuery.setOnClickListener(v -> openFilterDialog());
        btnQuery.setOnClickListener(v -> search());

        updateFilterUI();
    }

    // ******* FILTER DIALOG *******
    private void openFilterDialog() {
        FilterDialog filterDialog = new FilterDialog();
        filterDialog.show(getSupportFragmentManager(), "filterDialog");
    }

    public void onDialogEnds(String district) {
        filterDistrict = district;
        updateFilterUI();
    }

    // ******* Search *******
    private void search() {
        Retrofit r = RetrofitClient.getClient(MuseumAPI.URL);
        MuseumAPI museumAPI = r.create(MuseumAPI.class);
        Call<MuseumResult> call = museumAPI.getMuseums(filterDistrict);

        call.enqueue(new Callback<MuseumResult>() {
            @Override
            public void onResponse(Call<MuseumResult> call, Response<MuseumResult> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(QueryActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                MuseumResult result = response.body();
                assert result != null;
                System.out.println("Oleeeeee");
                System.out.println(result.getGraph().get(0).getAddress());
                System.out.println(result.getGraph().size());

                // TODO
            }

            @Override
            public void onFailure(Call<MuseumResult> call, Throwable t) {
                Toast.makeText(QueryActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                throw new RuntimeException(t);
            }
        });
    }

    // ******* UI *******
    private void updateFilterUI() {
        if (filterDistrict == null)
            txtvFilter.setText("");
        else
            txtvFilter.setText(String.format(
                getString(R.string.district_filter_template),
                filterDistrict
            ));
    }

    // ******* MENU *******

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.query_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        // TODO
        if (item.getItemId() == R.id.mnList) {

        }
        else if (item.getItemId() == R.id.mnMap) {

        }
        return super.onOptionsItemSelected(item);
    }
}