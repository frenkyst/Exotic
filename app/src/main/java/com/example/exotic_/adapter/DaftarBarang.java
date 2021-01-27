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
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class DaftarBarang extends RecyclerView.Adapter<DaftarBarang.BarangViewHolder> {
    private Context mContext;
    private List<Barang> Barangs;
    private OnItemClickListener mListener;


    public DaftarBarang(Context context, List<Barang> barang) {
        mContext = context;
        Barangs = barang;
    }

    @Override
    public BarangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_product, parent, false);
        return new DaftarBarang.BarangViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BarangViewHolder holder, int position) {
        Barang uploadCurrent = Barangs.get(position);
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

    public class BarangViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView NamaBarang,HargaBarang;
        public ImageView GambarBarang;

        public BarangViewHolder(View itemView) {
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
