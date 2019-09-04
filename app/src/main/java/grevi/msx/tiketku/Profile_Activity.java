package grevi.msx.tiketku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Profile_Activity extends AppCompatActivity {

    Button btn_edit_profile,btn_signOut;

    TextView passion,nama_lengkap;

    ImageView profile_pic,btn_back;

    DatabaseReference reference, reference_mytiket;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    TiketAdapter adapter;
    ArrayList <MyTiket> myTikets;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);

        btn_edit_profile = findViewById(R.id.btn_edit_profile);
        btn_signOut = findViewById(R.id.btn_signOut);
        passion = findViewById(R.id.passion);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        profile_pic = findViewById(R.id.profile_pic);
        btn_back = findViewById(R.id.btn_back);
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myTikets = new ArrayList<MyTiket>();

        getUsernameLocal();

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                passion.setText(dataSnapshot.child("bio").getValue().toString());

                Picasso.with(Profile_Activity.this).load(dataSnapshot.child("url_profile_pic").getValue().toString()).centerCrop().fit().into(profile_pic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Database Users Erorr",Toast.LENGTH_SHORT).show();
            }
        });

        reference_mytiket = FirebaseDatabase.getInstance().getReference().child("MyTiket").child(username_key_new);
        reference_mytiket.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    MyTiket a = ds.getValue(MyTiket.class);
                    myTikets.add(a);
                }
                adapter = new TiketAdapter(Profile_Activity.this , myTikets);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUsername();
                Intent signout = new Intent(Profile_Activity.this, SignIn_Activity.class);
                startActivity(signout);
                finish();
            }
        });

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(Profile_Activity.this, MyProfileAct.class);
                startActivity(profile);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }

    public void deleteUsername(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(username_key, null);
        editor.apply();
    }
}
