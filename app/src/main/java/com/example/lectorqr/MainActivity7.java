package com.example.lectorqr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.MapFragment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity7 extends AppCompatActivity implements Mapas.OnFragmentItereactionListerner {
    private static final  String FILE_NAME = "Location.txt";
    TextView gpsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        //gpsText = findViewById(R.id.data_gps);
        //gpsText.setMovementMethod(new ScrollingMovementMethod());
        //loaddata();

        Fragment fragmento = new Mapas();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenerdor,fragmento).commit();


    }

    private void loaddata() {
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null){
                sb.append(text).append("\n");
            }

            gpsText.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void homeButton(View view) {
        Intent homeButton = new Intent(this, MainActivity.class);
        startActivity(homeButton);
    }


}