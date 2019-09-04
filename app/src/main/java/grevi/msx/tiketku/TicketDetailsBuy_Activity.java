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

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class TicketDetailsBuy_Activity extends AppCompatActivity {

    TextView nama_wisata,lokasi_wisata,date_wisata,time_wisata,no_uniq;
    ImageView btn_back;
    Integer random = new Random().nextInt();

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    Integer owl = 0;

    Date MyDate = new Date();
    String date = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ENGLISH).format(MyDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details_buy_);

        btn_back = findViewById(R.id.btn_back);
        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi_wisata = findViewById(R.id.lokasi_wisata);
        date_wisata = findViewById(R.id.date_wisata);
        time_wisata = findViewById(R.id.time_wisata);
        no_uniq = findViewById(R.id.no_uniq);

        Bundle bundle = getIntent().getExtras();
        final String new_wisata = bundle.getString("nama_wisata");

        getUsernameLocal();

        owl = 1 + random;
        no_uniq.setText("TKU"+owl);

        reference = FirebaseDatabase.getInstance().getReference().child("wisata").child(new_wisata);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi_wisata.setText(dataSnapshot.child("lokasi_wisata").getValue().toString());
                date_wisata.setText(dataSnapshot.child("date_wisata").getValue().toString());
                time_wisata.setText(dataSnapshot.child("time_wisata").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(TicketDetailsBuy_Activity.this, Profile_Activity.class);
                startActivity(back);
                finish();
            }
        });

    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
