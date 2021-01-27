package com.example.exotic_.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.exotic_.R;
import com.example.exotic_.model.Barang;
import com.example.exotic_.model.BarangAksesoris;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class DaftarAksesoris extends RecyclerView.Adapter<DaftarAksesoris.BarangAksesorisViewHolder> {
    private Context mContext;
    private List<BarangAksesoris> Barangs;
    private OnItemClickListener mListener;


    public DaftarAksesoris(Context context, List<BarangAksesoris> barangaksesoris) {
        mContext = context;
        Barangs = barangaksesoris;
    }

    @Override
    public BarangAksesorisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_product_aksesoris, parent, false);
        return new DaftarAksesoris.BarangAksesorisViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BarangAksesorisViewHolder holder, int position) {
        BarangAksesoris uploadCurrent = Barangs.get(position);
        holder.NamaBarang.setText(uploadCurrent.getNamaBarang());

        DecimalFormat decim = new DecimalFormat("#,###");
        //tvtotaltransaksi.setText("Rp. "+decim.format(Integer.parseInt(totaltransaksi)));
        holder.HargaBarang.setText("Rp. "+decim.format(Integer.parseInt(uploadCurrent.getHargaBarang()))+",00");
        Picasso.get()
                .load(uploadCurrent.getGambarBarang())
                .placeholder(R.drawable.spk1)
                .fit()
                .centerCrop()
                .into(holder.GambarBarang);
    }

    @Override
    public int getItemCount() {
        return Barangs.size();
    }

    public class BarangAksesorisViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView NamaBarang,HargaBarang;
        public ImageView GambarBarang;

        public BarangAksesorisViewHolder(View itemView) {
            super(itemView);

            NamaBarang = itemView.findViewById(R.id.product_name);
            HargaBarang = itemView.findViewById(R.id.product_price);

            GambarBarang = itemView.findViewById(R.id.product_image);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClickAksesoris(position);
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
                            mListener.onWhatEverClickAksesoris(position);
                            return true;
                        case 2:
                            mListener.onDeleteClickAksesoris(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        void onItemClickAksesoris(int position);

        void onWhatEverClickAksesoris(int position);

        void onDeleteClickAksesoris(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
