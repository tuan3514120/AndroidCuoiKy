package com.example.covi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covi.database.DB;

import java.util.ArrayList;
import java.util.List;

public class Insert extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 10;
    private EditText txtName;
    private EditText txtDay;
    private EditText txtGender;
    private EditText txtAddress;
    private EditText txtPhone;
    private EditText txtStatus;
    private Button btnAdd;
    private RecyclerView rcvKbyt;
    private TextView tvDeleteAll;
    private EditText txtSearch;

    private KBYTAdapter showKBYT;
    private List<KBYT> mListKbyt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        initUi();

        showKBYT = new KBYTAdapter(new KBYTAdapter.Click() {
            @Override
            public void updateKbyt(KBYT kbyt) {
                clickUpdate(kbyt);
            }

            @Override
            public void deleteKbyt(KBYT kbyt) {
                clickDelete(kbyt);
            }
        });
        mListKbyt = new ArrayList<>();
        showKBYT.setData(mListKbyt);
        loadData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvKbyt.setLayoutManager(linearLayoutManager);

        rcvKbyt.setAdapter(showKBYT);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addKbyt();
            }
        });
        tvDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickDeleteAll();
            }
        });
        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_SEARCH){
                    handleSearch();
                }
                return false;
            }
        });
    }
    private void addKbyt() {
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
        KBYT kbyt = new KBYT(strName,strDay,strGender,strAddress,strPhone,strStatus);

        if(isKbytExist(kbyt)){
            Toast.makeText(this,"Bạn đã khai báo y tế bằng sđt này rồi, vui lòng cập nhật lại trạng thái",Toast.LENGTH_SHORT).show();
            return;
        }

        DB.getInstance(this).DAO().insert(kbyt);
        Toast.makeText(this,"Khai báo y tế thành công",Toast.LENGTH_SHORT).show();

        mListKbyt = DB.getInstance(this).DAO().getListKBYT();
        showKBYT.setData(mListKbyt);


        txtName.setText("");
        txtDay.setText("");
        txtAddress.setText("");
        txtGender.setText("");
        txtPhone.setText("");
        txtStatus.setText("");


        loadData();
    }



    private void initUi(){
        txtName = findViewById(R.id.txtname);
        txtDay = findViewById(R.id.txtday);
        txtGender = findViewById(R.id.txtgender);
        txtAddress = findViewById(R.id.txtaddress);
        txtPhone = findViewById(R.id.txtphone);
        txtStatus = findViewById(R.id.txtstatus);
        btnAdd = findViewById(R.id.btn_add);
        rcvKbyt= findViewById(R.id.rcv_kbyt);
        tvDeleteAll = findViewById(R.id.tv_delete_all);
        txtSearch = findViewById(R.id.txtsearch);
    }
    private void loadData(){
        mListKbyt = DB.getInstance(this).DAO().getListKBYT();
        showKBYT.setData(mListKbyt);
    }
    private boolean isKbytExist(KBYT kbyt){
        List<KBYT> list = DB.getInstance(this).DAO().checkKbyt(kbyt.getPhone());
        return list != null && !list.isEmpty();
    }
    private void clickUpdate(KBYT kbyt){
        Intent intent = new Intent(Insert.this, Update.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_kbyt",kbyt);
        intent.putExtras(bundle);
        startActivityForResult(intent, MY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            loadData();
        }
    }
    private void clickDelete(final KBYT kbyt){
        new AlertDialog.Builder(this).setTitle("Xác nhận xóa bản khai báo này?")
                .setMessage("Bạn có chắc chắn muốn xóa bản khai báo này?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DB.getInstance(Insert.this).DAO().deleteKbyt(kbyt);
                Toast.makeText(Insert.this,"Xóa thành công!",Toast.LENGTH_SHORT).show();
                loadData();
            }
        })
                .setNegativeButton("Không",null)
                .show();
    }
    private void clickDeleteAll(){
        new AlertDialog.Builder(this).setTitle("Xác nhận xóa tất cả các bản khai báo?")
                .setMessage("Bạn có chắc chắn muốn xóa toàn bộ bản khai báo này?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DB.getInstance(Insert.this).DAO().deleteAll();
                        Toast.makeText(Insert.this,"Xóa thành công!",Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                })
                .setNegativeButton("Không",null)
                .show();
    }
    private void handleSearch(){
        String strKeyword = txtSearch.getText().toString().trim();
        mListKbyt = new ArrayList<>();
        mListKbyt = DB.getInstance(this).DAO().search(strKeyword);
        showKBYT.setData(mListKbyt);

    }
}
