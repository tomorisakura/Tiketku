package grevi.msx.tiketku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView applogo;
    Animation app_splah;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        applogo = findViewById(R.id.applogo);
        app_splah = AnimationUtils.loadAnimation(this, R.anim.app_splash);

        applogo.startAnimation(app_splah);

        getUsernameLocal();
    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
        if (username_key_new.isEmpty()){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent a = new Intent(MainActivity.this, GetStarted_Activity.class);
                    startActivity(a);
                    finish();
                }
            },3000);
        }else {
            Intent b = new Intent(MainActivity.this, Dashboard_Activity.class);
            startActivity(b);
            finish();
        }
    }
}
