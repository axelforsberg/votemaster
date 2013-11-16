package se.splish.votemaster;

import java.util.ArrayList;
import java.util.List;

import se.splish.votemaster.helper.DatabaseHelper;
import se.splish.votemaster.model.Result;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class VotingActivity extends Activity {
	DatabaseHelper dbh;
	int vid = 2;
	int nbrOfVotes;

	ArrayList<Integer> selectedPositions = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.votegrid);
		dbh = new DatabaseHelper(this);

		Intent in = getIntent();
		Bundle b = in.getExtras();
		if (b != null) {
			nbrOfVotes = (Integer) b.get("nov");
			vid = (Integer) b.get("vid");
		}

		List<Result> results = dbh.getResultFromVote(vid);

		ArrayList<String> names = new ArrayList<String>();
		for (Result r : results) {
			names.add(dbh.getCandidate(r.getCid()).getName());
		}

		final GridView gridview = (GridView) findViewById(R.id.grid);
		gridview.setAdapter(new VotingAdapter(this, names));

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				if (selectedPositions.contains(position)) {
					selectedPositions.remove(selectedPositions.indexOf(position));
					gridview.getChildAt(position).setBackgroundColor(Color.LTGRAY);
				} else {
					if (selectedPositions.size() >= nbrOfVotes) {
						Toast.makeText(getBaseContext(), "Du har valt max antal kandidater.",
								Toast.LENGTH_SHORT).show();
					} else {
						selectedPositions.add(position);
						gridview.getChildAt(position).setBackgroundColor(Color.GREEN);
					}
				}
			}
		});
	}
}
