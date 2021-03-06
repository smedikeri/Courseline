package deccan.courseline;

import local.DBUtil;
import entities.Course;
import entities.Submission;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NotesActivity extends Activity {

	DBUtil mdb;
	Cursor mCursor;
	String userID;
	String courseID;
	Submission subm = null;
	Course course = null;
	Button b1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Update Note");
		getActionBar().setIcon(R.drawable.tbar_icon);
		setContentView(R.layout.deccan_courseline_activity_notes);
		
		mdb=new DBUtil(this);

		userID = getIntent().getStringExtra("userID");
		Log.d("NOTES", "User ID: " + userID);
		courseID = getIntent().getStringExtra("courseID");
		Log.d("NOTES", "Course ID: " + courseID);
		subm = (Submission) getIntent().getSerializableExtra("subm");
		Log.d("NOTES", "Sub ID: " + subm.getSubId());

		EditText edt = (EditText) findViewById(R.id.editNotes);
		
		mCursor = mdb.selectSub(userID, courseID, subm.getSubId());
		Log.d("NOTES", "no of records "+mCursor.getCount());	
		
		

		mCursor = mdb.selectSub(userID, courseID, subm.getSubId());
		if (mCursor.getCount() > 0) {
			mCursor.moveToFirst();
			edt.setText(mCursor.getString(8));
		}

		b1 = (Button) findViewById(R.id.Editbutton);
		//TableRow.LayoutParams params = new TableRow.LayoutParams();
		//params = new TableRow.LayoutParams();
		//params.weight = 0.5f;
		//b1.setLayoutParams(params);
        b1.setTextSize(25);
        //b1.setWidth(0);
        b1.setTypeface(null, Typeface.BOLD);
        b1.setTextColor(Color.WHITE);
        b1.setBackground(getResources().getDrawable((R.drawable.blue_menu_btn)));
		//b1.setText("Update Note");
		
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				EditText edt = (EditText) findViewById(R.id.editNotes);
				String notes = edt.getText().toString();
				Log.d("NOTES", notes);
				mCursor = mdb.selectSub(userID, courseID, subm.getSubId());
				if (mCursor.getCount() > 0) {
					Log.d("NOTES", "Updating already existing sub ");
					mCursor.moveToFirst();
					mdb.updateSub(userID, courseID, subm.getSubId(),
							mCursor.getBlob(3), mCursor.getBlob(4),
							mCursor.getBlob(5), mCursor.getBlob(6),
							mCursor.getBlob(7), notes);
				} else {
					Log.d("NOTES", "Inserting a new sub record ");
					mdb.insertSub(userID, courseID, subm.getSubId(), null,
							null, null, null, null, notes);
				}
				Toast toast = Toast.makeText(getBaseContext(),
						"Your new note for this submission has been saved",
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			
		});
		mCursor = mdb.selectSub(userID, courseID, subm.getSubId());
		Log.d("NOTES", "no of records "+mCursor.getCount());	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notes, menu);
		return true;
	}

}
