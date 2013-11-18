package se.splish.votemaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends Activity implements OnClickListener {
	Button create, actives, settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		create = (Button) findViewById(R.id.btn_create);
		create.setOnClickListener(this);
		
		actives = (Button) findViewById(R.id.btn_actives);
		actives.setOnClickListener(this);
		
		settings = (Button) findViewById(R.id.btn_settings);
		settings.setOnClickListener(this);
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
		case R.id.btn_settings:
			startActivity(new Intent(this,UserSettingActivity.class));
			break;
		default:
			break;
		}
	}
}
