package grevi.msx.tiketku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class TicketCheck_Activity extends AppCompatActivity {

    Button btn_buy,btn_min,btn_plus;
    TextView lokasi_wisata,nama_wisata,total_harga,desc,wallet,satuan_tiket,coution;
    ImageView thumbnail,btn_back;

    DatabaseReference reference,reference_users,reference_tiket,reference_wallet;

    Integer saldo = 0;
    Integer sisa_saldo;
    Integer jumlah_tiket = 1;
    Integer harga_tiket = 0;
    Integer totalX = 0,
            total = 0;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    String date_wisata = "";
    String time_wisata = "";

    Integer no_transaksi = new Random().nextInt();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_check_);

        btn_buy = findViewById(R.id.buy_ticket);
        btn_min = findViewById(R.id.btn_min);
        btn_plus = findViewById(R.id.btn_plus);
        btn_back = findViewById(R.id.btn_back);
        lokasi_wisata = findViewById(R.id.lokasi_wisata);
        nama_wisata = findViewById(R.id.nama_wisata);
        total_harga = findViewById(R.id.total_harga);
        desc = findViewById(R.id.desc);
        wallet = findViewById(R.id.wallet);
        satuan_tiket = findViewById(R.id.satuan_tiket);
        coution = findViewById(R.id.coution);
        thumbnail = findViewById(R.id.thumbnail);

        getUsernameLocal();
        btn_min.setEnabled(false);
        btn_buy.setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();
        final String new_tiket = bundle.getString("jenis_tiket");

        reference = FirebaseDatabase.getInstance().getReference().child("wisata").child(new_tiket);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi_wisata.setText(dataSnapshot.child("lokasi_wisata").getValue().toString());
                harga_tiket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());
                date_wisata = dataSnapshot.child("date_wisata").getValue().toString();
                time_wisata = dataSnapshot.child("time_wisata").getValue().toString();


                Picasso.with(TicketCheck_Activity.this).load(dataSnapshot.child("url_pic_thumbnail").getValue().toString()).centerCrop().fit().into(thumbnail);

                totalX = jumlah_tiket * harga_tiket;
                total_harga.setText("Rp. " +totalX +".000");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Database Wisata Erorr",Toast.LENGTH_SHORT).show();
            }
        });

        reference_users = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference_users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                saldo = Integer.valueOf(dataSnapshot.child("wallet").getValue().toString());
                wallet.setText("IDR " +saldo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Database Users Erorr",Toast.LENGTH_SHORT).show();
            }
        });

        coution.setVisibility(View.INVISIBLE);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlah_tiket -= 1;
                satuan_tiket.setText(jumlah_tiket.toString());
                total = totalX * jumlah_tiket;
                total_harga.setText("Rp." +total+ ".000");

                if (jumlah_tiket < 2){
                    btn_min.setEnabled(false);
                }
                if (total < saldo && saldo > total ){
                    coution.setVisibility(View.GONE);
                    wallet.setTextColor(Color.parseColor("#5A43E6"));
                    btn_buy.setEnabled(true);
                }
            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlah_tiket += 1;
                satuan_tiket.setText(jumlah_tiket.toString());
                total = totalX * jumlah_tiket;
                total_harga.setText("Rp." +total+ ".000");

                if (jumlah_tiket > 1){
                    btn_plus.animate().alpha(1).setDuration(350).start();
                    btn_min.setEnabled(true);
                    btn_buy.setVisibility(View.VISIBLE);
                }if (total > saldo && saldo < total){
                    coution.setVisibility(View.VISIBLE);
                    wallet.setTextColor(Color.parseColor("#8D8D8D"));
                    btn_buy.setEnabled(false);
                    Toast.makeText(getApplicationContext(),"Sorry , Your wallet is limited",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference_tiket = FirebaseDatabase.getInstance().getReference().child("MyTiket").child(username_key_new).child(nama_wisata.getText().toString() + no_transaksi);
                reference_tiket.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference_tiket.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference_tiket.getRef().child("lokasi_wisata").setValue(lokasi_wisata.getText().toString());
                        reference_tiket.getRef().child("ketentuan").setValue(desc.getText().toString());
                        reference_tiket.getRef().child("jumlah_tiket").setValue(jumlah_tiket.toString());
                        reference_tiket.getRef().child("date_wisata").setValue(date_wisata);
                        reference_tiket.getRef().child("time_wisata").setValue(time_wisata);

                        Intent checkout = new Intent(TicketCheck_Activity.this , SuccessBuy_Activity.class);
                        startActivity(checkout);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                reference_wallet = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference_wallet.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisa_saldo = saldo - total;
                        reference_wallet.getRef().child("wallet").setValue(sisa_saldo);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }

}
