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

public class SuccessBuy_Activity extends AppCompatActivity {

    Button btn_view_ticket,btn_home;
    TextView holiday,kontent_text;
    ImageView icon_success;

    Animation app_splah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_);

        btn_view_ticket = findViewById(R.id.btn_view_ticket);
        btn_home = findViewById(R.id.btn_home);
        icon_success = findViewById(R.id.icon_success);
        holiday = findViewById(R.id.holiday_text);
        kontent_text = findViewById(R.id.kontent_text);

        app_splah = AnimationUtils.loadAnimation(this, R.anim.app_splash);

        icon_success.startAnimation(app_splah);
        kontent_text.startAnimation(app_splah);
        holiday.startAnimation(app_splah);
        btn_home.startAnimation(app_splah);
        btn_view_ticket.startAnimation(app_splah);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent success = new Intent(SuccessBuy_Activity.this, Dashboard_Activity.class);
                startActivity(success);
                finish();
            }
        });

        btn_view_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkTicket = new Intent(SuccessBuy_Activity.this, Profile_Activity.class);
                startActivity(checkTicket);
                finish();
            }
        });
    }
}
