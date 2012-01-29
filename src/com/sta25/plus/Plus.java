package com.sta25.plus;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Plus extends Activity implements OnClickListener {

	private DatabaseHelper helper;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // すべてのボタンにクリックリスナーをセット
       View btn_gimu = findViewById(R.id.btn_gimu);
       btn_gimu.setOnClickListener(this);
       
       View btn_pool = findViewById(R.id.btn_pool);
       btn_pool.setOnClickListener(this);
       
       // データベース処理関連
		helper = new DatabaseHelper(this);
		final SQLiteDatabase db = helper.getReadableDatabase();

		Cursor c = db.query("tb_action", new String[] {"action_date", "action_name"}, null, null, null, null, null);
		c.moveToFirst();
		
		int current_month_cnt = 0;
		int current_year_cnt = 0;
				
		CharSequence[] list = new CharSequence[c.getCount()];
		for (int i = 0; i < list.length; i++) {
			list[i] = c.getString(0) + " " + c.getString(1);
/*
			if (isCurrentMonth((String)list[i])){
				current_month_cnt++;
			}
			if (isCurrentYear((String)list[i])){
				current_year_cnt++;
			}
*/
			c.moveToNext();
		}
		c.close();

		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, list);
		ListView listView = (ListView)findViewById(R.id.ListView01);
		listView.setAdapter(adapter);

/*
		TextView textView;
		textView = (TextView)findViewById(R.id.current_month_plus_cnt);
		textView.setText(current_month_cnt);

		textView = (TextView)findViewById(R.id.current_year_plus_cnt);
		textView.setText(current_year_cnt);
*/
/*		
		Spinner spinner = (Spinner)findViewById(R.id.Spinner01);
		spinner.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, list));
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String prefecture = ((Spinner)parent).getSelectedItem().toString();
				Cursor c;
				
				c = db.query("capitals", new String[] {"capital"}, "prefecture='" + prefecture + "'", null, null, null, null);
				c.moveToFirst();
				String capital = c.getString(0);
				c.close();
				
				TextView textView = (TextView)findViewById(R.id.TextView01);
				textView.setText(capital);
				
				c = db.query("local_dishes", new String[] {"local_dish"}, "prefecture='" + prefecture + "'", null, null, null, null);
				c.moveToFirst();
				CharSequence[] list = new CharSequence[c.getCount()];
				for (int i = 0; i < list.length; i++) {
					list[i] = c.getString(0);
					c.moveToNext();
				}
				c.close();
				ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(Main.this, android.R.layout.simple_list_item_1, list);
				ListView listView = (ListView)findViewById(R.id.ListView01);
				listView.setAdapter(adapter);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
			
		});

  */     
        
    
    }

	private boolean isCurrentMonth(String date) {
		// TODO Auto-generated method stub
		return true;
	}

	private boolean isCurrentYear(String date) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.btn_gimu:
			openConfirmDialog4Gimu();
			break;
		case R.id.btn_pool:
			openConfirmDialog4Pool();
			break;
		}
	}

	private void openConfirmDialog4Gimu() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
		.setTitle(R.string.plusConfirm4Gimu_title)
		.setItems(R.array.plusConfirm4Gimu_item, new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialoginterface, int i) {
				execPlus(i);
			}
		}).show();
	}

	private void openConfirmDialog4Pool() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
		.setTitle(R.string.plusConfirm4Pool_title)
		.setItems(R.array.plusConfirm4Pool_item, new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialoginterface, int i) {
				execPlus(i);
			}
		}).show();
	}

	private void execPlus(int i) {
		// TODO Auto-generated method stub
		
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		helper.close();
	}
	
	class DatabaseHelper extends SQLiteOpenHelper {
		private String[][] INIT_ACTION = {
			{"20120108", "プール++"},
			{"20120115", "プール++"},
			{"20120116", "プール++"},
			{"20120117", "プール++"},
			{"20120118", "プール++"},
			{"20120123", "プール++"},
			{"20120125", "プール++"},
		};
		
		public DatabaseHelper(Context context) {
			super(context, "plusplus", null, 1);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.beginTransaction();
			try {
				SQLiteStatement stmt;
				db.execSQL("create table tb_action (action_date text primary key, action_name text not null);");

				stmt = db.compileStatement("insert into tb_action values (?, ?);");
				
				for (String[] action : INIT_ACTION) {
					stmt.bindString(1, action[0]);
					stmt.bindString(2, action[1]);
					stmt.executeInsert();
				}
				db.setTransactionSuccessful();
			} finally {
				db.endTransaction();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}
}