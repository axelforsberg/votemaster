package se.splish.votemaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends Activity implements OnClickListener {
	Button result, create, actives, settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		create = (Button) findViewById(R.id.btn_create);
		create.setOnClickListener(this);
		
		actives = (Button) findViewById(R.id.btn_actives);
		actives.setOnClickListener(this);
		
		result = (Button) findViewById(R.id.btn_result);
		result.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_create:
			startActivity(new Intent(this, CreateActivity.class));
			break;
		case R.id.btn_actives:
			startActivity(new Intent(this, VotesActivity.class));
			break;
		case R.id.btn_result:
			startActivity(new Intent(this, ResultActivity.class));
			break;
		case R.id.btn_settings:
			break;
		default:
			break;
		}
	}
}
