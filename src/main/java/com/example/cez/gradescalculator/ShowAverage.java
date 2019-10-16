package com.example.cez.gradescalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by cez on 1/3/2017.
 */

public class ShowAverage extends Activity {
    public static String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent myIntent = getIntent();
        Bundle bundle = myIntent.getExtras();
        String finalandroid = bundle.getString("androidgrade");
        String finaljava = bundle.getString("javagrade");
        String finalxml = bundle.getString("xmlgrade");
        String finaloop = bundle.getString("oopgrade");

        //--debugging display
        Log.i(TAG,"Android grade : " + finalandroid);
        Log.i(TAG,"Java grade : " + finaljava);
        Log.i(TAG,"XML grade : " + finalxml);
        Log.i(TAG,"OOP grade : " + finaloop);

        //check if the string is Null or Empty()
        finalandroid= (finalandroid == null ||finalandroid.isEmpty())? "0":finalandroid;
        finaljava= (finaljava == null ||finaljava.isEmpty())? "0":finaljava;
        finalxml= (finalxml == null ||finalxml.isEmpty())? "0":finalxml;
        finaloop= (finaloop == null ||finaloop.isEmpty())? "0":finaloop;

        Double average = (Double) ((Integer.parseInt(finalandroid) +
                                    Integer.parseInt(finaljava)  +
                                    Integer.parseInt(finalxml)  +
                                    Integer.parseInt(finaloop)) / 4.0);

        TextView tv = (TextView) findViewById(R.id.finalaverage);

        tv.append ("Average : " + Double.toString(average));

    }
}
