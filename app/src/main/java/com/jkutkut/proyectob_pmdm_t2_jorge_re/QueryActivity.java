package com.jkutkut.proyectob_pmdm_t2_jorge_re;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jkutkut.proyectob_pmdm_t2_jorge_re.api.MuseumAPI;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.api.RetrofitClient;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.api.result.MuseumResult;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.dialog.FilterDialog;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.dialog.FilterDialogListener;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.list.MuseumAdapter;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class QueryActivity extends AppCompatActivity implements FilterDialogListener {

    private static final int LIST_MODE = 0;
    private static final int MAP_MODE = 1;

    private TextView txtvFilter;
    private FrameLayout flMuseums;
    private RecyclerView rvMuseums;

    private String filterDistrict;
    private int mode;
    private MuseumResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        setTitle(R.string.query_activity_title);

        filterDistrict = null;
        mode = LIST_MODE;

        txtvFilter = findViewById(R.id.txtvFilter);
        flMuseums = findViewById(R.id.flMuseums);
        AppCompatButton btnFilterQuery = findViewById(R.id.btnFilterQuery);
        AppCompatButton btnQuery = findViewById(R.id.btnQuery);

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
            public void onResponse(@NonNull Call<MuseumResult> call, @NonNull Response<MuseumResult> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(QueryActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                result = response.body();
                assert result != null;
                updateUI();
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

    private void updateUI() {
        if (result == null)
            return;
        if (mode == LIST_MODE)
            updateListUI();
        else if (mode == MAP_MODE)
            updateMapUI();
    }

    private void updateListUI() {
//        rvMuseums = new RecyclerView(this);
//        rvMuseums.setLayoutManager(new LinearLayoutManager(this));
//        getLayoutInflater().inflate(R.layout.list_view, rvMuseums);
//
//        MuseumAdapter adapter = new MuseumAdapter();
//        rvMuseums.setAdapter(adapter);
//
//        flMuseums.removeAllViews();
//        flMuseums.addView(rvMuseums);
//
//        adapter.setData(result.getGraph());
        // TODO fix
    }

    private void updateMapUI() {
        Toast.makeText(this, "Map mode", Toast.LENGTH_SHORT).show(); // TODO
    }

    // GETTERS
    public RecyclerView getRvMuseums() {
        // TODO
//        if (rvMuseums == null) { // TODO is it better to free the memory?
//            rvMuseums = new RecyclerView(this);
//            rvMuseums.setLayoutManager(new LinearLayoutManager(this));
//            getLayoutInflater().inflate(R.layout.list_view, rvMuseums);
//            MuseumAdapter adapter = new MuseumAdapter();
//            rvMuseums.setAdapter(adapter);
//        }
        return rvMuseums;
    }

    // ******* MENU *******

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.query_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (item.getItemId() == R.id.mnList) {
            mode = LIST_MODE;
        }
        else if (item.getItemId() == R.id.mnMap) {
            mode = MAP_MODE;
        }
        // TODO clear flMuseums
        return super.onOptionsItemSelected(item);
    }
}