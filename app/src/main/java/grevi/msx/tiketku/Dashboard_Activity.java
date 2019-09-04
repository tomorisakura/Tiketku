package grevi.msx.tiketku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Dashboard_Activity extends AppCompatActivity {

    ImageView pisa_icon,img_profile,
            torri_icon,
            pagoda_icon,
            sphinx_icon,
            borobudur_icon,
            eifel_icon;

    ImageView thumb_kyoto,thumb_paris,thumb_pisa,thumb_festjap;

    TextView nama_lengkap,bio,wallet;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_);

        pisa_icon = findViewById(R.id.pisa_icon);
        torri_icon = findViewById(R.id.torri_icon);
        pagoda_icon = findViewById(R.id.pagoda_icon);
        borobudur_icon = findViewById(R.id.borobudur_icon);
        sphinx_icon = findViewById(R.id.sphinx_icon);
        eifel_icon = findViewById(R.id.eifel_icon);

        thumb_kyoto = findViewById(R.id.thumb_kyoto);
        thumb_festjap = findViewById(R.id.thumb_festjap);
        thumb_pisa = findViewById(R.id.thumb_pisa);
        thumb_paris = findViewById(R.id.thumb_paris);

        img_profile = findViewById(R.id.img_profile);

        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);
        wallet = findViewById(R.id.wallet);

        getUsernameLocal();

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                wallet.setText("IDR " + dataSnapshot.child("wallet").getValue().toString());

                Picasso.with(Dashboard_Activity.this).load(dataSnapshot.child("url_profile_pic").getValue().toString()).centerCrop().fit().into(img_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profil = new Intent(Dashboard_Activity.this, Profile_Activity.class);
                startActivity(profil);
            }
        });

        //menu icon
        pisa_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pisa = new Intent(Dashboard_Activity.this, TicketDetail_Activity.class);
                pisa.putExtra("jenis_tiket","Pisa");
                startActivity(pisa);
            }
        });
        torri_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pisa = new Intent(Dashboard_Activity.this, TicketDetail_Activity.class);
                pisa.putExtra("jenis_tiket","Temple");
                startActivity(pisa);
            }
        });
        pagoda_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pisa = new Intent(Dashboard_Activity.this, TicketDetail_Activity.class);
                pisa.putExtra("jenis_tiket","Pagoda");
                startActivity(pisa);
            }
        });
        borobudur_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pisa = new Intent(Dashboard_Activity.this, TicketDetail_Activity.class);
                pisa.putExtra("jenis_tiket","Borobudur");
                startActivity(pisa);
            }
        });
        sphinx_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pisa = new Intent(Dashboard_Activity.this, TicketDetail_Activity.class);
                pisa.putExtra("jenis_tiket","Sphinx");
                startActivity(pisa);
            }
        });
        eifel_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pisa = new Intent(Dashboard_Activity.this, TicketDetail_Activity.class);
                pisa.putExtra("jenis_tiket","Eifel");
                startActivity(pisa);
            }
        });

        //Discover Menu

        thumb_kyoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kyoto = new Intent(Dashboard_Activity.this, Article_Activity.class);
                kyoto.putExtra("nama_article","author1");
                startActivity(kyoto);
            }
        });

        thumb_festjap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kyoto = new Intent(Dashboard_Activity.this, Article_Activity.class);
                kyoto.putExtra("nama_article","author4");
                startActivity(kyoto);
            }
        });

        thumb_paris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kyoto = new Intent(Dashboard_Activity.this, Article_Activity.class);
                kyoto.putExtra("nama_article","author3");
                startActivity(kyoto);
            }
        });

        thumb_pisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kyoto = new Intent(Dashboard_Activity.this, Article_Activity.class);
                kyoto.putExtra("nama_article","author2");
                startActivity(kyoto);
            }
        });

    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
