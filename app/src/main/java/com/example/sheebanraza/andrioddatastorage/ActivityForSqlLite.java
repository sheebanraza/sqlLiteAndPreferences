package com.example.sheebanraza.andrioddatastorage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityForSqlLite extends Activity {

    int counter = 0;
    private SimpleDateFormat s=new SimpleDateFormat("MM/dd/yyyy-hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter=sharedPrefs.getInt("SQL_COUNTER", 0);
        setContentView(R.layout.activity_activity_for_sql_lite);
    }

    public void saveMessage(View view) {
        EditText editText = (EditText) findViewById(R.id.inputMessage);
        String message = editText.getText().toString();
        DatabaseController dataController = new DatabaseController(getBaseContext());
        dataController.open();
        long retValue = dataController.insert(message);
        dataController.close();
        if (retValue != -1) {
            Context context = getApplicationContext();
            Toast.makeText(context, "Data Saved Successfully", Toast.LENGTH_LONG).show();

            try {
                counter += 1;
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("SQL_COUNTER", counter);
                editor.commit();

                OutputStreamWriter out = new OutputStreamWriter(openFileOutput(PreferencesActivity.STORE_PREFERENCES, MODE_APPEND));
                out.write("\nSQLite " + counter + ", " + s.format(new Date()));
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter=sharedPrefs.getInt("SQL_COUNTER", 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
