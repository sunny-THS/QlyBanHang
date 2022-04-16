package com.example.qlybanhangonline;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DangKy extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnDatePicker;
    EditText txtDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        mapping();
        btnDatePicker.setOnClickListener(this);
    }

    private void mapping() {
        btnDatePicker = findViewById(R.id.btn_date);
        txtDate = findViewById(R.id.in_date);
    }

    @Override
    public void onClick(View view) {
        // Get Current Date
        Calendar calendar= Calendar.getInstance();
        int Year = calendar.get(Calendar.YEAR);
        int Month = calendar.get(Calendar.MONTH);
        int Day = calendar.get(Calendar.DAY_OF_MONTH);
        switch (view.getId()) {
            case R.id.btn_date:
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd//MM/yyy");
                                Calendar chooseDate = Calendar.getInstance();
                                chooseDate.set(i,i1,i2);
                                String trDate = simpleDateFormat.format(chooseDate.getTime());
                                txtDate.setText(trDate);
                            }
                        }, Year, Month, Day

                );
                datePickerDialog.show();
                break;
        }

    }
}