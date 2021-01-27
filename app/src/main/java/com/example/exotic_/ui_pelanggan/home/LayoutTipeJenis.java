package com.example.exotic_.ui_pelanggan.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exotic_.R;
import com.example.exotic_.adapter.DaftarBarang;
import com.example.exotic_.model.Barang;
import com.example.exotic_.ui_pelanggan.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class LayoutTipeJenis extends Fragment implements DaftarBarang.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private DaftarBarang Adapter;
    private ProgressBar ProgressCircle;
    private FirebaseStorage Storage;
    private DatabaseReference DatabaseRef;
    private ValueEventListener DBListener;
    private List<Barang> Barangs;

    public View onCreateView(@NonNull LayoutInflater inflater,
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
        Adapter.setOnItemClickListener(com.example.exotic_.ui_pelanggan.home.LayoutTipeJenis.this);
        Storage = FirebaseStorage.getInstance();
        DatabaseRef = FirebaseDatabase.getInstance().getReference("Barang");
        DBListener = DatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Barangs.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(postSnapshot.child("jenisBarang").exists()){
                        String query = postSnapshot.child("jenisBarang").getValue(String.class);
                        if(MainActivity.Jenis.equals(query)){
                            Barang upload = postSnapshot.getValue(Barang.class);
                            upload.setKey(postSnapshot.getKey());
                            Barangs.add(upload);
                        }
                    }

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

    }

    @Override
    public void onWhatEverClick(int position) {

    }

    @Override
    public void onDeleteClick(int position) {

    }
}