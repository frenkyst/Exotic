package com.example.exotic_.ui_kasir.crud;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.exotic_.R;

public class InputDataFragment extends Fragment {

    private EditText NamaBarang,JenisBarang,HargaBarang;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.kasir_fragment_input_data_barang, container, false);

        NamaBarang = root.findViewById(R.id.txNama);
        JenisBarang = root.findViewById(R.id.txJenis);
        HargaBarang = root.findViewById(R.id.txHarga);

        NamaBarang.setText("tes");
        JenisBarang.setText("a");
        HargaBarang.setText("000");

        01:73:4A:25:1B:2B:D9:6E:85:4B:19:53:80:34:94:45:39:24:A3:46

        return root;
    }
}