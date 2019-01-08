package com.example.pc252.expendabletextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
   private ExpendableTextView expendableTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expendableTextView=(ExpendableTextView)findViewById(R.id.expendableTextView);
    }
}
