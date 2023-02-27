package com.jkutkut.proyectob_pmdm_t2_jorge_re.custom;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

import com.jkutkut.proyectob_pmdm_t2_jorge_re.R;

public class CustomSpinner extends AppCompatSpinner {

    public CustomSpinner(@NonNull Context context) {
        super(context);
    }

    public CustomSpinner(@NonNull Context context, int mode) {
        super(context, mode);
    }

    public CustomSpinner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public CustomSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
    }

    public void setStyleAndElements(Context context, String[] elements) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.custom_spinner, elements);
        adapter.setDropDownViewResource(R.layout.custom_spinner);
        this.setAdapter(adapter);
    }
}
