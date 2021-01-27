package com.example.exotic_.ui_kasir.crud;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.exotic_.R;
import com.example.exotic_.model.Barang;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class InputDataFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private TextView ButtonPilihGambar;
    private EditText NamaBarang,JenisBarang,JumlahBarang,HargaBarang;
    private Button InputBarang;
    private Uri ImageUri;
    private ImageView ImageView;
    private ProgressBar ProgressBar;
    private DatabaseReference DatabaseRef,DatabaseRefBarang;
    private StorageReference StorageRef;
    private StorageTask UploadTask;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.kasir_fragment_input_data_barang, container, false);

        NamaBarang = root.findViewById(R.id.txNama);
        JenisBarang = root.findViewById(R.id.txJenisBarang);
        JumlahBarang = root.findViewById(R.id.txJumlah);
        HargaBarang = root.findViewById(R.id.txHarga);
        InputBarang = root.findViewById(R.id.txInputBarang);
        ButtonPilihGambar = root.findViewById(R.id.pilih_gambar_barang);
        ImageView = root.findViewById(R.id.image_view_gambar_barang);
        ProgressBar = root.findViewById(R.id.progress_bar);

        StorageRef = FirebaseStorage.getInstance().getReference("Barang");
        DatabaseRefBarang = FirebaseDatabase.getInstance().getReference("Barang");
        InputBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UploadTask != null && UploadTask.isInProgress()) {
                    Toast.makeText(getContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

        
        ButtonPilihGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });



        return root;
    }

//    public void SimpanDataBarang(){
//        Barang data = new Barang(NamaBarang.getText().toString().trim(),
//                JenisBarang.getText().toString().trim(),
//                JumlahBarang.getText().toString().trim(),
//                HargaBarang.getText().toString().trim());
//        //String uploadId = mDatabaseRef.push().getKey();
//        DatabaseRefBarang.push().setValue(data);
//        Toast.makeText(getContext(), "Simpan successful", Toast.LENGTH_LONG).show();
//    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            ImageUri = data.getData();
            Picasso.get().load(ImageUri).into(ImageView);
        }

    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile() {
        if (ImageUri != null) {
            final StorageReference fileReference = StorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(ImageUri));
            UploadTask = fileReference.putFile(ImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ProgressBar.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_LONG).show();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Barang upload = new Barang(NamaBarang.getText().toString().trim(),
                                            JenisBarang.getText().toString().trim(),
                                            JumlahBarang.getText().toString().trim(),
                                            HargaBarang.getText().toString().trim(),
                                            uri.toString());
                                    //String uploadId = mDatabaseRef.push().getKey();
                                    DatabaseRefBarang.push().setValue(upload);
                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            ProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(getContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}