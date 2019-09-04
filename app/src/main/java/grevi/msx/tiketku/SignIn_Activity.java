package grevi.msx.tiketku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class SignIn_Activity extends AppCompatActivity {

    TextView btn_create_akun;
    EditText passwordText,usernameText;
    Button btn_login;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_);

        btn_create_akun = findViewById(R.id.btn_create_akun);
        btn_login = findViewById(R.id.btn_login);
        passwordText = findViewById(R.id.password_text);
        usernameText = findViewById(R.id.username_text);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = usernameText.getText().toString();
                final String password = passwordText.getText().toString();

                if (username.isEmpty() && password.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),"Username dan Password kosong",Toast.LENGTH_SHORT).show();
                        btn_login.setEnabled(true);
                        btn_login.setText("Login");
                    }
                else
                    {
                        if (password.isEmpty())
                        {
                            Toast.makeText(getApplicationContext(),"Password kosong",Toast.LENGTH_SHORT).show();
                            btn_login.setEnabled(true);
                            btn_login.setText("Login");
                        }

                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){
                                        String passwordFB = dataSnapshot.child("password").getValue().toString();
                                        String usernameFb = dataSnapshot.child("username").getValue().toString();

                                        if (username.equals(usernameFb) && password.equals(passwordFB)){
                                            getUsernameLocal();

                                            Toast.makeText(getApplicationContext(),"Wellcome to Dashboard",Toast.LENGTH_SHORT).show();

                                            Intent home = new Intent(SignIn_Activity.this, Dashboard_Activity.class);
                                            startActivity(home);
                                            finish();
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(),"Username dan password salah",Toast.LENGTH_SHORT).show();
                                            btn_login.setEnabled(true);
                                            btn_login.setText("Login");
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(getApplicationContext(),"Terjadi Pemeliharaan Sistem \nMohon Maaf hehe",Toast.LENGTH_SHORT).show();
                                }
                            });
                    }
            }
        });

        btn_create_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a  = new Intent(SignIn_Activity.this, Register_Activity.class);
                startActivity(a);

            }
        });
    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(username_key, usernameText.getText().toString());
        editor.apply();
    }
}
