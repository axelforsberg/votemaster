package se.splish.votemaster;

import java.util.List;

import se.splish.votemaster.helper.DatabaseHelper;
import se.splish.votemaster.model.Candidate;
import se.splish.votemaster.model.Result;
import se.splish.votemaster.model.Vote;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Toast;

public class CreateActivity extends Activity implements OnClickListener, OnValueChangeListener {
	EditText name, description;
	NumberPicker nbrOfVotes, nbrOfCandidates;
	Button create;
	private ListView myList;
	private CandidateAdapter myAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);

		name = (EditText) findViewById(R.id.et_name);
		description = (EditText) findViewById(R.id.et_description);
		create = (Button) findViewById(R.id.btn_create_vote);
		create.setOnClickListener(this);

		nbrOfCandidates = (NumberPicker) findViewById(R.id.numberPicker1);
		nbrOfCandidates.setMaxValue(100);
		nbrOfCandidates.setMinValue(1);
		nbrOfCandidates.setValue(2);
		nbrOfCandidates.setOnValueChangedListener(this);

		nbrOfVotes = (NumberPicker) findViewById(R.id.numberPicker2);
		nbrOfVotes.setMaxValue(100);
		nbrOfVotes.setMinValue(1);
		nbrOfVotes.setValue(1);
		nbrOfVotes.setOnValueChangedListener(this);

		myList = (ListView) findViewById(R.id.listView1);
		myList.setItemsCanFocus(true);
		myAdapter = new CandidateAdapter(this, 2);
		myList.setAdapter(myAdapter);

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_create_vote:
			createVote();
			break;
		}
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		switch (picker.getId()) {
		case R.id.numberPicker1:
			myAdapter.setCount(newVal);
			break;
		case R.id.numberPicker2:
			break;
		}
	}

	private void createVote() {
		if (name.getText().toString().length() < 1) {
			Toast.makeText(this, "Ange ett namn på omröstningen", Toast.LENGTH_SHORT).show();
			return;
		}

		if (!myAdapter.isNamesPresent()) {
			Toast.makeText(this,
					"Ange ett namn på alla kandidater eller minska antalet kandidater",
					Toast.LENGTH_SHORT).show();
			return;
		}

		DatabaseHelper dbh = new DatabaseHelper(this);
		Vote vote = new Vote(name.getText().toString(), description.getText().toString(),
				nbrOfVotes.getValue());
		int vid = dbh.createVote(vote);
		List<Candidate> candidates = myAdapter.getCandidates();
		for (Candidate c : candidates) {
			int cid = dbh.createCandidate(c);
			dbh.createResult(new Result(vid, cid, 0));
		}

		// List<Vote> votes = dbh.getAllVotes();
		// for(Vote v: votes){
		// Log.d("votename", v.getName());
		// Log.d("id ", v.getId()+ "");
		// }
		//
		// List<Candidate> cands = dbh.getAllCandidates();
		// for(Candidate c: cands){
		// Log.d("candnamn", c.getName());
		// Log.d("id ", c.getId()+ "");
		// }
		//
		// List<Result> resultlist = dbh.getResultFromVote(vid);
		// for(Result r: resultlist){
		// Log.d("res", r.getVid() + " " + r.getCid() + " " + r.getVotes());
		// }

		startActivity(new Intent(this, VotesActivity.class));
	}
}
