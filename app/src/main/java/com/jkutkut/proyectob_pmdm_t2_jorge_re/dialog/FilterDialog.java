package com.jkutkut.proyectob_pmdm_t2_jorge_re.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.jkutkut.proyectob_pmdm_t2_jorge_re.R;
import com.jkutkut.proyectob_pmdm_t2_jorge_re.custom.CustomSpinner;

public class FilterDialog extends DialogFragment {
    private FilterDialogListener listener;

    private CustomSpinner spnDistrict;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = requireActivity().getLayoutInflater()
                .inflate(R.layout.filter_dialog, null);

        spnDistrict = v.findViewById(R.id.spnDistrict);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        builder.setPositiveButton(
            "Ok",
            (dialog, which) -> listener.onDialogEnds(spnDistrict.getSelectedItem().toString())
        );
        builder.setNegativeButton(
            "Cancel",
            (dialog, which) -> listener.onDialogEnds(null)
        );

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof FilterDialogListener))
            throw new RuntimeException("Context must implement FilterDialogListener");
        listener = (FilterDialogListener) context;
    }
}
