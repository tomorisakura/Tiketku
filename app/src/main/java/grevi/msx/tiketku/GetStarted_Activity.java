package grevi.msx.tiketku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GetStarted_Activity extends AppCompatActivity {

    ImageView applogo_white;
    TextView textDiscover;
    Button button_sign, btn_new_akun;

    Animation app_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started_);

        button_sign = findViewById(R.id.button_sign_in);
        btn_new_akun = findViewById(R.id.btn_new_akun);
        applogo_white = findViewById(R.id.logo_white);
        textDiscover = findViewById(R.id.text_discover);
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);

        applogo_white.startAnimation(app_splash);
        textDiscover.startAnimation(app_splash);

        btn_new_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a  = new Intent(GetStarted_Activity.this, Register_Activity.class);
                startActivity(a);
            }
        });

        button_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a  = new Intent(GetStarted_Activity.this, SignIn_Activity.class);
                startActivity(a);
                finish();
            }
        });

    }
}
