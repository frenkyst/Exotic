package com.example.exotic_.ui_pelanggan.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exotic_.R;
import com.example.exotic_.adapter.DaftarKeranjang;
import com.example.exotic_.model.Keranjang;
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

public class TroliFragment extends Fragment implements DaftarKeranjang.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private DaftarKeranjang Adapter;
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
        View root = inflater.inflate(R.layout.pelanggan_fragment_troli, container, false);
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
        Keranjangs = new ArrayList<>();
        Adapter = new DaftarKeranjang(getContext(), Keranjangs);
        mRecyclerView.setAdapter(Adapter);
        Adapter.setOnItemClickListener(TroliFragment.this);
        Storage = FirebaseStorage.getInstance();
        DatabaseRef = FirebaseDatabase.getInstance().getReference("Keranjang/"+uid);
        DBListener = DatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                Keranjangs.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Keranjang upload = postSnapshot.getValue(Keranjang.class);
                    upload.setKey(postSnapshot.getKey());
                    Keranjangs.add(upload);
                    DaftarKeranjang.intHargaBarang2=0;
                    DaftarKeranjang.intHargaBarang = 0;
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

        BBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabaseRefAkun = FirebaseDatabase.getInstance().getReference("Akun/" + uid);
                mDBListenerAkun = mDatabaseRefAkun.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("uidAkun").exists()){
                            uid=(dataSnapshot.child("uidAkun").getValue(String.class));
                            NamaPelanggan=(dataSnapshot.child("namaAkun").getValue(String.class));
                            nohp=(dataSnapshot.child("noTeleponAkun").getValue(String.class));
                            alamat=(dataSnapshot.child("alamatAkun").getValue(String.class));
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }
                });

                DatabaseReference fromPath, toPath;
                fromPath = FirebaseDatabase.getInstance().getReference("Keranjang/"+uid);
                toPath = FirebaseDatabase.getInstance().getReference("Transaksi/");

                copyRecord(fromPath,toPath);

                Toast.makeText(getActivity(), "Transaksi Berhasil !!", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
    public void copyRecord(DatabaseReference fromPath, final DatabaseReference toPath) {
        fromPath.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.push().setValue("1", new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError,
                                           DatabaseReference databaseReference) {
                        String uniqueKey = databaseReference.getKey();
                        toPath.child(uniqueKey+"/DaftarBarang").setValue(dataSnapshot.getValue());
                        toPath.child(uniqueKey+"/uid").setValue(uid);
                        toPath.child(uniqueKey+"/namaPelanggan").setValue(NamaPelanggan);
                        toPath.child(uniqueKey+"/alamatPelanggan").setValue(alamat);
                        toPath.child(uniqueKey+"/nohp").setValue(nohp);
                        toPath.child(uniqueKey+"/totalBayar").setValue(TBayar.getText().toString());
                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        fromPath.removeValue();
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