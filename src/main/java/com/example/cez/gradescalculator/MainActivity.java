package com.example.cez.gradescalculator;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static String TAG;
    private EditText androidgrade, javagrade, xmlgrade, oopgrade;


    private String colleges[] = {
            "Seneca Valley College",
            "Hummingbird Academy",
            "Harrington High",
            "Georgian Bay College",
            "Saracuse Academy",
            "St-Vincent College",
            "Seneca College",
            "Seneca York",
            "Seneca North"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG,"Student name: Cecile Seloterio - 147147169"  );


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,colleges);
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.simpledropdown);
        actv.setThreshold(1);
        actv.setAdapter(adapter);


        Button avg_button = (Button)  findViewById(R.id.avg_button);
        Button min_button = (Button)  findViewById(R.id.min_button);
        Button max_button = (Button)  findViewById(R.id.max_button);

        androidgrade = (EditText) findViewById(R.id.androidgrade);
        javagrade = (EditText) findViewById(R.id.javagrade);
        xmlgrade = (EditText) findViewById(R.id.xmlgrade);
        oopgrade = (EditText) findViewById(R.id.oopgrade);


        avg_button.setOnClickListener(this);
        min_button.setOnClickListener(this);
        max_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

            switch (v.getId()) {
                case R.id.avg_button:
                    Intent intent = new Intent(this,ShowAverage.class);
                    sendData(intent);
                    //adding key/value pair to the Explicit Intent
                    break;

                case R.id.min_button:
                    Intent  intent2 = new Intent(this,ShowMin.class);
                    sendData(intent2);
                    break;

                case R.id.max_button:
                    Intent  intent3 = new Intent(this,ShowMax.class);
                    sendData(intent3);
                    break;
            }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        for (int j = 0; j < menu.size(); j++) {
            MenuItem item = menu.getItem(j);
            Log.d(TAG, "set flag for " + item.getTitle());
            item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            //noinspection SimplifiableIfStatement
            case R.id.about_action:

                //AlertDialog alertDialog = new AlertDialog.Builder(n.create(); //Read Update
                //alertDialog.setTitle("About..");
               // alertDialog.setMessage("Look into our school for a variety of  IT programs: http://www.senecacollege.ca.");
                //alertDialog.show();

                Intent intent4 = new Intent(this, About.class);
                startActivity(intent4);
                break;

            case R.id.min_action:
                Intent  intent2 = new Intent(this,ShowMin.class);
                sendData(intent2);
                break;

            case R.id.avg_action:
                Intent intent = new Intent(this,ShowAverage.class);
                sendData(intent);
                break;

            case R.id.max_action:
                Intent  intent3 = new Intent(this,ShowMax.class);
                sendData(intent3);
                break;


        }

        return super.onOptionsItemSelected(item);
    }


    private void sendData(Intent intent) {

        if(androidgrade.getText().toString().length()>0) {
            intent.putExtra("androidgrade", androidgrade.getText().toString());
            Log.v(TAG, "androidgrade : " + androidgrade.getText().toString());
        }
        if(javagrade.getText().toString().length()>0) {
            intent.putExtra("javagrade", javagrade.getText().toString());
            Log.v(TAG, "javagrade : " + javagrade.getText().toString());
        }

        if(xmlgrade.getText().toString().length()>0) {
            intent.putExtra("xmlgrade", xmlgrade.getText().toString());
            Log.v(TAG, "xmlgrade : " + xmlgrade.getText().toString());
        }
        if(oopgrade.getText().toString().length()>0) {
            intent.putExtra("oopgrade", oopgrade.getText().toString());
            Log.v(TAG, "oopgrade : " + oopgrade.getText().toString());
        }
        startActivity(intent);

    }
}
