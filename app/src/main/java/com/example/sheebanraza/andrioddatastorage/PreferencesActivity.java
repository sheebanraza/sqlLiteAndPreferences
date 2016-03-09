package com.example.sheebanraza.andrioddatastorage;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PreferencesActivity extends AppCompatActivity {

    public final static String STORE_PREFERENCES = "storePrefFinal.txt";
    public int counter = 0;
    private SimpleDateFormat s = new SimpleDateFormat("MM/dd/yyyy-hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        setupActionBar();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter = sharedPrefs.getInt("COUNTER", 0);

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter = sharedPrefs.getInt("COUNTER", 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
          //  getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSave(View view) {
        EditText bookName = (EditText) findViewById(R.id.bookName);
        EditText author = (EditText) findViewById(R.id.bookAuthorText);
        EditText description = (EditText) findViewById(R.id.descriptionText);

        if (bookName != null && author != null && description != null) {
            try {
                counter += 1;

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                Editor editor = sharedPreferences.edit();
                editor.putInt("COUNTER", counter);
                editor.commit();

                OutputStreamWriter out = new OutputStreamWriter(openFileOutput(STORE_PREFERENCES, MODE_APPEND));
                String message = "\nSaved Preference " + counter + ", " + s.format(new Date());
                out.write(message);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onCancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
