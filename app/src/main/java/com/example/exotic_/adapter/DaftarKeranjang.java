package com.example.exotic_.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.exotic_.R;
import com.example.exotic_.model.Keranjang;
import com.example.exotic_.ui_pelanggan.home.TroliFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class DaftarKeranjang extends RecyclerView.Adapter<DaftarKeranjang.KeranjangViewHolder> {
    public static int intHargaBarang2=0;
    public static int intHargaBarang=0;
    private Context mContext;
    private List<Keranjang> Keranjangs;
    private OnItemClickListener mListener;
//    private int intHargaBarang = 0;
//    private final int[] Jumlahint;
//    private String uid;
//    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//        uid = user.getUid();
//    }


    public DaftarKeranjang(Context context, List<Keranjang> keranjang) {
        mContext = context;
        Keranjangs = keranjang;
    }

    @Override
    public KeranjangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_selected_product, parent, false);
        return new DaftarKeranjang.KeranjangViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(KeranjangViewHolder holder, int position) {
        Keranjang uploadCurrent = Keranjangs.get(position);
        holder.NamaBarang.setText(uploadCurrent.getNamaBarang());

        DecimalFormat decim = new DecimalFormat("#,###");
        //tvtotaltransaksi.setText("Rp. "+decim.format(Integer.parseInt(totaltransaksi)));

        String HargaBarang = null,
                Jumlah1 = null;
        if(uploadCurrent.getHargaBarang()!=null && uploadCurrent.getJumlahBarang()!=null){
            Jumlah1 = decim.format(Integer.parseInt(uploadCurrent.getJumlahBarang()));
            intHargaBarang = Integer.parseInt(uploadCurrent.getHargaBarang());
            HargaBarang = decim.format(intHargaBarang);
            intHargaBarang = intHargaBarang*Integer.parseInt(uploadCurrent.getJumlahBarang());
            intHargaBarang2 = intHargaBarang2+intHargaBarang;
            TroliFragment.TBayar.setText("Rp. "+decim.format(intHargaBarang2)+",00");
        }
        holder.HargaBarang.setText("Rp. "+HargaBarang+",00");
        holder.Jumlah.setText(Jumlah1);
        Picasso.get()
                .load(uploadCurrent.getGambarBarang())
                .placeholder(R.drawable.spk1)
                .fit()
                .centerCrop()
                .into(holder.GambarBarang);
        final int[] Jumlahint = new int[Keranjangs.size()];
        final int[] HargaBarangint = new int[Keranjangs.size()];
        Jumlahint[position] = Integer.parseInt(Jumlah1);
        HargaBarangint[position] = Integer.parseInt(uploadCurrent.getHargaBarang());
        holder.kurangi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Jumlahint[position]!=1){
                    Jumlahint[position] = Jumlahint[position] -1;
                    holder.Jumlah.setText(String.valueOf(Jumlahint[position]));
                    intHargaBarang2 = intHargaBarang2-HargaBarangint[position];
                    TroliFragment.TBayar.setText("Rp. "+decim.format(intHargaBarang2)+",00");
                    setJumlah(uploadCurrent.getKey(),String.valueOf(Jumlahint[position]));
                }
            }
        });
        holder.tambahi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Jumlahint[position] = Jumlahint[position] +1;
                holder.Jumlah.setText(String.valueOf(Jumlahint[position]));
                intHargaBarang2 = intHargaBarang2+HargaBarangint[position];
                TroliFragment.TBayar.setText("Rp. "+decim.format(intHargaBarang2)+",00");
                setJumlah(uploadCurrent.getKey(),String.valueOf(Jumlahint[position]));
            }
        });

        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Keranjang/"+ TroliFragment.uid+"/"+uploadCurrent.getKey());
        holder.hapusKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseRef.removeValue();
            }
        });

//        holder.Jumlah.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                setJumlah(uploadCurrent.getKey(),String.valueOf(Jumlahint[position]));
//            }
//        });

    }

    private void setJumlah (String refKey,String jumlahBarang){

        DatabaseReference mDatabaseRefadd = FirebaseDatabase.getInstance().getReference("Keranjang/"+ TroliFragment.uid+"/"+refKey);
        mDatabaseRefadd.child("jumlahBarang").setValue(jumlahBarang);

    }
    @Override
    public int getItemCount() {
        return Keranjangs.size();
    }

    public class KeranjangViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView NamaBarang,HargaBarang,Jumlah;
        public ImageView kurangi,tambahi;
        RelativeLayout hapusKeranjang;
        public ImageView GambarBarang;

        public KeranjangViewHolder(View itemView) {
            super(itemView);

            NamaBarang = itemView.findViewById(R.id.product_name);
            HargaBarang = itemView.findViewById(R.id.product_price);
            GambarBarang = itemView.findViewById(R.id.product_image);
            Jumlah = itemView.findViewById(R.id.product_selectedQuantity);
            kurangi =  itemView.findViewById(R.id.kurangi);
            tambahi = itemView.findViewById(R.id.tambahi);
            hapusKeranjang = itemView.findViewById(R.id.hapus_keranjang);

//            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem doWhatever = menu.add(Menu.NONE, 1, 1, "Edit");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");

            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        case 1:
                            mListener.onWhatEverClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onWhatEverClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

}
