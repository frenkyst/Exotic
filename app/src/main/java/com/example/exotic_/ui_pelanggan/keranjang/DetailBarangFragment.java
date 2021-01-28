package com.example.exotic_.ui_pelanggan.keranjang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exotic_.R;
import com.example.exotic_.adapter.DaftarDetailTransaksi;
import com.example.exotic_.adapter.DaftarKeranjang;
import com.example.exotic_.model.Keranjang;
import com.example.exotic_.ui_pelanggan.MainActivity;
import com.example.exotic_.ui_pelanggan.home.TroliFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DetailBarangFragment extends Fragment implements DaftarKeranjang.OnItemClickListener, DaftarDetailTransaksi.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private DaftarDetailTransaksi Adapter;
    private ProgressBar ProgressCircle;
    private FirebaseStorage Storage;
    private DatabaseReference DatabaseRef,mDatabaseRefAkun;
    private ValueEventListener DBListener,mDBListenerAkun;
    private ImageButton BBayar;
    private List<Keranjang> Keranjangs;
    public static String uid,NamaPelanggan,nohp,alamat;
    public static TextView TBayar;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.daftar_barang, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }
        mRecyclerView = root.findViewById(R.id.rv_selected_product);
        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        ProgressCircle = root.findViewById(R.id.progress_circle);
        Keranjangs = new ArrayList<>();
        Adapter = new DaftarDetailTransaksi(getContext(), Keranjangs);
        mRecyclerView.setAdapter(Adapter);
        Adapter.setOnItemClickListener(DetailBarangFragment.this);
        Storage = FirebaseStorage.getInstance();
        DatabaseRef = FirebaseDatabase.getInstance().getReference("Transaksi/"+ MainActivity.refKeyBarang+"/DaftarBarang");
        DBListener = DatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                Keranjangs.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Keranjang upload = postSnapshot.getValue(Keranjang.class);
                    upload.setKey(postSnapshot.getKey());
                    Keranjangs.add(upload);
                }
                Adapter.notifyDataSetChanged();
//                ProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
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

    }

    @Override
    public void onDeleteClick(int position) {

        Toast.makeText(getContext(), "Normal click at position:2323 " + position, Toast.LENGTH_SHORT).show();
    }
}