package com.example.exe201;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imgSet, imgVoucher, imgMaterial, imgMyVoucher;
    ImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgSet= findViewById(R.id.setCalen);
        imgVoucher= findViewById(R.id.viewVoucher);
        imgProfile= findViewById(R.id.profile);
        imgMaterial= findViewById(R.id.viewMaterial);
        imgMyVoucher= findViewById(R.id.viewMyVoucher);

        Intent intent= getIntent();
        String username= intent.getStringExtra("user");

        imgSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), OrderActivity.class);
                intent.putExtra("user",username);
                startActivity(intent);
                finish();
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), ViewProfileActivity.class);
                intent.putExtra("user",username);
                startActivity(intent);
                finish();
            }
        });

        imgVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), ViewVoucherActivity.class);
                intent.putExtra("user",username);
                startActivity(intent);
                finish();
            }
        });

        imgMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), ViewMaterialActivity.class);
                intent.putExtra("user",username);
                startActivity(intent);
                finish();
            }
        });

        imgMyVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), MyVoucherActivity.class);
                intent.putExtra("user",username);
                startActivity(intent);
                finish();
            }
        });
    }
}