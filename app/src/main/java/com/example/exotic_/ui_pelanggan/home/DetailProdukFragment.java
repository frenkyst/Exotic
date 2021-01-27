package com.example.exotic_.ui_pelanggan.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.exotic_.R;
import com.example.exotic_.model.Akun;
import com.example.exotic_.model.Keranjang;
import com.example.exotic_.ui_kasir.KasirActivity;
import com.example.exotic_.ui_kasir.akun.NotificationsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailProdukFragment extends Fragment {
    private String uid,uid1,NamaPelanggan,nohp,image,alamat;
    private ImageView Gambar;
    private TextView NamaBarang,Jumlah,Harga;
    private Button BMasukanKeranjang;
    private NotificationsViewModel notificationsViewModel;

    private DatabaseReference mDatabaseRef,mDatabaseRefKeranjang,mDatabaseRefAkun;
    private ValueEventListener mDBListenerKeranjang,mDBListenerAkun;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.pelanggan_fragment_detail_produk, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid1 = user.getUid();
        }

        Gambar = root.findViewById(R.id.image_barang_K);
        NamaBarang= root.findViewById(R.id.txNamaBarangK);
        Harga= root.findViewById(R.id.txHargaBarangK);
        Jumlah= root.findViewById(R.id.txNJumlahBarangK);

                image=KasirActivity.gambar;
                NamaBarang.setText(KasirActivity.namaBarang);
                Harga.setText(KasirActivity.hargabarang);
                Jumlah.setText(KasirActivity.jumlahbarang);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.spk1)
                        .into(Gambar);
        BMasukanKeranjang=root.findViewById(R.id.txMasukanKeranjang);

        mDatabaseRefAkun = FirebaseDatabase.getInstance().getReference("Akun/"+uid1);
        mDBListenerAkun = mDatabaseRefAkun.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uid=(dataSnapshot.child("uidAkun").getValue(String.class));
                NamaPelanggan=(dataSnapshot.child("namaAkun").getValue(String.class));
                nohp=(dataSnapshot.child("noTeleponAkun").getValue(String.class));
                alamat=(dataSnapshot.child("alamatAkun").getValue(String.class));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                        mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Barang/-MRwgvJdmv3MEh-3WnKG");
//        mDBListenerKeranjang = mDatabaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                image=(dataSnapshot.child("gambarBarang").getValue(String.class));
//                NamaBarang.setText(dataSnapshot.child("namaBarang").getValue(String.class));
//                Harga.setText(dataSnapshot.child("hargaBarang").getValue(String.class));
//                Jumlah.setText(dataSnapshot.child("jumlahBarang").getValue(String.class));
//                Picasso.get()
//                        .load(image)
//                        .placeholder(R.drawable.spk1)
//                        .into(Gambar);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
////                        mProgressCircle.setVisibility(View.INVISIBLE);
//            }
//        });

        BMasukanKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseRefKeranjang = FirebaseDatabase.getInstance().getReference("Keranjang");
                Keranjang data = new Keranjang(NamaPelanggan,
                        uid,alamat,nohp,
                        NamaBarang.getText().toString().trim(),
                        "1",
                        Harga.getText().toString().trim(),
                        image);
                //String uploadId = mDatabaseRef.push().getKey();
                mDatabaseRefKeranjang.setValue(data);
                Toast.makeText(getContext(), "Simpan successful", Toast.LENGTH_LONG).show();
            }
        });


        return root;


    }


}