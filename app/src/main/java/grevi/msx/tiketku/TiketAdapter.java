package grevi.msx.tiketku;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TiketAdapter extends RecyclerView.Adapter<TiketAdapter.myViewHolder> {

    Context context;
    ArrayList<MyTiket> myTiket;

    public TiketAdapter(Context c, ArrayList<MyTiket> p){
        context = c;
        myTiket = p;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mytiket, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.xnama_wisata.setText(myTiket.get(position).getNama_wisata());
        holder.xlokasi_wisata.setText(myTiket.get(position).getLokasi_wisata());
        holder.xjumlah_tiket.setText(myTiket.get(position).getJumlah_tiket());

        final String getWisata = myTiket.get(position).getNama_wisata();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tiketDetails = new Intent(context, TicketDetailsBuy_Activity.class);
                tiketDetails.putExtra("nama_wisata", getWisata);
                context.startActivity(tiketDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTiket.size();
    } //mereturn semua tiket

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView xnama_wisata,xlokasi_wisata,xjumlah_tiket;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            xnama_wisata = itemView.findViewById(R.id.xnama_wisata);
            xlokasi_wisata = itemView.findViewById(R.id.xlokasi_wisata);
            xjumlah_tiket = itemView.findViewById(R.id.xjumlah_tiket);
        }
    }
}
