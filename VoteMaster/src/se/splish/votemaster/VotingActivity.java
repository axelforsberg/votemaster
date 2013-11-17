package se.splish.votemaster;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import se.splish.votemaster.helper.DatabaseHelper;
import se.splish.votemaster.model.Result;
import se.splish.votemaster.model.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class VotingActivity extends Activity {
	DatabaseHelper dbh;
	int vid;
	int nbrOfVotes;
	GridView gridview = null;

	ArrayList<Integer> selectedPositions = new ArrayList<Integer>();
	List<Result> results = null;

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

		results = dbh.getResultFromVote(vid);

		ArrayList<String> names = new ArrayList<String>();
		for (Result r : results) {
			names.add(dbh.getCandidate(r.getCid()).getName());
		}

		Button vote = (Button) findViewById(R.id.voting_btn_vote);
		vote.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				updateResult();
				restoreTable();
				playSound();
				showDialog();
			}
		});

		gridview = (GridView) findViewById(R.id.grid);
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

	private void updateResult() {
		dbh.incrementVote(vid);
		Log.d("incrementCall", vid + "");
		for (int i : selectedPositions) {
			dbh.incrementResult(vid, results.get(i).getCid());
		}
	}

	private void playSound() {
		if (getSettings().getSound()) {
			MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.floop);
			mp.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					mp.release();
				}

			});
			mp.start();
		}
	}

	private void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Tack för din röst!");
		builder.setMessage("Nästa person kan rösta om " + getSettings().getWaitTime() + " sekunder");
		builder.setCancelable(false);

		final AlertDialog dlg = builder.create();

		dlg.show();

		final Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				dlg.dismiss();
				t.cancel(); 
			}
		}, Integer.parseInt(getSettings().getWaitTime())*1000); 
	}

	private void restoreTable() {
		for (int i : selectedPositions) {
			gridview.getChildAt(i).setBackgroundColor(Color.LTGRAY);
		}
		selectedPositions.clear();

	}

	public Settings getSettings() {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		return new Settings(sharedPrefs.getBoolean("prefSoundOn", true), sharedPrefs.getString(
				"prefWaitTime", "4"));
	}
}
