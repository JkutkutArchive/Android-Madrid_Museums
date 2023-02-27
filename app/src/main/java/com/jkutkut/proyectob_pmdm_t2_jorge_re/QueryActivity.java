package com.jkutkut.proyectob_pmdm_t2_jorge_re;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.jkutkut.proyectob_pmdm_t2_jorge_re.dialog.FilterDialog;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.dialog.FilterDialogListener;

import org.jetbrains.annotations.NotNull;

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
        Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show(); // TODO
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
        if (item.getItemId() == R.id.mnList) {

        }
        else if (item.getItemId() == R.id.mnMap) {

        }
        else {
            // TODO
        }
        return super.onOptionsItemSelected(item);
    }
}