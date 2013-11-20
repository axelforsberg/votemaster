package se.splish.votemaster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.splish.votemaster.helper.AlphanumComparator;
import se.splish.votemaster.helper.DatabaseHelper;
import se.splish.votemaster.model.Candidate;
import se.splish.votemaster.model.Result;
import se.splish.votemaster.model.Vote;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class VoteDetailsFragment extends Fragment implements OnClickListener {
	OnVoteDeletedListener mCallback;
	DatabaseHelper dbh;
	final static String ARG_POSITION = "position";
	int mCurrentPosition = -1;
	ListView resultList;
	Boolean resultsIsVisible = false;
	Button show, remove;
	View v;

	public interface OnVoteDeletedListener {
		public void onVoteDeleted();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// If activity recreated (such as from screen rotate), restore
		// the previous article selection set by onSaveInstanceState().
		// This is primarily necessary when in the two-pane layout.
		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
		}

		// Inflate the layout for this fragment
		v = inflater.inflate(R.layout.details_view, container, false);
		return v;
	}

	@Override
	public void onStart() {
		super.onStart();
		Bundle args = getArguments();
		if (args != null) {
			updateDetailsView(args.getInt(ARG_POSITION));
		} else if (mCurrentPosition != -1) {
			updateDetailsView(mCurrentPosition);
		} else {
			((VotesActivity) getActivity()).hideUI();
		}
		dbh = new DatabaseHelper(this.getActivity());
	}

	public void updateDetailsView(int pos) {
		((VotesActivity) getActivity()).showUI();

		List<Vote> v = getVotes();
		List<Result> res = getResult(v.get(pos).getId());

		// Vote name
		TextView name = (TextView) getActivity().findViewById(R.id.details_name);
		name.setText(v.get(pos).getName());

		// Vote description
		TextView description = (TextView) getActivity().findViewById(R.id.details_description);
		description.setText(v.get(pos).getDescription());

		// Vote description
		TextView tnov = (TextView) getActivity().findViewById(R.id.tnov);
		tnov.setText("Totala antalet röstande: " + v.get(pos).getTotalVotes());

		// Open vote button
		Button open = (Button) getActivity().findViewById(R.id.details_btn_open);
		open.setOnClickListener(this);

		// Show result button
		show = (Button) getActivity().findViewById(R.id.details_btn_result);
		show.setOnClickListener(this);
		show.setText("Visa resultat");
		resultsIsVisible = false;

		// Remove vote button
		remove = (Button) getActivity().findViewById(R.id.details_btn_delete);
		remove.setOnClickListener(this);

		// Result list
		resultList = (ListView) getActivity().findViewById(R.id.list_result);

		List<String> results = new ArrayList<String>();
		
		int totalVotes = 0;
		for (Result r : res) {
			totalVotes += r.getVotes();
		}
		
		for (Result r : res) {
			if (totalVotes > 0) {
				double percent = ((double)r.getVotes() / (double)totalVotes)*100;
				percent = (double)Math.round(percent * 100) / 100;
				results.add(percent + "% \t" + r.getVotes() + " röster: \t" + getCandidate(r.getCid()));
			} else {
				results.add(r.getVotes() + " röster  -  " + getCandidate(r.getCid()));
			}
		}
		
		Collections.sort(results,new AlphanumComparator());
		Collections.reverse(results);

		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(),
				R.layout.row_result, results);
		resultList.setAdapter(listAdapter);

		resultList.setVisibility(View.INVISIBLE);

		mCurrentPosition = pos;
	}

	private void removeVote(int vid) {
		List<Result> results = dbh.getResultFromVote(vid);
		for (Result r : results) {
			dbh.removeCandidate(r.getCid());
		}
		dbh.removeResult(vid);
		dbh.removeVote(vid);
		mCallback.onVoteDeleted();
	}

	private String getCandidate(int cid) {
		Candidate c = dbh.getCandidate(cid);
		return c.getName();
	}

	private List<Vote> getVotes() {
		List<Vote> votes = dbh.getAllVotes();
		return votes;
	}

	private List<Result> getResult(int vid) {
		List<Result> results = dbh.getResultFromVote(vid);
		return results;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// Save the current article selection in case we need to recreate the
		// fragment
		outState.putInt(ARG_POSITION, mCurrentPosition);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.details_btn_open:
			Intent in = new Intent(getActivity(), VotingActivity.class);
			in.putExtra("vid", getVotes().get(mCurrentPosition).getId());
			in.putExtra("nov", getVotes().get(mCurrentPosition).getNbrOfVotes());
			startActivity(in);
			break;
		case R.id.details_btn_result:
			if (resultsIsVisible) {
				resultsIsVisible = false;
				resultList.setVisibility(View.INVISIBLE);
				show.setText("Visa resultat");
			} else {
				resultsIsVisible = true;
				resultList.setVisibility(View.VISIBLE);
				show.setText("Göm resultat");
			}
			break;
		case R.id.details_btn_delete:
			alertMessage();
			break;

		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception.
		try {
			mCallback = (OnVoteDeletedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnVoteDeletedListener");
		}
	}

	public void alertMessage() {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:

					removeVote(getVotes().get(mCurrentPosition).getId());
					Toast.makeText(getActivity(), "Omröstningen raderad", Toast.LENGTH_LONG).show();
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		};
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("Är du säker på att du vill radera omröstningen?")
				.setPositiveButton("Ja", dialogClickListener)
				.setNegativeButton("Nej", dialogClickListener).show();
	}
}
