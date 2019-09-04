package grevi.msx.tiketku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Article_Activity extends AppCompatActivity {

    ImageView thumbnail,xprofile_pic,photo_spots1,photo_spots2,photo_spots3,btn_back;

    TextView kontent,nama_author,media,lokasi_article,judul_article;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        thumbnail = findViewById(R.id.thumbnail);
        xprofile_pic = findViewById(R.id.xprofile_pic);
        photo_spots1 = findViewById(R.id.photo_spots1);
        photo_spots2 = findViewById(R.id.photo_spots2);
        photo_spots3 = findViewById(R.id.photo_spots3);
        btn_back = findViewById(R.id.btn_back);

        kontent = findViewById(R.id.kontent);
        nama_author = findViewById(R.id.nama_author);
        media = findViewById(R.id.media);
        lokasi_article = findViewById(R.id.lokasi_article);
        judul_article = findViewById(R.id.judul_article);

        Bundle bundle = getIntent().getExtras();
        final String new_article = bundle.getString("nama_article");

        reference = FirebaseDatabase.getInstance().getReference().child("Article").child(new_article);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kontent.setText(dataSnapshot.child("content").getValue().toString());
                nama_author.setText(dataSnapshot.child("nama_author").getValue().toString());
                media.setText(dataSnapshot.child("media").getValue().toString());
                lokasi_article.setText(dataSnapshot.child("lokasi_article").getValue().toString());
                judul_article.setText(dataSnapshot.child("judul_article").getValue().toString());

                Picasso.with(Article_Activity.this).load(dataSnapshot.child("photo_spots1").getValue().toString()).centerCrop().fit().into(thumbnail);
                Picasso.with(Article_Activity.this).load(dataSnapshot.child("photo_spots1").getValue().toString()).centerCrop().fit().into(photo_spots1);
                Picasso.with(Article_Activity.this).load(dataSnapshot.child("photo_spots2").getValue().toString()).centerCrop().fit().into(photo_spots2);
                Picasso.with(Article_Activity.this).load(dataSnapshot.child("photo_spots3").getValue().toString()).centerCrop().fit().into(photo_spots3);

                Picasso.with(Article_Activity.this).load(dataSnapshot.child("profile_pic").getValue().toString()).centerCrop().fit().into(xprofile_pic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
