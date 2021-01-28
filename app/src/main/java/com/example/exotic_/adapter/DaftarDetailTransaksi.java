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

import androidx.recyclerview.widget.RecyclerView;

import com.example.exotic_.R;
import com.example.exotic_.model.Keranjang;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DaftarDetailTransaksi extends RecyclerView.Adapter<DaftarDetailTransaksi.TransaksiViewHolder> {
    private Context mContext;
    private List<Keranjang> Keranjangs;
    private OnItemClickListener mListener;

    public DaftarDetailTransaksi(Context context, List<Keranjang> transaksi) {
        mContext = context;
        Keranjangs = transaksi;
    }

    @Override
    public TransaksiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_selected_product, parent, false);
        return new DaftarDetailTransaksi.TransaksiViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(TransaksiViewHolder holder, int position) {
        Keranjang uploadCurrent = Keranjangs.get(position);
        holder.NamaBarang.setText(uploadCurrent.getNamaBarang());
        holder.HargaBarang.setText(uploadCurrent.getHargaBarang());
        holder.JumlahBarang.setText(uploadCurrent.getJumlahBarang());
//        DecimalFormat decim = new DecimalFormat("#,###");
//        holder.TotalHarga.setText("Rp. "+decim.format(Integer.parseInt(uploadCurrent.getTotalBayar()))+",00");
//        holder.TotalHarga.setText(uploadCurrent.getTotalBayar());
        Picasso.get()
                .load(uploadCurrent.getGambarBarang())
                .placeholder(R.drawable.spk1)
                .fit()
                .centerCrop()
                .into(holder.GambarBarang);

    }

    @Override
    public int getItemCount() {
        return Keranjangs.size();
    }

    public class TransaksiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView NamaBarang,HargaBarang,JumlahBarang;
        public ImageView GambarBarang;

        public TransaksiViewHolder(View itemView) {
            super(itemView);

            NamaBarang = itemView.findViewById(R.id.product_name);
            HargaBarang = itemView.findViewById(R.id.product_price);
            JumlahBarang = itemView.findViewById(R.id.product_selectedQuantity);
            GambarBarang = itemView.findViewById(R.id.product_image);
//            TotalHarga = itemView.findViewById(R.id.total_bayar);

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
