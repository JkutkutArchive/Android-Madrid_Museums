package com.jkutkut.proyectob_pmdm_t2_jorge_re;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.jkutkut.proyectob_pmdm_t2_jorge_re.api.MuseumAPI;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.api.RetrofitClient;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.api.result.MuseumResultAPI;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.dialog.FilterDialog;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.dialog.FilterDialogListener;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.list.ListViewFragment;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.map.MapViewFragment;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class QueryActivity extends AppCompatActivity implements FilterDialogListener {

    private static final int LIST_MODE = 0;
    private static final int MAP_MODE = 1;

    private TextView txtvFilter;
    AppCompatButton btnQuery;

    private String filterDistrict;
    private int mode;
    private MuseumResultAPI result;
    private Fragment currentResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        setTitle(R.string.query_activity_title);

        filterDistrict = null;
        mode = LIST_MODE;

        txtvFilter = findViewById(R.id.txtvFilter);
        AppCompatButton btnFilterQuery = findViewById(R.id.btnFilterQuery);
        btnQuery = findViewById(R.id.btnQuery);

        btnFilterQuery.setOnClickListener(v -> openFilterDialog());
        btnQuery.setOnClickListener(v -> search());

        updateFilterUI();
    }

    // ******* FILTER DIALOG *******
    private void openFilterDialog() {
        FilterDialog filterDialog = new FilterDialog();
        // TODO send current filter
        filterDialog.show(getSupportFragmentManager(), "filterDialog");
    }

    public void onDialogEnds(String district) {
        // current null, new null -> do nothing
        // current !null, new !null and same -> do nothing
        // else -> clear results
//        if (filterDistrict == null && district == null ||
//            filterDistrict != null && filterDistrict.equals(district))
//            do nothing;
        // De Morgan
        if ((filterDistrict != null || district != null) &&
            (filterDistrict == null || !filterDistrict.equals(district)))
            clearResults();
        filterDistrict = district;
        updateFilterUI();
    }

    // ******* Search *******
    private void search() {
        Retrofit r = RetrofitClient.getClient(MuseumAPI.URL);
        MuseumAPI museumAPI = r.create(MuseumAPI.class);
        Call<MuseumResultAPI> call = museumAPI.getMuseums(filterDistrict);

        call.enqueue(new Callback<MuseumResultAPI>() {
            @Override
            public void onResponse(@NonNull Call<MuseumResultAPI> call, @NonNull Response<MuseumResultAPI> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(QueryActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                result = response.body();
                assert result != null;
                updateUI();
            }

            @Override
            public void onFailure(@NonNull Call<MuseumResultAPI> call, @NonNull Throwable t) {
                Toast.makeText(QueryActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
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
        btnQuery.setText(String.format(
            getString(R.string.btn_query),
            getString(
                (mode == LIST_MODE) ?
                    R.string.btn_query_list :
                    R.string.btn_query_map
            )
        ));
    }

    private void updateUI() {
        if (result == null)
            return;
        if (result.getMuseums().size() == 0) {
            Toast.makeText(this, R.string.no_results, Toast.LENGTH_LONG).show();
            return;
        }
        if (mode == LIST_MODE)
            currentResultFragment = ListViewFragment.newInstance(result);
        else if (mode == MAP_MODE)
            currentResultFragment = MapViewFragment.newInstance(result);
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.flMuseums, currentResultFragment)
            .commit();
    }

    private void clearResults() {
        if (currentResultFragment == null)
            return;
        getSupportFragmentManager()
            .beginTransaction()
            .remove(currentResultFragment)
            .commit();
    }

    // ******* MENU *******

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.query_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        boolean result = super.onOptionsItemSelected(item);
        int newMode = -1;
        if (item.getItemId() == R.id.mnList)
            newMode = LIST_MODE;
        else if (item.getItemId() == R.id.mnMap)
            newMode = MAP_MODE;
        if (newMode == -1 || newMode == mode)
            return result;
        mode = newMode;
        clearResults();
        updateFilterUI();
        return result;
    }
}