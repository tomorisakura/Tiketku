package grevi.msx.tiketku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TicketDetail_Activity extends AppCompatActivity {

    Button buy_ticket;

    TextView nama_wisata,
            lokasi_wisata,
            poto_spot,
            wifi,
            festival,
            desc;

    ImageView thumbnail,btn_back;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail_);

        buy_ticket = findViewById(R.id.buy_ticket);
        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi_wisata = findViewById(R.id.lokasi_wisata);
        poto_spot = findViewById(R.id.poto_spot);
        wifi = findViewById(R.id.wifi);
        festival = findViewById(R.id.festival);
        desc = findViewById(R.id.desc);

        thumbnail = findViewById(R.id.thumbnail);
        btn_back = findViewById(R.id.btn_back);

        Bundle bundle = getIntent().getExtras();
        final String new_tiket = bundle.getString("jenis_tiket");

        reference = FirebaseDatabase.getInstance().getReference().child("wisata").child(new_tiket);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi_wisata.setText(dataSnapshot.child("lokasi_wisata").getValue().toString());
                poto_spot.setText(dataSnapshot.child("is_photo_spot").getValue().toString());
                wifi.setText(dataSnapshot.child("is_wifi").getValue().toString());
                festival.setText(dataSnapshot.child("is_festival").getValue().toString());
                desc.setText(dataSnapshot.child("desc").getValue().toString());

                Picasso.with(TicketDetail_Activity.this).load(dataSnapshot.child("url_pic_thumbnail").getValue().toString()).centerCrop().fit().into(thumbnail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(TicketDetail_Activity.this, TicketCheck_Activity.class);
                detail.putExtra("jenis_tiket",new_tiket);
                startActivity(detail);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
