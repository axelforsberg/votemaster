package se.splish.votemaster;

import java.util.ArrayList;
import java.util.List;

import se.splish.votemaster.helper.DatabaseHelper;
import se.splish.votemaster.model.Candidate;
import se.splish.votemaster.model.Result;
import se.splish.votemaster.model.Vote;
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

public class VoteDetailsFragment extends Fragment implements OnClickListener {
	final static String ARG_POSITION = "position";
	int mCurrentPosition = -1;
	ListView resultList;
	View v;
	
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
		
		// Open vote button
		Button open = (Button) getActivity().findViewById(R.id.details_btn_open);
		open.setOnClickListener(this);
		
		// Show result button
		Button show = (Button) getActivity().findViewById(R.id.details_btn_result);
		show.setOnClickListener(this);

		// Result list
		resultList = (ListView) getActivity().findViewById( R.id.list_result); 
		
		List<String> results = new ArrayList<String>();
		for(Result r: res) {
			results.add(r.getVotes() + "   " + getCandidate(r.getCid()));
		}
		
		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.row_result, results);  
		resultList.setAdapter(listAdapter);
		
		resultList.setVisibility(View.INVISIBLE);
		
		mCurrentPosition = pos;
	}
	
	private String getCandidate(int cid){
		DatabaseHelper dbh = new DatabaseHelper(this.getActivity());
		Candidate c = dbh.getCandidate(cid);
		return c.getName();
	}

	private List<Vote> getVotes() {
		DatabaseHelper dbh = new DatabaseHelper(this.getActivity());
		List<Vote> votes = dbh.getAllVotes();
		return votes;
	}
	
	private List<Result> getResult(int vid){
		DatabaseHelper dbh = new DatabaseHelper(this.getActivity());
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
				Intent in = new Intent(getActivity(),VotingActivity.class);
				in.putExtra("vid", getVotes().get(mCurrentPosition).getId());
				in.putExtra("nov", getVotes().get(mCurrentPosition).getNbrOfVotes());
				startActivity(in);
				break;
			case R.id.details_btn_result:
				resultList.setVisibility(View.VISIBLE);
				break;
		}
	}
}