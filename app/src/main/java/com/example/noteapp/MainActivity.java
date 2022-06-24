package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    ListView noteList;
    static ArrayList<String> note = new ArrayList<>();
    static ArrayAdapter adapter;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.noteapp", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("note",null);
//        note.add("Example note");
        note = new ArrayList<>(set);
        noteList = findViewById(R.id.list);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,note);
        noteList.setAdapter(adapter);
        noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),Note_Editor.class);
                intent.putExtra("noteId",i);
                startActivity(intent);
            }
        });
        noteList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int itemRemove=i;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.alert_light_frame)
                        .setTitle("Delete note")
                        .setMessage("Are u sure to delete note")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //delete note
                                note.remove(itemRemove);
                                adapter.notifyDataSetChanged();

                                HashSet<String> set = new HashSet<>(MainActivity.note);
                                sharedPreferences.edit().putStringSet("note",set).apply();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            }
        });





    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if( item.getItemId() == R.id.note){
                Intent intent = new Intent(getApplicationContext(),Note_Editor.class);
                startActivity(intent);
                return true;
        }return false;
    }
}