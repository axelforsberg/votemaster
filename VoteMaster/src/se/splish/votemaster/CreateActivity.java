package se.splish.votemaster;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CreateActivity extends Activity implements OnClickListener {
	EditText name, description, nbrOfCandidates, nbrOfVotes;
	Button create;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
		
		name = (EditText) findViewById(R.id.et_name);
		description = (EditText) findViewById(R.id.et_description);
		nbrOfCandidates = (EditText) findViewById(R.id.et_nbrOfCandidates);
		nbrOfVotes = (EditText) findViewById(R.id.et_nbrOfVotes);
		
		create = (Button) findViewById(R.id.btn_create_vote);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_create_vote:
				break;
		}
	}
}
