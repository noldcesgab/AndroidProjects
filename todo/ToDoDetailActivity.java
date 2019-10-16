package com.example.todo;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.todo.MyToDoContentProvider;
import com.example.todo.ToDoTableHandler;

/*
 * ToDoDetailActivity allows to enter a new ToDo item 
 * or to change an existing
 */
public class ToDoDetailActivity extends Activity {
  private Spinner mCategory;
  private EditText mTitleText;
  private EditText mBodyText;

  private Uri todoUri;

  @Override
  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    setContentView(R.layout.todo_edit);

    mCategory = (Spinner) findViewById(R.id.category);
    mTitleText = (EditText) findViewById(R.id.todo_edit_summary);
    mBodyText = (EditText) findViewById(R.id.todo_edit_description);
    Button confirmButton = (Button) findViewById(R.id.todo_edit_button);

    Bundle extras = getIntent().getExtras();

    // Check from the saved Instance
    todoUri = (bundle == null) ? null : (Uri) bundle.getParcelable(MyToDoContentProvider.CONTENT_ITEM_TYPE);

    // Or passed from the other activity
    if (extras != null) {
      todoUri = extras.getParcelable(MyToDoContentProvider.CONTENT_ITEM_TYPE);
      fillData(todoUri);
    }

    confirmButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        if (TextUtils.isEmpty(mTitleText.getText().toString())) {
          makeToast();
        } else {
          setResult(RESULT_OK);
          finish();
        }
      }

    });
  }

  private void fillData(Uri uri) {
    String[] projection = { ToDoTableHandler.COLUMN_SUMMARY, ToDoTableHandler.COLUMN_DESCRIPTION, ToDoTableHandler.COLUMN_CATEGORY };
    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
    if (cursor != null) {
      cursor.moveToFirst();
      String category = cursor.getString(cursor.getColumnIndexOrThrow(ToDoTableHandler.COLUMN_CATEGORY));

      for (int i = 0; i < mCategory.getCount(); i++) {

        String s = (String) mCategory.getItemAtPosition(i);
        if (s.equalsIgnoreCase(category)) {
          mCategory.setSelection(i);
        }
      }

      mTitleText.setText(cursor.getString(cursor.getColumnIndexOrThrow(ToDoTableHandler.COLUMN_SUMMARY)));
      mBodyText.setText(cursor.getString(cursor.getColumnIndexOrThrow(ToDoTableHandler.COLUMN_DESCRIPTION)));

      // Always close the cursor
      cursor.close();
    }
  }

  protected void onSavedInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    saveState();
    outState.putParcelable(MyToDoContentProvider.CONTENT_ITEM_TYPE, todoUri);
  }

  @Override
  protected void onPause() {
    super.onPause();
    saveState();
  }

  private void saveState() {
    String category = (String) mCategory.getSelectedItem();
    String summary = mTitleText.getText().toString();
    String description = mBodyText.getText().toString();

    // Only save if either summary or description
    // is available

    if (description.length() == 0 && summary.length() == 0) {
      return;
    }

    ContentValues values = new ContentValues();
    values.put(ToDoTableHandler.COLUMN_CATEGORY, category);
    values.put(ToDoTableHandler.COLUMN_SUMMARY, summary);
    values.put(ToDoTableHandler.COLUMN_DESCRIPTION, description);

    if (todoUri == null) {
      // New ToDo
      todoUri = getContentResolver().insert(MyToDoContentProvider.CONTENT_URI, values);
    } else {
      // Update ToDo
      getContentResolver().update(todoUri, values, null, null);
    }
  }

  private void makeToast() {
    Toast.makeText(ToDoDetailActivity.this, "Please maintain a summary",Toast.LENGTH_LONG).show();
  }
} 