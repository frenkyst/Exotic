package com.example.exotic_.ui_pelanggan.keranjang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exotic_.R;
import com.example.exotic_.adapter.DaftarTransaksi;
import com.example.exotic_.model.Barang;
import com.example.exotic_.model.Transaksi;
import com.example.exotic_.ui_pelanggan.MainActivity;
import com.example.exotic_.ui_pelanggan.home.DetailProdukFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class TransaksiFragment extends Fragment implements DaftarTransaksi.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private DaftarTransaksi Adapter;
    private ProgressBar ProgressCircle;
    private FirebaseStorage Storage;
    private DatabaseReference DatabaseRef;
    private ValueEventListener DBListener;
    private ImageButton BBayar;
    private List<Transaksi> Transaksis;
    public static String uid;
    public static TextView TBayar;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.pelanggan_fragment_keranjang, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }
        BBayar = root.findViewById(R.id.btn_bayar);
        TBayar = root.findViewById(R.id.total_price);
        mRecyclerView = root.findViewById(R.id.rv_selected_product);
        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        ProgressCircle = root.findViewById(R.id.progress_circle);
        Transaksis = new ArrayList<>();
        Adapter = new DaftarTransaksi(getContext(), Transaksis);
        mRecyclerView.setAdapter(Adapter);
        Adapter.setOnItemClickListener(TransaksiFragment.this);
        Storage = FirebaseStorage.getInstance();
        DatabaseRef = FirebaseDatabase.getInstance().getReference("Transaksi/");
        DBListener = DatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Transaksis.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Transaksi upload = postSnapshot.getValue(Transaksi.class);
                    upload.setKey(postSnapshot.getKey());
                    Transaksis.add(upload);
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
        Transaksi selectedItem = Transaksis.get(position);
        MainActivity.refKeyBarang = selectedItem.getKey();

        MainActivity.namaPelanggan = selectedItem.getNamaPelanggan();
        MainActivity.nohp = selectedItem.getNohp();
        MainActivity.alamatPelanggan = selectedItem.getAlamatPelanggan();
        MainActivity.totalBayar = selectedItem.getTotalBayar();

        AppCompatActivity activity = (AppCompatActivity) getContext();
        Fragment myFragment = new DetailBarangFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();
    }

    @Override
    public void onWhatEverClick(int position) {

        Toast.makeText(getContext(), "Normal click at position:wawawaa " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {

        Toast.makeText(getContext(), "Normal click at position:2323 " + position, Toast.LENGTH_SHORT).show();
    }
}
