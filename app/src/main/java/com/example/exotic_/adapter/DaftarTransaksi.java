package com.example.exotic_.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.exotic_.R;
import com.example.exotic_.model.Transaksi;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class DaftarTransaksi extends RecyclerView.Adapter<DaftarTransaksi.TransaksiViewHolder> {
    private Context mContext;
    private List<Transaksi> Transaksis;
    private OnItemClickListener mListener;

    public DaftarTransaksi(Context context, List<Transaksi> transaksi) {
        mContext = context;
        Transaksis = transaksi;
    }

    @Override
    public TransaksiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_transaksi, parent, false);
        return new DaftarTransaksi.TransaksiViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(TransaksiViewHolder holder, int position) {
        Transaksi uploadCurrent = Transaksis.get(position);
        holder.NamaPembeli.setText(uploadCurrent.getNamaPelanggan());
        holder.NoTelepon.setText(uploadCurrent.getNohp());
        holder.AlamatPembeli.setText(uploadCurrent.getAlamatPelanggan());
//        DecimalFormat decim = new DecimalFormat("#,###");
//        holder.TotalHarga.setText("Rp. "+decim.format(Integer.parseInt(uploadCurrent.getTotalBayar()))+",00");
        holder.TotalHarga.setText(uploadCurrent.getTotalBayar());
        Picasso.get()
                .load(uploadCurrent.getGambarAkun())
                .placeholder(R.drawable.spk1)
                .fit()
                .centerCrop()
                .into(holder.GambarAkun);

    }

    @Override
    public int getItemCount() {
        return Transaksis.size();
    }

    public class TransaksiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView NamaPembeli,NoTelepon,AlamatPembeli,TotalHarga;
        public ImageView GambarAkun;

        public TransaksiViewHolder(View itemView) {
            super(itemView);

            NamaPembeli = itemView.findViewById(R.id.product_name);
            NoTelepon = itemView.findViewById(R.id.notel);
            AlamatPembeli = itemView.findViewById(R.id.alamat);
            GambarAkun = itemView.findViewById(R.id.product_image);
            TotalHarga = itemView.findViewById(R.id.total_bayar);

            itemView.setOnClickListener(this);
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
