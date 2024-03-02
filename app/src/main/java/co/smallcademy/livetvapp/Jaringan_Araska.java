package co.smallcademy.livetvapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.window.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

public class Jaringan_Araska extends AppCompatActivity {

    ImageView btn_repete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.internet_laiha);

        btn_repete = (ImageView) findViewById(R.id.btn_repete);

        btn_repete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Jaringan_Araska.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }


}
