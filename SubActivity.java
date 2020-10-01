package com.example.csvmove;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Intent intent = getIntent();
        String selectedText = intent.getStringExtra("Text");

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.list_item);
        com.example.csvmove.CsvReader parser = new com.example.csvmove.CsvReader();
        parser.reader(getApplicationContext(),selectedText);
        ListViewAdapter2 listViewAdapter2 = new ListViewAdapter2(this, R.layout.list_item, parser.objects);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(listViewAdapter2);

    }
}