package grevi.msx.tiketku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessRegs_Activity extends AppCompatActivity {

    Button btn_explore;
    ImageView icon_success;
    TextView kontentt,selamat;

    Animation app_splah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_regs_);

        btn_explore = findViewById(R.id.btn_explore);
        icon_success = findViewById(R.id.icon_success);
        kontentt = findViewById(R.id.kontentt);
        selamat = findViewById(R.id.selamat);
        app_splah = AnimationUtils.loadAnimation(this, R.anim.app_splash);

        icon_success.setAnimation(app_splah);
        kontentt.setAnimation(app_splah);
        selamat.setAnimation(app_splah);
        btn_explore.setAnimation(app_splah);

        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(SuccessRegs_Activity.this , Dashboard_Activity.class);
                startActivity(a);
                finish();
            }
        });
    }
}
