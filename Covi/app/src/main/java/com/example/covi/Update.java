package com.example.covi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.covi.database.DB;

public class Update extends AppCompatActivity {

    private EditText txtName;
    private EditText txtDay;
    private EditText txtGender;
    private EditText txtAddress;
    private EditText txtPhone;
    private EditText txtStatus;
    private Button btnUpdate;

    private KBYT mKbyt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        txtName = findViewById(R.id.txtname);
        txtDay = findViewById(R.id.txtday);
        txtGender = findViewById(R.id.txtgender);
        txtAddress = findViewById(R.id.txtaddress);
        txtPhone = findViewById(R.id.txtphone);
        txtStatus = findViewById(R.id.txtstatus);
        btnUpdate = findViewById(R.id.btn_update);

        mKbyt = (KBYT) getIntent().getExtras().get("object_kbyt");
        if(mKbyt != null){
            txtName.setText(mKbyt.getName());
            txtDay.setText(mKbyt.getDay());
            txtGender.setText(mKbyt.getGender());
            txtAddress.setText(mKbyt.getAddress());
            txtPhone.setText(mKbyt.getPhone());
            txtStatus.setText(mKbyt.getStatus());

        }
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateKbyt();
            }
        });
    }

    private void updateKbyt() {
        String strName = txtName.getText().toString().trim();
        String strDay = txtDay.getText().toString().trim();
        String strGender = txtGender.getText().toString().trim();
        String strAddress = txtAddress.getText().toString().trim();
        String strPhone = txtPhone.getText().toString().trim();
        String strStatus = txtStatus.getText().toString().trim();
        if (TextUtils.isEmpty(strName) ||TextUtils.isEmpty(strDay)||TextUtils.isEmpty(strGender)||
                TextUtils.isEmpty(strAddress)||TextUtils.isEmpty(strPhone)||TextUtils.isEmpty(strStatus)){
            return;
        }
        mKbyt.setName(strName);
        mKbyt.setDay(strDay);
        mKbyt.setAddress(strAddress);
        mKbyt.setGender(strGender);
        mKbyt.setPhone(strPhone);
        mKbyt.setStatus(strStatus);

        DB.getInstance(this).DAO().updateKbyt(mKbyt);
        Toast.makeText(this,"Cập nhật khai báo thành công!",Toast.LENGTH_SHORT).show();

        Intent intentResult = new Intent();
        setResult(Activity.RESULT_OK,intentResult);
        finish();
    }
}