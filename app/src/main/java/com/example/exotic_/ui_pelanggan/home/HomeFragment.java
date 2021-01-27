package com.example.exotic_.ui_pelanggan.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exotic_.R;
import com.example.exotic_.adapter.DaftarAksesoris;
import com.example.exotic_.adapter.DaftarBarang;
import com.example.exotic_.model.Barang;
import com.example.exotic_.model.BarangAksesoris;
import com.example.exotic_.ui_kasir.KasirActivity;
import com.example.exotic_.ui_pelanggan.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements DaftarBarang.OnItemClickListener, DaftarAksesoris.OnItemClickListener {

    Button ButtonViewAll;
    LinearLayout LayaoutHU,LayaoutPower,LayaoutSplit,LayaoutSubwoofer;
    private RecyclerView mRecyclerView;
    private DaftarBarang Adapter;
    private RecyclerView mRecyclerViewAks;
    private DaftarAksesoris AdapterAks;
    private FirebaseStorage Storage;
    private DatabaseReference DatabaseRef;
    private ValueEventListener DBListener;
    private DatabaseReference DatabaseRefAks;
    private ValueEventListener DBListenerAks;
    private List<Barang> Barangs;
    private List<BarangAksesoris> BarangAksesoriss;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.pelanggan_fragment_home, container, false);

        ButtonViewAll = root.findViewById(R.id.txBViewAll);

        ButtonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatActivity activity = (AppCompatActivity) getContext();
                Fragment myFragment = new com.example.exotic_.ui_kasir.home.HomeFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();
            }
        });
    LayaoutHU =root.findViewById(R.id.txLHU);
    LayaoutHU.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            MainActivity.Jenis="HU";
            AppCompatActivity activity = (AppCompatActivity) getContext();
            Fragment myFragment = new com.example.exotic_.ui_pelanggan.home.LayoutTipeJenis();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();
        }
    });
        LayaoutPower =root.findViewById(R.id.txLPower);
        LayaoutPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.Jenis="Power";
                AppCompatActivity activity = (AppCompatActivity) getContext();
                Fragment myFragment = new com.example.exotic_.ui_pelanggan.home.LayoutTipeJenis();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();
            }
        });
        LayaoutSplit =root.findViewById(R.id.txLSplit);
        LayaoutSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.Jenis="Split";
                AppCompatActivity activity = (AppCompatActivity) getContext();
                Fragment myFragment = new com.example.exotic_.ui_pelanggan.home.LayoutTipeJenis();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();
            }
        });
        LayaoutSubwoofer =root.findViewById(R.id.txLSubwoofer);
        LayaoutSubwoofer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.Jenis="Subwoofer";
                AppCompatActivity activity = (AppCompatActivity) getContext();
                Fragment myFragment = new com.example.exotic_.ui_pelanggan.home.LayoutTipeJenis();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewAks = root.findViewById(R.id.rvhorizontal);
//        mRecyclerViewAks.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerViewAks.setLayoutManager(layoutManager);
        mRecyclerViewAks.setHasFixedSize(true);
//        mRecyclerViewAks.la
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        ProgressCircle = root.findViewById(R.id.progress_circle);
        BarangAksesoriss = new ArrayList<>();
        AdapterAks = new DaftarAksesoris(getContext(), BarangAksesoriss);
        mRecyclerViewAks.setAdapter(AdapterAks);
        AdapterAks.setOnItemClickListener(com.example.exotic_.ui_pelanggan.home.HomeFragment.this);
        Storage = FirebaseStorage.getInstance();
        DatabaseRefAks = FirebaseDatabase.getInstance().getReference("Barang");
        DBListenerAks = DatabaseRefAks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BarangAksesoriss.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(postSnapshot.child("jenisBarang").exists()){
                        String query = postSnapshot.child("jenisBarang").getValue(String.class);
                        if("Aksesoris".equals(query)){
                            BarangAksesoris upload = postSnapshot.getValue(BarangAksesoris.class);
                            upload.setKey(postSnapshot.getKey());
                            BarangAksesoriss.add(upload);
                        }
                    }
                }
                AdapterAks.notifyDataSetChanged();
//                ProgressCircle.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                ProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        mRecyclerView = root.findViewById(R.id.rv_product);
//        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        ProgressCircle = root.findViewById(R.id.progress_circle);
        Barangs = new ArrayList<>();
        Adapter = new DaftarBarang(getContext(), Barangs);
        mRecyclerView.setAdapter(Adapter);
        Adapter.setOnItemClickListener(com.example.exotic_.ui_pelanggan.home.HomeFragment.this);
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
        Barang selectedItem = Barangs.get(position);
        KasirActivity.referenceKeyBarang = selectedItem.getKey();

        KasirActivity.namaBarang = selectedItem.getNamaBarang();
        KasirActivity.hargabarang = selectedItem.getHargaBarang();
        KasirActivity.jumlahbarang = selectedItem.getJumlahBarang();
        KasirActivity.gambar = selectedItem.getGambarBarang();

        AppCompatActivity activity = (AppCompatActivity) getContext();
        Fragment myFragment = new DetailProdukFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();
    }

    @Override
    public void onWhatEverClick(int position) {
    }

    @Override
    public void onDeleteClick(int position) {

    }

    @Override
    public void onItemClickAksesoris(int position) {

        BarangAksesoris selectedItem1 = BarangAksesoriss.get(position);
        KasirActivity.referenceKeyBarang = selectedItem1.getKey();

        KasirActivity.namaBarang = selectedItem1.getNamaBarang();
        KasirActivity.hargabarang = selectedItem1.getHargaBarang();
        KasirActivity.jumlahbarang = selectedItem1.getJumlahBarang();
        KasirActivity.gambar = selectedItem1.getGambarBarang();

        AppCompatActivity activity = (AppCompatActivity) getContext();
        Fragment myFragment = new DetailProdukFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();
    }

    @Override
    public void onWhatEverClickAksesoris(int position) {

    }

    @Override
    public void onDeleteClickAksesoris(int position) {

    }
}