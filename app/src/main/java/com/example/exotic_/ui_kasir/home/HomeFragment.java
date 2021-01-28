package com.example.exotic_.ui_kasir.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exotic_.R;
import com.example.exotic_.adapter.DaftarBarang;
import com.example.exotic_.model.Barang;
import com.example.exotic_.ui_kasir.KasirActivity;
import com.example.exotic_.ui_kasir.crud.EditDataBarangFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements DaftarBarang.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private DaftarBarang Adapter;
    private ProgressBar ProgressCircle;
    private FirebaseStorage Storage;
    private DatabaseReference DatabaseRef;
    private ValueEventListener DBListener;
    private List<Barang> Barangs;

    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.kasir_fragment_home, container, false);

        mRecyclerView = root.findViewById(R.id.rv_selected_product);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        ProgressCircle = root.findViewById(R.id.progress_circle);
        Barangs = new ArrayList<>();
        Adapter = new DaftarBarang(getContext(), Barangs);
        mRecyclerView.setAdapter(Adapter);
        Adapter.setOnItemClickListener(HomeFragment.this);
        Storage = FirebaseStorage.getInstance();
        DatabaseRef = FirebaseDatabase.getInstance().getReference("Barang");
        DBListener = DatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Barangs.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Barang upload = postSnapshot.getValue(Barang.class);
                    upload.setKey(postSnapshot.getKey());
                    Barangs.add(upload);
                }
                Adapter.notifyDataSetChanged();
//                ProgressCircle.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                ProgressCircle.setVisibility(View.INVISIBLE);
            }
        });



        return root;
    }


    @Override
    public void onItemClick(int position) {

        Toast.makeText(getContext(), "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWhatEverClick(int position) {

        Barang selectedItem = Barangs.get(position);
        Toast.makeText(getContext(), "Edit data Barang yang di pilih " + position, Toast.LENGTH_SHORT).show();

        KasirActivity.referenceKeyBarang = selectedItem.getKey();
        KasirActivity.gambar = selectedItem.getGambarBarang();
        KasirActivity.namaBarang = selectedItem.getNamaBarang();
        KasirActivity.jenisBarang = selectedItem.getJenisBarang();
        KasirActivity.jumlahbarang = selectedItem.getJumlahBarang();
        KasirActivity.hargabarang = selectedItem.getHargaBarang();

        AppCompatActivity activity = (AppCompatActivity) getContext();
        Fragment myFragment = new EditDataBarangFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();
    }

    @Override
    public void onDeleteClick(int position) {
        Barang selectedItem = Barangs.get(position);
        final String selectedKey = selectedItem.getKey();
        StorageReference imageRef = Storage.getReferenceFromUrl(selectedItem.getGambarBarang());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DatabaseRef.child(selectedKey).removeValue();
//                Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(getContext(), "Hapus " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DatabaseRef.removeEventListener(DBListener);
    }
}