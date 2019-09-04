package grevi.msx.tiketku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register_Activity extends AppCompatActivity {

    Button continue_one;
    EditText mail_text,username,password;
    ImageView btn_back;
    DatabaseReference reference,reference_users;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        continue_one = findViewById(R.id.continue_one);
        mail_text = findViewById(R.id.email_address);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_back = findViewById(R.id.btn_back);

        continue_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                continue_one.setEnabled(false);
                continue_one.setText("Continue");

                reference_users = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                reference_users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Toast.makeText(getApplicationContext(),"Username Sudah Ada",Toast.LENGTH_SHORT).show();
                            continue_one.setEnabled(true);
                            continue_one.setText("Continue");
                        }else
                            {
                                getUsernameLocal();

                                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                                        dataSnapshot.getRef().child("email_address").setValue(mail_text.getText().toString());
                                        dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                                        dataSnapshot.getRef().child("wallet").setValue(600);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                Intent a  = new Intent(Register_Activity.this, RegisterTwo_Activity.class);
                                startActivity(a);
                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(username_key,username.getText().toString());
        editor.apply();
    }

}
