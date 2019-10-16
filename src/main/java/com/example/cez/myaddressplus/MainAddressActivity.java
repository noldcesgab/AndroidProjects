package com.example.cez.myaddressplus;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.AlertDialog;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.cez.myaddressplus.DBContenProvider;
import com.example.cez.myaddressplus.DBConnHandler;

import static com.example.cez.myaddressplus.R.styleable.AlertDialog;


public class MainAddressActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT = 1;
    private static final int DELETE_ID = Menu.FIRST + 1;
    public static String TAG;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //call the layout to view
        setContentView(R.layout.activity_main);
        //set the height of the column

        Log.d(TAG,"Student name: Cecile Seloterio - 147147169"  );

        this.getListView().setDividerHeight(2);
        //call a method to fill the list
         fillList();
        // Specify that your listview has a context menu attached
        //floating menu that appears when the user performs a long-click on an element
        registerForContextMenu(getListView());

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
            case R.id.insert_action:
                createContacts();
                break;
            case R.id.about_action:

                AlertDialog alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(MainAddressActivity.this , android.R.style.Theme_Dialog)).create(); //Read Update
                alertDialog.setTitle("About..");

                alertDialog.setMessage("My is a nice and simple Android \nApplication that allows user to query/insert/" +
                        "update/delete his \nhome address. It is written for API 11 or newer. It supports Tables. ");

                alertDialog.show();  //<-- See This!
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Uri uri = Uri.parse(DBContenProvider.CONTENT_URI + "/" + info.id);
                getContentResolver().delete(uri, null, null);
                fillList();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
    }

    private void fillList() {

        //Retrieve the array of First name in the Database of COLUMN_FNAME
        String[] from = new String[] { DBConnHandler.COLUMN_FNAME };

        //resource where to map the cursor content
        int[] to = new int[] { R.id.label };

        //initialize the list view
        getLoaderManager().initLoader(0, null, this);

        // adapter to map columns from a cursor to TextViews or ImageViews of xml file
        adapter = new SimpleCursorAdapter(this, R.layout.address_row, null, from, to, 0);
        setListAdapter(adapter);
    }

    public void createContacts() {
        //Create new intent (action) for the Insert
        Intent i = new Intent (this, AddressDetailActivity.class);
        startActivityForResult(i,ACTIVITY_CREATE);
    }

    // Opens the second activity if an entry is clicked
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, AddressDetailActivity.class);
        Uri todoUri = Uri.parse(DBContenProvider.CONTENT_URI + "/" + id);
        i.putExtra(DBContenProvider.CONTENT_ITEM_TYPE, todoUri);

        // Activity returns an result if called with startActivityForResult
        startActivityForResult(i, ACTIVITY_EDIT);
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {DBConnHandler.COLUMN_ID,DBConnHandler.COLUMN_ANNOTATE,
                DBConnHandler.COLUMN_FNAME, DBConnHandler.COLUMN_LNAME,
                DBConnHandler.COLUMN_ADDRESS, DBConnHandler.COLUMN_CITY, DBConnHandler.COLUMN_COUNTRY, DBConnHandler.COLUMN_POSTALCODE };


        //instead of creating a variables to hold the data, cursor is created
        //CursorLoader(this.getActivity, CONTEN_URI, projection,selection,selectionArgs, sortOrder);
        CursorLoader cursorLoader = new CursorLoader(this, DBContenProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
