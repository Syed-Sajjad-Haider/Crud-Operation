package com.example.allcrudopperationperformeds;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ClickedData extends AppCompatActivity
{

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_data);

        textView = (TextView) findViewById(R.id.earnedname);

        textView.setText(getIntent().getStringExtra("name"));

    }
}