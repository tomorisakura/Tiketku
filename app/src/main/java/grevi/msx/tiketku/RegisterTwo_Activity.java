package grevi.msx.tiketku;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.security.PublicKey;

public class RegisterTwo_Activity extends AppCompatActivity {

    Button continue_two,btn_add_photo;
    ImageView profile_pic,btn_back;
    EditText nama_lengap,bio;

    Integer max_pic = 1;
    Uri picture;

    DatabaseReference reference;
    StorageReference storage;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two_);

        getUsernameLocal();

        continue_two = findViewById(R.id.continue_two);
        btn_add_photo = findViewById(R.id.btn_add_photo);
        profile_pic = findViewById(R.id.profil_pic);
        nama_lengap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);
        btn_back = findViewById(R.id.btn_back);

        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });

        continue_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //harus ada pada saat upload
                continue_two.setEnabled(false);
                continue_two.setText("Loading...");

                //menyimpan di firebase
                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                storage = FirebaseStorage.getInstance().getReference().child("User path/Photouser").child(username_key_new);

                if (picture != null)
                {
                    final StorageReference storageReference1 = storage.child(System.currentTimeMillis()+ "." +urlExtension(picture)); //didapatkan dari function url Extension
                    storageReference1.putFile(picture).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                             storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String uri_photo = uri.toString();
                                    reference.getRef().child("url_profile_pic").setValue(uri_photo);
                                    reference.getRef().child("nama_lengkap").setValue(nama_lengap.getText().toString());
                                    reference.getRef().child("bio").setValue(bio.getText().toString());
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Intent regTwo = new Intent(RegisterTwo_Activity.this,SuccessRegs_Activity.class);
                                    startActivity(regTwo);
                                }
                            });
                        }
                    });
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    String urlExtension(Uri uri){
        ContentResolver resolv = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(resolv.getType(uri));
    }

    public void findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, max_pic);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == max_pic && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            picture = data.getData();
            Picasso.with(this).load(picture).centerCrop().fit().into(profile_pic);
        }

    }

    //menyimpan lokal user
    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
