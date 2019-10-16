package com.example.cez.myaddressplus;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cez.myaddressplus.DBContenProvider;
import com.example.cez.myaddressplus.DBConnHandler;
import com.example.cez.myaddressplus.DBSqLiteHelper;

/**
 * Created by user on 2/9/2017.
 * This class will handle the connectivity between the views
 * and model such what input in the edit text will be sent to the
 * table
 */

public class AddressDetailActivity extends Activity {
    private Spinner mAnnotate, mCity;
    private EditText mFname,
            mLname,
            mAddress,
            mCountry,
            mPostalCode;
    private Button mSubmit;
    private Uri addressUri;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.insert_contact);

        //get the reference to resources
        mAnnotate = (Spinner) findViewById(R.id.annotations);
        mFname = (EditText) findViewById(R.id.firstname);
        mLname = (EditText) findViewById(R.id.lastname);
        mAddress = (EditText) findViewById(R.id.address);
        mCity = (Spinner) findViewById(R.id.city);
        mCountry = (EditText) findViewById(R.id.autocomplete_country);
        mPostalCode = (EditText) findViewById(R.id.postalcode);
        mSubmit = (Button) findViewById(R.id.submit_button);

        Bundle extras = getIntent().getExtras();

        // Check from the saved Instance
        addressUri = (bundle == null) ? null : (Uri) bundle.getParcelable(DBContenProvider.CONTENT_ITEM_TYPE);

        // Or passed from the other activity
        if (extras != null) {
          addressUri = extras.getParcelable(DBContenProvider.CONTENT_ITEM_TYPE);
            fillList(addressUri);
       }

        mSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(mFname.getText().toString())) {
                    makeToast();
                } else {
                    setResult(RESULT_OK);
                    finish();
                }
            }

        });


    }
    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    protected void onSavedInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putParcelable(DBContenProvider.CONTENT_ITEM_TYPE, addressUri);
    }

    private void fillList(Uri uri) {
       // DBConnHandler db = new DBConnHandler();

        String[] projection = {DBConnHandler.COLUMN_ID,DBConnHandler.COLUMN_ANNOTATE,
                DBConnHandler.COLUMN_FNAME, DBConnHandler.COLUMN_LNAME,
                DBConnHandler.COLUMN_ADDRESS, DBConnHandler.COLUMN_CITY, DBConnHandler.COLUMN_COUNTRY, DBConnHandler.COLUMN_POSTALCODE };

        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            mFname.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBConnHandler.COLUMN_FNAME)));
            mLname.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBConnHandler.COLUMN_LNAME)));
        }
        cursor.close();
    }

    private void saveState() {
      //  DBConnHandler db = new DBConnHandler();

        // Only save if either summary or description
        // is available
        String fname = (String) mFname.getText().toString();

        if (fname.length() == 0) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(DBConnHandler.COLUMN_ID, 0);
        values.put(DBConnHandler.COLUMN_ANNOTATE, (String) mAnnotate.getSelectedItem().toString());
        values.put(DBConnHandler.COLUMN_FNAME,fname);
        values.put(DBConnHandler.COLUMN_LNAME, (String) mLname.getText().toString());
        values.put(DBConnHandler.COLUMN_ADDRESS, mAddress.getText().toString());
        values.put(DBConnHandler.COLUMN_CITY, mCity.toString());
        values.put(DBConnHandler.COLUMN_COUNTRY, mCountry.getText().toString());
        values.put(DBConnHandler.COLUMN_POSTALCODE,mPostalCode.getText().toString());


        if (addressUri == null) {
            // New
            addressUri = getContentResolver().insert(DBContenProvider.CONTENT_URI, values);
        } else {
            // Update
            getContentResolver().update(addressUri, values, null, null);
        }
    }

    private void makeToast() {
        Toast.makeText(AddressDetailActivity.this, "Please INPUT first name",Toast.LENGTH_LONG).show();
    }


}
