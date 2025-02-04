package com.example.exe201;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class ViewVoucherActivity extends AppCompatActivity {
    ImageView btnBack;
    ListView lvVoucher;
    ArrayList<Voucher> arrayVoucher;
    voucherAdapter adapter;
    DBHelper DB;
    int vitri;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_voucher);
        btnBack= findViewById(R.id.back4);
        lvVoucher= findViewById(R.id.lvVoucher);
        builder= new AlertDialog.Builder(this);
        DB= new DBHelper(this);
        arrayVoucher= new ArrayList<>();
        adapter= new voucherAdapter(this, R.layout.voucher_row, arrayVoucher);
        lvVoucher.setAdapter(adapter);

        Intent intent= getIntent();
        String username= intent.getStringExtra("user");

        getView();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("user",username);
                startActivity(intent);
                finish();
            }
        });

        lvVoucher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vitri=i;
                builder.setTitle("Xác nhận mua voucher")
                        .setMessage("Bạn có muốn mua voucher này?")
                        .setCancelable(true)
                        .setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                insertMyVoucher();
                                dialogInterface.cancel();
                            }
                        })
                        .setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });
    }

    private void insertMyVoucher() {
        DB.queryData("Create Table if not exists UserVoucher (voucherOrderID Integer Primary Key Autoincrement," +
                "userName nvarchar(20), voucherName nvarchar(20), voucherCode nvarchar(20), exchangeDate nvarchar(20),Constraint fk_User Foreign Key (userName) references User(userName))");
        Intent intent= getIntent();
        String username= intent.getStringExtra("user");
        Cursor res= DB.getData(username);
        if(res.getCount()== 0){
            Toast.makeText(ViewVoucherActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            return;
        }
        int myPoint=0;
        while (res.moveToNext()){
            myPoint= res.getInt(9);
        }
        Voucher voucher= arrayVoucher.get(vitri);
        if(voucher.getVoucherPrice()>myPoint) {
            Toast.makeText(this, "Điểm của bạn không đủ để mua voucher này", Toast.LENGTH_SHORT).show();
        }else {
            Boolean buy= DB.insertMyVoucher(username, voucher.getVoucherName(), getRanString(10));
            if(buy== true){
                Toast.makeText(ViewVoucherActivity.this, "Mua thành công",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(ViewVoucherActivity.this, "Mua thất bại",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static String getRanString(int a){
        final String character= "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder result= new StringBuilder();
        while (a>0){
            Random r= new Random();
            result.append(character.charAt(r.nextInt(character.length())));
            a--;
        }
        return result.toString();
    }

    private void getView() {
       DB.queryData("Create Table if not exists Voucher (voucherID Integer Primary Key Autoincrement," +
               "voucherName nvarchar(20), expiredDate nvarchar(20), voucherPrice Integer, description nvarchar(50))");
        DB.queryData("Create Table if not exists UserVoucher (voucherOrderID Integer Primary Key Autoincrement," +
                "userName nvarchar(20), voucherName nvarchar(20), voucherCode nvarchar(20), exchangeDate nvarchar(20),Constraint fk_User Foreign Key (userName) references User(userName))");
//        DB.queryData();
        Cursor dataVoucher= DB.getVoucher();
        arrayVoucher.clear();
        while (dataVoucher.moveToNext()){
            String ten= dataVoucher.getString(1);
            String hsd= dataVoucher.getString(2);
            int gia= dataVoucher.getInt(3);
            String mota= dataVoucher.getString(4);
            int id= dataVoucher.getInt(0);
            arrayVoucher.add(new Voucher(id, ten, hsd, gia, mota));
        }
        adapter.notifyDataSetChanged();
    }
}