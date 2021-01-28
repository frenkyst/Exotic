package com.example.exotic_.ui_pelanggan.akun;

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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.exotic_.R;
import com.example.exotic_.model.Akun;
import com.example.exotic_.ui_kasir.akun.NotificationsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AkunFragment extends Fragment {
    private String uid,email,image,status;
    private TextView NamaEmail;
    private EditText NamaPengguna,NoHp,Alamat,EmailAkunNoEdit;
    private Button BInputDataAkun;
    private ImageView FotoAkun;
    private NotificationsViewModel notificationsViewModel;

    private DatabaseReference mDatabaseRef,mDatabaseRefAkun;
    private ValueEventListener mDBListenerAkun;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.kasir_fragment_akun, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
            email = user.getEmail();
            image = user.getPhotoUrl().toString();
        }

        NamaEmail = root.findViewById(R.id.txnama_email);
        NamaPengguna= root.findViewById(R.id.txNamaPengguna);
        NoHp= root.findViewById(R.id.txNoHp);
        Alamat= root.findViewById(R.id.txAlamatPembeli);
        EmailAkunNoEdit = root.findViewById(R.id.txEmail);

        BInputDataAkun=root.findViewById(R.id.txInputDataAkun);
        FotoAkun = root.findViewById(R.id.image_view_akun);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Akun/"+uid);
        mDBListenerAkun = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                EmailAkunNoEdit.setText(dataSnapshot.child("emailAkun").getValue(String.class));
                NamaPengguna.setText(dataSnapshot.child("namaAkun").getValue(String.class));
                NamaEmail.setText(dataSnapshot.child("namaAkun").getValue(String.class));
                NoHp.setText(dataSnapshot.child("noTeleponAkun").getValue(String.class));
                Alamat.setText(dataSnapshot.child("alamatAkun").getValue(String.class));
                image = dataSnapshot.child("photoAkun").getValue(String.class);
                status = dataSnapshot.child("statusAkun").getValue(String.class);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.spk1)
                        .into(FotoAkun);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                        mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

        BInputDataAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseRefAkun = FirebaseDatabase.getInstance().getReference("Akun/"+uid);
                Akun data = new Akun(uid,
                        email,
                        NamaPengguna.getText().toString().trim(),
                        NoHp.getText().toString().trim(),
                        Alamat.getText().toString().trim(),
                        image,
                        status);
                //String uploadId = mDatabaseRef.push().getKey();
                mDatabaseRefAkun.setValue(data);
                Toast.makeText(getContext(), "Simpan successful", Toast.LENGTH_LONG).show();
            }
        });


        return root;


    }


}